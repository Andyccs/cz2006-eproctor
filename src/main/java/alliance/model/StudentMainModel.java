package alliance.model;

import java.util.Observable;
import java.util.Observer;

import alliance.entity.LoginSession;
import alliance.model.interfaces.StudentMainModelInterface;

public class StudentMainModel extends Observable implements StudentMainModelInterface{
	private int state;
	
	private LoginSession loginSession;

	/**
	 * Set the state of the model
	 * @param state				New state of the model
	 */
	@Override
	public void setState(int state) {
		this.state = state;
		setChanged();
		notifyObservers();
	}

	/**
	 * Get the state of the model
	 * @return State			Current state of the model
	 */
	@Override
	public int getState() {
		return state;
	}

	/**
	 * Set the login session
	 * @param loginSession		New login session
	 */
	@Override
	public void setLoginSession(LoginSession loginSession) {
		this.loginSession = loginSession;
	}

	/**
	 * Retrieve the current login session
	 * @return LoginSession		Current login session
	 */
	@Override
	public LoginSession getLoginSession() {
		return loginSession;
	}

	/**
	 * Add a new observer to the model
	 * @param observer			New observer
	 */
	@Override
	public void registerObserver(Observer observer) {
		addObserver(observer);
	}
}
