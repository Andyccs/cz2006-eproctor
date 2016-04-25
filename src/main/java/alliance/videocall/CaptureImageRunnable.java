package alliance.videocall;

import java.awt.image.BufferedImage;
import java.util.Observable;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

public class CaptureImageRunnable extends Observable implements Runnable{

	Webcam webcam;
	
	BufferedImage image;
	
	private volatile boolean start;
	
	public CaptureImageRunnable() {
//		Dimension[] nonStandardResolutions = new Dimension[] {
//				WebcamResolution.HD720.getSize(),
//				WebcamResolution.PAL.getSize(),
//				new Dimension(640, 480)
//			};
		
		webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		
		
	}
	
	@Override
	public void run() {
		webcam.open();
		start = true;
		while(start){
			try {
				image = webcam.getImage();
				setChanged();
				notifyObservers(image);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		webcam.close();
		
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}
	
}
