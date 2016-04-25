package alliance.videocall;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import alliance.networking.server.Identity;
import alliance.networking.server.VideoSerializable;

/**
 * When an image is captured or received, {@code VideoCallObserver}
 * will be notified. Users of {@code VideoCall} need to implement
 * this class.
 * @author Andy
 *
 */
public abstract class VideoCallObserver implements Observer{
	@Override
	public final void update(Observable o, Object arg) {
		if(arg instanceof BufferedImage){
			updateSelfView((BufferedImage)arg);
		}
		else if(arg instanceof VideoSerializable){
			try {
				VideoSerializable vs = (VideoSerializable) arg;
				ByteArrayInputStream bais = 
						new ByteArrayInputStream(vs.getVideoImage());
				BufferedImage image = ImageIO.read(bais);
				updateOtherView(image, vs.getIdentity());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method will be called when an image is captured by 
	 * webcam. 
	 * @param image
	 * The image that is captured by webcam
	 */
	public abstract void updateSelfView(BufferedImage image);
	
	/**
	 * This method will be called when an image is received 
	 * from the network
	 * @param image
	 * The received image
	 * @param identity
	 * The identity of the sender. The {@code ipAdress} attribute
	 * of the {@code identity} is unique for each sender
	 */
	public abstract void updateOtherView(BufferedImage image,Identity identity);
	
	
}
