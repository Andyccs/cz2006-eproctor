package alliance.entity;

import java.io.Serializable;

public class Invigilator extends Actor implements InvigilatorInterface,Serializable{
	
	private String name;
	private String address;
	private int postalCode;
	private String phoneNumber;
	private String invigilatorID;
	private Account acc;
	
	public Invigilator() {
		super(new Account());
	}
	
	public Invigilator(String name, String address, int postalCode, String phoneNumber, Account acc, String invigilatorID) {
		super(acc);
		this.invigilatorID = invigilatorID;
		this.name = name;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Getter for name
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter for name
	 * @param name				New name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for address
	 * @return String
	 */
	public String getAddress(){
		return address;
	}
	
	/**
	 * Setter for address
	 * @param address			New Address
	 */
	public void setAddress(String address){
		this.address = address;
	}
	
	/**
	 * Getter for postal code
	 * @return int
	 */
	public int getPostalCode(){
		return postalCode;
	}
	
	/**
	 * Setter for postal code
	 * @param postalCode		New postal code
	 */
	public void setPostalCode(int postalCode){
		this.postalCode = postalCode;
	}
	
	/**
	 * Getter for phone number
	 * @return String
	 */
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	/**
	 * Setter for phone number
	 * @param phoneNumber		New phone number
	 */
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber= phoneNumber;
	}
	
	/**
	 * Getter for invigilator ID
	 * @return String
	 */
	public String getInvigilatorID() {
		return invigilatorID;
	}

	/**
	 * Setter for invigilator ID
	 * @param invigilatorID		New invigilator ID
	 */
	public void setInvigilatorID(String invigilatorID) {
		this.invigilatorID = invigilatorID;
	}

}
