package schedule.view;

import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class CustomerLocationViewController {

	@FXML
	private PieChart pieChart;

	private ObservableList<PieChart.Data> pieChartData;

	public CustomerLocationViewController() {}

	/**
	 * The initialize method is called after the constructor by JavaFX
	 */
	@FXML
	private void initilize() {

	}

	public void setData(Map<String, Double> data) {
		pieChartData = FXCollections.observableArrayList();

		data.forEach((key, value) -> pieChartData.add(new PieChart.Data(key, value)));

		pieChart.setData(pieChartData);
	}
}
