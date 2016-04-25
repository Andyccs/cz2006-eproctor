package alliance.model;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import alliance.display.BookingTable;
import alliance.entity.LoginSession;
import alliance.model.interfaces.BookExamModelInterface;

public class BookExamModel extends Observable implements BookExamModelInterface{
	
	private LoginSession session;
	
	private List<BookingTable> courses;
	
	private Hashtable<String, BookingTable> coursesHash;
	
	private Boolean bookSuccess;
	
	private String errorMessage;
	
	/**
	 * Set the LoginSession
	 * @param ls		New LoginSession
	 */
	@Override
	public void setLoginSession(LoginSession ls) {
		session = ls;
	}

	/**
	 * Get the LoginSession
	 * @return LoginSession
	 */
	@Override
	public LoginSession getLoginSession() {
		return session;
	}

	/**
	 * Get a list of course to take
	 * @return List<BookingTable>
	 */
	@Override
	public List<BookingTable> getCourseToTake() {
		return courses;
	}

	/**
	 * Set the available courses the student can take into the booking table display.
	 * @param courses	List of courses available
	 */
	@Override
	public void setCourseToTake(List<BookingTable> courses) {
		this.courses = courses;
		coursesHash = new Hashtable<>();
		for (Iterator iterator = courses.iterator(); iterator.hasNext();) {
			BookingTable bookingTable = (BookingTable) iterator.next();
			coursesHash.put(bookingTable.getCourseCode(), bookingTable);
		}
		setChanged();
	}

	/**
	 * Set the value of bookSuccess
	 * @param b			New boolean value for bookSuccess
	 */
	@Override
	public void setBookSuccess(Boolean b) {
		bookSuccess = b;
		setChanged();
	}

	/**
	 * Return the value of bookSuccess
	 * @return Boolean
	 */
	@Override
	public Boolean isBookSuccess() {
		return bookSuccess;
	}

	/**
	 * Get a booking table containing courses to take
	 * @param courseCode		Course code to identify the target course
	 * @return BookingTable 	BookingTable containing courses
	 */
	@Override
	public BookingTable getCourseToTake(String courseCode) {
		return coursesHash.get(courseCode);
	}

	/**
	 * Set an error message
	 * @param mesage			Error message
	 */
	@Override
	public void setErrorMessage(String message) {
		errorMessage = message;
	}

	/**
	 * Get the error message
	 * @return String
	 */
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

}
