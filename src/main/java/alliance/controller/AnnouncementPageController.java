package alliance.controller;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import alliance.controller.interfaces.AnnouncementPageControllerInterface;
import alliance.dBadapter.EProctorAdapter;
import alliance.dbaccess.EProctorDBController;
import alliance.dbaccess.interfaces.EProctorDBInterface;
import alliance.model.interfaces.AnnouncementPageModelInterface;

public class AnnouncementPageController implements AnnouncementPageControllerInterface{
	
	private AnnouncementPageModelInterface model;
	
	public AnnouncementPageController(AnnouncementPageModelInterface model) {
		this.model = model;
	}
	/**
	 * Set the list of announcements from the model to display on the 
	 * announcement page UI
	 */
	@Override
	public void initialize() {
		if(model.getAnnouncement()!=null){
			return;
		}
//		EProctorDBInterface db = EProctorDBController.getInstance();
		EProctorAdapter db = new ClassPathXmlApplicationContext("META-INF/spring/EProctorAdapter.xml").getBean(EProctorAdapter.class);
		List<String> announcements = db.getAnnoucement();

		model.setAnnouncements(announcements);
	}
}
