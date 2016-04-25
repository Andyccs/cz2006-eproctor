package alliance.launcher;


import javafx.application.Application;
import javafx.stage.Stage;
import alliance.dbaccess.EProctorDBController;
import alliance.entity.Account;
import alliance.entity.LoginSession;
import alliance.view.ExamPaperView;

public class ExamPaperLauncher extends Application{
	
	public static void main(String[] args) {
        launch(args);
    }
	
	@Override
	public void start(Stage primaryStage) {
		ExamPaperView examPaperView = new ExamPaperView(
				new LoginSession(
				new Account("U1220001G", "test", "STUDENT")),
				EProctorDBController.getInstance().getBookingSchedule("U1220001G").get(1));
		examPaperView.load(primaryStage);
	}
}