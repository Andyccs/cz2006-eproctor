package alliance.entity;

import java.io.Serializable;

public class Account implements Serializable{

	private String username;
	private String password;
	private String domain;

	public Account() {
	}

	public Account(String username, String password, String domain) {
		this.username = username;
		this.password = password;
		this.domain = domain;
	}

	/**
	 * Getter for user name
	 * @return String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter for user name
	 * @param username		New user name
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter for password
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for password
	 * @param password		New password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for domain
	 * @return String
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Setter for domain
	 * @param domain		New domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

}
