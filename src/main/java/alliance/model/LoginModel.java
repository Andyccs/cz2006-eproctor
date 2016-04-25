package alliance.model;

import java.util.Observable;
import java.util.Observer;

import alliance.entity.Account;
import alliance.model.interfaces.LoginModelInterface;

public class LoginModel extends Observable implements LoginModelInterface{
	private boolean usernameEmpty;
	private boolean passwordEmpty;
	
	private Account account;
	
	/**
	 * Check if the "user name field is empty" flag is raised
	 * @return boolean			Result of check
	 */
	public boolean isUsernameEmpty() {
		return usernameEmpty;
	}
	
	/**
	 * Set the value of the flag for user name empty
	 * @param usernameEmpty		Result to set to the flag
	 */
	public void setUsernameEmpty(boolean usernameEmpty) {
		this.usernameEmpty = usernameEmpty;
		setChanged();
	}
	
	/**
	 * Check if the "password field is empty" flag is raised
	 * @return boolean			Result of check
	 */
	public boolean isPasswordEmpty() {
		return passwordEmpty;
	}
	
	/**
	 * Set the value of the flag for password is empty
	 * @param passwordEmpty		Result to set to the flag
	 */
	public void setPasswordEmpty(boolean passwordEmpty) {
		this.passwordEmpty = passwordEmpty;
		setChanged();
	}
	/**
	 * Add an new observer
	 * @param observer			The new observer
	 */
	@Override
	public void registerObserver(Observer observer) {
		addObserver(observer);
	}
	
	/**
	 * Get account information
	 * @return Account			The object holding account information
	 */
	public Account getAccount() {
		return account;
	}
	
	/**
	 * Set new account information
	 * @param account			The object holding new account information
	 */
	public void setAccount(Account account) {
		this.account = account;
		setChanged();
	}
	
}
