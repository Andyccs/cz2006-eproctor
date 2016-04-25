package alliance.controller.interfaces;

public interface BookExamControlerInterface {

	public void initialize();

	public void submitToDB(String courseCode, String date, String time);

	public boolean isInputValid(String courseCode, String date, String time);

}
