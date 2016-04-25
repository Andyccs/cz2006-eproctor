package alliance.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import alliance.entity.ExaminationPaperQuestionOption;

public class ExamPaperParser {
	public static ArrayList<ExaminationPaperQuestionOption> parse(String xml) throws SAXException, IOException, ParserConfigurationException {
//			File XMLfile = new File(EXAMFILEPATH);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		InputStream is = new ByteArrayInputStream(xml.getBytes());
		Document doc = dBuilder.parse(is);
		doc.getDocumentElement().normalize();
		
		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//			ID = doc.getElementsByTagName("id").item(0).getTextContent();
//			Course = doc.getElementsByTagName("course").item(0).getTextContent();
//			Duration = Integer.parseInt(doc.getElementsByTagName("duration").item(0).getTextContent());
		NodeList nList = doc.getElementsByTagName("question");
		
		ArrayList<ExaminationPaperQuestionOption> paper = new ArrayList<ExaminationPaperQuestionOption>();
		
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			 
			//Parse a question from XML to ArrayLists
			Element eElement = (Element) nNode;
			
			String content = eElement.getElementsByTagName("content").item(0).getTextContent();
			String optionA = eElement.getElementsByTagName("a").item(0).getTextContent();
			String optionB = eElement.getElementsByTagName("b").item(0).getTextContent();
			String optionC = eElement.getElementsByTagName("c").item(0).getTextContent();
			String optionD = eElement.getElementsByTagName("d").item(0).getTextContent();
			
			ExaminationPaperQuestionOption question = new ExaminationPaperQuestionOption();
			
			question.setContent(content);
			question.setOptionA(optionA);
			question.setOptionB(optionB);
			question.setOptionC(optionC);
			question.setOptionD(optionD);
			
			paper.add(question);
		}
		return paper;
	}
}
