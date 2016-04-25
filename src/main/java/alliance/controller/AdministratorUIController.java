package alliance.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import alliance.dBadapter.EProctorAdapter;
import alliance.entity.Invigilator;
import alliance.entity.LoginSession;

public class AdministratorUIController {

	private LoginSession ls;

	private Scene scene;
	private Stage stage;
	private Parent parent;

	private AnchorPane anchorPane;

	@FXML
	private TableView<Invigilator> personTable;
	@FXML
	private TableColumn<Invigilator, String> firstNameColumn;

	@FXML
	private Label nameLabel;
	@FXML
	private Label addressLabel;
	@FXML
	private Label postalCodeLabel;
	@FXML
	private Label phoneNumberLabel;
	@FXML
	private Label usernameLabel, passwordLabel;
	
	private static final String XML_PATH = "META-INF/spring/EProctorAdapter.xml";
	
	private ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	
	private EProctorAdapter proctorAdt;
	
	private int orignalInvigilatorSize;
	
	

	/**
	 * The data as an observable list of Persons.
	 */
	private ObservableList<Invigilator> personData = FXCollections
			.observableArrayList();

	/**
	 * Constructor
	 */
	public AdministratorUIController(LoginSession ls) {

		stage = new Stage();
		this.ls = ls;
		proctorAdt = context.getBean(EProctorAdapter.class);

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
					"/alliance/view/AdminUI.fxml"));
			fxmlLoader.setController(this);

			parent = (AnchorPane) fxmlLoader.load();
			scene = new Scene(parent);
		} catch (IOException e) {
			e.printStackTrace();
		}

		personData = proctorAdt.getInvigilatorDetails();
		orignalInvigilatorSize = personData.size();
		personTable.setItems(getPersonData());

	}

	/**
	 *  Initialization
	 */
	@FXML
	private void initialize() {
		// Initialize the person table
		firstNameColumn
				.setCellValueFactory(new PropertyValueFactory<Invigilator, String>(
						"name"));
		// Auto resize columns
		personTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// clear person
		showInvigilatorDetails(null);

		// Listen for selection changes
		personTable.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<Invigilator>() {

					@Override
					public void changed(
							ObservableValue<? extends Invigilator> observable,
							Invigilator oldValue, Invigilator newValue) {
						showInvigilatorDetails(newValue);
					}
				});
	}

	/**
	 * Returns the data as an observable list of Persons.
	 * 
	 * @return
	 */
	public ObservableList<Invigilator> getPersonData() {
		return personData;
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewPerson() {
		Invigilator tempPerson = new Invigilator();
		boolean okClicked = showPersonEditDialog(tempPerson);
		if (okClicked) {
			getPersonData().add(tempPerson);
		}
	}

	/**
	 * Opens a dialog to edit details for the specified person. If the user
	 * clicks OK, the changes are saved into the provided person object and true
	 * is returned.
	 * 
	 * @param person
	 *            the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	private boolean showPersonEditDialog(Invigilator person) {
		try {
			// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(
					AdministratorUIController.class
							.getResource("/alliance/view/InvigilatorEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Invigilator Details");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller
			InvigilatorEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Fills all text fields to show details about the person. If the specified
	 * person is null, all text fields are cleared.
	 * 
	 * @param person
	 *            the person or null
	 */
	private void showInvigilatorDetails(Invigilator person) {
		if (person != null) {
			nameLabel.setText(person.getName());
			addressLabel.setText(person.getAddress());
			postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
			phoneNumberLabel.setText(person.getPhoneNumber()); // phone number
																// in string
																// just in case
																// got +65
																// characters
			usernameLabel.setText(person.getAccount().getUsername());
			passwordLabel.setText(person.getAccount().getPassword());

		} else {
			nameLabel.setText("");
			addressLabel.setText("");
			postalCodeLabel.setText("");
			phoneNumberLabel.setText("");
			usernameLabel.setText("");
			passwordLabel.setText("");
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleEditPerson() {
		Invigilator selectedPerson = personTable.getSelectionModel()
				.getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = showPersonEditDialog(selectedPerson);
			if (okClicked) {
				refreshPersonTable();
				showInvigilatorDetails(selectedPerson);
			}

		} else {
			// Nothing selected
			Dialogs.showWarningDialog(stage,
					"Please select a person in the table.",
					"No Person Selected", "No Selection");
		}
	}

	/**
	 * Called when user clicks save. Edits/Add invigilator account
	 */
	@FXML
	private void handleSaveButton() {

		// Convert ObservableList to a normal ArrayList
		ArrayList<Invigilator> personList = new ArrayList<>(personData);
		for (int j = 0; j <orignalInvigilatorSize; j++) {
			if (!proctorAdt.editInvigilators(personList.get(j).getAccount()
					.getUsername(), personList.get(j).getName(), personList
					.get(j).getAddress(), personList.get(j).getPostalCode(),
					personList.get(j).getPhoneNumber(), personList.get(j)
							.getAccount().getPassword())) {
				Dialogs.showErrorDialog(stage, "Unsuccessful editing invigilator details");
			}
		}
		for (int i = orignalInvigilatorSize; i < personList.size(); i++) {
			if (!proctorAdt.addInvigilators(personList.get(i).getAccount()
					.getUsername(), personList.get(i).getName(), personList
					.get(i).getAddress(), personList.get(i).getPostalCode(),
					personList.get(i).getPhoneNumber(), personList.get(i)
							.getAccount().getPassword())) {
				Dialogs.showErrorDialog(stage, "Unsuccessful adding invigilator account");
			}
		}
		Dialogs.showInformationDialog(stage, "Successfully make changes to database");

	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 * 
	 * @param file
	 *            the file or null to remove the path
	 */
	public void setPersonFilePath(File file) {
		Preferences prefs = Preferences
				.userNodeForPackage(AdministratorUIController.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());
		} else {
			prefs.remove("filePath");
		}
	}

	@FXML
	private void handleQuitButton() {
		Platform.exit();
	}

	/**
	 * Refreshes the table. This is only necessary if an item that is already in
	 * the table is changed. New and deleted items are refreshed automatically.
	 * 
	 * This is a workaround because otherwise we would need to use property
	 * bindings in the model class and add a *property() method for each
	 * property. Maybe this will not be necessary in future versions of JavaFX
	 * (see http://javafx-jira.kenai.com/browse/RT-22599)
	 */
	private void refreshPersonTable() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		personTable.setItems(null);
		personTable.layout();
		personTable.setItems(getPersonData());
		// Must set the selected index again (see
		// http://javafx-jira.kenai.com/browse/RT-26291)
		personTable.getSelectionModel().select(selectedIndex);
	}

	public void home() {
		stage.setTitle("Administrator Home Screen");
		stage.setScene(scene);
		stage.setResizable(true);
		stage.hide();
		stage.show();
	}

}
