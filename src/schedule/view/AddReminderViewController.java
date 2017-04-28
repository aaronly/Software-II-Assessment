package schedule.view;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import schedule.model.Reminder;

public class AddReminderViewController {

	private Stage stage;

	private Reminder reminder;
	private int appointmentId;
	private LocalDateTime start;

	@FXML
	private TextField amount;
	@FXML
	private ComboBox<ChronoUnit> unit;

	public AddReminderViewController() {}

	/**
	 * The initialize method is called after the constructor by JavaFX
	 */
	@FXML
	private void initialize() {
		ObservableList<ChronoUnit> units = FXCollections.observableArrayList();
		units.add(ChronoUnit.MINUTES);
		units.add(ChronoUnit.HOURS);
		units.add(ChronoUnit.DAYS);

		unit.setItems(units);
		unit.getSelectionModel().select(ChronoUnit.MINUTES);
	}

	public void setupDialog(Stage stage, int apptId, LocalDateTime apptTime) {
		this.stage = stage;
		appointmentId = apptId;
		start = apptTime;
	}

	public Reminder getReminder() {
		return reminder;
	}

	@FXML
	private void handleSave() {
		start = start.minus(Integer.parseInt(amount.getText()), unit.getValue());
		// I am unsure about color, or snooze values. So, at the moment, a reminder can hold those values,
		// but all new reminders will have the color red, and 15 minutes of snooze.
		reminder = new Reminder(start, Color.RED, appointmentId, 15, ChronoUnit.MINUTES);
		stage.close();
	}

	@FXML
	private void handleCancel() {
		stage.close();
	}
}
