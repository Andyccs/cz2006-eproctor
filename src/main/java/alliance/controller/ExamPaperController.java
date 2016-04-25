package alliance.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import alliance.controller.interfaces.ExamPaperControllerInterface;
import alliance.dBadapter.EProctorAdapter;
import alliance.dbaccess.UniversityDBController;
import alliance.dbaccess.interfaces.UniversityDBInterface;
import alliance.dbaccess.model.Exam;
import alliance.entity.ExaminationPaperQuestionOption;
import alliance.model.ExamPaperModel;
import alliance.model.interfaces.ExamPaperModelInterface;
import alliance.model.interfaces.ExamPaperModelInterface.State;
import alliance.util.ExamPaperParser;
import alliance.videocall.VideoCall;
import alliance.videocall.WebcamVideoCall;
import alliance.voicecall.SimpleVoiceCall;

public class ExamPaperController implements ExamPaperControllerInterface {
	private ExamPaperModelInterface model;
	
	public static final Logger logger = LoggerFactory.getLogger(ExamPaperController.class);
	
	private static final String XML_PATH = "META-INF/spring/EProctorAdapter.xml";
	
	private ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	
	private EProctorAdapter db;
	
	public ExamPaperController(ExamPaperModelInterface model) throws FileNotFoundException, IOException, ClassNotFoundException {
		this.model = model;
		
		db = context.getBean(EProctorAdapter.class);
	}
	/**
	 * Confirm forfeit 
	 */
	@Override
	public void forfeitConfirmation() {
		if(model.getState()!=ExamPaperModelInterface.State.EXAM){
			throw new IllegalStateException("Current state: "+ model.getState());
		}
		model.setState(State.FORFEIT);
		model.notifyObservers();
	}

	/**
	 * Confirm submission
	 */
	@Override
	public void submitConfirmation() {
		if(model.getState()!=ExamPaperModelInterface.State.EXAM){
			throw new IllegalStateException("Current state: "+ model.getState());
		}
		model.setState(State.SUBMIT);
		model.notifyObservers();
	}
	
	/**
	 * Submit forfeit
	 */
	@Override
	public void forfeit() {
		if(model.getState()!=ExamPaperModelInterface.State.FORFEIT){
			throw new IllegalStateException("Current state: "+ model.getState());
		}
		
		//delete the file
		java.io.File file = new java.io.File("ExamPaperModel");
		file.delete();
		
		model.setState(State.FORFEITED);
		model.notifyObservers();
	}
	
	/**
	 * Submit paper
	 */
	@Override
	public void submit() {
		if(!(model.getState()==ExamPaperModelInterface.State.SUBMIT||(
				model.getState()==ExamPaperModelInterface.State.EXAM&&model.getTime()==0))){
			throw new IllegalStateException("Current state: "+ model.getState());
		}
		
		//submit to database here
		String submit = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n\t<Submission>\n\t\t<id>";
		submit = submit.concat(model.getCourse().getExamID()).concat("</id>\n\t");
		for (int i=0;i<model.getAnswers().length;i++){
			submit = submit.concat("\t<Question number = '"+(i+1)+"'>\n\t\t\t");
			submit = submit.concat("<Answer>"+model.getAnswers()[i]+"</Answer>\n\t\t");
			submit = submit.concat("</Question>\n\t");
		}
		submit = submit.concat("</Submission>\n");
		db.setExamAns(
				model.getLoginSession().getAccount().getUsername(), 
				Integer.parseInt(model.getCourse().getExamID()), 
				submit);
		
		//close the video call
		try {
			WebcamVideoCall.getInstance().endVideoCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			SimpleVoiceCall.getInstance().endVoiceCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//delete the file
		java.io.File file = new java.io.File("ExamPaperModel");
		file.delete();
		
		//submit report
		db.submitProctorReport(model.getLoginSession().getAccount().getUsername(), model.getCourse().getCourseCode(), 
				"Number of Alert: 0\nForfeit Exam: No\nComment: Student behaves well");
		
		model.setState(State.SUBMITTED);	
		model.notifyObservers();
	}

	@Override
	public void initialize() throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException {
		if(model.getState()!=null){
			throw new IllegalStateException("Current state: "+ model.getState());
		}
		if(model.getLoginSession()==null || model.getCourse()==null){
			throw new NullPointerException("ID or course of model is null");
		}
		
		//check pass model if exist
		File file = new File("ExamPaperModel");
		ExamPaperModelInterface fileModel = null;
		if(file.exists()){
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
			fileModel = (ExamPaperModel) is.readObject();
		}
		
		if(fileModel!=null && fileModel.equals(model)){
			model.setExaminationPaperQuestionOption(fileModel.getExaminationPaperQuestionOption());
			
			String[] answer = fileModel.getAnswers();
			for (int i = 0; i < answer.length; i++) {
				model.setAnswers(i, answer[i]);
			}
			model.setTime(fileModel.getTime());
		}
		else{
			file.delete();
			
			Exam exam = db.getExamInfo(model.getCourse().getCourseCode()).get(0);
			String xml = exam.getQuestion();
			
			ArrayList<ExaminationPaperQuestionOption> paper = ExamPaperParser.parse(xml);
			model.setExaminationPaperQuestionOption(paper);

			model.setTime(model.getCourse().getDuration()*60*60*1000);
			
	//		debug purpose
	//		model.setTime(5000);
		}
		
		model.setState(State.STARTING);
		
		model.notifyObservers();
		
	}

	/**
	 * Records answer 
	 */
	@Override
	public void answerQuestion(int questionNo, String answer) {
		model.setAnswers(questionNo, answer);
		
		try {
			FileOutputStream file = new FileOutputStream("ExamPaperModel");
			ObjectOutputStream object = new ObjectOutputStream(file);
			object.writeObject((ExamPaperModel)model);
			object.close();
			logger.trace("write answer to file");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
