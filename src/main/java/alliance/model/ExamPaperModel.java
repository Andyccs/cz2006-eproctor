package alliance.model;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import alliance.dbaccess.model.BookExam;
import alliance.entity.ExaminationPaperQuestionOption;
import alliance.entity.LoginSession;
import alliance.model.interfaces.ExamPaperModelInterface;

public class ExamPaperModel extends Observable implements ExamPaperModelInterface,Serializable {
	private State state;
	private BookExam course;
	private alliance.entity.LoginSession loginSession;
	private String[] answer;
	private long interval;
	private List<ExaminationPaperQuestionOption> paper;

	/**
	 * Getter for state
	 * @return State		State of exam paper model
	 */
	@Override
	public State getState() {
		return state;
	}

	/**
	 * Setter for state
	 * @param state			New state
	 */
	@Override
	public void setState(State state) {
		this.state = state;
		setChanged();
	}

	/**
	 * Getter for course
	 * @return course		Course of exam paper
	 */
	@Override
	public BookExam getCourse() {
		return course;
	}

	/**
	 * Setter for course
	 * @param 				New course information for exam paper
	 */
	@Override
	public void setCourse(BookExam course) {
		this.course = course;
		setChanged();
	}

	/**
	 * Getter for LoginSession
	 * @return LoginSession
	 */
	@Override
	public alliance.entity.LoginSession getLoginSession() {
		return loginSession;
	}

	/**
	 * Setter for LoginSession
	 * @param id
	 */
	@Override
	public void setLoginSession(LoginSession id) {
		this.loginSession = id;
		setChanged();
	}

	/**
	 * Get an array of answers
	 */
	@Override
	public String[] getAnswers() {
		return answer;
	}

	/**
	 * Set answer to the array
	 * @param questionNo		(Question number-1) and the index of array
	 * @param ans				Answer made by student
	 */
	@Override
	public void setAnswers(int questionNo, String ans) {
		answer[questionNo] = ans;
		setChanged();
	}

	/**
	 * Get the duration of the exam
	 * @return long
	 */
	@Override
	public long getTime() {
		return interval;
	}

	/**
	 * Set the duration of the exam
	 * @param interval
	 */
	@Override
	public void setTime(long interval) {
		this.interval = interval;
		setChanged();
	}

	/**
	 * Add an observer to the subject
	 * @param observer
	 */
	@Override
	public void registerObserver(Observer observer) {
		addObserver(observer);
	}

	/**
	 * Set up the exam paper on observers using a list of exam paper question options
	 * @param paper
	 */
	@Override
	public void setExaminationPaperQuestionOption(
			List<ExaminationPaperQuestionOption> paper) {
		answer = new String[paper.size()];
		this.paper = paper;
		setChanged();
	}

	/**
	 * Retrieve the exam paper
	 * @return List<ExaminationPaperQuestionOption>
	 */
	@Override
	public List<ExaminationPaperQuestionOption> getExaminationPaperQuestionOption() {
		return paper;
	}
	
	/**
	 * Check if the argument(obj) is an exam paper with matching ID and login session
	 * @param obj
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ExamPaperModel){
			ExamPaperModel newModel = (ExamPaperModel) obj;
			if(newModel.getCourse().getExamID().equals(course.getExamID())&&
				newModel.getLoginSession().getAccount().getUsername().equals(loginSession.getAccount().getUsername())){
				return true;
			}
		}
		return false;
	}

}
