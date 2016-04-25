package alliance.view;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import alliance.controller.RuleAndRegulationController;
import alliance.controller.interfaces.RuleAndRegulationControllerInterface;
import alliance.dbaccess.model.BookExam;
import alliance.entity.LoginSession;
import alliance.model.RuleAndRegulationModel;
import alliance.model.interfaces.RuleAndRegulationModelInterface;

public class RuleAndRegulationView implements Initializable,Observer{

	private RuleAndRegulationModelInterface model;
	
	private RuleAndRegulationControllerInterface control;
	
	private GridPane parent;
	
    private Scene scene;
    
    private Stage stage;
    
    @FXML
    private Label courseCode;
    
    @FXML
    private ImageView invigilatorVideo;
    
    @FXML
    private ImageView studentVideo;
    
    @FXML
    private Button proceed;
    
    public RuleAndRegulationView(LoginSession session,BookExam exam) throws IOException {
    	
    	model = new RuleAndRegulationModel();
    	model.setProceedPermission(false);
    	
		control = new RuleAndRegulationController(model);
    	
		model.setStudentDetails(session);
		
		model.setExamination(exam);
		
		model.registerObserver(this);

        try {
        	FXMLLoader fxmlLoader = new FXMLLoader(RuleAndRegulationView.class.getResource("/alliance/view/Rules&RegulationPage.fxml"));
        	fxmlLoader.setController(this);
            parent = (GridPane) fxmlLoader.load();
            scene = new Scene(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	proceed.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event){
    			ExamPaperView examPaper = new ExamPaperView(model.getStudentDetails(), model.getExamination());
    			examPaper.load(stage);
    		}
    	});
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg.equals("SELF")){
			studentVideo.setImage(SwingFXUtils.toFXImage(model.getSelfImage(), null));
		}
		if(arg.equals("OTHER")){
			invigilatorVideo.setImage(SwingFXUtils.toFXImage(model.getInvigilatorImage(), null));
		}
	}
	
	public void load(Stage stage){		
		this.stage = stage;
    	
		stage.setTitle("Rule and Regulation");
    	stage.setScene(scene);
    	stage.setFullScreen(true);
    	stage.hide();
    	stage.show();
    	
    	control.startVideoCall();
    	courseCode.setText(
    			model.getExamination().getCourseCode()+" "+
    			model.getExamination().getCourseName());
    }
}
