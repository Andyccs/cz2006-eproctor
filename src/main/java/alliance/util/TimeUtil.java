package alliance.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class TimeUtil {
	
	private SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
	private SimpleDateFormat tf = new SimpleDateFormat("h:mm:ss a");
	
	private static TimeUtil uniqueInstance;
		
	private TimeUtil() {
	}
	
	public static TimeUtil getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new TimeUtil();
		}
		return uniqueInstance;
	}
	
	public void showDateTime(Label DateNow, Label TimeNow) {
		
		Task<Void> dateTask = new Task<Void>(){
			@Override
		    protected Void call() throws Exception {	        
		        while (true) {
		            updateMessage(df.format(new Date()));
		            try {
		                Thread.sleep(100);
		            } catch (InterruptedException ex) {
		                break;
		            }
		        }
		        return null;
		    }
		};
		DateNow.textProperty().bind(dateTask.messageProperty());
		Thread t = new Thread(dateTask);
		t.setName("DateUpdater");
		t.setDaemon(true);
		t.start();
		Task<Void> timeTask = new Task<Void>(){
			@Override
		    protected Void call() throws Exception {	        
		        while (true) {
		            updateMessage(tf.format(new Date()));
		            try {
		                Thread.sleep(100);
		            } catch (InterruptedException ex) {
		                break;
		            }
		        }
		        return null;
		    }
		};
		TimeNow.textProperty().bind(timeTask.messageProperty());
		Thread t2 = new Thread(timeTask);
		t2.setName("TimeUpdater");
		t2.setDaemon(true);
		t2.start();
		
	}
}
