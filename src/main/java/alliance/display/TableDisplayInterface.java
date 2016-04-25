package alliance.display;

import javafx.collections.ObservableList;
import alliance.entity.LoginSession;

@Deprecated
public interface TableDisplayInterface {
	
	public ObservableList getTableDisplay(LoginSession ls);

}
