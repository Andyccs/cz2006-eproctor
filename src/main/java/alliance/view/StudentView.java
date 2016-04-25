package alliance.view;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import alliance.controller.StudentController;
import alliance.controller.interfaces.StudentControllerInterface;
import alliance.display.CourseTable;
import alliance.display.TableDisplayInterface;
import alliance.entity.LoginSession;
import alliance.model.StudentMainModel;
import alliance.model.interfaces.StudentMainModelInterface;

public class StudentView implements Observer{
	
    private Scene scene;
    
    private BorderPane rootLayout;
    
    private StudentControllerInterface controller;
    
    private StudentMainModelInterface model;

	public StudentView(LoginSession ls) {
		model = new StudentMainModel();
		
		model.setLoginSession(ls);
		
		model.registerObserver(this);
		controller = new StudentController(model);
		
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/alliance/view/SideButtons.fxml"));
			fxmlLoader.setController(this);
			rootLayout = (BorderPane) fxmlLoader.load();
		    scene = new Scene(rootLayout);
		    
		} catch (IOException e) {
		    e.printStackTrace();
	    }
		
	}
	
	@FXML
	private VBox sideBar;

    @FXML
	private TableView<CourseTable> courseTable;
    
	@FXML
	private TableColumn<CourseTable, String> courseCodeColumn;
	
	@FXML
	private TableColumn<CourseTable, String> timeRemainingColumn;
	
	@FXML 
	private void handleHomeButton() {
		controller.showAnnouncement();
	}
	
	@FXML
	private void handleBookingExam() {
		controller.showBookingExam();
	}
	
	@FXML
	private void handleCheckSchedule() {
		controller.showSchedule();
	}
	
	@FXML
	private void handleTakeExamination() {
		controller.takeExamination();
	}
	
	@FXML
	private void handleQuit() {
		Platform.exit();
	}
	
	@FXML
	public void initialize() {
		// Initialize the person table
		courseCodeColumn.setCellValueFactory(new PropertyValueFactory<CourseTable, String>("courseCode"));
		timeRemainingColumn.setCellValueFactory(new PropertyValueFactory<CourseTable, String>("time"));
		
		TableDisplayInterface td = new CourseTable();
		courseTable.setItems(td.getTableDisplay(model.getLoginSession())); //need double check
		courseTable.setId("SideTable");
	}

	public void home(Stage stage) {
		controller.showAnnouncement();
		
        stage.setTitle("Student Home Screen");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.hide();
        stage.show();
        this.stage = stage;
    }
	
	private Stage stage;
	
    private AnnouncementPageView announcementPageView = new AnnouncementPageView();
    private BookExamView bookExaminationView;
    private CheckScheduleView checkScheduleView;
    private TakeExamView takeExamView;
	
	@Override
	public void update(Observable o, Object arg) {
		
		if(!(arg instanceof String)){
			int state = model.getState();
			if(state==StudentMainModel.STATE_ANNOUNCEMENT){
				announcementPageView.showAnnouncement(rootLayout);
			}
			else if(state==StudentMainModel.STATE_BOOK_EXAM){
				if(bookExaminationView==null){
					bookExaminationView = new BookExamView(model.getLoginSession());
					bookExaminationView.addModelObserver(this);
				}
				bookExaminationView.showBooking(rootLayout,stage);
			}
			else if(state==StudentMainModel.STATE_SHOW_SCHEDULE){
				if(checkScheduleView==null){
					checkScheduleView = new CheckScheduleView(model.getLoginSession());
				}
				checkScheduleView.showSchedule(rootLayout);
			}
			else if(state==StudentMainModel.STATE_TAKE_EXAM){
				if(takeExamView==null){
					takeExamView = new TakeExamView(model.getLoginSession());
				}
				takeExamView.showExamView(rootLayout, stage);
			}
		}
		else{
			//OBSERVER THE BOOK EXAM MODEL HERE
			TableDisplayInterface td = new CourseTable();
			courseTable.setItems(td.getTableDisplay(model.getLoginSession()));
		}
		
	}
}
