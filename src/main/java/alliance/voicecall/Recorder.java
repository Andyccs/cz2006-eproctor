package alliance.voicecall;

import java.io.ByteArrayOutputStream;
import java.util.Observable;

import javax.sound.sampled.TargetDataLine;

public class Recorder extends Observable implements Runnable{
	TargetDataLine line;
	ByteArrayOutputStream out;
	
	boolean start;
	
	public Recorder(NetworkMicrophone microphone) {
		this.line = microphone.getLine();
	}
	
	@Override
	public void run() {
		int numberByte;
		byte[] data = new byte[line.getBufferSize()];
		
		line.start();
		start = true;
		
		while(start){
			try {
				if(out==null){
					out = new ByteArrayOutputStream();
				}
	
				numberByte = line.read(data, 0, data.length);
				out.write(data, 0, numberByte);
				setChanged();
				notifyObservers(out);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}
	
}
