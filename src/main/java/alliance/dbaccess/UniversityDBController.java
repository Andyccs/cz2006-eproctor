package alliance.dbaccess;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import alliance.dbaccess.interfaces.UniversityDBInterface;
import alliance.dbaccess.model.Course;
import alliance.dbaccess.model.Exam;
import alliance.dbaccess.model.Student;

public class UniversityDBController implements UniversityDBInterface {
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	// SQL statements
	public final String SQL_GET_STUDENT = "SELECT * from student WHERE studentID = ?";
//	public final String SQL_GET_EXAMINFO = "SELECT * from exam WHERE courseCode = ? AND curdate() > exam.StartDate";
	public final String SQL_GET_EXAMINFO = "SELECT * from exam WHERE courseCode = ?";
	
	public final String SQL_GET_COURSECODE = "SELECT CourseCode from exam WHERE ExamID = ?";
	public final String SQL_SET_EXAMS = "INSERT INTO submit_exam(StudentID, ExamID, Answer) VALUES(?,?,?)";
	public final String SQL_SET_COMPLETE = "UPDATE  take_course SET Complete = 1 WHERE StudentID = ? and CourseCode = ?";
	
	public final String SQL_GET_COURSEINFO = "SELECT * FROM course WHERE coursecode = ?";

	public static final String XML_PATH = "META-INF/spring/u_database.xml";
	public static final ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	
	private static UniversityDBController instance;
	
	private UniversityDBController() {
		
	}
	
	public static UniversityDBController getInstance(){
		if(instance==null){
			instance = new UniversityDBController();
		}
		return instance;
	}

	/**
	 * Set the source of data
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}


	/**
	 * Get info of student
	 * @param student ID	Identifier of student
	 * @return Student		Returns Student object
	 */
	@Override
	@Cacheable("getStudentInfo")
	public Student getStudentInfo(String studentID) {
		RowMapper<Student> mapper = new RowMapper<Student>() {
			@Override
			public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
				String name = rs.getString("name");
				String studentID = rs.getString("studentID");
				String university = rs.getString("university");
				String email = rs.getString("email");

				Student student = new Student(name, studentID, university,
						email);
				return student;
			}
		};
		List<Student> studentInfo = jdbcTemplate.getJdbcOperations().query(
				SQL_GET_STUDENT, mapper, studentID);

		if(studentInfo!=null && studentInfo.size()!=0){
			return studentInfo.get(0);
		}
		return null;
		
	}

	/**
	 * Retrieve Exam Paper information
	 * @param courseCode		Course Code for exam
	 * @return List<Exam>		Returns a list of exam papers
	 */
	@Override
	@Cacheable("getExamInfo")
	public List<Exam> getExamInfo(String courseCode) {
		RowMapper<Exam> mapper = new RowMapper<Exam>() {
			@Override
			public Exam mapRow(ResultSet rs, int rowNum) throws SQLException {
				int examID = rs.getInt("examID");
				String courseCode = rs.getString("courseCode");
				int duration = rs.getInt("duration");
				String description = rs.getString("description");
				String style = rs.getString("style");
				String question = rs.getString("question");
				Date startDate = rs.getDate("startDate");
				Date endDate = rs.getDate("endDate");

				Exam exam = new Exam(examID, courseCode, duration,
						description, style, question,
						startDate, endDate);
				return exam;
			}
		};
		List<Exam> examInfo = jdbcTemplate.getJdbcOperations().query(
				SQL_GET_EXAMINFO, mapper, courseCode);

		return examInfo;
	}
	
	/**
	 * Retrieve the course code from examID
	 * @param examID	Identifier for exam paper
	 * @return			Return the course code
	 */
	@Cacheable("getCourseCode")
	private String getCourseCode(final int examID) {
		RowMapper<String> mapper = new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String courseCode = rs.getString("CourseCode");

				return courseCode;
			}
		};
			List<String> list = jdbcTemplate.getJdbcOperations().query(SQL_GET_COURSECODE, mapper, examID);
			
			return list.get(0);
	}

	/**
	 * Update exam answer into database
	 * @param studentID		Identifier for student
	 * @param examID		Identifier for exam paper
	 * @param ans			Answers made by student
	 * @return int			Return result of submission
	 */
	@Override
	public int setExamAns(final String studentID, final int examID, final String ans) {
		if (jdbcTemplate.getJdbcOperations().update(SQL_SET_EXAMS, new PreparedStatementSetter() {		  
			@Override
			public void setValues(java.sql.PreparedStatement ps)
					throws SQLException {			
				ps.setString(1, studentID);
				ps.setInt(2, examID);
				ps.setString(3, ans);
			}
		  }) == 1 && jdbcTemplate.getJdbcOperations().update(SQL_SET_COMPLETE, new PreparedStatementSetter() {		  
			@Override
			public void setValues(java.sql.PreparedStatement ps)
					throws SQLException {
				ps.setString(1, studentID);
				ps.setString(2, getCourseCode(examID));
			}			
	  }) == 1) return 1;
		
		return 0;
	}

	/**
	 * Retrieve course information
	 * @param courseCode		Course code of target
	 * @return Course			Returns Course object
	 */
	@Override
	@Cacheable("getCourseInfo")
	public Course getCourseInfo(final String courseCode) {
		RowMapper<Course> mapper = new RowMapper<Course>() {
			@Override
			public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
				String courseName = rs.getString("courseName");

				Course course = new Course(courseCode, courseName);
				return course;
			}
		};
		List<Course> courseInfo = jdbcTemplate.getJdbcOperations().query(
				SQL_GET_COURSEINFO, mapper, courseCode);

		if(courseInfo.size()==0)
			return null;
		else
			return courseInfo.get(0);
	}
}
