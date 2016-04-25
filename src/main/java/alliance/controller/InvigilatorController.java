package alliance.controller;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import alliance.controller.interfaces.InvigilatorControllerInterface;
import alliance.dBadapter.EProctorAdapter;
import alliance.dbaccess.EProctorDBController;
import alliance.dbaccess.UniversityDBController;
import alliance.dbaccess.interfaces.EProctorDBInterface;
import alliance.dbaccess.interfaces.UniversityDBInterface;
import alliance.dbaccess.model.BookExam;
import alliance.dbaccess.model.Student;
import alliance.model.RuleAndRegulationRemoteModel;
import alliance.model.interfaces.InvigilatorModelInterface;
import alliance.networking.server.Identity;
import alliance.videocall.VideoCall;
import alliance.videocall.VideoCallObserver;
import alliance.videocall.WebcamVideoCall;
import alliance.voicecall.SimpleVoiceCall;
import alliance.voicecall.VoiceCall;

public class InvigilatorController implements InvigilatorControllerInterface{
	
	private InvigilatorModelInterface model;
	
	private VideoCall videoCall;
	private VoiceCall voiceCall;
	
	private static final String XML_PATH = "META-INF/spring/EProctorAdapter.xml";
	
	private ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	
	private EProctorAdapter db;
	
	public InvigilatorController(InvigilatorModelInterface model) {
		this.model = model;
		db = context.getBean(EProctorAdapter.class);
	}

	/**
	 * Initialise the video call function for the inviligator to interact with the student
	 * during the taking of the exam
	 * 
	 */
	@Override
	public void startVideoCall() {
		VideoCallObserver videoObserver = new VideoCallObserver() {
			
			@Override
			public synchronized void updateSelfView(BufferedImage image) {
				model.setSelfImage(image);
				model.notifyObservers("SELF");
			}
			
			@Override
			public synchronized void updateOtherView(BufferedImage image, Identity identity) {
				model.setOtherImage(identity, image);
				model.notifyObservers(identity);
			}
		};
		
		videoCall = WebcamVideoCall.getInstance();
		voiceCall = SimpleVoiceCall.getInstance();
		
		try {
			Identity identity = new Identity(Identity.INVIGILATOR);
			identity.setId(model.getLoginSession().getAccount().getUsername());
			videoCall.startVideoCall(videoObserver, identity);
			voiceCall.startVoiceCall(identity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * Obtain the details of the assigned student for the inviligator
 * for authentication purposes
 * @param id	Identification code for the student
 */
	/**
	 * Retrieve student info from database
	 */
	@Override
	public void getStudentDetails(String id) {
		Student student = db.getStudentInfo(id);
		
		model.setStudentDetails(student);
	}
/**
 * Obtain the examination details of the assigned student for the inviligator
 * @param id	Identification code for the student
 * @param examId	Identification code for the exam paper
 */
	/**
	 * Retrieve exam details from database
	 */
	@Override
	public void getExamDetails(String id,String examId) {
		List<BookExam> examinations = db.getBookingSchedule(id);
		
		for (Iterator<BookExam> iterator = examinations.iterator(); iterator.hasNext();) {
			BookExam bookExam = iterator.next();
			if(bookExam.getExamID().equals(examId)){
				model.setBookExam(bookExam, id);
			}
		}
	
	}

	/**
	 * (Unfinished)
	 * Enables the proceed button after invigilator verifies student
	 */
	@Override
	public void allowStudentProceedToExamination(String ipaddress, String id) throws MalformedURLException, RemoteException, NotBoundException {
		//Added on 4/3/2014 without debugging
//		RuleAndRegulationRemoteModel remoteModel = 
//				(RuleAndRegulationRemoteModel) 
//				Naming.lookup("rmi://"+ipaddress+"/"+id);
//		remoteModel.setProceed(true);
	}

}
