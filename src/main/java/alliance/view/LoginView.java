package alliance.view;


import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import alliance.controller.AdministratorUIController;
import alliance.controller.LoginController;
import alliance.controller.interfaces.LoginControllerInterface;
import alliance.entity.Account;
import alliance.entity.LoginSession;
import alliance.model.LoginModel;
import alliance.model.interfaces.LoginModelInterface;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class LoginView implements Initializable,Observer{
	
    private Parent parent;
    private Scene scene;
    private Stage stage;
    
    @FXML
    private Button login, quit;
    
    @FXML
    private PasswordField password;
    
    @FXML
    private TextField username;
    
    @FXML
    private ComboBox<String> domain;
    
    @FXML
    private Label actiontarget;
    
    LoginControllerInterface loginController;
    
    LoginModelInterface loginModel;
    
    private static final Logger logger = LoggerFactory.getLogger(LoginView.class);
    
    public LoginView() {
    	//initialize controller and model here 
    	loginModel = new LoginModel();
    	loginController = new LoginController(loginModel);
		
    	loginModel.registerObserver(this);
    	
    	FXMLLoader fxmlLoader = null;
    	try {
        	fxmlLoader = new FXMLLoader(LoginView.class.getResource("/alliance/view/LoginUI.fxml"));
        	fxmlLoader.setController(this);    
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	
    }
    
    @Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    	
    	login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            	
            	logger.trace(username.getText() + " " + password.getText() + " " + domain.getValue().toString());
            	
            	loginController.rcvInput(username.getText(), password.getText(), domain.getValue().toString());
            	
            }
        });
    	
    	quit.setOnAction(new EventHandler<ActionEvent>() {
    		
    		@Override
    		public void handle(ActionEvent event) {
    			Platform.exit();
    		}
    	});

    }
	
    
	public void launch(Stage stage) {

    	
        this.stage = stage;
        stage.setTitle("E-Proctor");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.hide();
        stage.show();
    }

	@Override
	public void update(Observable o, Object arg) {
		actiontarget.setTextFill(Color.FIREBRICK);
		if(loginModel.isPasswordEmpty() || loginModel.isUsernameEmpty()){
			actiontarget.setText("Some fields are empty");
		}
		else{
			actiontarget.setText("");
		}
		
		if(loginModel.getAccount()!=null) {
			Account account = loginModel.getAccount();
			if(account.getDomain().equals("STUDENT")){
				StudentView view = new StudentView(new LoginSession(account));
				view.home(stage);
			}
			else if(account.getDomain().equals("INVIGILATOR")){
				InvigilatorMonitorExamView view = new InvigilatorMonitorExamView(new LoginSession(account));
				view.load(stage);
			}
			else if(account.getDomain().equals("ADMINISTRATOR")){
				AdministratorUIController view = new AdministratorUIController(new LoginSession(account));
				view.home();
			}
		}
		else{
			actiontarget.setText("Login failed / Server errors");
		}
	}
}


	



