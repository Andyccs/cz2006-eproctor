package alliance.controller;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import alliance.controller.interfaces.TakeExamControllerInterface;
import alliance.dBadapter.EProctorAdapter;
import alliance.dbaccess.EProctorDBController;
import alliance.dbaccess.interfaces.EProctorDBInterface;
import alliance.dbaccess.model.BookExam;
import alliance.model.interfaces.TakeExamModelInterface;

public class TakeExamController implements TakeExamControllerInterface {
	private TakeExamModelInterface model;
	
	private Logger logger = LoggerFactory.getLogger(TakeExamController.class);
	
	private static final String XML_PATH = "META-INF/spring/EProctorAdapter.xml";
	
	private ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	
	private EProctorAdapter db;
	
	public TakeExamController(TakeExamModelInterface model) {
		this.model = model;
		db = context.getBean(EProctorAdapter.class);
	}

	@Override
	public void initialize() {
		List<BookExam> bookExam = db.getBookingSchedule(model.getLoginSession().getAccount().getUsername());
		
		model.setSelectedCourses(null);
		model.setProceedToRule(false);
		model.setCourse(bookExam);
		model.notifyObservers();
	}
/**
 * Initialise the exam UI for the student to start taking the exam.
 * @param courseCode	Course code of the exam taken
 */
	@Override
	public void takeExam(String courseCode) {

		BookExam exam = model.getCourse(courseCode);
		model.setSelectedCourses(exam);
		
		Date date = exam.getDate();
		Time startTime = exam.getStartTime();
		
		String startTimeString = startTime.toString();
		String[] temp = startTimeString.split(":");
		String hour = temp[0];
		String minute = temp[1];
		String second = temp[2];
		
		String dateString = date.toString();
		String[] temp2 = dateString.split("-");
		String year = temp2[0];
		String month = temp2[1];
		String day = temp2[2];
		
		Calendar examDateTime = 
				new GregorianCalendar(
						Integer.parseInt(year), 
						Integer.parseInt(month)-1, 
						Integer.parseInt(day), 
						Integer.parseInt(hour), 
						Integer.parseInt(minute), 
						Integer.parseInt(second));
		Locale locale = new Locale("SG");
		TimeZone tz = TimeZone.getDefault();
		tz.setID("GMT+08");
		
		examDateTime.setTimeZone(tz);

		Calendar current = Calendar.getInstance(tz, locale);		
		
		Calendar before10 = (Calendar) examDateTime.clone();
		before10.roll(Calendar.MINUTE, -10);
		before10.roll(Calendar.HOUR_OF_DAY,-1);
		
		Calendar after2 = (Calendar) examDateTime.clone();
		after2.roll(Calendar.HOUR_OF_DAY, exam.getDuration());
		
		SimpleDateFormat format = new SimpleDateFormat("YYY-MM-dd HH:mm:ss");
		System.out.println(format.format(current.getTime()));
		System.out.println(format.format(before10.getTime()));
		System.out.println(format.format(after2.getTime()));
		
		
		boolean greaterThanTenMinute = current.after(before10);
		boolean lessThanDuration = current.before(after2);
		if(greaterThanTenMinute && lessThanDuration){
			logger.debug("Go to rule and regulation screen");
			model.setProceedToRule(true);
			model.notifyObservers();
		}
		else{
			logger.debug("Cannot go to next page");
			model.setProceedToRule(false);
			model.notifyObservers();
		}
	}
		
	
}
