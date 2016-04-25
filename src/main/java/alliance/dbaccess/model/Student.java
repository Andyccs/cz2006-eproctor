package alliance.dbaccess.model;

import java.io.Serializable;

public class Student implements Serializable{
	private String name;
	private String studentID;
	private String university;
	private String email;
	
	/**
	 * Getter for name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * Getter for University
	 * @return
	 */
	public String getUniversity() {
		return university;
	}

	/**
	 * Setter for University
	 * @param university
	 */
	public void setUniversity(String university) {
		this.university = university;
	}

	/**
	 * Getter for email
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public Student (String name, String studentID, String uni, String email) {
		this.name = name;
		this.studentID = studentID;
		this.university = uni;
		this.email = email;
	}
	
}
