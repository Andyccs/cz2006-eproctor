package alliance.dbaccess;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import alliance.dbaccess.model.Student;
public class UniversityDBControllerTest_kavan {
	public static final String XML_PATH = "META-INF/spring/u_database.xml";
	public static final ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	public static Logger logger = LoggerFactory.getLogger(UniversityDBControllerTest_kavan.class);
	public static UniversityDBController uControl;
	public static NamedParameterJdbcTemplate jdbcTemplate;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger.info("start beforeclass");
		DataSource dataSource = context.getBean(DriverManagerDataSource.class);
		
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		String SQL_INSERT = "INSERT INTO STUDENT(StudentID, Name, University, Email) VALUES(?,?,?,?)";
		uControl = context.getBean(UniversityDBController.class);
		jdbcTemplate.getJdbcOperations().update(SQL_INSERT, new PreparedStatementSetter() {		  
			@Override
			public void setValues(java.sql.PreparedStatement ps)
					throws SQLException {			
				ps.setString(1, "U1221142B");
				ps.setString(2, "kavan");
				ps.setString(3, "ntu");
				ps.setString(4, "kkoh008@e.ntu.edu.sg");
			}
		  });
	}


	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		String SQL_DELETE = "DELETE FROM STUDENT WHERE StudentID = ?";

		jdbcTemplate.getJdbcOperations().update(SQL_DELETE, new PreparedStatementSetter() {		  
			@Override
			public void setValues(java.sql.PreparedStatement ps)
					throws SQLException {			
				ps.setString(1, "U1221142B");
			}
		  });
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetStudentInfo1() {
		Student result = new Student( "kavan", "U1221142B", "ntu", "kkoh008@e.ntu.edu.sg");
		Student st = uControl.getStudentInfo("U1221142B");

		assertEquals("student name", st.getName(), result.getName());
		assertEquals("student id", st.getStudentID(), result.getStudentID());
		assertEquals("university", st.getUniversity(), result.getUniversity());
		assertEquals("email", st.getEmail(), result.getEmail());
	}
	@Test
	public void testGetStudentInfo2() {
		Student st = uControl.getStudentInfo("U1221142C");

		assertEquals("student name is null", st, null);

	}	
}