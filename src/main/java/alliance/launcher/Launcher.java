package alliance.launcher;

import alliance.entity.Account;
import alliance.entity.LoginSession;
import alliance.view.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 
 * Run this launcher to start up the application
 *
 */
public class Launcher extends Application{
	
	public static void main(String[] args) {
        launch(args);
    }
	
	@Override
	public void start(Stage primaryStage) {
		LoginView loginView = new LoginView();
		loginView.launch(primaryStage);
	}
}