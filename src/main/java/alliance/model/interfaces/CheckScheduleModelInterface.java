package alliance.model.interfaces;

import java.util.List;
import java.util.Observer;

import alliance.dbaccess.model.BookExam;
import alliance.entity.LoginSession;

public interface CheckScheduleModelInterface {
	public void addObserver(Observer observer);
	public void notifyObservers();
	
	public void setLoginSession(LoginSession ls);
	public LoginSession getLoginSession();
	
	public void setExamination(List<BookExam> exam);
	public List<BookExam> getExaminations();
}
