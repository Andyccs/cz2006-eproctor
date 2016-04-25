package alliance.dbaccess;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import alliance.dbaccess.model.TakeCourse;

import com.android.dx.rop.cst.CstArray.List;

/**
 * 
 * @author Andy
 *
 */
public class EProctorDBControllerTest_andy {
	
	static EProctorDBController controller;
	static String eproctor_xml = "/META-INF/spring/eproctor_database.xml";
	static ApplicationContext eproctor_context = new ClassPathXmlApplicationContext(eproctor_xml);
	static NamedParameterJdbcTemplate eproctor_jdbc;
	
	static String university_xml = "/META-INF/spring/u_database.xml";
	static ApplicationContext university_context = new ClassPathXmlApplicationContext(university_xml);
	static NamedParameterJdbcTemplate university_jdbc;
	
	//add student
	public static final String add_student_sql = "INSERT INTO student (studentid, name, university, email) VALUES ('U1220441H','Andy','NTU','andychong2@gmail.com');";
	
	//add student_account
	public static final String add_student_account_sql = "INSERT INTO account_student (Username,Password) VALUES ('U1220441H','test');";
	
	//add course
	public static final String add_course_sql ="INSERT INTO course(CourseCode,CourseName) VALUES ('test123','testCourse');";
	
	//add take_course
	public static final String add_take_course_sql = "INSERT INTO take_course(StudentId, CourseCode, NoOfRetake,Complete) VALUES('U1220441H','test123',0,0);";
		
	//delete take_course
	public static final String delete_take_course_sql = "DELETE FROM `university_db`.`take_course` WHERE `take_course`.`StudentID` = 'U1220441H' AND `take_course`.`CourseCode` = 'test123';";
	
	//delete course
	public static final String delete_course_sql = "DELETE FROM `university_db`.`course` WHERE `course`.`CourseCode` = 'test123';";
	
	//delete student account
	public static final String delete_student_account_sql = "DELETE FROM `eproctor_db`.`account_student` WHERE `account_student`.`Username` = 'U1220441H';";	
	
	//delete student
	public static final String delete_student_sql = "DELETE FROM `university_db`.`student` WHERE `student`.`StudentID` = 'U1220441H';";
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		controller = eproctor_context.getBean(EProctorDBController.class);
		
		DataSource ds1 = eproctor_context.getBean(DataSource.class);
		eproctor_jdbc = new NamedParameterJdbcTemplate(ds1);
		
		DataSource ds2 = university_context.getBean(DataSource.class);
		university_jdbc = new NamedParameterJdbcTemplate(ds2);
		
		//add student
		university_jdbc.getJdbcOperations().execute(add_student_sql);
		
		//add student account
		eproctor_jdbc.getJdbcOperations().execute(add_student_account_sql);
		
		//add course
		university_jdbc.getJdbcOperations().execute(add_course_sql);
		
		//add course to take
		university_jdbc.getJdbcOperations().execute(add_take_course_sql);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		university_jdbc.getJdbcOperations().execute(delete_take_course_sql);
		university_jdbc.getJdbcOperations().execute(delete_course_sql);
		eproctor_jdbc.getJdbcOperations().execute(delete_student_account_sql);
		university_jdbc.getJdbcOperations().execute(delete_student_sql);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCourseToTake1() {
		//succeed test
		java.util.List<TakeCourse> course = controller.getCourseToTake("U1220441H");
		assertTrue(course!=null && course.size()==1);
		assertTrue(course.get(0).getCourseCode().equals("test123"));
		assertTrue(course.get(0).getCourseName().equals("testCourse"));
		assertTrue(course.get(0).getNoOfRetake()==0);
	}
	
	@Test
	public void testGetCourseToTake2() {
		//fail test
		java.util.List<TakeCourse> course = controller.getCourseToTake("U1234567G");
		assertTrue(course.size()==0);
	}	

}
