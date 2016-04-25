package alliance.voicecall;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class NetworkMicrophone {
	TargetDataLine line;
	private static NetworkMicrophone instance;
	
	private NetworkMicrophone() throws Exception {
		AudioFormat format = new AudioFormat(44000, 8, 2, true, true);
		
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		
		if(!AudioSystem.isLineSupported(info)){
			throw new Exception();
		}
		
		line = (TargetDataLine) AudioSystem.getLine(info);
		line.open(format);
	}
	
	public static NetworkMicrophone getInstance() throws Exception{
		if(instance==null){
			instance = new NetworkMicrophone();
		}
		return instance;
	}

	public TargetDataLine getLine() {
		return line;
	}
	
}
