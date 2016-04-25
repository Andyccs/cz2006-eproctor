package alliance.view;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import alliance.controller.AnnouncementPageController;
import alliance.controller.CheckScheduleController;
import alliance.controller.interfaces.CheckScheduleControllerInterface;
import alliance.dbaccess.model.BookExam;
import alliance.entity.LoginSession;
import alliance.javafx.PTableColumn;
import alliance.model.CheckScheduleModel;
import alliance.model.interfaces.CheckScheduleModelInterface;
import alliance.util.TimeUtil;

public class CheckScheduleView implements Observer{

	private AnchorPane overviewPage;
	private TimeUtil tu;
	
	private CheckScheduleControllerInterface controller;
	
	private CheckScheduleModelInterface model;
	
	public CheckScheduleView(LoginSession ls) {
		model = new CheckScheduleModel();
		model.setLoginSession(ls);
		model.addObserver(this);
		
		controller = new CheckScheduleController(model);

		
		try {
			FXMLLoader loader = new FXMLLoader(
					AnnouncementPageController.class
							.getResource("/alliance/view/CheckSchedule.fxml"));
			loader.setController(this);
			overviewPage = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private TableView<BookExam> courseDetails;

	@FXML
	private PTableColumn<BookExam, String> courseDateColumn;

	@FXML
	private PTableColumn<BookExam, String> courseCodeColumn;
	
	@FXML
	private PTableColumn<BookExam, String> courseNameColumn;

	@FXML
	private PTableColumn<BookExam, String> courseTimeslotColumn;

	@FXML
	private PTableColumn<BookExam, String> courseDescriptionColumn;

	@FXML
	private PTableColumn<BookExam, String> courseDurationColumn;

	@FXML
	private PTableColumn<BookExam, String> courseFormatColumn;

	@FXML
	private Label DateNow, TimeNow;

	@FXML
	public void initialize() {
		tu = TimeUtil.getInstance();
		tu.showDateTime(DateNow, TimeNow);

		courseCodeColumn
				.setCellValueFactory(new PropertyValueFactory<BookExam, String>(
						"courseCode"));
		
		courseNameColumn
				.setCellValueFactory(new PropertyValueFactory<BookExam, String>(
						"courseName"));
		
		courseDescriptionColumn
				.setCellValueFactory(new PropertyValueFactory<BookExam, String>(
						"description"));

		courseDurationColumn
				.setCellValueFactory(new PropertyValueFactory<BookExam, String>(
						"duration"));

		courseFormatColumn
				.setCellValueFactory(new PropertyValueFactory<BookExam, String>(
						"style"));
		courseDateColumn
				.setCellValueFactory(new PropertyValueFactory<BookExam, String>(
						"date"));

		courseTimeslotColumn
				.setCellValueFactory(new PropertyValueFactory<BookExam, String>(
						"startTime"));
		
	}
	
	public void showSchedule(BorderPane rootLayout) {
		controller.initialize();
		
		rootLayout.setCenter(overviewPage);
	}

	@Override
	public void update(Observable o, Object arg) {
		List<BookExam> exam = model.getExaminations();
		
		ObservableList<BookExam> examObservableList = FXCollections.observableArrayList();
		
		examObservableList.addAll(exam);

		courseDetails.setItems(examObservableList);
	}

}
