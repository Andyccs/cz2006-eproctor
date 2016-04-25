package alliance.controller;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import alliance.controller.interfaces.LoginControllerInterface;
import alliance.dBadapter.EProctorAdapter;
import alliance.dbaccess.EProctorDBController;
import alliance.entity.Account;
import alliance.model.interfaces.LoginModelInterface;



public class LoginController implements LoginControllerInterface{

	private LoginModelInterface model;
	
	public LoginController(LoginModelInterface model) {
		this.model = model;
	}
	
	/**
	 * Return boolean value that can then be known if the login
	 * is successful. If verification is successful, the actor
	 * will be presented with the respective UI.
	 * 
	 * @param accountName Username that is entered into the UI
	 * @param password	  Password that is entered into the UI
	 * @param domain      Domain that is specified into the UI
	 */
	public void rcvInput(String accountName, String pass, String priviledge) {
    	if(accountName==null || accountName.isEmpty()){
    		model.setUsernameEmpty(true);
    		model.notifyObservers();
    		return;
    	}

    	if(pass==null || pass.isEmpty()){
    		model.setPasswordEmpty(true);
    		model.notifyObservers();
    		return;
    	}

    	model.setUsernameEmpty(false);
    	model.setPasswordEmpty(false);
    	
    	//authenticate the user here
//    	EProctorAdapter proctorAdt = EProctorAdapter.getInstance(null);
    	EProctorAdapter proctorAdt  = new ClassPathXmlApplicationContext("META-INF/spring/EProctorAdapter.xml").getBean(EProctorAdapter.class);
    	proctorAdt.setLs(null);
    	
    	boolean authenticate = proctorAdt.authenticate(accountName, pass, priviledge);
    	
    	//if authenticate successfully, let them go to next UI
		if (authenticate) {
			model.setAccount(new Account(accountName, pass, priviledge));
			model.notifyObservers();
		}
		else{
			model.setAccount(null);
			model.notifyObservers();
		}
	}
}
