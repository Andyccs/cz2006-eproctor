package alliance.model.interfaces;

import java.awt.image.BufferedImage;
import java.util.Observer;

import alliance.dbaccess.model.BookExam;
import alliance.dbaccess.model.Student;
import alliance.entity.LoginSession;
import alliance.networking.server.Identity;

public interface InvigilatorModelInterface {

	public void setLoginSession(LoginSession session);
	public LoginSession getLoginSession(); 
	
	public void setSelfImage(BufferedImage image);
	public BufferedImage getSelfImage();
	
	public void setOtherImage(Identity id, BufferedImage image);
	public BufferedImage getOtherImage(String id);
	public String[] getOtherId();
	public Identity getOtherIdentity(String id);
	
	public void addObserver(Observer observer);
	public void notifyObservers();
	public void notifyObservers(Object o);
	
	public void setStudentDetails(Student student);
	public Student getStudentDetails(String studentId);
	
	public void setBookExam(BookExam exam,String studentId);
	public BookExam getBookExam(String studentId);
}
