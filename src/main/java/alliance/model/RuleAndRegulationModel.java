package alliance.model;


import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import alliance.dbaccess.model.BookExam;
import alliance.entity.LoginSession;
import alliance.model.interfaces.RuleAndRegulationModelInterface;
import alliance.networking.server.Identity;

public class RuleAndRegulationModel extends Observable implements RuleAndRegulationModelInterface{
	
	private LoginSession login;
	
	private boolean permission;
	
	private BufferedImage selfImage;
	
	private BufferedImage otherImage;
	
	private Identity invigilatorId;
	
	private BookExam examination;
	
	/**
	 * Set new student details
	 * @param login				The login session that holds student details
	 */
	@Override
	public void setStudentDetails(LoginSession login) {
		this.login = login;
	}

	/**
	 * Get student details
	 * @return LoginSession		The student details are within the LoginSession
	 */
	@Override
	public LoginSession getStudentDetails() {
		return login;
	}
	
	/**
	 * Set the permission for the student to proceed with exam
	 * @param proceed			The permission value
	 */
	@Override
	public void setProceedPermission(boolean proceed) {
		this.permission = proceed;
		setChanged();
	}

	/**
	 * Retrieve the permission to see if student allow to take exam
	 * @return boolean			The permission
	 */
	@Override
	public boolean getProceedPermission() {
		return permission;
	}

	/**
	 * Get the image of own webcam view
	 * @return BufferedImage	Own image
	 */
	@Override
	public BufferedImage getSelfImage() {
		return selfImage;
	}

	/**
	 * Set the webcam view for own image
	 * @param image				Own image
	 */
	@Override
	public void setSelfImage(BufferedImage image) {
		this.selfImage = image;
		setChanged();
	}

	/**
	 * Get the image of invigilator's webcam view
	 * @return otherImage		Invigilator's image
	 */
	@Override
	public BufferedImage getInvigilatorImage() {
		return otherImage;
	}

	/**
	 * Set the webcam view of invigilator's image
	 * @param image				Invigilator's image
	 */
	@Override
	public void setInvigilatorImage(BufferedImage image) {
		this.otherImage = image;
		setChanged();
	}

	/**
	 * Get the identity of invigilator
	 * @return Identity			Identity of invigilator
	 */
	@Override
	public Identity getInvigilatorID() {
		return invigilatorId;
	}

	/**
	 * Set the id of Invigilator
	 * @param id				Identifier for invigilator
	 */
	@Override
	public void setInvigilatorID(Identity id) {
		this.invigilatorId = id;
	}

	/**
	 * Add new observer to the model
	 * @param observer			New observer
	 */
	@Override
	public void registerObserver(Observer observer) {
		addObserver(observer);
	}

	/**
	 * Retrieve the examination details
	 * @return BookExam			Exams details contained in BookExam object
	 */
	@Override
	public BookExam getExamination() {
		return examination;
	}

	/**
	 * Set the examination details
	 * @param examination		Exams details that are in BookExam object
	 */
	@Override
	public void setExamination(BookExam examination) {
		this.examination = examination;
	}
	
	
	
}
