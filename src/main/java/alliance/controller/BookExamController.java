package alliance.controller;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import alliance.controller.interfaces.BookExamControlerInterface;
import alliance.dBadapter.EProctorAdapter;
import alliance.dbaccess.EProctorDBController;
import alliance.dbaccess.UniversityDBController;
import alliance.dbaccess.interfaces.UniversityDBInterface;
import alliance.dbaccess.model.BookExam;
import alliance.dbaccess.model.Exam;
import alliance.dbaccess.model.TakeCourse;
import alliance.display.BookingTable;
import alliance.model.interfaces.BookExamModelInterface;

public class BookExamController implements BookExamControlerInterface{
	
	private BookExamModelInterface model;
	
	private static final String XML_PATH = "META-INF/spring/EProctorAdapter.xml";
	
	private ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	
	private EProctorAdapter db;
	
	public BookExamController(BookExamModelInterface model) {
		this.model = model;
		db = context.getBean(EProctorAdapter.class);
	}
	
	@Override
	public void initialize() {
		List<TakeCourse> takeCourse;
		
		takeCourse = 
//				EProctorDBController.getInstance()
//				context.getBean(EProctorAdapter.class)
				db
				.getCourseToTake(model.getLoginSession()
						.getAccount().getUsername());
				
		List<BookingTable> bookExam = new ArrayList<BookingTable>();
		
		for (int i = 0; i < takeCourse.size(); i++) {
			String courseCode = takeCourse.get(i).getCourseCode();
			
			String courseName = takeCourse.get(i).getCourseName();
			
			Exam exam = db.getExamInfo(courseCode).get(0);
			
			String description = exam.getDescription();
			
			String duration = String.valueOf(exam.getDuration());
			
			String startDate = exam.getStartDate().toString();
			
			String endDate = exam.getEndDate().toString();
			
			String format = exam.getStyle();
			
			BookingTable temp = new BookingTable(courseCode, courseName, description, duration, format, startDate, endDate);
			
			bookExam.add(temp);
		}
		
		model.setBookSuccess(null);
		model.setCourseToTake(bookExam);
		model.notifyObservers("");
	}
	/**
	 * Returns a boolean value that determines if the input values are valid.
	 * If input is valid, the booking details can be submitted.
	 * @param courseCode  Course code picked from the drop-down list in UI
	 * @param date		  Date entered in the UI	
	 * @param timeSlot	  Time slot picked from the drop-down list in UI
	 * @return true/false
	 */
	/**
	 * Checks if input is valid
	 */
	@Override
	public boolean isInputValid(String courseCode, String date,
			String timeSlot) {
		model.setErrorMessage(null);
		//factory method applied
		if(courseCode == null||courseCode.equals("")|| db.getExamInfo(courseCode).size() == 0)
			model.setErrorMessage("No Course Code Selected\n");
		else if(date == null || date.compareTo("") == 0)
			model.setErrorMessage("No date is entered\n");
		else if(timeSlot == null||timeSlot.toString().equals(""))
			model.setErrorMessage("No Time Slot selected\n");
		else if(!timeSlot.matches("\\d{2}:\\d{2}:\\d{2}"))
			model.setErrorMessage("Time Slot selected is incorrect. Format of Time Slot is: HH:mm");
		else if((Integer.parseInt(timeSlot.substring(0, 2)) < 24 && Integer.parseInt(timeSlot.substring(0, 2)) >= 0 &&
			    Integer.parseInt(timeSlot.substring(3, 5)) < 60 && Integer.parseInt(timeSlot.substring(3, 5)) >= 0) == false)
			model.setErrorMessage("Time Slot selected is not in the correct 24hr format");
		else if(!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			model.setErrorMessage("Date entered is incorrect. Format of date is: YYYY-MM-DD\n");
		else if(db.getExamInfo(courseCode).get(0).getStartDate().compareTo(Date.valueOf(date)) > 0 || 
				db.getExamInfo(courseCode).get(0).getEndDate().compareTo(Date.valueOf(date)) < 0)
			model.setErrorMessage("Date entered should within the exam period\n");
		
		if(model.getErrorMessage()!=null){
			return false;
		}
		return true;
	}
	/**
	 * Add booking details to the book exam model, provided that the checking function 
	 * isInputValid returns true. 
	 * @param courseCode  Course code picked from the drop-down list in UI
	 * @param date		  Date entered in the UI	
	 * @param timeSlot	  Time slot picked from the drop-down list in UI
	 */
	/**
	 * Submit booking to DB
	 */
	@Override
	public void submitToDB(String courseCode, String date, String timeSlot) {
		// timeslot is the starting time which is passed as 08:00 / 12:00 /
		// 16:00
		// date is passed as YYYY-MM-DD
		if(!isInputValid(courseCode, date,timeSlot)){
			model.setBookSuccess(false);
			model.notifyObservers("");
			return;
		}
		
		try {
			Date examDate = Date.valueOf(date);

			Time startTime = Time.valueOf(timeSlot);
			
			BookingTable book = model.getCourseToTake(courseCode);
			
			Time endTime = getEndTime(startTime,book.getDuration());
			
			boolean status = 
//					EProctorDBController.getInstance()
//					context.getBean(EProctorAdapter.class)
					db
					.addBookingRecord(model.getLoginSession().getAccount().getUsername(), 
					courseCode, examDate, startTime, endTime);
			
			model.setBookSuccess(status);
			model.notifyObservers("");
		} catch (Exception e) {
			e.printStackTrace();
			model.setBookSuccess(false);
			model.notifyObservers("");
		}
	}

	/**
	 * Get the ending time of the exam.
	 * @param startTime
	 * @param duration
	 * @return Time
	 * @throws ParseException
	 */
	private Time getEndTime(Time startTime,String duration) throws ParseException{
		String convertEndTime = startTime.toString();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		java.util.Date d = new java.util.Date();

		d = df.parse(convertEndTime);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.HOUR, Integer.parseInt(duration));
		
		Time endTime = Time.valueOf(df.format(cal.getTime()));
		return endTime;
	}

}
