package alliance.dbaccess.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class BookExam implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9141059057079655495L;
	private String examID;
	private String courseCode;
	private String courseName;
	private int duration;
	private String description;
	private String style;
	private Date date;
	private Time startTime;
	private Time endTime;

	public BookExam(String examID, String courseCode, String courseName, int duration,
			String description, String style, Date date, Time startTime,
			Time endTime) {
		super();
		this.examID = examID;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.duration = duration;
		this.description = description;
		this.style = style;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Getter for course code
	 * @return String		Course code
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
	 * Getter for course name
	 * @return String 		Course name
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Setter for course name
	 * @param courseName
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	/**
	 * Getter for duration
	 * @return int			Duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Setter for duration
	 * @param duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Getter for description
	 * @return String		Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter for description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter for style
	 * @return String		Style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * Setter for style
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * Getter for date
	 * @return Date			Date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Setter for date
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Getter for start time
	 * @return Time			Start time
	 */
	public Time getStartTime() {
		return startTime;
	}

	/**
	 * Setter for start time
	 * @param startTime
	 */
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	/**
	 * Getter for end time
	 * @return Time			End time
	 */
	public Time getEndTime() {
		return endTime;
	}

	/**
	 * Setter for end time
	 * @param endTime
	 */
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	/**
	 * Getter for exam ID
	 * @return String		Exam ID
	 */
	public String getExamID() {
		return examID;
	}

	/**
	 * Setter for exam ID
	 * @param examID
	 */
	public void setExamID(String examID) {
		this.examID = examID;
	}

}
