package alliance.dbaccess.model;

import java.io.Serializable;
import java.sql.Date;

public class Exam implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -667385409729851744L;
	private int examID;
	private String courseCode;
	private int duration;
	private String description;
	private String style;
	private String question;
	private Date startDate;
	private Date endDate;
	
	public Exam(int examID, String courseCode, int duration,
			String description, String style, String question,
			Date startDate, Date endDate) {
		this.examID = examID;
		this.courseCode = courseCode;
		this.duration = duration;
		this.description = description;
		this.style = style;
		this.question = question;
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}
	
	/**
	 * Getter for exam ID
	 * @return
	 */
	public int getExamID() {
		return examID;
	}
	
	/**
	 * Setter for exam ID
	 * @param examID
	 */
	public void setExamID(int examID) {
		this.examID = examID;
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
	 * Getter for course duration
	 * @return
	 */
	public int getDuration() {
		return duration;
	}
	
	/**
	 * Setter for course duration
	 * @param duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	/**
	 * Getter for course description
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Setter for course description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Getter for style
	 * @return
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
	 * Getter for questions
	 * @return
	 */
	public String getQuestion() {
		return question;
	}
	
	/**
	 * Setter for questions
	 * @param question
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	
	/**
	 * Getter for start date
	 * @return
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * Setter for start date
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Getter for end date
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * Setter for end date
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
