package alliance.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import alliance.dBadapter.EProctorAdapter;
import alliance.dbaccess.model.Exam;
import alliance.model.BookExamModel;
import alliance.model.interfaces.BookExamModelInterface;

/**
 * 
 * @author Benjie
 *
 */
public class BookExamControllerTest {
	
	private static BookExamModelInterface model;
	public static BookExamController bookExamCtrl;
	
	public static final String XML_PATH = "META-INF/spring/EProctorAdapter.xml";
	public static final ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	public static EProctorAdapter db;
	
	public static Logger logger = LoggerFactory.getLogger(BookExamController.class);	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		db = context.getBean(EProctorAdapter.class);	
		
		Exam exam = db.getExamInfo("CZ2001").get(0);
		logger.debug(exam.getStartDate().toString());
		logger.debug(exam.getEndDate().toString());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {	
		model = new BookExamModel();
		bookExamCtrl = new BookExamController(model);	
	}

	@After
	public void tearDown() throws Exception {
		model = null;
		bookExamCtrl = null;
	}

	@Test
	public void testIsInputValid1() {
		assertTrue("Valid Input", bookExamCtrl.isInputValid("CZ2001", "2014-04-20", "00:00:00"));	
	}
	@Test
	public void testIsInputValid2() {
		assertTrue("Valid Input", bookExamCtrl.isInputValid("CZ2001", "2014-04-30", "00:00:00"));
	}

	@Test
	public void testIsInputValid3() {
		assertTrue("Valid Input", bookExamCtrl.isInputValid("CZ2001", "2014-04-20", "23:59:00"));	
	}
	
	@Test
	public void testIsInputValid4() {
		assertTrue("Valid Input", bookExamCtrl.isInputValid("CZ2001", "2014-04-20", "23:59:00"));	
	}
	
	@Test
	public void testIsInputValid5() {
		assertFalse("Wrong Date - 2014-04-19", bookExamCtrl.isInputValid("CZ2001", "2014-04-19", "00:00:00"));
	}
	
	@Test
	public void testIsInputValid6() {
		assertFalse("Wrong Date - 2014-04-31", bookExamCtrl.isInputValid("CZ2001", "2014-04-31", "00:00:00"));
	}
	@Test
	public void testIsInputValid7() {
		assertFalse("Wrong Date - 2014-04-19", bookExamCtrl.isInputValid("CZ2001", "2014-04-19", "23:59:00"));
	}
	@Test
	public void testIsInputValid8() {
		assertFalse("Wrong Date - 2014-04-31", bookExamCtrl.isInputValid("CZ2001", "2014-04-31", "23:59:00"));
	}
	@Test
	public void testIsInputValid9() {
		assertFalse("Wrong Time Slot - 23:60", bookExamCtrl.isInputValid("CZ2001", "2014-04-20", "23:60:00"));
	}
	@Test
	public void testIsInputValid10() {
		assertFalse("Wrong Time Slot - 23:60", bookExamCtrl.isInputValid("CZ2001", "2014-04-30", "23:60:00"));
	}
	@Test
	public void testIsInputValid11() {
		assertFalse("Wrong Time Slot - 24:00", bookExamCtrl.isInputValid("CZ2001", "2014-04-20", "24:00:00"));
	}
	@Test
	public void testIsInputValid12() {
		assertFalse("Wrong Time Slot - 24:00", bookExamCtrl.isInputValid("CZ2001", "2014-04-30", "24:00:00"));
	}
	@Test
	public void testIsInputValid13() {
		assertFalse("Wrong Course Code", bookExamCtrl.isInputValid("ABCDEF", "2014-04-20", "00:00:00"));
	}
	@Test
	public void testIsInputValid14() {
		assertFalse("Wrong Course Code", bookExamCtrl.isInputValid("ABCDEF", "2014-04-30", "00:00:00"));
	}
	@Test
	public void testIsInputValid15() {
		assertFalse("Wrong Course Code", bookExamCtrl.isInputValid("ABCDEF", "2014-04-20", "23:59:00"));
	}	
	@Test
	public void testIsInputValid16() {
		assertFalse("Wrong Course Code", bookExamCtrl.isInputValid("ABCDEF", "2014-04-30", "23:59:00"));
	}	
	@Test
	public void testIsInputValid17() {
		assertTrue("Valid Input", bookExamCtrl.isInputValid("CZ2001", "2014-04-25", "12:30:00"));	
	}	

}
