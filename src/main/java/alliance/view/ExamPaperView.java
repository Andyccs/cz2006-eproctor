package alliance.view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import alliance.controller.ExamPaperController;
import alliance.controller.interfaces.ExamPaperControllerInterface;
import alliance.dbaccess.model.BookExam;
import alliance.entity.ExaminationPaperQuestionOption;
import alliance.entity.LoginSession;
import alliance.model.ExamPaperModel;
import alliance.model.interfaces.ExamPaperModelInterface;
import alliance.model.interfaces.ExamPaperModelInterface.State;
import alliance.videocall.WebcamVideoCall;
import alliance.voicecall.SimpleVoiceCall;

public class ExamPaperView implements Initializable,Observer{
	
	private BorderPane pane;
	
	private Scene scene;
	
	private Stage stage;
    
    @FXML
    private Button Forfeit, Submit;
    
    @FXML
    private Label TimeLeft;
    
    @FXML
    private VBox ExamArea;
    
    private ExamPaperControllerInterface control;
    
    private ExamPaperModelInterface model;
    
    
    public ExamPaperView(LoginSession loginSession, BookExam course) {
    	
    	
		model = new ExamPaperModel();
		model.registerObserver(this);
		model.setCourse(course);
		model.setLoginSession(loginSession);
		
		try {
			control = new ExamPaperController(model);
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
    	
       	FXMLLoader fxmlLoader = null;
    	try {
        	fxmlLoader = new FXMLLoader(LoginView.class.getResource("/alliance/view/ExamPaper.fxml"));
        	fxmlLoader.setController(this);    
        	pane = (BorderPane) fxmlLoader.load();
            scene = new Scene(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    
	public void load(Stage stage) {
        stage.setTitle("Examination");
        stage.setScene(scene);
//        stage.setFullScreen(true);
        stage.hide();
        stage.show();
        this.stage = stage;
        
        try {
			control.initialize();
		} catch (SAXException | IOException | ParserConfigurationException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Forfeit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            	control.forfeitConfirmation();
            }
        });
		
        Submit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            	control.submitConfirmation();
            }
        });
	}


	@Override
	public void update(Observable o, Object arg) {
		State state = model.getState();
		if(state.equals(State.STARTING)){
			loadTimer();
			
			List<ExaminationPaperQuestionOption> listOfQuestion = 
					model.getExaminationPaperQuestionOption();

			for (int i=0;i<listOfQuestion.size();i++) {
				ExaminationPaperQuestionOption examinationPaperQuestionOption = 
						listOfQuestion.get(i);
				
				populatedExamPaper(i, examinationPaperQuestionOption);			    
			}
			model.setState(State.EXAM);
			model.notifyObservers();
		}
		else if(state.equals(State.EXAM)){
			//do ntg
		}
		else if(state.equals(State.FORFEIT)){
			//show a dialog box for confirmation
			DialogResponse response = 
					Dialogs.showConfirmDialog(stage, "Forfeit Examination?", "What?", "Forfeit", DialogOptions.YES_NO);
			if(response.toString().equals(DialogResponse.YES.toString())){
				control.forfeit();
			}
			else{
				model.setState(State.EXAM);
				model.notifyObservers();
			}
		}
		else if(state.equals(State.FORFEITED)){
			try {
				WebcamVideoCall.getInstance().endVideoCall();
				SimpleVoiceCall.getInstance().endVoiceCall();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					
					StudentView view = new StudentView(model.getLoginSession());
					view.home(stage);
					Dialogs.showInformationDialog(stage, "End of examination");
				}
			});
		}
		else if(state.equals(State.SUBMIT)){
			DialogResponse response = 
					Dialogs.showConfirmDialog(stage, "Submit examination?", "What?", "Submit", DialogOptions.YES_NO);
			
			String temp = response.toString();
			
			if(response.toString().equals(DialogResponse.YES.toString())){
				control.submit();
			}
			else{
				model.setState(State.EXAM);
				model.notifyObservers();
			}
		}
		else if(state.equals(State.SUBMITTED)){
			try {
				WebcamVideoCall.getInstance().endVideoCall();
				SimpleVoiceCall.getInstance().endVoiceCall();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					
					StudentView view = new StudentView(model.getLoginSession());
					view.home(stage);
					Dialogs.showInformationDialog(stage, "End of examination");
				}
			});
		}
	}

	private void populatedExamPaper(int i, ExaminationPaperQuestionOption examinationPaperQuestionOption) {
		//add grid
		GridPane grid = new GridPane();
		grid.setMaxWidth(700);
		grid.setHgap(0);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		
		//Numbers
		Text num = new Text(Integer.toString(i+1)+". 	");
		num.setFont(Font.font("System", FontWeight.BOLD, 16));
		grid.add(num, 0, 0);
		GridPane.setValignment(num, VPos.TOP);
		
		//Questions
		Text question = new Text(examinationPaperQuestionOption.getContent());
		question.setWrappingWidth(600);
		question.setFont(Font.font("System", FontWeight.BOLD, 16));
		grid.add(question, 1, 0); 
		
		
		//Answers
		ToggleGroup group  = new ToggleGroup();
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

			@Override
			public void changed(ObservableValue<? extends Toggle> ov,
					Toggle toggle, Toggle new_toggle) {
				int q;
				if (new_toggle==null) {
					System.out.println("Deselected");
					q = Integer.parseInt(toggle.getUserData().toString().substring(0, 1));
					control.answerQuestion(q, "");
				}
				else
				{
					q = Integer.parseInt(new_toggle.getUserData().toString().substring(0, 1));
					String ans = new_toggle.getUserData().toString().substring(1);
					control.answerQuestion(q, ans);
				}
			}
		});
		
		ToggleButton tb1 = new ToggleButton("A");
		tb1.setId(i+"A");
		tb1.setUserData(i+"A");
		tb1.setToggleGroup(group);
		
		ToggleButton tb2 = new ToggleButton("B");
		tb2.setId(i+"B");
		tb2.setUserData(i+"B");
		tb2.setToggleGroup(group);
		
		ToggleButton tb3 = new ToggleButton("C");
		tb3.setId(i+"C");
		tb3.setUserData(i+"C");
		tb3.setToggleGroup(group);
		
		ToggleButton tb4 = new ToggleButton("D");
		tb4.setId(i+"D");
		tb4.setUserData(i+"D");
		tb4.setToggleGroup(group);
		
		if(model.getAnswers()[i]!=null){
			if(model.getAnswers()[i].equals("A")){
				tb1.setSelected(true);
			}
			else if(model.getAnswers()[i].equals("B")){
				tb2.setSelected(true);
			}
			else if(model.getAnswers()[i].equals("C")){
				tb3.setSelected(true);
			}
			else if(model.getAnswers()[i].equals("D")){
				tb4.setSelected(true);
			}
		}
		
		//Add buttons with the MCQ texts
		grid.add(MCQs(tb1,examinationPaperQuestionOption.getOptionA()), 1, 2);
		grid.add(MCQs(tb2,examinationPaperQuestionOption.getOptionB()), 1, 3);
		grid.add(MCQs(tb3,examinationPaperQuestionOption.getOptionC()), 1, 4);
		grid.add(MCQs(tb4,examinationPaperQuestionOption.getOptionD()), 1, 5);
		
		grid.add(new Text(), 2, 5);
		
		ExamArea.getChildren().add(grid);
	}

	private HBox MCQs(ToggleButton tb, String option){
		HBox MCQ = new HBox(5);
		Text tt = null;
		
		tt = new Text(option);

		MCQ.getChildren().add(tb);
		MCQ.getChildren().add(tt);
		
		return MCQ;
	}
	
	
	/**
	 * Load the timer that count-down to the end of exam
	 */
    private void loadTimer(){
		Task<Void> timerTask = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask(){

					@Override
					public void run() {
						long interval = model.getTime();
				        
				        updateMessage(formatTime(interval));
						
						model.setTime(interval-1000);
						
						if(model.getTime()==0){
							this.cancel();
							control.submit();
						}

					}
					
				},1000l,1000l);
				return null;
			}
		};
		TimeLeft.textProperty().bind(timerTask.messageProperty());
		TimeLeft.setStyle("-fx-background-color: #112233;");
    	Thread t = new Thread(timerTask);
    	t.setName("DateUpdater");
    	t.setDaemon(true);
    	t.start();
    }
    
    private String formatTime(long millis) {

        String output = "00:00";
        try {
            long seconds = millis / 1000;
            long minutes = seconds / 60;
            long hours = seconds / 3600;

            seconds = seconds % 60;
            minutes = minutes % 60;
            hours = hours % 24;

            String sec = String.valueOf(seconds);
            String min = String.valueOf(minutes);
            String hur = String.valueOf(hours);

            if (seconds < 10)
                sec = "0" + seconds;
            if (minutes < 10)
                min = "0" + minutes;
            if (hours < 10)
                hur = "0" + hours;

            output = hur + ":" + min + ":" + sec;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
