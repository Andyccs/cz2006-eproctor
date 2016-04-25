package alliance.dbaccess;

import alliance.dbaccess.interfaces.EProctorDBInterface;

@Deprecated 
/**
 * will be moved to testing folder
 * @author Andy
 */
public class Main {

	public static void main(String[] args) {
//		EProctorDBController jdbc = context.getBean(EProctorDBController.class);
//		UniversityDBInterface jdbc = UniversityDBController.getInstance();
//		EProctorDBInterface jdbc = EProctorDBController.getInstance();
//		System.out.println("The database: ");
//		
//		Boolean account = jdbc.authenticate("INVIGILATOR", "I0001", "test");
//		System.out.println(account);
		
/*		List<Exam> examList = jdbc.getBookingSchedule("U1220001G");
		for (Iterator<Exam> iterator = examList.iterator(); iterator.hasNext();) {
			Exam exam = (Exam) iterator.next();
			System.out.println(exam.getExamID());
			System.out.println(exam.getCourseCode());
			System.out.println(exam.getCourseName());
			System.out.println(exam.getDescription());
			System.out.println(exam.getDuration());
			System.out.println(exam.getStyle());
			System.out.println(exam.getDate());
			System.out.println(exam.getStartTime());
			System.out.println(exam.getEndTime());
		}*/
		
/*		List<String> announcementList = jdbc.getAnnoucement();
		for (Iterator<String> iterator = announcementList.iterator(); iterator.hasNext();) {
			String annoucement = (String) iterator.next();
			System.out.println(annoucement);
		}*/
		
//		System.out.print(jdbc.addAnnoucement("HAHA!!!"));
		
		//System.out.print(jdbc.addInvigilator("I0002", "Andy", "AMK, Block 11", 645312, 123456789));
		
/*		List<Invigilator> invigilatorList = jdbc.getAllInvigilator();
		for (Iterator<Invigilator> iterator = invigilatorList.iterator(); iterator.hasNext();) {
			Invigilator invigilator = (Invigilator) iterator.next();
			System.out.println(invigilator.getInvigilatorID());
			System.out.println(invigilator.getName());
			System.out.println(invigilator.getAddress());
			System.out.println(invigilator.getPostalCode());
			System.out.println(invigilator.getPhoneNumber());
			System.out.println();
		}*/
		
		//System.out.println(jdbc.getStudentInfo("U1220001G"));
			
	}
}
