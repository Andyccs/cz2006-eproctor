package alliance.dBadapter;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import alliance.dbaccess.EProctorDBController;
import alliance.dbaccess.UniversityDBController;
import alliance.dbaccess.interfaces.EProctorDBInterface;
import alliance.dbaccess.interfaces.UniversityDBInterface;
import alliance.dbaccess.model.BookExam;
import alliance.dbaccess.model.Course;
import alliance.dbaccess.model.Exam;
import alliance.dbaccess.model.Invigilator;
import alliance.dbaccess.model.Student;
import alliance.dbaccess.model.TakeCourse;
import alliance.display.CourseTable;
import alliance.entity.LoginSession;

public class EProctorAdapter implements EProctorDBInterface,UniversityDBInterface{

	private EProctorDBInterface eProctordb;
	private UniversityDBInterface uniDB;
	private LoginSession ls;

	private static EProctorAdapter instance;
	
	private EProctorAdapter(){
		
	}
	
	/**
	 * Instantiate a new EProctorAdapter
	 * @return		EProctorAdapter
	 */
	public static EProctorAdapter getInstance(){
		if(instance==null){
			instance = new EProctorAdapter();
		}
		return instance;
			
	}
	
	public EProctorAdapter(LoginSession ls) {
//		eProctordb = EProctorDBController.getInstance();
//		uniDB = UniversityDBController.getInstance();
		this.ls = ls;
	}

	/**
	 * Get an arraylist of booked exam information
	 * @return List<CourseTable>
	 */
	public List<CourseTable> getBookedExam() {
		
		List<CourseTable> bookedExam = new ArrayList<CourseTable>();
		List<BookExam> dbBookExam = new ArrayList<BookExam>();
		dbBookExam = eProctordb.getBookingSchedule(ls.getAccount().getUsername());
		
		for (int i = 0; i < dbBookExam.size(); i++) {
			CourseTable temp = new CourseTable();
			temp.setCourseCode(dbBookExam.get(i).getCourseCode());
			temp.setCourseDescription(dbBookExam.get(i).getDescription());
			temp.setDate(dbBookExam.get(i).getDate().toString());
			temp.setDuration("" + dbBookExam.get(i).getDuration() + "hours");
			temp.setFormat(dbBookExam.get(i).getStyle());
			temp.setTime(dbBookExam.get(i).getStartTime().toString() + " - " + dbBookExam.get(i).getEndTime().toString());
			bookedExam.add(temp);
		}
	
		return bookedExam;
 	}
	
	/**
	 * Authenticate login information with database
	 * @param username		Entered username
	 * @param password		Entered password
	 * @param domain		Selected domain
	 * @return boolean
	 */
	public boolean authenticate(String username, String password, String domain) {
		try {
			return eProctordb.authenticate(domain, username, password);
		}
		catch (Exception e){
			return false;
		}
	}
	
	/**
	 * Setter for eProctordb
	 * @param eProctordb	eProctor database
	 */
	public void seteProctordb(EProctorDBInterface eProctordb) {
		this.eProctordb = eProctordb;
	}

	/**
	 * Setter for university database
	 * @param uniDB			University database
	 */
	public void setUniDB(UniversityDBInterface uniDB) {
		this.uniDB = uniDB;
	}

	/**
	 * Setter for login session
	 * @param ls			Login session
	 */
	public void setLs(LoginSession ls) {
		this.ls = ls;
	}

	/**
	 * Getter for student information from database
	 * @param studentID		Identifier for student
	 * @return Student
	 */
	@Override
	public Student getStudentInfo(String studentID) {
		try {
			return uniDB.getStudentInfo(studentID);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Getter for exam information from database
	 * @param courseCode	Identifier for the course
	 * @return List<Exam>
	 */
	@Override
	public List<Exam> getExamInfo(String courseCode) {
		try {
			return uniDB.getExamInfo(courseCode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Setter for exam answer to database, called when student submit paper
	 * @param studentID		Identifier for student
	 * @param examID		Identifier for exampaper
	 * @param ans			Student answers in the form of XML in String
	 */
	@Override
	public int setExamAns(String studentID, int examID, String ans) {
		try {
			return uniDB.setExamAns(studentID, examID, ans);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Getter for course information
	 * @param courseCode	Identifier for course
	 * @return Course
	 */
	@Override
	public Course getCourseInfo(String courseCode) {
		try {
			return uniDB.getCourseInfo(courseCode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Getter for exam booking schedule
	 * @param studentID		Identifier for student
	 * @return List<BookExam>
	 */
	@Override
	public List<BookExam> getBookingSchedule(String studentID) {
		try {
			return eProctordb.getBookingSchedule(studentID);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Add the booking to the student record in database
	 * @param studentID		Identifier for student
	 * @param courseCode	Identifier for course
	 * @return boolean
	 */
	@Override
	public boolean addBookingRecord(String studentID, String courseCode,
			Date examDate, Time startTime, Time endTime) {
		try {
			return eProctordb.addBookingRecord(studentID, courseCode, examDate, startTime, endTime);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Getter for courses to take
	 * @param studentID
	 * @return List<TakeCourse>
	 */
	@Override
	public List<TakeCourse> getCourseToTake(String studentID) {
		try {
			return eProctordb.getCourseToTake(studentID);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Getter for the announcements
	 * @return List<String>
	 */
	@Override
	public List<String> getAnnoucement() {
		try {
			return eProctordb.getAnnoucement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Add new announcement into announcement table in database
	 *@param annoucement		The announcement
	 *@return
	 */
	@Override
	public int addAnnoucement(String annoucement) {
		try {
			return eProctordb.addAnnoucement(annoucement);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Add new invigilator into database
	 * @param invigilatorID		Identifier for the invigilator
	 * @param name				Name of the invigilator
	 * @param address			Address of invigilator
	 * @param postalCode		Postal code of invigilator address
	 * @param phoneNumber		Phone number of invigilator
	 * @param password			Password of the invigilator
	 * @return boolean
	 */
	@Override
	public boolean addInvigilator(String invigilatorID, String name,
			String address, int postalCode, int phoneNumber, String password) {
		try {
			return eProctordb.addInvigilator(invigilatorID, name, address, postalCode, phoneNumber, password);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Edit the invigilator information
	 * @param invigilatorID		Identifier of invigilator
	 * @param password			Password of invigilator
	 * @param name				Name of invigilator
	 * @param address			Address of invigilator
	 * @param postalCode		Postal code of invigilator
	 * @param phoneNumber		Phone number of invigilator
	 * @return boolean
	 */
	@Override
	public boolean editInvigilatorInfo(String invigilatorID, String password,
			String name, String address, int postalCode, int phoneNumber) {
		try {
			return eProctordb.editInvigilatorInfo(invigilatorID, password, name, address, postalCode, phoneNumber);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	/**
	 * Get a list of all invigilators
	 * @return List<Invigilator>
	 */
	@Override
	public List<Invigilator> getAllInvigilator() {
		try {
			return eProctordb.getAllInvigilator();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Submit a report from the invigilator to the database
	 * @param studentID			Identifier for students
	 * @param examID			Identifier for exam
	 * @param proctorReport		The report
	 */
	@Override
	public boolean submitProctorReport(String studentID, String examID,
			String proctorReport) {
		try {
			return eProctordb.submitProctorReport(studentID, examID, proctorReport);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * Get the list of details of invigilator details
	 * @return
	 */
	public ObservableList<alliance.entity.Invigilator> getInvigilatorDetails() {

		ObservableList<alliance.entity.Invigilator> personData = FXCollections
				.observableArrayList();

		List<alliance.dbaccess.model.Invigilator> invigilatorDB;;
		invigilatorDB = eProctordb.getAllInvigilator();
		System.out.println("invigilatorDB size = " + invigilatorDB.size());

		for (int i = 0; i < invigilatorDB.size(); i++) {
			alliance.entity.Invigilator temp = new alliance.entity.Invigilator();
			temp.setName(invigilatorDB.get(i).getName());
			temp.setAddress(invigilatorDB.get(i).getAddress());
			temp.setAccount(invigilatorDB.get(i).getUsername(), invigilatorDB
					.get(i).getPassword(), "INVIGILATOR");
			temp.setPhoneNumber(invigilatorDB.get(i).getPhoneNumber() + "");
			temp.setPostalCode(invigilatorDB.get(i).getPostalCode());
			personData.add(temp);
		}

		return personData;
	}
	
	/**
	 * Edit the invigilator record in the database
	 * @param invigilatorID			Identifier for invigilator
	 * @param name					Name of invigilator
	 * @param address				Address of invigilator
	 * @param postalCode			Postal code of invigilator
	 * @param phone					Phone number of invigilator
	 * @param password				Password of invigilator
	 * @return boolean
	 */
	public boolean editInvigilators(String invigilatorID, String name,
			String address, int postalCode, String phone, String password) {
		
		int phoneNumber;
		try {
			phoneNumber = Integer.parseInt(phone);
		} catch (NumberFormatException e) {
			System.out.println("Number Format Error!");
			return false;
		}
		
		return eProctordb.editInvigilatorInfo(invigilatorID, password, name, address, postalCode, phoneNumber);
	}
	
	/**
	 * Insert invigilator to database
	 * @param invigilatorID			Identifier for invigilator
	 * @param name					Name of invigilator
	 * @param address				Address of invigilator
	 * @param postalCode			Postal code of invigilator
	 * @param phone					Phone number of invigilator
	 * @param password				Password of invigilator
	 * @return boolean
	 */
	public boolean addInvigilators(String invigilatorID, String name,
			String address, int postalCode, String phone, String password) {

		int phoneNumber;
		try {
			phoneNumber = Integer.parseInt(phone);
		} catch (NumberFormatException e) {
			System.out.println("Number Format Error!");
			return false;
		}

		return eProctordb.addInvigilator(invigilatorID, name, address,
				postalCode, phoneNumber, password);

	}
}
