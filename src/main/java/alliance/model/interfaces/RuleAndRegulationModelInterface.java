package alliance.model.interfaces;

import java.awt.image.BufferedImage;
import java.util.Observer;

import alliance.dbaccess.model.BookExam;
import alliance.entity.LoginSession;
import alliance.networking.server.Identity;



public interface RuleAndRegulationModelInterface{
	public void setStudentDetails(LoginSession login);
	public LoginSession getStudentDetails();
	
	public void setProceedPermission(boolean proceed);
	public boolean getProceedPermission();
	
	public BufferedImage getSelfImage();
	public void setSelfImage(BufferedImage image);
	
	public BufferedImage getInvigilatorImage();
	public void setInvigilatorImage(BufferedImage image);
	
	public Identity getInvigilatorID();
	public void setInvigilatorID(Identity id);
	
	public void registerObserver(Observer observer);
	
	public BookExam getExamination();
	public void setExamination(BookExam examination);
	
	public void notifyObservers();
	public void notifyObservers(Object o);
}
