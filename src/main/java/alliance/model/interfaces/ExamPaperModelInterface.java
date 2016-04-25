package alliance.model.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Observer;

import alliance.dbaccess.model.BookExam;
import alliance.entity.ExaminationPaperQuestionOption;
import alliance.entity.LoginSession;

public interface ExamPaperModelInterface extends Serializable {
	public enum State {STARTING,EXAM, SUBMIT, FORFEIT, SUBMITTED, FORFEITED}
	
	public State getState();
	public void setState(State state);
	
	public BookExam getCourse();
	public void setCourse(BookExam course);
	
	public LoginSession getLoginSession();
	public void setLoginSession(LoginSession id);
	
	public String[] getAnswers();
	public void setAnswers(int questionNo, String ans);
	
	public long getTime();
	public void setTime(long interval);
	
	public void registerObserver(Observer observer);
	public void notifyObservers();
	
	public void setExaminationPaperQuestionOption(List<ExaminationPaperQuestionOption> paper);
	public List<ExaminationPaperQuestionOption> getExaminationPaperQuestionOption();

}
