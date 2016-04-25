package alliance.view;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import alliance.controller.AnnouncementPageController;
import alliance.controller.BookExamController;
import alliance.controller.interfaces.BookExamControlerInterface;
import alliance.dbaccess.model.TakeCourse;
import alliance.display.BookingTable;
import alliance.entity.LoginSession;
import alliance.javafx.PTableColumn;
import alliance.model.BookExamModel;
import alliance.model.interfaces.BookExamModelInterface;
import alliance.util.TimeUtil;

public class BookExamView implements Observer,Initializable{

	private AnchorPane overviewPage;
	
	private TimeUtil tu;
	
	private Stage stage;
	
	private BorderPane rootLayout;
	
	private BookExamControlerInterface controller;
	
	private BookExamModelInterface model;
	
	private Logger logger = LoggerFactory.getLogger(BookExamView.class);

	public BookExamView(LoginSession ls) {
		model = new BookExamModel();
		model.setLoginSession(ls);
		model.addObserver(this);
		
		controller = new BookExamController(model);
		
		try {
			FXMLLoader loader = new FXMLLoader(
					BookExamView.class
							.getResource("/alliance/view/Booking.fxml"));
			loader.setController(this);
			overviewPage = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showBooking(BorderPane rootLayout, Stage stage) {
		this.stage = stage;
		this.rootLayout = rootLayout;
		rootLayout.setCenter(overviewPage);
		
		controller.initialize();
	}

	@FXML
	private Label TimeNow, DateNow;

	@FXML
	private Label statusLabel;

	@FXML
	private ComboBox<String> courseCode;

	@FXML
	private ComboBox<String> timeSlot;

	@FXML
	private TextField datePicked;

	@FXML
	private TableView<BookingTable> bookingTable;
	
	@FXML
	private PTableColumn<BookingTable, String> courseCodeColumn;

	@FXML
	private PTableColumn<BookingTable, String> courseDescriptionColumn;

	@FXML
	private PTableColumn<BookingTable, String> durationColumn;

	@FXML
	private PTableColumn<BookingTable, String> formatColumn;
	
	@FXML
	private PTableColumn<BookingTable, String> startDateColumn;

	@FXML
	private PTableColumn<BookingTable, String> endDateColumn;
	
	@FXML
	public void initialize(URL arg0, ResourceBundle arg1) {
		tu = TimeUtil.getInstance();
		tu.showDateTime(DateNow, TimeNow);

		statusLabel.setTextFill(Color.FIREBRICK);

		courseCodeColumn
				.setCellValueFactory(new PropertyValueFactory<BookingTable, String>(
						"courseCode"));
		
		courseDescriptionColumn
				.setCellValueFactory(new PropertyValueFactory<BookingTable, String>(
						"courseDescription"));

		durationColumn
				.setCellValueFactory(new PropertyValueFactory<BookingTable, String>(
						"duration"));

		formatColumn
				.setCellValueFactory(new PropertyValueFactory<BookingTable, String>(
						"format"));
		startDateColumn
				.setCellValueFactory(new PropertyValueFactory<BookingTable, String>(
						"startDate"));

		endDateColumn
				.setCellValueFactory(new PropertyValueFactory<BookingTable, String>(
						"endDate"));

		timeSlot.getItems().setAll("08:00", "14:00", "18:00");
	}

	@FXML
	private void handleSubmitButton() {
		controller.submitToDB(
				courseCode.getValue(),
				datePicked.getText(), 
				timeSlot.getValue()!=null?timeSlot.getValue()+":00":null) ;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(model.isBookSuccess()!=null && model.isBookSuccess()==true){
			Dialogs.showInformationDialog(stage, "Successfully booked with the system");
			model.setBookSuccess(null);
			controller.initialize();
		}
		
		else if(model.isBookSuccess()!=null && model.isBookSuccess()==false){
			Dialogs.showErrorDialog(stage, model.getErrorMessage());
			model.setErrorMessage(null);
			model.setBookSuccess(null);
		}
		
		if(model.getCourseToTake()!=null){
			List<BookingTable> courses = model.getCourseToTake();
			ObservableList<BookingTable> observableCourses = FXCollections.observableArrayList();
			observableCourses.addAll(courses);
			bookingTable.setItems(observableCourses);
			
			ObservableList<String> courseCodes = FXCollections.observableArrayList();
			for (int i = 0; i < courses.size(); i++) {
				courseCodes.add(courses.get(i).getCourseCode());
			}
			courseCode.setItems(courseCodes);
		}
	}
	
	public void addModelObserver(Observer observer){
		model.addObserver(observer);
	}

}
