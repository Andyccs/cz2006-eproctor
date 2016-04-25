package alliance.voicecall;

import alliance.networking.client.Client;
import alliance.networking.interfaces.ClientInterface;
import alliance.networking.server.Identity;


public class SimpleVoiceCall implements VoiceCall{

//	String host;
//	Integer port;
	
	ClientInterface client;
	
	NetworkMicrophone microphone;
	Recorder recorder;
	
	Thread worker;
	
	private static SimpleVoiceCall instance;
	
	private SimpleVoiceCall() {
		client = Client.getInstance();
	}
	
	public static SimpleVoiceCall getInstance(){
		if(instance==null){
			instance = new  SimpleVoiceCall();
		}
		return instance;
	}
	
	@Override
	public void startVoiceCall(Identity identity) throws Exception {
		microphone = NetworkMicrophone.getInstance();
		recorder = new Recorder(microphone);
		recorder.addObserver(new SendVoiceObserver(identity));
		
		client.registerObserver(NetworkSpeaker.getInstance());
		NetworkSpeaker.getInstance().start();
		
		client.start(identity);
		
		worker = new Thread(recorder);
		worker.setPriority(Thread.MAX_PRIORITY);
		worker.start();
	}

	@Override
	public void endVoiceCall() throws Exception {
		recorder.deleteObservers();
		recorder.setStart(false);
		client.stop();
		NetworkSpeaker.getInstance().stop();
	}

	@Override
	public void mute(boolean flag) throws Exception {
		if(flag){
			NetworkSpeaker.getInstance().stop();
		}
		else{
			NetworkSpeaker.getInstance().start();
		}
	}

}
