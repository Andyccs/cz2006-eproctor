package alliance.dbaccess;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import alliance.dbaccess.interfaces.EProctorDBInterface;
import alliance.dbaccess.model.Account;
import alliance.dbaccess.model.BookExam;
import alliance.dbaccess.model.Invigilator;
import alliance.dbaccess.model.TakeCourse;

public class EProctorDBController implements EProctorDBInterface{
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public static final String SQL_AUTHENTICATE_STUDENT = "SELECT * from account_student WHERE Username = ? AND Password = ?";
	public static final String SQL_AUTHENTICATE_INVIGILATOR = "SELECT * from account_invigilator WHERE Username = ? AND Password = ?";
	public static final String SQL_AUTHENTICATE_ADMIN = "SELECT * from account_admin WHERE Username = ? AND Password = ?";
//	public static final String SQL_ADD_STUDENT_ACCOUNT = "INSERT INTO account_student (Username, Password) VALUES (?, ?)";
	public static final String SQL_ADD_INVIGILATOR_ACCOUNT = "INSERT INTO account_invigilator (Username, Password) VALUES (?, ?)";
//	public static final String SQL_ADD_ADMIN_ACCOUNT = "INSERT INTO account_admin (Username, Password) VALUES (?, ?)";
	public static final String SQL_GET_COURSE_TO_TAKE = "SELECT * FROM (SELECT * FROM university_db.take_course NATURAL JOIN university_db.course WHERE StudentID = ?) AS t1 WHERE Complete = 0 AND NOT EXISTS (SELECT * FROM (SELECT * FROM eproctor_db.book_exam NATURAL JOIN (university_db.exam AS exam NATURAL JOIN university_db.course AS course)) AS t2 WHERE t1.CourseCode = t2.CourseCode)";
	public static final String SQL_GET_EXAM_ID = "SELECT * from university_db.exam WHERE Date(StartDate) <= ? AND Date(EndDate) >= ? AND CourseCode = ?";
	public static final String SQL_ADD_BOOKING_RECORD = "INSERT INTO book_exam (StudentID, ExamID, ExamDate, StartTime, EndTime) VALUES (?, ?, ?, ?, ?)";
	public static final String SQL_SUBMIT_PROCTOR_REPORT = "INSERT INTO announcement (StudentID, ExamID, ProctorReport) VALUES (?, ?, ?)";
	public static final String SQL_GET_BOOKING_SCHEDULE = "SELECT * FROM book_exam NATURAL JOIN (university_db.exam AS exam NATURAL JOIN university_db.course AS course) , university_db.take_course WHERE book_exam.StudentID = ? AND exam.coursecode = take_course.coursecode AND complete=0 AND book_exam.StudentId = take_course.StudentId;"; //need to add constraint on date (show bookings after today)
	public static final String SQL_GET_ANNOUCEMENT = "select * from announcement";
	public static final String SQL_ADD_ANNOUCEMENT = "INSERT INTO announcement (Announcement) VALUES (?)";
	public static final String SQL_ADD_INVIGILATOR = "INSERT INTO invigilator (InvigilatorID, Name, Address, PostalCode, PhoneNumber) VALUES (?, ?, ?, ?, ?)";
	public static final String SQL_EDIT_INVIGILATOR_INFO = "UPDATE invigilator SET Name = ?, Address = ?, PostalCode = ?, PhoneNumber = ? WHERE InvigilatorID = ?";
	public static final String SQL_EDIT_INVIGILATOR_PASSWORD = "UPDATE account_invigilator SET Password = ? WHERE Username = ?";
	public static final String SQL_GET_ALL_INVIGILATOR = "SELECT * from invigilator, account_invigilator WHERE invigilator.InvigilatorID = account_invigilator.Username";
	public static final String XML_PATH = "META-INF/spring/eproctor_database.xml";
	public static final ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	
	private DataSource dataSource;
	
	private static EProctorDBController instance;
	
	private EProctorDBController() {
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * Set data source for jdbc
	 * @param dataSource
	 */
	public static EProctorDBController getInstance(){
		if(instance==null){
			instance = new EProctorDBController();
		}
		return instance;
	}
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
	}
	
	/**
	 * Authenticate login
	 * @param domain		Student/Admin/Invigilator
	 * @param username		Username
	 * @param password		Password
	 * @return boolean		Returns authenticate result
	 */
	@Cacheable("authenticate")
	public boolean authenticate(String domain, String username, String password){
		String selectTableSQL = null;
		
		if (domain.equals("STUDENT"))
			selectTableSQL = SQL_AUTHENTICATE_STUDENT;
		else if (domain.equals("INVIGILATOR"))
			selectTableSQL = SQL_AUTHENTICATE_INVIGILATOR;
		else if (domain.equals("ADMINISTRATOR"))
			selectTableSQL = SQL_AUTHENTICATE_ADMIN;
		else
			return false;
		
		RowMapper<Account> mapper = new RowMapper<Account>() {
			@Override
			public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
				String username = rs.getString("Username");
				String password = rs.getString("Password");
				Account account = new Account(username, password);
				return account;
			}
		};
		
		List<Account> list = jdbcTemplate.getJdbcOperations().query(selectTableSQL,mapper,username,password);
		
		//System.out.println(list);
		if (list.size() == 1)
			return true;
		else
			return false;	
	}

	/**
	 * Retrieve Booking Schedule from database
	 * @param studentID			Student ID whose booking schedule is to be retrieved
	 * @return List<BookExam> 	Returns a list of Exam Bookings
	 */
	@Cacheable("getBookingSchedule")
	public List<BookExam> getBookingSchedule(String studentID) {
		//Take Exam Controller
		//Check Schedule Controller
		System.out.println("\n\n\nget booking\n\n\n");
		
		RowMapper<BookExam> mapper = new RowMapper<BookExam>() {
			@Override
			public BookExam mapRow(ResultSet rs, int rowNum) throws SQLException {
				String examID = rs.getString("ExamID");
				String courseCode = rs.getString("CourseCode");
				String courseName = rs.getString("CourseName");
				int duration = rs.getInt("Duration");
				String description = rs.getString("Description");
				String style = rs.getString("Style");
				Date date = rs.getDate("ExamDate");
				Time startTime = rs.getTime("StartTime");
				Time endTime = rs.getTime("EndTime");
				
				BookExam bookExam = new BookExam(examID, courseCode, courseName, duration, description, style, date, startTime, endTime);
	
				return bookExam;
			}
		
		};
		return jdbcTemplate.getJdbcOperations().query(SQL_GET_BOOKING_SCHEDULE,mapper,studentID);
	}
	
	/**
	 * Get the Exam ID for the particular paper
	 * @param examDate		Date of exam
	 * @param courseCode	Course code
	 * @return String		Exam ID
	 */
	@Cacheable("getExamID")
	private String getExamID(Date examDate, String courseCode) {
		RowMapper<String> mapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String examID = rs.getString("ExamID");
				
				return examID;
			}
			
		};
		List<String> list = jdbcTemplate.getJdbcOperations().query(SQL_GET_EXAM_ID,mapper,examDate,examDate,courseCode);
		if(list!=null && list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * Add booking record to student in database
	 * @param studentID			Identifier for student
	 * @param courseCode		Course code to be added
	 * @param examDate			Date of exam
	 * @param startTime			Start time of exam period
	 * @param endTime			End time of exam period
	 * @return boolean 			Returns the result of adding booking
	 */
	@Caching(
			evict={
			@CacheEvict(value = "getCourseToTake", allEntries=true),
			@CacheEvict(value = "getBookingSchedule", allEntries=true)
			}
	)
	public boolean addBookingRecord(final String studentID, final String courseCode, final Date examDate, final Time startTime, final Time endTime) {
		try {
			if(jdbcTemplate.getJdbcOperations().update(SQL_ADD_BOOKING_RECORD, new PreparedStatementSetter() {		  
				@Override
				public void setValues(java.sql.PreparedStatement ps)
						throws SQLException {
					ps.setString(1, studentID);
					ps.setString(2, getExamID(examDate,courseCode));
					ps.setDate(3, examDate);
					ps.setTime(4, startTime);
					ps.setTime(5, endTime);
				}
			}) == 1) 
				return true;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Retrieve the courses available
	 * @param studentID			Identifier for student
	 * @return List<TakeCourse>	Returns a list of courses for student to take
	 */
	@Cacheable("getCourseToTake")
	public List<TakeCourse> getCourseToTake(String studentID) {
		
		//Book Exam Controller
		
		System.out.println("\n\nANdy Chong Here\n\n");
		
		RowMapper<TakeCourse> mapper = new RowMapper<TakeCourse>() {
		@Override
		public TakeCourse mapRow(ResultSet rs, int rowNum) throws SQLException {
			String studentID = rs.getString("StudentID");
			String courseCode = rs.getString("CourseCode");
			String courseName = rs.getString("CourseName");
			int noOfRetake = rs.getInt("NoOfRetake");
			
			TakeCourse takeCourse = new TakeCourse(studentID, courseCode, courseName, noOfRetake);

			return takeCourse;
		}
		
	};
		return jdbcTemplate.getJdbcOperations().query(SQL_GET_COURSE_TO_TAKE,mapper,studentID);
	}
	
	/**
	 * Retrieve announcement from database
	 * @return List<String>		Returns a list of announcements
	 */
	@Cacheable("getAnnoucement")
	public List<String> getAnnoucement() {
		System.out.println("get announcement");
		RowMapper<String> mapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String annoucement = rs.getString("Announcement");
				
				return annoucement;
			}
			
		};
		return jdbcTemplate.getJdbcOperations().query(SQL_GET_ANNOUCEMENT,mapper);
	}
	
	/**
	 * Add announcement to database
	 * @param annoucement		Announcement to be added
	 * @return int
	 */
	@CacheEvict(value="getAnnoucement", allEntries=true)
	public int addAnnoucement(final String announcement) {
		
		return jdbcTemplate.getJdbcOperations().update(SQL_ADD_ANNOUCEMENT, new PreparedStatementSetter() {		  
				@Override
				public void setValues(java.sql.PreparedStatement ps)
						throws SQLException {
					ps.setString(1, announcement);
				}
			  });	  		
	}
	
	/**
	 * Add invigilator to database record
	 * @param invigilatorID		Unique ID of invigilator
	 * @param name				Name of invigilator
	 * @param address			Address of invigilator
	 * @param postalCode		Postal code of address
	 * @param phoneNumber		Phone number
	 * @param password			Password for account
	 * @return boolean			Returns result of adding to database
	 */
	@CacheEvict(value="getAllInvigilator", allEntries=true)
	public boolean addInvigilator(final String invigilatorID, final String name, final String address, final int postalCode, final int phoneNumber, final String password) {
		try {
			if (jdbcTemplate.getJdbcOperations().update(SQL_ADD_INVIGILATOR, new PreparedStatementSetter() {		  
				@Override
				public void setValues(java.sql.PreparedStatement ps)
						throws SQLException {
					ps.setString(1, invigilatorID);
					ps.setString(2, name);
					ps.setString(3, address);
					ps.setInt(4, postalCode);
					ps.setInt(5, phoneNumber);
				}
			}) == 1 && jdbcTemplate.getJdbcOperations().update(SQL_ADD_INVIGILATOR_ACCOUNT, new PreparedStatementSetter() {		  
				@Override
				public void setValues(java.sql.PreparedStatement ps)
						throws SQLException {
					ps.setString(1, invigilatorID);
					ps.setString(2, password);
				}			
  }) == 1) 
				return true;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Update the invigilator's information
	 * @param invigilatorID		Unique ID of invigilator
	 * @param password			Password for account
	 * @param name				Name of invigilator
	 * @param address			Address of invigilator
	 * @param postalCode		Postal code of address
	 * @param phoneNumber		Phone number
	 * @return boolean			Returns result of update
	 */
	@CacheEvict(value="getAllInvigilator", allEntries=true)
	public boolean editInvigilatorInfo(final String invigilatorID, final String password, final String name, final String address, final int postalCode, final int phoneNumber) {	
		if (jdbcTemplate.getJdbcOperations().update(SQL_EDIT_INVIGILATOR_PASSWORD, new PreparedStatementSetter() {		  
			@Override
			public void setValues(java.sql.PreparedStatement ps)
					throws SQLException {
				ps.setString(1, password);
				ps.setString(2, invigilatorID);
			}
		}) == 1 && jdbcTemplate.getJdbcOperations().update(SQL_EDIT_INVIGILATOR_INFO, new PreparedStatementSetter() {  
			@Override
			public void setValues(java.sql.PreparedStatement ps)
					throws SQLException {			
				ps.setString(1, name);
				ps.setString(2, address);
				ps.setInt(3, postalCode);
				ps.setInt(4, phoneNumber);
				ps.setString(5, invigilatorID);
			}
		  }) == 1) return true;
		return false;
	}
	
	/**
	 * Retrieve a list of all invigilators
	 * @return List<Invigilator>
	 */
	@Cacheable("getAllInvigilator")
	public List<Invigilator> getAllInvigilator() {	
		RowMapper<Invigilator> mapper = new RowMapper<Invigilator>() {
		@Override
		public Invigilator mapRow(ResultSet rs, int rowNum) throws SQLException {
			String invigilatorID = rs.getString("InvigilatorID");
			String username = rs.getString("Username");
			String password = rs.getString("Password");
			String name = rs.getString("Name");
			String address = rs.getString("Address");
			int postalCode = rs.getInt("PostalCode");
			int phoneNumber = rs.getInt("PhoneNumber");			
			Invigilator invigilator = new Invigilator(invigilatorID, username, password, name, address, postalCode, phoneNumber);

			return invigilator;
		}
		
	};
		return jdbcTemplate.getJdbcOperations().query(SQL_GET_ALL_INVIGILATOR,mapper);
	}
	
	
	/**
	 * Submit report by invigilator
	 * @param studentID			Identifier for student
	 * @param examID			Identifier for the exam paper mentioned in report
	 * @param proctorReport		Report
	 * @return boolean			Returns the result of submission
	 */
	public boolean submitProctorReport(final String studentID, final String examID, final String proctorReport) {
		if (jdbcTemplate.getJdbcOperations().update(SQL_ADD_INVIGILATOR, new PreparedStatementSetter() {		  
			@Override
			public void setValues(java.sql.PreparedStatement ps)
					throws SQLException {
				ps.setString(1, studentID);
				ps.setString(2, examID);
				ps.setString(3, proctorReport);
			}}) == 1) return true;
		return false;
	}
}