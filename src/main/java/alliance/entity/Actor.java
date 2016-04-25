package alliance.entity;

import java.io.Serializable;

public abstract class Actor implements Serializable{
	
	private Account acc;
	
	public Actor(Account acc) {
		this.acc = acc;
	}

	/**
	 * Getter for account
	 * @return
	 */
	public Account getAccount() {
		return acc;
	}
	
	/**
	 * Setter for account
	 * @param username		New username for account
	 * @param password		New password for account
	 * @param domain		Domain of account
	 */
	public void setAccount(String username, String password, String domain) {
		acc.setUsername(username);
		acc.setPassword(password);
		acc.setDomain(domain);
	}
	
}
