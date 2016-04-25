package alliance.dbaccess.model;

import java.io.Serializable;

public class Account implements Serializable{
	
	private String username;
	private String password;

	public Account(String username,String password){
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Getter for username
	 * @return String		Username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Setter for username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Getter for password
	 * @return String		Password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Setter for password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}	
}
