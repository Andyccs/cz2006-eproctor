package alliance.dbaccess.interfaces;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javafx.collections.ObservableList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import alliance.dbaccess.model.BookExam;
import alliance.dbaccess.model.Invigilator;
import alliance.dbaccess.model.TakeCourse;

public interface EProctorDBInterface {
	
	public static final String XML_PATH = "META-INF/spring/eproctor_database.xml";
	
	public static final ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	
	/**
	 * 
	 * @param domain
	 * @param acc
	 * @return Boolean
	 * @throws SQLException
	 */
	public boolean authenticate(String domain, String username, String password);
	
	/**
	 * 
	 * @param invigilatorID
	 * @return Invigilator
	 * @throws SQLException
	 */
	//public Invigilator getInvigilatorInfo(String invigilatorID);
	
	/**
	 * 
	 * @param studentID
	 * @param exam
	 * @return int noOfRetake
	 * @throws SQLException
	 */
	//public int getNoOfRetake(String studentID, Exam exam);
	
	/**
	 * 
	 * @param studentID
	 * @param exam
	 * @return boolean
	 * @throws SQLException
	 */
	//public boolean editBookingRecord(String studentID, Exam exam);
	
	/**
	 * 
	 * @param studentID
	 * @return
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public List<BookExam> getBookingSchedule(String studentID);
	
	/**
	 * 
	 * @param studentID
	 * @param courseCode
	 * @param examDate
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public boolean addBookingRecord(final String studentID, final String courseCode, final Date examDate, final Time startTime, final Time endTime);
	
	/**
	 * 
	 * @param studentID
	 * @return
	 */
	public List<TakeCourse> getCourseToTake(String studentID);
	
	/**
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public List<String> getAnnoucement();
	
	/**
	 * 
	 * @param annoucement
	 * @return
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public int addAnnoucement(final String annoucement);
	
	/**
	 * 
	 * @param annoucement
	 * @return
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public boolean addInvigilator(final String invigilatorID, final String name, final String address, final int postalCode, final int phoneNumber, final String password);
	
	/**
	 * 
	 * @param name
	 * @param address
	 * @param postalCode
	 * @param invigilatorID
	 * @param password
	 * @return
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public boolean editInvigilatorInfo(final String invigilatorID, final String password, final String name, final String address, final int postalCode, final int phoneNumber);
	
	/**
	 * 
	 * @param adminID
	 * @return
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public List<Invigilator> getAllInvigilator();
	
	/**
	 * 
	 * @param studentID
	 * @param examID
	 * @param proctorReport
	 * @return
	 */
	public boolean submitProctorReport(String studentID, String examID, String proctorReport);

	

	
	
}
