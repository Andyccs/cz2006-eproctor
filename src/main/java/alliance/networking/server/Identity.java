package alliance.networking.server;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Identity implements Serializable{
	public transient static final String STUDENT = "student";
	public transient static final String INVIGILATOR = "invigilator";
	private String name;
	private String ipAddress;
	private String id;
	private String examId;
	
	public Identity(String identity) throws UnknownHostException {
		this.name = identity;
		ipAddress = InetAddress.getLocalHost().getHostAddress();
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
	 * @param identity
	 */
	public void setName(String identity) {
		this.name = identity;
	}

	/**
	 * Getter for ip address
	 * @return
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * Getter for ID
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter for ID
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter for exam ID
	 * @return
	 */
	public String getExamId() {
		return examId;
	}

	/**
	 * Setter for exam ID
	 * @param examId
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	
}
