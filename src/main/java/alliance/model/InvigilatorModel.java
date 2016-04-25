package alliance.model;

import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.Observable;

import alliance.dbaccess.model.BookExam;
import alliance.dbaccess.model.Student;
import alliance.entity.LoginSession;
import alliance.model.interfaces.InvigilatorModelInterface;
import alliance.networking.server.Identity;

public class InvigilatorModel extends Observable implements InvigilatorModelInterface{
	private LoginSession session;
	
	private BufferedImage selfImage;
	
	/**
	 * Set the login session
	 * @param session		Login Session
	 */
	@Override
	public void setLoginSession(LoginSession session) {
		this.session = session;
		otherId = new Hashtable<>();
		otherImage = new Hashtable<>();
		studentHashtable = new Hashtable<>();
		bookExamHashtable = new Hashtable<>();
	}

	/**
	 * Get the login session
	 * @return LoginSession
	 */
	@Override
	public LoginSession getLoginSession() {
		return session;
	}

	/**
	 * Set the webcam view of own image
	 * @param image			Own image
	 */
	@Override
	public void setSelfImage(BufferedImage image) {
		this.selfImage = image;
		setChanged();
	}

	/**
	 * Get the image of own webcam view
	 * @return BufferedImage
	 */
	@Override
	public BufferedImage getSelfImage() {
		return selfImage;
	}

	private Hashtable<String, Identity> otherId;
	
	private Hashtable<String, BufferedImage> otherImage;
	
	/**
	 * Set the webcam view of other images
	 * @param id			Identifier for the other person
	 * @param image			Other person's image
	 */
	@Override
	public void setOtherImage(Identity id, BufferedImage image) {
		String stringId = id.getId();
		otherId.put(stringId, id);
		otherImage.put(stringId, image);
		setChanged();
	}

	/**
	 * Get the image of the other person
	 * @param id			Identifier for the other person
	 * @return BufferedImage
	 */
	@Override
	public BufferedImage getOtherImage(String id) {
		return otherImage.get(id);
	}

	/**
	 * Get an array of other people's ID
	 * @return String[]
	 */
	@Override
	public String[] getOtherId() {
		Object[] object = otherId.keySet().toArray();
		String[] temp = new String[object.length];
		for (int i = 0; i < object.length; i++) {
			temp[i] = (String) object[i];
		}
		return temp;
	}

	/**
	 * Get the identity of a person using the person's id
	 * @param id			Identifier for the person
	 * @return Identity
	 */
	@Override
	public Identity getOtherIdentity(String id) {
		return otherId.get(id);
	}

	private Hashtable<String, Student> studentHashtable;
	
	private Hashtable<String, BookExam> bookExamHashtable;
	
	/**
	 * Set a student object into the student hash table
	 * @param student		Student object to be inserted
	 */
	@Override
	public void setStudentDetails(Student student) {
		studentHashtable.put(student.getStudentID(), student);
	}

	/**
	 * Get a student object referred to by a student id
	 * @param studentId		Identifier for the student
	 * @return Student		The student object
	 */
	@Override
	public Student getStudentDetails(String studentId) {
		return studentHashtable.get(studentId);
	}

	/**
	 * Set an exam booked by a particular student into the book exam hash table
	 * @param exam			Exam booked
	 * @param studentId		Identifier for the student
	 */
	@Override
	public void setBookExam(BookExam exam, String studentId) {
		bookExamHashtable.put(studentId, exam);
	}

	/**
	 * Get the exam booked by a particular student
	 * @param studentId		Identifier for the student
	 * @return BookExam		Booked exam
	 */
	@Override
	public BookExam getBookExam(String studentId) {
		return bookExamHashtable.get(studentId);
	}

}
