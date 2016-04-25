package alliance.dbaccess.interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import alliance.dbaccess.model.Course;
import alliance.dbaccess.model.Exam;
import alliance.dbaccess.model.Student;

public interface UniversityDBInterface {
	public static final String XML_PATH = "META-INF/spring/u_database.xml";
	public static final ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	
	/**
	 * 
	 * @param studentID
	 * @return student
	 */
	public Student getStudentInfo(String studentID);
	/**
	 * 
	 * @param courseCode
	 * @return exam
	 */
	public List<Exam> getExamInfo(String courseCode);
	/**
	 * 
	 * @param submitExamID
	 * @param examID
	 * @param ans
	 * @return number of rows affected
	 */
	public int setExamAns(String studentID, int examID, String ans);
	/**
	 * 
	 * @param courseCode
	 * @return course
	 * @throws SQLException 
	 */
	public Course getCourseInfo(String courseCode);
	
}
