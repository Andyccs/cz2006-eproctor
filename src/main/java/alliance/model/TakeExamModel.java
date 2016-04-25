package alliance.model;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import alliance.dbaccess.model.BookExam;
import alliance.entity.LoginSession;
import alliance.model.interfaces.TakeExamModelInterface;

public class TakeExamModel extends Observable implements TakeExamModelInterface {
	private LoginSession loginSession;
	
	private List<BookExam> courses;
	
	private Hashtable<String, BookExam> coursesHashtable;
	
	private BookExam selectedCourses;
	
	private boolean proceedToRule;

	/**
	 * Retrieve the current login session
	 * @return LoginSession			Current login session
	 */
	@Override
	public LoginSession getLoginSession() {
		return loginSession;
	}

	/**
	 * Set a new login session
	 * @param session				New login session
	 */
	@Override
	public void setLoginSession(LoginSession session) {
		this.loginSession = session;
	}

	/**
	 * Get a list of exam and course details
	 * @return List<BookExam>		Current exam and course details
	 */
	@Override
	public List<BookExam> getCourse() {
		return courses;
	}

	/**
	 * Set the exam and course details
	 * @param course				New exam and course details
	 */
	@Override
	public void setCourse(List<BookExam> courses) {
		this.courses = courses;
		coursesHashtable = new Hashtable<>();
		for (Iterator iterator = courses.iterator(); iterator.hasNext();) {
			BookExam bookExam = (BookExam) iterator.next();
			coursesHashtable.put(bookExam.getCourseCode(), bookExam);
		}
		
		setChanged();
	}

	/**
	 * Get the exam details of the selected course
	 * @return BookExam				Details of selected course
	 */
	public BookExam getSelectedCourses() {
		return selectedCourses;
	}

	/**
	 * Set the selected courses
	 * @param BookExam				New selected course
	 */
	public void setSelectedCourses(BookExam selectedCourses) {
		this.selectedCourses = selectedCourses;
		setChanged();
	}

	/**
	 * Select course based on course index
	 * @param course				Index of the course on hashtable
	 * @return BookExam				Course selected using argument
	 */
	@Override
	public BookExam getCourse(String course) {
		return coursesHashtable.get(course);
	}

	/**
	 * Set the value of proceed to rule boolean
	 * @param b						New proceed to rule value
	 */
	@Override
	public void setProceedToRule(boolean b) {
		proceedToRule = b;
	}

	/**
	 * Get current proceed to rule value
	 * @return boolean
	 */
	@Override
	public boolean isProceedToRule() {
		return proceedToRule;
	}
	
	
}
