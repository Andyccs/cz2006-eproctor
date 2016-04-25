package alliance.display;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import alliance.dBadapter.EProctorAdapter;
import alliance.entity.LoginSession;

public class CourseTable implements TableDisplayInterface {
	
	private String courseCode;
	private String courseDescription;
	private String date;
	private String time;
	private String duration;
	private String format;
	
	private static EProctorAdapter proctorAdt;
	
	private final ObservableList<CourseTable> courseData = FXCollections.observableArrayList();
	
	public CourseTable() {
		if(proctorAdt==null){
			proctorAdt  = new ClassPathXmlApplicationContext("META-INF/spring/EProctorAdapter.xml").getBean(EProctorAdapter.class);
		}
	}
	
	public CourseTable(String courseCode, String time) {
		this.setCourseCode(courseCode);
		this.setTime(time);
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
	 * Getter for date
	 * @return
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Setter for date
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Getter for time
	 * @return
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Setter for time
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
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
	
	@Deprecated
	public ObservableList<CourseTable> getTableDisplay(LoginSession ls) {
		retrieveDatabase(ls);
		return courseData;
	}
	
	@Deprecated
	private void retrieveDatabase(LoginSession ls) {
		List<CourseTable> bookExam;
//		EProctorAdapter proctorAdt = EProctorAdapter.getInstance(ls);
    	proctorAdt.setLs(ls);
		
		bookExam = proctorAdt.getBookedExam();
		
		for (int i = 0; i < bookExam.size(); i++) {
//			System.out.println(bookExam.get(i).courseCode);
			courseData.add(bookExam.get(i));
		}
	}
}
