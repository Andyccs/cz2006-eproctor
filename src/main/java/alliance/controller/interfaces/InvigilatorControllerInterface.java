package alliance.controller.interfaces;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public interface InvigilatorControllerInterface {

	public void startVideoCall();

	public void getStudentDetails(String id);
	
	public void getExamDetails(String id, String examId);
	
	public void allowStudentProceedToExamination(String ipaddress, String id) throws MalformedURLException, RemoteException, NotBoundException;
	
}
