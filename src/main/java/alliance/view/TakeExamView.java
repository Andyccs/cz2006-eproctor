package alliance.view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import alliance.controller.TakeExamController;
import alliance.controller.interfaces.TakeExamControllerInterface;
import alliance.dbaccess.model.BookExam;
import alliance.entity.LoginSession;
import alliance.model.TakeExamModel;
import alliance.model.interfaces.TakeExamModelInterface;

public class TakeExamView implements Initializable,Observer {

	private AnchorPane overviewPage;
	
    private Stage stage;

	@FXML
	private Label DateNow;
	
	@FXML
	private Label TimeNow;
	
	@FXML
	private TableView<BookExam> courseTable;
	
	@FXML
	private TableColumn<BookExam,String> courseCodeColumn;
	
	@FXML
	private TableColumn<BookExam,String> nameColumn;
	
	@FXML
	private TableColumn<BookExam,String> timeColumn;
	
	@FXML
	private TableColumn<BookExam,String> dateColumn;
	
	@FXML
	private ComboBox<String> courseCodeList;
	
	private TakeExamControllerInterface controller;
	
	private TakeExamModelInterface model;
	
	public TakeExamView(LoginSession session) {
		model = new TakeExamModel();
		controller = new TakeExamController(model);
		model.setLoginSession(session);
		model.addObserver(this);
		
		try {
			FXMLLoader loader = new FXMLLoader(
					TakeExamView.class
							.getResource("/alliance/view/TakeExam.fxml"));
			loader.setController(this);
			overviewPage = (AnchorPane) loader.load();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleTakeExamButton() {
		controller.takeExam(courseCodeList.getValue());
	}

	@FXML
	public void initialize(URL arg0, ResourceBundle arg1) {
		alliance.util.TimeUtil tu = alliance.util.TimeUtil.getInstance();
		tu.showDateTime(DateNow, TimeNow);
	}
	
	public void showExamView(BorderPane rootLayout, Stage stage) {
		this.stage = stage;
		rootLayout.setCenter(overviewPage);
		
		controller.initialize();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (model.getSelectedCourses()==null) {
			List<BookExam> courses = model.getCourse();
			//set up table
			ObservableList<BookExam> observableCourses = FXCollections
					.observableArrayList();
			observableCourses.setAll(courses);
			courseCodeColumn
					.setCellValueFactory(new PropertyValueFactory<BookExam, String>(
							"courseCode"));
			nameColumn
					.setCellValueFactory(new PropertyValueFactory<BookExam, String>(
							"courseName"));
			dateColumn
					.setCellValueFactory(new PropertyValueFactory<BookExam, String>(
							"date"));
			timeColumn
					.setCellValueFactory(new PropertyValueFactory<BookExam, String>(
							"startTime"));
			courseTable.setItems(observableCourses);
			courseTable.setId("take exam table");
			
			//set up drop down list
			ObservableList<String> courseCode = FXCollections
					.observableArrayList();
			for (int i = 0; i < courses.size(); i++) {
				courseCode.add(courses.get(i).getCourseCode());
			}
			courseCodeList.setItems(courseCode);
			if (courseCode.size() != 0) {
				courseCodeList.setValue(courseCode.get(0));
			}
		}
		else{
			if(model.isProceedToRule()){
				//proceed to rule and regulation page
				try {
					RuleAndRegulationView ruleAndRegulationView = new RuleAndRegulationView(model.getLoginSession(), model.getSelectedCourses());
					ruleAndRegulationView.load(stage);
				} catch (IOException e) {
					e.printStackTrace();
					Dialogs.showErrorDialog(stage, "Network errors, please try again later. ");
				}
			}
			else{
				Dialogs.showErrorDialog(stage, "Cannot proceed to examination.\nTime remaining more than 10 minutes.");
			}
		}
	}
}
