package schedule.view;

import static schedule.SchedulingApplication.getApptTypes;

import java.text.DateFormatSymbols;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import schedule.model.Appointment;

public class ApptTypeMonthlySummaryController {

	@FXML
	private BarChart<String, Number> barChart;
	@FXML
	private CategoryAxis xAxis;

	private ObservableList<String> months = FXCollections.observableArrayList();
	private List<String> types = getApptTypes();

	public ApptTypeMonthlySummaryController() {}

	/**
	 * The initialize method is called after the constructor by JavaFX
	 */
	@FXML
	private void initialize() {
		String[] monthNames = DateFormatSymbols.getInstance(Locale.getDefault()).getMonths();
		for (String month : monthNames) {
			if(!month.isEmpty()) months.add(month);
		}
		xAxis.setCategories(months);
	}

	public void setAppointmentData(ObservableList<Appointment> appointments) {
		// loop through each appointment type
		for (String type : types) {
			// create a new series
			XYChart.Series<String, Number> series = new XYChart.Series<>();
			series.setName(type);

			// filter the appointments based on the type
			FilteredList<Appointment> apptsByType = appointments.filtered(a -> type.equals(a.getDescription()));

			// loop through every month
			for (String month : months) {

				// filter the appointments based on month
				FilteredList<Appointment> apptsByMonth = apptsByType.filtered(a -> {
					Month apptMonth = a.getStart().getMonth();
					String monthName = apptMonth.getDisplayName(TextStyle.FULL, Locale.getDefault());
					return month.equals(monthName);	
				});

				// add the twice-filtered appointments to the data series
				series.getData().add(new XYChart.Data<String, Number>(month, apptsByMonth.size()));
			}
			barChart.getData().add(series);
		}
	}
}
