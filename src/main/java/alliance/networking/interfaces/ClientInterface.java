package alliance.networking.interfaces;

import java.util.Observer;

import alliance.networking.client.Client;
import alliance.networking.server.Identity;

/**
 * {@code ClientInterface} is a network client that is specially 
 * design for eProtor application.
 * {@code ClientInterface} is responsible to handle network 
 * communication. It must be able to start(), stop(), 
 * sendObject(), and receive object. When is received an
 * object from the network, it will notify all its observer.
 * An observer can be added by calling the registerObserver()
 * method.
 * @author Andy
 *
 */
public interface ClientInterface {
	
	/**
	 * Start the client
	 * @param identity 
	 * Identity of client, whether it is a {@code Identity.STUDENT}
	 * or {@code Identity.INVIGILATOR}
	 * @throws Exception 
	 * When the client cannot connect to a host. Change the
	 * properties file under META-INF/spring/network.properties
	 * to change the host address and port number.
	 * @see Identity
	 * @see Client
	 */
	public void start(Identity identity) throws Exception;
	
	/**
	 * Stop the client. Client will stop gracefully
	 * @throws Exception
	 */
	public void stop() throws Exception;
	
	/**
	 * Send an object to the network. If Client is 
	 * {@code Identity.STUDENT}, the object will be send to 
	 * another client whom Identity is 
	 * {@code Identity.INVIGILATOR} and vice versa .
	 * @param object the object to be sent
	 * @see Observer
	 */
	public void sendObject(Object object);
	
	/**
	 * Add an observer of {@code ClientInterface}. When 
	 * {@code ClientInterface} receive an Object from 
	 * the network, it will notify all its observer.
	 * @param observer
	 * @see Observer
	 */
	public void registerObserver(Observer observer);
	
	/**
	 * delete an observer
	 * @param observer
	 */
	public void deregisterObserver(Observer observer);
	
	public boolean isRunning();
}
