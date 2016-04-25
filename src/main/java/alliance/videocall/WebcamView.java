package alliance.videocall;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebcamView extends ImageView implements Observer{
	private static final Logger logger = LoggerFactory.getLogger(WebcamView.class);

	@Override
	public void update(Observable o, Object arg) {
		if(arg!=null && arg instanceof byte[]){
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream((byte[])arg);
				BufferedImage image = ImageIO.read(bais);
				setImage(SwingFXUtils.toFXImage(image, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(arg!=null && arg instanceof BufferedImage){
			setImage(SwingFXUtils.toFXImage((BufferedImage)arg, null));
		}
	}
}
