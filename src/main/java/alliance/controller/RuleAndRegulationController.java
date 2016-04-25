package alliance.controller;

import java.awt.image.BufferedImage;

import alliance.controller.interfaces.RuleAndRegulationControllerInterface;
import alliance.model.interfaces.RuleAndRegulationModelInterface;
import alliance.networking.server.Identity;
import alliance.videocall.VideoCall;
import alliance.videocall.VideoCallObserver;
import alliance.videocall.WebcamVideoCall;
import alliance.voicecall.SimpleVoiceCall;
import alliance.voicecall.VoiceCall;



public class RuleAndRegulationController implements RuleAndRegulationControllerInterface{
	
	private RuleAndRegulationModelInterface model;
	
	private VideoCall videoCall;
	
	private VoiceCall voiceCall;
	
	public RuleAndRegulationController(RuleAndRegulationModelInterface model) {
		this.model = model;
	}

	/**
	 * Start video call for student
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
				model.setInvigilatorImage(image);
				model.setInvigilatorID(identity);
				model.notifyObservers("OTHER");
			}
		};
		videoCall = WebcamVideoCall.getInstance();
		voiceCall = SimpleVoiceCall.getInstance();
		
		try {
			Identity identity = new Identity(Identity.STUDENT);
			identity.setId(model.getStudentDetails().getAccount().getUsername());
			identity.setExamId(model.getExamination().getExamID());
			videoCall.startVideoCall(videoObserver, identity);
			voiceCall.startVoiceCall(identity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
