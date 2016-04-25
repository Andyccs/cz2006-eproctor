package alliance.videocall;

import alliance.networking.client.Client;
import alliance.networking.interfaces.ClientInterface;
import alliance.networking.server.Identity;

public class WebcamVideoCall implements VideoCall{
	
	private ClientInterface client;
	
	private CaptureImageRunnable captureImage;
	private Thread captureImageWorker;

	
	private static WebcamVideoCall instance;
	
	private WebcamVideoCall() {
		client = Client.getInstance();
		captureImage = new CaptureImageRunnable();
	}
	
	public static WebcamVideoCall getInstance(){
		if(instance==null){
			instance = new WebcamVideoCall();
		}
		return instance;
	}
	
	@Override
	public void startVideoCall(VideoCallObserver observer, Identity identity) throws Exception {
		client.registerObserver(observer);
		captureImage.addObserver(observer);
		captureImage.addObserver(new SendImageObserver(identity));
		
		client.start(identity);
		
		captureImageWorker = new Thread(captureImage);
		captureImageWorker.setName("CaptureImageWorker");
		captureImageWorker.start();
		captureImageWorker.setPriority(Thread.MAX_PRIORITY);
	
	}

	@Override
	public void endVideoCall() throws Exception {
		captureImage.setStart(false);
		captureImage.deleteObservers();
		client.stop();
	}

}
