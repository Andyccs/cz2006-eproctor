package alliance.dbaccess;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
public class UniversityDBControllerTest_keith {
	//public static NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	public static final String XML_PATH = "META-INF/spring/u_database.xml";
	public static final ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	public static Logger logger = LoggerFactory.getLogger(UniversityDBControllerTest_keith.class);
	public static UniversityDBController uControl;
	public static NamedParameterJdbcTemplate jdbcTemplate;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DriverManagerDataSource dataSource = context.getBean(DriverManagerDataSource.class);
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		uControl = context.getBean(UniversityDBController.class);
		
		String SQL_INSERT_1 = "INSERT INTO course VALUE('testCourse','testCourseName');";
		jdbcTemplate.getJdbcOperations().execute(SQL_INSERT_1);
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		String SQL_DELETE_1 = "DELETE FROM course WHERE CourseCode = 'testCourse';";
		jdbcTemplate.getJdbcOperations().execute(SQL_DELETE_1);
	}
	@Before
	public void setUp() throws Exception {
	}
	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testGetCourseInfoFailure(){
		logger.info("getCourseInfo: abc123 (not in database)");
		assertNull(uControl.getCourseInfo("abc123"));
	}
	

	@Test
	public void testGetCourseInfoSuccess() {
		logger.info("getCourseInfo: " + uControl.getCourseInfo("testCourse").getCourseName() );
		assertEquals("CourseInfo", uControl.getCourseInfo("testCourse").getCourseName(), "testCourseName");	
	}
}