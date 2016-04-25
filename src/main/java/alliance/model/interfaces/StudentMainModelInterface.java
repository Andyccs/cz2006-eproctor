package alliance.model.interfaces;

import java.util.Observer;

import alliance.entity.LoginSession;

public interface StudentMainModelInterface {
	public static final int STATE_ANNOUNCEMENT = 0;
	public static final int STATE_BOOK_EXAM = 1;
	public static final int STATE_SHOW_SCHEDULE = 2;
	public static final int STATE_TAKE_EXAM = 3;
	
	public void setState(int state);
	public int getState();
	
	public void setLoginSession(LoginSession loginSession);
	public LoginSession getLoginSession();
	
	public void registerObserver(Observer observer);
	
}
