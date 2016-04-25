package alliance.controller;

import alliance.controller.interfaces.StudentControllerInterface;
import alliance.model.interfaces.StudentMainModelInterface;

public class StudentController implements StudentControllerInterface {
	
	private StudentMainModelInterface model;

	public StudentController(StudentMainModelInterface studentViewModel) {
		this.model = studentViewModel;
	}
	
	@Override
	public void showAnnouncement() {
		model.setState(StudentMainModelInterface.STATE_ANNOUNCEMENT);
	}

	@Override
	public void showBookingExam() {
		model.setState(StudentMainModelInterface.STATE_BOOK_EXAM);
	}

	@Override
	public void showSchedule() {
		model.setState(StudentMainModelInterface.STATE_SHOW_SCHEDULE);
	}

	@Override
	public void takeExamination() {
		model.setState(StudentMainModelInterface.STATE_TAKE_EXAM);
	}

}
