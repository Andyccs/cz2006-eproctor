package alliance.controller;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import alliance.controller.interfaces.CheckScheduleControllerInterface;
import alliance.dBadapter.EProctorAdapter;
import alliance.dbaccess.EProctorDBController;
import alliance.dbaccess.model.BookExam;
import alliance.model.interfaces.CheckScheduleModelInterface;

public class CheckScheduleController implements CheckScheduleControllerInterface{
	
	private CheckScheduleModelInterface model;
	
	private static final String XML_PATH = "META-INF/spring/EProctorAdapter.xml";
	
	private ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	
	public EProctorAdapter db = context.getBean(EProctorAdapter.class);
	
	public CheckScheduleController (CheckScheduleModelInterface model) {
		this.model = model;
		
	}

	@Override
	public void initialize() {

		List<BookExam> exam = 
//				EProctorDBController.getInstance()
				db
				.getBookingSchedule(
						model.getLoginSession()
						.getAccount().getUsername());
		model.setExamination(exam);
		model.notifyObservers();
	}

	@Override
	public void setModel(CheckScheduleModelInterface model) {
		this.model = model;
	}

	
}
