package alliance.model.interfaces;

import java.util.List;
import java.util.Observer;

import alliance.dbaccess.model.BookExam;
import alliance.entity.LoginSession;

public interface TakeExamModelInterface {
	public LoginSession getLoginSession();
	public void setLoginSession(LoginSession session);
	
	public List<BookExam> getCourse();
	public BookExam getCourse(String course);
	public void setCourse(List<BookExam> courses);
	
	public void notifyObservers();
	public void addObserver(Observer takeExamView);
	
	public BookExam getSelectedCourses();
	public void setSelectedCourses(BookExam selectedCourses);
	
	public void setProceedToRule(boolean b);
	public boolean isProceedToRule();
}
