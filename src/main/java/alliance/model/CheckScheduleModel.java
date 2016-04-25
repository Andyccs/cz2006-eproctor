package alliance.model;

import java.util.List;
import java.util.Observable;

import alliance.dbaccess.model.BookExam;
import alliance.entity.LoginSession;
import alliance.model.interfaces.CheckScheduleModelInterface;

public class CheckScheduleModel extends Observable implements CheckScheduleModelInterface{
	
	private LoginSession session;
	
	private List<BookExam> examination;
	
	/**
	 * Setter for LoginSession
	 * @param ls
	 */
	@Override
	public void setLoginSession(LoginSession ls) {
		session = ls;
	}

	/**
	 * Getter for LoginSession
	 * @return LoginSession
	 */
	@Override
	public LoginSession getLoginSession() {
		return session;
	}

	/**
	 * Set the examination schedule
	 * @param exam
	 */
	@Override
	public void setExamination(List<BookExam> exam) {
		examination = exam;
		setChanged();
	}

	/**
	 * Get a list of exam schedules
	 * @return List<BookExam>
	 */
	@Override
	public List<BookExam> getExaminations() {
		return examination;
	}

}
