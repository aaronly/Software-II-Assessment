package schedule.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

import javafx.scene.paint.Color;

public class Reminder {

	private int appointmentId;
	private Color color;
	private LocalDateTime localDT;
	private int snoozeIncrement;
	private ChronoUnit snoozeUnit;

	public Reminder(LocalDateTime localDT, Color color, int appointmentId, int snoozeIncrement, ChronoUnit snoozeUnit) {
		this.localDT = localDT;
		this.color = color;
		this.appointmentId = appointmentId;
		this.snoozeIncrement = snoozeIncrement;
		this.snoozeUnit = snoozeUnit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((localDT == null) ? 0 : localDT.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Reminder other = (Reminder) obj;

		if (localDT == null) {
			if (other.localDT != null)
				return false;
		} else if (!localDT.equals(other.localDT))
			return false;
		return true;
	}
	@Override
	public String toString() {
		String dateTime = localDT.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
		return dateTime;
	}

	public int getAppointmentId() {
		return appointmentId;
	}
	public Color getColor() {
		return color;
	}
	public LocalDateTime getLocalDT() {
		return localDT;
	}
	public int getSnoozeIncrement() {
		return snoozeIncrement;
	}
	public ChronoUnit getSnoozeUnit() {
		return snoozeUnit;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setLocalDT(LocalDateTime localDT) {
		this.localDT = localDT;
	}
	public void setSnoozeIncrement(int snoozeIncrement) {
		this.snoozeIncrement = snoozeIncrement;
	}
	public void setSnoozeUnit(ChronoUnit snoozeUnit) {
		this.snoozeUnit = snoozeUnit;
	}


}
