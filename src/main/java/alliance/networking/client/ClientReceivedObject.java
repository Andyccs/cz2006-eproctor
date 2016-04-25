package alliance.networking.client;

import java.util.Observable;

public class ClientReceivedObject extends Observable {
	Object received;
	private static ClientReceivedObject instance;
	
	private ClientReceivedObject() {
		// TODO Auto-generated constructor stub
	}
	public static ClientReceivedObject getInstance(){
		if(instance==null){
			instance = new ClientReceivedObject();
		}
		return instance;
	}
	
	public Object getReceived() {
		return received;
	}

	public void setReceived(Object received) {
		this.received = received;
		setChanged();
	}
	
}
