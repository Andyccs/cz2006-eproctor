package alliance.model.interfaces;

import java.util.List;
import java.util.Observer;

public interface AnnouncementPageModelInterface {
	public void setAnnouncements(List<String> announcements);
	public List<String> getAnnouncement();
	public void addObserver(Observer observer);
}
