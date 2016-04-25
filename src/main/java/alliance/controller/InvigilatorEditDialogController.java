package alliance.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import alliance.entity.Invigilator;

public class InvigilatorEditDialogController {
	@FXML
	private TextField nameField;
	@FXML
	private TextField addressField;
	@FXML
	private TextField postalCodeField;
	@FXML
	private TextField phoneNumberField;
	@FXML
	private TextField usernameField, passwordField;

	private Stage dialogStage;
	private Invigilator person;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 * 
	 * @param Invigilator
	 */
	public void setPerson(Invigilator person) {
		this.person = person;

		nameField.setText(person.getName());
		addressField.setText(person.getAddress());
		postalCodeField.setText(Integer.toString(person.getPostalCode()));
		phoneNumberField.setText(person.getPhoneNumber());
		usernameField.setText(person.getAccount().getUsername());
		passwordField.setText(person.getAccount().getPassword());
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			person.setName(nameField.getText());
			person.setAddress(addressField.getText());
			person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
			person.setPhoneNumber(phoneNumberField.getText());
			person.getAccount().setUsername(usernameField.getText());
			person.getAccount().setPassword(passwordField.getText());

			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (nameField.getText() == null || nameField.getText().length() == 0) {
			errorMessage += "No valid name!\n";
		}

		if (addressField.getText() == null
				|| addressField.getText().length() == 0) {
			errorMessage += "No valid address!\n";
		}

		if (postalCodeField.getText() == null
				|| postalCodeField.getText().length() == 0) {
			errorMessage += "No valid postal code!\n";
		} else {
			// try to parse the postal code into an int
			try {
				Integer.parseInt(postalCodeField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid postal code (must be an integer)!\n";
			}
		}

		if (phoneNumberField.getText() == null
				|| phoneNumberField.getText().length() == 0) {
			errorMessage += "No valid phone number!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message
			Dialogs.showErrorDialog(dialogStage, errorMessage,
					"Please correct invalid fields", "Invalid Fields");
			return false;
		}
	}
}
