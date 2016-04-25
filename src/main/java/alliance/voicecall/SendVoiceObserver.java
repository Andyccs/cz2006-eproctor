package alliance.voicecall;

import java.io.ByteArrayOutputStream;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alliance.networking.client.Client;
import alliance.networking.server.Identity;
import alliance.networking.server.VoiceSerializable;




public class SendVoiceObserver implements Observer {
	private static final Logger logger = LoggerFactory.getLogger(SendVoiceObserver.class);
	private Identity identity;
	
	public SendVoiceObserver(Identity identity) {
		this.identity = identity;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		logger.trace("sending voice");
		if(arg!=null && arg instanceof ByteArrayOutputStream){
			ByteArrayOutputStream out = (ByteArrayOutputStream)arg;
			byte[] voiceInByte = out.toByteArray();
			out.reset();
			Client.getInstance().sendObject(new VoiceSerializable(voiceInByte,identity));
		}
	}

}
