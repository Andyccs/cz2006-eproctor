package alliance.entity;

import java.io.Serializable;

public class LoginSession implements Serializable{
	
	private Account acc;
	
	public LoginSession (Account acc) {
		this.acc = acc;
	}
	
	/**
	 * Getter for account
	 * @return Account
	 */
	public Account getAccount() {
		return acc;
	}
	
	

}
