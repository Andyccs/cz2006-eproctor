package alliance.dbaccess.model;

import java.io.Serializable;

public class TakeCourse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2350607760571986471L;
	private String studentID;
	private String courseCode;
	private String courseName;
	private int noOfRetake;
	
	public TakeCourse(String studentID, String courseCode, String courseName,
			int noOfRetake) {
		super();
		this.studentID = studentID;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.noOfRetake = noOfRetake;
	}

	/**
	 * Getter for student ID
	 * @return
	 */
	public String getStudentID() {
		return studentID;
	}

	/**
	 * Setter for student ID
	 * @param studentID
	 */
	public void setStudentID(String studentID) {
		this.studentID = studentID;
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
	 * Getter for course name
	 * @return
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
	 * Get number of retakes left
	 * @return
	 */
	public int getNoOfRetake() {
		return noOfRetake;
	}

	/**
	 * Set number of retakes left
	 * @param noOfRetake
	 */
	public void setNoOfRetake(int noOfRetake) {
		this.noOfRetake = noOfRetake;
	}
	
}
