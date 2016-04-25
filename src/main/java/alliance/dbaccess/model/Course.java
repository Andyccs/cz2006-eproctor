package alliance.dbaccess.model;

import java.io.Serializable;

public class Course implements Serializable{
	private String courseCode;
	private String courseName;
	
	public Course(String courseCode, String courseName) {
		super();
		this.courseCode = courseCode;
		this.courseName = courseName;
	}
	/**
	 * Getter for course code
	 * @return	Course code
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
	 * @return	Course name
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

}
