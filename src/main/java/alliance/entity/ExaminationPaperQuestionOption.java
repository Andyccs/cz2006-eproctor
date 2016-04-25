package alliance.entity;

import java.io.Serializable;

public class ExaminationPaperQuestionOption implements Serializable{
	private String content;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	
	/**
	 * Getter of question content
	 * @return String
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * Setter of question content
	 * @param content		New question content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * Getter of option A content
	 * @return String
	 */
	public String getOptionA() {
		return optionA;
	}
	
	/**
	 * Setter of option A content
	 * @param optionA		New option A content
	 */
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	
	/**
	 * Getter for option A content
	 * @return String
	 */
	public String getOptionB() {
		return optionB;
	}
	
	/**
	 * Setter for option A content
	 * @param optionB		New option B content
	 */
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	
	/**
	 * Getter for option C content
	 * @return String
	 */
	public String getOptionC() {
		return optionC;
	}
	
	/**
	 * Setter for option C content
	 * @param optionC		New option C content
	 */
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	
	/**
	 * Getter for option D content
	 * @return String
	 */
	public String getOptionD() {
		return optionD;
	}
	
	/**
	 * Setter for option D content
	 * @param optionD		New option d content
	 */
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	
	
}
