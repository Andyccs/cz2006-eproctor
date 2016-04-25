package alliance.voicecall;

import alliance.networking.server.Identity;

/**
 * {@code VoiceCall} provides an interface to start 
 * and stop a voice call for eProtor application. 
 * @author Andy
 */
public interface VoiceCall {
	
	/**
	 * Start a voice call
	 * @param identity
	 * specified the identity of this video call client,
	 * whether this client is a Identity.STUDENT or
	 * Identity.INVIGILATOR
	 * @throws Exception
	 * the microphone is not found
	 * cannot connect to host
	 * port is being used
	 */
	public void startVoiceCall(Identity identity) throws Exception;
	
	/**
	 * End a voice call
	 * @throws Exception 
	 */
	public void endVoiceCall() throws Exception;
	
	/**
	 * 
	 * @param flag 
	 * if true, mute the microphone
	 * it false, open the microphone
	 * @throws Exception
	 */
	public void mute(boolean flag) throws Exception;
}
