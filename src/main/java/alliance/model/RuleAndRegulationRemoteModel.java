package alliance.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import alliance.model.interfaces.RuleAndRegulationModelInterface;

public class RuleAndRegulationRemoteModel extends UnicastRemoteObject implements Remote{
	
	private RuleAndRegulationModelInterface model;
	
	public RuleAndRegulationRemoteModel(RuleAndRegulationModelInterface model) throws RemoteException {
		this.model = model;
	}

	/**
	 * Set proceed permission
	 * @param b
	 */
	public void setProceed(boolean b){
		model.setProceedPermission(b);
	}
}
