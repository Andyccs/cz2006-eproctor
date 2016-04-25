package alliance.dbaccess;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Time;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class EProctorDBControllerTest_yongmeng {

	static EProctorDBController controller;
	
	static NamedParameterJdbcTemplate university_jdbc;
	
	static NamedParameterJdbcTemplate eproctor_jdbc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//set up database
		controller = new ClassPathXmlApplicationContext("META-INF/spring/eproctor_database.xml").getBean(EProctorDBController.class);
		
		DataSource ds = new ClassPathXmlApplicationContext("META-INF/spring/u_database.xml").getBean(DataSource.class);
		university_jdbc = new NamedParameterJdbcTemplate(ds);
		
		DataSource ds2 = new ClassPathXmlApplicationContext("META-INF/spring/eproctor_database.xml").getBean(DataSource.class);
		eproctor_jdbc = new NamedParameterJdbcTemplate(ds2);
		
		university_jdbc.getJdbcOperations().execute("insert into student values('U123456I', 'Meng','NTU','meng@e.ntu.edu.sg');"); //add student
		university_jdbc.getJdbcOperations().execute("insert into course values('CZ3005','AI');"); //add course
		university_jdbc.getJdbcOperations().execute("insert into take_course values('U123456I','CZ3005',0,0);"); //take course
		university_jdbc.getJdbcOperations().execute("insert into exam (ExamId,CourseCode,Duration,Description,Style,Question,StartDate,EndDate) "
				+ "values ('20','CZ3005','2','Just a testing','MCQ','<xml></xml>','2014-04-10','2014-04-29');"); //add exam
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		university_jdbc.getJdbcOperations().execute("delete from exam where ExamId = '20';"); //delete exam
		university_jdbc.getJdbcOperations().execute("delete from take_course where StudentID='U123456I';"); //delete take course
		university_jdbc.getJdbcOperations().execute("delete from course where CourseCode='CZ3005';"); //delete course
		university_jdbc.getJdbcOperations().execute("delete from student where StudentID='U123456I';"); //delete student
	}
	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		eproctor_jdbc.getJdbcOperations().execute(delete_book_exam_sql);
		eproctor_jdbc.getJdbcOperations().execute("delete from book_exam where Studentid='U1220002K';");
		
	}

	private static String delete_book_exam_sql ="delete from book_exam where Studentid='U123456I';";
	private static boolean bookStatus;
	
	@Test
	public void testAddBookingRecord1() {
		bookStatus = controller.addBookingRecord("U123456I", "CZ3005", Date.valueOf("2014-04-25"), Time.valueOf("08:00:00"), Time.valueOf("10:00:00")); //valid
		assertTrue(bookStatus);
	}
	
	@Test
	public void testAddBookingRecord2() {
		//no foreign key constraint
		bookStatus = controller.addBookingRecord("U1220002K", "CZ3005", Date.valueOf("2014-04-25"), Time.valueOf("08:00:00"), Time.valueOf("10:00:00"));
		assertFalse(bookStatus); //invalid Matric No.
	}
	
	@Test
	public void testAddBookingRecord3() {
		bookStatus = controller.addBookingRecord("U123456I", "ABCDEF", Date.valueOf("2014-04-25"), Time.valueOf("08:00:00"), Time.valueOf("10:00:00")); //invalid CourseCode
		assertFalse(bookStatus);
	}
	@Test
	public void testAddBookingRecord4() {
		
		bookStatus = controller.addBookingRecord("U123456I", "CZ3005", Date.valueOf("2014-04-01"), Time.valueOf("08:00:00"), Time.valueOf("10:00:00")); //invalid Exam Date
		assertFalse(bookStatus);
	}

	@Test
	public void testAddBookingRecord6() {
		bookStatus = controller.addBookingRecord("U123456I", "CZ3005", Date.valueOf("2014-04-30"), Time.valueOf("08:00:00"), Time.valueOf("10:00:00")); //invalid Exam Date
		assertFalse(bookStatus);
	}
	@Test
	public void testAddBookingRecord7() {
		bookStatus = controller.addBookingRecord("U123456I", "CZ3005", Date.valueOf("2014-04-25"), Time.valueOf("07:59:00"), Time.valueOf("10:00:00")); //invalid start time
		assertFalse(bookStatus);
				
	}
	@Test
	public void testAddBookingRecord8() {
		bookStatus = controller.addBookingRecord("U123456I", "CZ3005", Date.valueOf("2014-04-25"), Time.valueOf("08:00:00"), Time.valueOf("10:01:00")); //invalid end time
		assertFalse(bookStatus);
	}
}
