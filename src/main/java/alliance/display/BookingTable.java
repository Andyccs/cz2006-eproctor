package alliance.display;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import alliance.entity.LoginSession;

public class BookingTable implements TableDisplayInterface {
	
	private String courseCode;
	private String courseName;
	private String courseDescription;
	private String duration;
	private String format;
	private String startDate;
	private String endDate;
	
	
	private final ObservableList<BookingTable> bookingData = FXCollections.observableArrayList();
	
	public BookingTable() {
		//bookingData.add(new BookingTable("CZ2006 - Software Engineering", "Blah Blah Blah", "2hours", "MCQ", "25/4/2014", "25/5/2014"));
	}
	
	//To-Be Deleted after implememting database access
	public BookingTable(String courseCode, String courseName, String courseDescription, String duration, String format, String startDate, String endDate) {
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		this.duration = duration;
		this.format = format;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	/**
	 * Get an observable list of booking table
	 */
	public ObservableList<BookingTable> getTableDisplay(LoginSession ls) {
		retrieveDatabase(ls);
		return bookingData;	
	}
	
	@Deprecated
	private void retrieveDatabase(LoginSession ls) {
//		List<BookingTable> bookExam;
//		EProctorAdapter proctorAdt = new EProctorAdapter(ls);
//		bookExam = proctorAdt.getBookedExam();
//		
//		for (int i = 0; i < bookExam.size(); i++) {
//			System.out.println(bookExam.get(i).courseCode);
//			bookingData.add(bookExam.get(i));
//		}
	}

	/**
	 * Getter for course code
	 * @return
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * Setter for course code
	 * @param courseCode
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	/**
	 * Getter for course description
	 * @return
	 */
	public String getCourseDescription() {
		return courseDescription;
	}

	/**
	 * Setter for course description
	 * @param courseDescription
	 */
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	/**
	 * Getter for duration
	 * @return
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * Setter for duration
	 * @param duration
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * Getter for format
	 * @return
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Setter for format
	 * @param format
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * Getter for start date
	 * @return
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Setter for start date
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Getter for end date
	 * @return
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * Setter for end date
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
