package alliance.voicecall;

import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import alliance.networking.server.VoiceSerializable;


public class NetworkSpeaker implements Observer,Runnable {

	private static NetworkSpeaker instance;
	SourceDataLine sourceDataLine;
	
	private boolean start;
	private boolean hasData;
	Thread worker;
	
	private byte[] voiceByte;
	
	private NetworkSpeaker() throws Exception{
		AudioFormat format = new AudioFormat(44000, 8, 2, true, true);
		DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format);
		if(!AudioSystem.isLineSupported(sourceInfo)){
			throw new Exception();
		}
		sourceDataLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
		sourceDataLine.open(format);
		hasData = false;
	}
	
	public void start(){
		if(!start){
			worker = new Thread(this);
			worker.start();
			
			start = true;
		}
	}
	
	public void stop(){
		if(start){
			start = false;
		}
	}
	
	public static NetworkSpeaker getInstance() throws Exception{
		if(instance==null){
			instance = new NetworkSpeaker();
		}
		return instance;
	}
	@Override
	public void update(Observable o, Object arg) {
		if(arg!=null && arg instanceof VoiceSerializable){
			VoiceSerializable voice = (VoiceSerializable)arg;
			voiceByte = voice.getVoice();
			hasData = true;
		}
	}
	@Override
	public void run() {
		while(start){
			if(hasData){	
				sourceDataLine.start();
				sourceDataLine.write(voiceByte, 0, voiceByte.length);
				hasData = false;
			}
		}
	}

}
