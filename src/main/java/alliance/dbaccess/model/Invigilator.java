package alliance.dbaccess.model;

import java.io.Serializable;

public class Invigilator implements Serializable{
	
	private static final long serialVersionUID = 7675967296112447058L;
	private String invigilatorID;
	private String username;
	private String password;
	private String name;
	private String address;
	private int postalCode;
	private int phoneNumber;

	public Invigilator(String invigilatorID, String username, String password, String name, String address, int postalCode, int phoneNumber) {
		this.invigilatorID = invigilatorID;
		this.username = username;
		this.password = password;
		this.name = name;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Getter for username
	 * @return
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
	 * @return
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

	/**
	 * Getter for name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter for name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for address
	 * @return
	 */
	public String getAddress(){
		return address;
	}
	
	/**
	 * Setter for address
	 * @param address
	 */
	public void setAddress(String address){
		this.address = address;
	}
	
	/**
	 * Getter for postal code
	 * @return
	 */
	public int getPostalCode(){
		return postalCode;
	}
	
	/**
	 * Setter for postal code
	 * @param postalCode
	 */
	public void setPostalCode(int postalCode){
		this.postalCode = postalCode;
	}
	
	/**
	 * Getter for phone number
	 * @return
	 */
	public int getPhoneNumber(){
		return phoneNumber;
	}
	
	/**
	 * Setter for phone number
	 * @param phoneNumber
	 */
	public void setPhoneNumber(int phoneNumber){
		this.phoneNumber= phoneNumber;
	}
	
	/**
	 * Getter for invigilator ID
	 * @return
	 */
	public String getInvigilatorID() {
		return invigilatorID;
	}

	/**
	 * Setter for invigilator ID
	 * @param invigilatorID
	 */
	public void setInvigilatorID(String invigilatorID) {
		this.invigilatorID = invigilatorID;
	}

}
