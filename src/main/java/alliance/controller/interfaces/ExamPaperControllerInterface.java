package alliance.controller.interfaces;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface ExamPaperControllerInterface {

	public void forfeitConfirmation();

	public void submitConfirmation();
	
	public void forfeit();
	
	public void submit();
	
	public void answerQuestion(int questionNo, String answer);

	public void initialize() throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException;
}
