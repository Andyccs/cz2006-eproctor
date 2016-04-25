package alliance.videocall;

import alliance.networking.server.Identity;

/**
 * {@code VideoCall} provides an interface to start 
 * and stop a video call for eProtor application.
 * The video call here refer to only video without 
 * any sound. Only image from webcam will be sent to
 * other {@code VideoCall} client.
 * @author Andy
 *
 */
public interface VideoCall {
	
	/**
	 * Start a video call
	 * @param observer 
	 * this observer will be notified when an image is 
	 * captured by webcam or an image is received 
	 * from the network
	 * @param identity
	 * specified the identity of this video call client,
	 * whether this client is a Identity.STUDENT or
	 * Identity.INVIGILATOR
	 * @throws Exception
	 * the webcam is not found
	 * cannot connect to host
	 * port is being used
	 */
	public void startVideoCall(VideoCallObserver observer,
			Identity identity) throws Exception;
	
	/**
	 * End a video call
	 * @throws Exception 
	 */
	public void endVideoCall() throws Exception;
}
