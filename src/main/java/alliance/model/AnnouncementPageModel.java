package alliance.model;

import java.util.List;
import java.util.Observable;

import alliance.model.interfaces.AnnouncementPageModelInterface;

public class AnnouncementPageModel extends Observable implements AnnouncementPageModelInterface {
	List<String> anouncements;
	/**
	 * Set the announcements in the announcement page UI for all
	 * the users of the eProctor software.
	 * @param announcements	List of announcements in the model
	 */
	@Override
	public void setAnnouncements(List<String> announcements) {
		this.anouncements = announcements;
		setChanged();
		notifyObservers();
	}

	/**
	 * Get the announcement in the form of a list
	 * @return List<String>
	 */
	@Override
	public List<String> getAnnouncement() {
		return anouncements;
	}

}
