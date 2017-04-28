package schedule.view;

import static schedule.SchedulingApplication.lang;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import schedule.SchedulingApplication;

public class UserLoginController {

	private SchedulingApplication mainApp;
	private Stage stage;

	@FXML
	private TextField userNameField;
	@FXML
	private PasswordField passwordField;

	public UserLoginController() {}

	/**
	 * The initialize method is called after the constructor by JavaFX
	 */
	@FXML
	private void initialize() {

	}

	/**
	 * Set up the user login window
	 * @param mainApp the main application
	 * @param stage the stage for the User Login Controller
	 */
	public void setUpLogin(SchedulingApplication mainApp, Stage stage) {
		this.stage = stage;
		this.mainApp = mainApp;
	}

	@FXML
	private void handleLogin() {

		// do not allow an empty user name
		// an empty PASSWORD could be allowed for a guest account
		if (userNameField.getText() == null || userNameField.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(lang.getString("userNameBlankTitle"));
			alert.setHeaderText(lang.getString("userNameBlankMessage"));
			alert.initOwner(stage);
			alert.showAndWait();
			return;
		}

		// attempt to login
		mainApp.login(userNameField.getText(), passwordField.getText());
	}

	@FXML
	private void handleCancel() {
		Platform.exit();
	}

}
