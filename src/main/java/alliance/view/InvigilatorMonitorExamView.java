package alliance.view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import alliance.controller.InvigilatorController;
import alliance.controller.interfaces.InvigilatorControllerInterface;
import alliance.dbaccess.model.BookExam;
import alliance.dbaccess.model.Student;
import alliance.entity.LoginSession;
import alliance.model.InvigilatorModel;
import alliance.model.RuleAndRegulationRemoteModel;
import alliance.model.interfaces.InvigilatorModelInterface;
import alliance.networking.server.Identity;

public class InvigilatorMonitorExamView implements Observer,Initializable{
	
	private InvigilatorModelInterface model;
	
	private InvigilatorControllerInterface control;
	
	private Stage stage;
	
	@FXML
	private ImageView OwnImage;
	
	@FXML
	private ImageView StudentImageOne;
	
	@FXML
	private ImageView StudentImageTwo;
	
	@FXML
	private TextArea studentDetail1,studentDetail2;
	
//	@FXML
//	private Button proceedButton1,proceedButton2;
	
	@FXML
	private void handleQuit(){
		Platform.exit();
		System.exit(0);
	}
	
	@FXML
	private void handleMute1(){
		
	}
	
	@FXML
	private void handleMute2(){
		
	}
	
	@FXML
	private void handleAlert1(){
		Dialogs.showInformationDialog(stage, "Alert first student");
	}
	
	@FXML
	private void handleAlert2(){
		Dialogs.showInformationDialog(stage, "Alert second student");
	}
	
//	@FXML
//	private void handleProceed1(){
//		try {
//			String id = model.getOtherId()[0];
//			Identity identity = model.getOtherIdentity(id);
//			String ipaddress = identity.getIpAddress();
//			
//			control.allowStudentProceedToExamination(ipaddress, id);
//			proceedButton1.setVisible(false);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NotBoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//	@FXML
//	private void handleProceed2(){
//		try {
//			String id = model.getOtherId()[1];
//			Identity identity = model.getOtherIdentity(id);
//			String ipaddress = identity.getIpAddress();
//			
//			control.allowStudentProceedToExamination(ipaddress, id);
//			proceedButton1.setVisible(false);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NotBoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	private BorderPane borderPane;
	
	private Scene scene;
	
	public InvigilatorMonitorExamView(LoginSession session){
		this.model = new InvigilatorModel();
		model.setLoginSession(session);
		model.addObserver(this);
		
		this.control = new InvigilatorController(model);
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(InvigilatorMonitorExamView.class.getResource("/alliance/view/InvigilatorView.fxml"));
			fxmlLoader.setController(this);
			borderPane = (BorderPane) fxmlLoader.load();
			scene = new Scene(borderPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void load(Stage stage){
		this.stage = stage;
		
		stage.setTitle("Rules & Regulation");
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.hide();
		stage.show();
		
		control.startVideoCall();
		
		studentDetail1.setText("Waiting for student");
		studentDetail2.setText("Waiting for student");
		
		StudentImageOne.setVisible(false);
		StudentImageTwo.setVisible(false);
	}
	
	private String studentID1;
	private String studentID2;
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg.equals("SELF")){
			OwnImage.setImage(SwingFXUtils.toFXImage(model.getSelfImage(), null));
		}
		
		else if(arg instanceof Identity){
			Identity identity = (Identity) arg;
			String[] id = model.getOtherId();
			
			if(id[0]!=null && id[0].equals(identity.getId())){
				StudentImageOne.setImage(SwingFXUtils.toFXImage(model.getOtherImage(id[0]), null));
				StudentImageOne.setVisible(true);
				if(studentDetail1.getText().equals("Waiting for student")){
					setStudentDetails1(id[0]);
				}
			}
			if(id[1]!=null && id[1].equals(identity.getId())){
				StudentImageTwo.setImage(SwingFXUtils.toFXImage(model.getOtherImage(id[1]), null));
				StudentImageTwo.setVisible(true);
				if(studentDetail2.getText().equals("Waiting for student")){
					setStudentDetails2(id[1]);
				}
			}
			
//			if(id.length==2){
//				StudentImageOne.setImage(SwingFXUtils.toFXImage(model.getOtherImage(id[0]), null));
//				StudentImageTwo.setImage(SwingFXUtils.toFXImage(model.getOtherImage(id[1]), null));
//				
//				setStudentDetails1(id[0]);
//				setStudentDetails2(id[1]);
//				
//				StudentImageOne.setVisible(true);
//				StudentImageTwo.setVisible(true);
//			}
//			else if(id.length==1){
//				StudentImageOne.setImage(SwingFXUtils.toFXImage(model.getOtherImage(id[0]), null));
//
//				setStudentDetails1(id[0]);
//				studentDetail2.setText("Waiting for student");
//				
//				StudentImageOne.setVisible(true);
//				StudentImageTwo.setVisible(false);
//			}
//			else{
//				studentDetail1.setText("Waiting for student");
//				studentDetail2.setText("Waiting for student");
//				
//				StudentImageOne.setVisible(false);
//				StudentImageTwo.setVisible(false);	
//			}
		
		}
	}

	private void setStudentDetails2(String id) {
		studentID2 = id;
		Identity studentIdentity = model.getOtherIdentity(studentID2);
		
		control.getStudentDetails(studentIdentity.getId());
		control.getExamDetails(studentIdentity.getId(),studentIdentity.getExamId());
		
		Student student = model.getStudentDetails(id);
		BookExam exam = model.getBookExam(id);
		
		String details = toStringStudentDetails(student, exam);
		studentDetail2.setText(details);
	}

	//cache this method
	private void setStudentDetails1(String id) {
		studentID1 = id;
		Identity studentIdentity = model.getOtherIdentity(studentID1);
		
		control.getStudentDetails(studentIdentity.getId());
		control.getExamDetails(studentIdentity.getId(),studentIdentity.getExamId());
		
		Student student = model.getStudentDetails(id);
		BookExam exam = model.getBookExam(id);
		
		String details = toStringStudentDetails(student, exam);
		studentDetail1.setText(details);
	}

	private String toStringStudentDetails(Student student, BookExam exam) {
		StringBuilder sb = new StringBuilder();
		sb.append("Student name: ").append(student.getName()).append("\n");
		sb.append("Student id: ").append(student.getStudentID()).append("\n");
		sb.append("University :").append(student.getUniversity()).append("\n");
		sb.append("Email: ").append(student.getEmail()).append("\n").append("\n");
		sb.append("Examination name: ").append(exam.getCourseCode()).append(" ").append(exam.getCourseName()).append("\n");
		sb.append("Description: ").append(exam.getDescription()).append("\n");
		sb.append("Start time: ").append(exam.getStartTime()).append("\n");
		sb.append("End time: ").append(exam.getEndTime()).append("\n");
		return sb.toString();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
