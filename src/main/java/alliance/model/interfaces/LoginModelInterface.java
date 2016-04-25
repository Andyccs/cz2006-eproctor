package alliance.model.interfaces;

import java.util.Observer;

import alliance.entity.Account;

public interface LoginModelInterface {
	public boolean isUsernameEmpty();
	public void setUsernameEmpty(boolean usernameEmpty);
	public boolean isPasswordEmpty();
	public void setPasswordEmpty(boolean passwordEmpty);
	
	public Account getAccount();
	public void setAccount(Account privilage);
	public void registerObserver(Observer observer);
	public void notifyObservers();
}
