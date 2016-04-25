package alliance.view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import alliance.controller.AnnouncementPageController;
import alliance.controller.interfaces.AnnouncementPageControllerInterface;
import alliance.model.AnnouncementPageModel;
import alliance.model.interfaces.AnnouncementPageModelInterface;
import alliance.util.TimeUtil;


public class AnnouncementPageView implements Initializable,Observer {
	
	private AnchorPane overviewPage;
	
	private AnnouncementPageModelInterface model;
	
	private AnnouncementPageControllerInterface controller;
	
	
	public AnnouncementPageView() {
		try {
			FXMLLoader loader = new FXMLLoader(AnnouncementPageView.class.getResource("/alliance/view/AnnouncementPage.fxml"));
			loader.setController(this);
			overviewPage = (AnchorPane) loader.load();
			
			model = new AnnouncementPageModel();
			controller = new AnnouncementPageController(model);
			model.addObserver(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private Label DateNow, TimeNow;
	
	@FXML
	private VBox an;
	
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		TimeUtil tu = TimeUtil.getInstance();
		tu.showDateTime(DateNow, TimeNow);
		
	}
	
	public void showAnnouncement(BorderPane rootLayout) {
		rootLayout.setCenter(overviewPage);	
		controller.initialize();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		List<String> announcements = model.getAnnouncement();
		for (int i = 0; i < announcements.size(); i++) {
			an.getChildren().add(addGridPane(announcements.get(i)));
		}
	}
	
	private GridPane addGridPane(String announcement){
		GridPane grid = new GridPane();
		grid.setMaxWidth(700);
		grid.setHgap(0);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(10, 10, 10, 10));
	    
	    Text question = new Text(announcement);
	    question.setWrappingWidth(600);
	    question.setFont(Font.font("System", FontWeight.BOLD, 16));
	    grid.add(question, 1, 0); 
	    
	    return grid;
	}
	
	
}
