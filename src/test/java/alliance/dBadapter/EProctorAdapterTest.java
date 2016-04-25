package alliance.dBadapter;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import alliance.dbaccess.EProctorDBController;

/**
 * 
 * @author Chee Yong
 *
 */
public class EProctorAdapterTest {

	public EProctorAdapter db;
	
	public static final String XML_PATH_ADAPTER = "META-INF/spring/EProctorAdapter.xml";
	public static final ApplicationContext contextAdapter = new ClassPathXmlApplicationContext(XML_PATH_ADAPTER);
	
	public static final String XML_PATH_DB = "META-INF/spring/eproctor_database.xml";
	public static final ApplicationContext contextDB = new ClassPathXmlApplicationContext(XML_PATH_DB);

	public static EProctorAdapter eAdapter;
	public static NamedParameterJdbcTemplate jdbcTemplate;
	
	//sql statement of adding sample data
	
	public static final String ADD_STUDENT_SQL = "INSERT INTO account_student(Username, Password) VALUES ('U1330001F', 'test1')";
	public static final String ADD_INVIGILATOR_INFO_SQL = "INSERT INTO invigilator (InvigilatorID, Name, Address, PostalCode, PhoneNumber) VALUES ('I0009', 'Ian', 'Bedok North St 3, Blk 321, #11-513',642321,93331111)";
	public static final String ADD_INVIGILATOR_SQL = "INSERT INTO account_invigilator(Username, Password) VALUES ('I0009', 'test2')";
	public static final String ADD_ADMIN_INFO_SQL = "INSERT INTO admin (AdminID, Name) VALUES ('A0005', 'David')";
	public static final String ADD_ADMIN_SQL = "INSERT INTO account_admin(Username, Password) VALUES ('A0005', 'test3')";
	
	//sql statement of deleting sample data
	public static final String DELETE_STUDENT_SQL = "DELETE FROM account_student WHERE Username = 'U1330001F'";
	public static final String DELETE_INVIGILATOR_SQL = "DELETE FROM account_invigilator WHERE Username = 'I0009'";
	public static final String DELETE_INVIGILATOR_INFO_SQL = "DELETE FROM invigilator WHERE InvigilatorID = 'I0009'";
	public static final String DELETE_ADMIN_SQL = "DELETE FROM account_admin WHERE Username = 'A0005'";
	public static final String DELETE_ADMIN_INFO_SQL = "DELETE FROM admin WHERE AdminID = 'A0005'";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DriverManagerDataSource dataSource = contextDB.getBean(DriverManagerDataSource.class);
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		eAdapter = contextAdapter.getBean(EProctorAdapter.class);
		
		jdbcTemplate.getJdbcOperations().execute(ADD_STUDENT_SQL);
		jdbcTemplate.getJdbcOperations().execute(ADD_INVIGILATOR_INFO_SQL);
		jdbcTemplate.getJdbcOperations().execute(ADD_INVIGILATOR_SQL);
		jdbcTemplate.getJdbcOperations().execute(ADD_ADMIN_INFO_SQL);
		jdbcTemplate.getJdbcOperations().execute(ADD_ADMIN_SQL);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		jdbcTemplate.getJdbcOperations().execute(DELETE_STUDENT_SQL);
		jdbcTemplate.getJdbcOperations().execute(DELETE_INVIGILATOR_SQL);
		jdbcTemplate.getJdbcOperations().execute(DELETE_INVIGILATOR_INFO_SQL);
		jdbcTemplate.getJdbcOperations().execute(DELETE_ADMIN_SQL);
		jdbcTemplate.getJdbcOperations().execute(DELETE_ADMIN_INFO_SQL);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAuthenticate_Student() {
		assertTrue(eAdapter.authenticate("U1330001F", "test1", "STUDENT"));
	}
	
	@Test
	public void testAuthenticate_Invigilator() {
		assertTrue(eAdapter.authenticate("I0009", "test2", "INVIGILATOR"));
	}

	@Test
	public void testAuthenticate_Admin() {
		assertTrue(eAdapter.authenticate("A0005", "test3", "ADMINISTRATOR"));
	}

	@Test
	public void testAuthenticate_InvalidUsername1() {
		assertFalse(eAdapter.authenticate("U1330009G", "test1", "STUDENT"));
	}

	@Test
	public void testAuthenticate_InvalidUsername2() {
		assertFalse(eAdapter.authenticate("I0010", "test2", "INVIGILATOR"));
	}

	@Test
	public void testAuthenticate_InvalidUsername3() {
		assertFalse(eAdapter.authenticate("A0010", "test3", "ADMINISTRATOR"));
	}
	
	@Test
	public void testAuthenticate_InvalidPassword1() {
		assertFalse(eAdapter.authenticate("U1330001F", "test5", "STUDENT"));
	}
	
	@Test
	public void testAuthenticate_InvalidPassword2() {
		assertFalse(eAdapter.authenticate("I0009", "test6", "INVIGILATOR"));
	}
	
	@Test
	public void testAuthenticate_InvalidPassword3() {
		assertFalse(eAdapter.authenticate("A0005", "test7", "ADMINISTRATOR"));
	}
	
	@Test
	public void testAuthenticate_InvalidDomain1() {
		assertFalse(eAdapter.authenticate("U1330001F", "test1", "INVIGILATOR"));
	}
	
	@Test
	public void testAuthenticate_InvalidDomain2() {
		assertFalse(eAdapter.authenticate("I0009", "test2", "ADMINISTRATOR"));
	}
	
	@Test
	public void testAuthenticate_InvalidDomain3() {
		assertFalse(eAdapter.authenticate("A0005", "test3", "STUDENT"));
	}
}
