package alliance.videocall;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alliance.networking.client.Client;
import alliance.networking.server.Identity;
import alliance.networking.server.VideoSerializable;




public class SendImageObserver implements Observer{
	private static final Logger logger = LoggerFactory.getLogger(SendImageObserver.class);
	
	private Identity identity;

	public SendImageObserver(Identity identity) {
		this.identity = identity;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		logger.trace("sending image");
		if(arg!=null && arg instanceof BufferedImage){
			try {
				BufferedImage image = (BufferedImage) arg;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, "jpg", baos);
				baos.flush();
				byte[] imageInByte = baos.toByteArray();
				baos.close();
				Client.getInstance().sendObject(new VideoSerializable(imageInByte,identity));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
