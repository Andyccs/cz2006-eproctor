package alliance.model.interfaces;

import java.util.List;
import java.util.Observer;

import alliance.display.BookingTable;
import alliance.entity.LoginSession;

public interface BookExamModelInterface {

	public void setLoginSession(LoginSession ls);
	public LoginSession getLoginSession();

	public void addObserver(Observer bookExamView);
	public void notifyObservers();
	public void notifyObservers(Object o);

	public List<BookingTable> getCourseToTake();
	public void setCourseToTake(List<BookingTable> courses);
	
	public void setBookSuccess(Boolean b);
	public Boolean isBookSuccess();

	public BookingTable getCourseToTake(String courseCode);
	
	public void setErrorMessage(String message);
	public String getErrorMessage();

	
}
