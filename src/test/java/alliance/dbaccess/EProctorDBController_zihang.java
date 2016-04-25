package alliance.dbaccess;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import alliance.dbaccess.EProctorDBController;


public class EProctorDBController_zihang {
	
	public static final String XML_PATH = "META-INF/spring/EProctorAdapter.xml";
	public static final ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	public static Logger logger = LoggerFactory.getLogger(EProctorDBController_zihang.class);
	public static NamedParameterJdbcTemplate jdbcTemplate;
	public static EProctorDBController eControl;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger.info("start beforeclass");
		eControl = new ClassPathXmlApplicationContext("META-INF/spring/eproctor_database.xml").getBean(EProctorDBController.class);		
		DataSource ds = new ClassPathXmlApplicationContext("META-INF/spring/eproctor_database.xml").getBean(DataSource.class);
		jdbcTemplate = new NamedParameterJdbcTemplate(ds);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		jdbcTemplate.getJdbcOperations().execute("delete from invigilator where InvigilatorID like 'T12%';");
	}

	@Test
	public void testAddInvigilator() {
		// Correct input
		assertTrue(eControl.addInvigilator("T123", "test", "test ave", 12345, 12345, "test"));
	}
	
	@Test
	public void testAddInvigilator1(){
		assertFalse(eControl.addInvigilator( null , "test", "test ave", 12345, 12345, "test"));
	}
	@Test
	public void testAddInvigilator2(){
		assertFalse(eControl.addInvigilator("T124", null , "test ave", 12345, 12345, "test"));
	}
	@Test
	public void testAddInvigilator3(){
		assertFalse(eControl.addInvigilator("T125", "test", null , 12345, 12345, "test"));
	}
	@Test
	public void testAddInvigilator4(){
		assertFalse(eControl.addInvigilator("T126", "test", "test ave", -1 , 12345, "test"));
	}
	@Test
	public void testAddInvigilator5(){
		assertFalse(eControl.addInvigilator("T127", "test", "test ave", 12345, -1 , "test"));
	}
	@Test
	public void testAddInvigilator6(){
		assertFalse(eControl.addInvigilator("T128", "test", "test ave", 12345, 12345, null ));
	}

}
