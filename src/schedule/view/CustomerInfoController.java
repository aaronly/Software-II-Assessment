package schedule.view;

import static schedule.SchedulingApplication.addCustomer;
import static schedule.SchedulingApplication.lang;
import static schedule.SchedulingApplication.updateCustomer;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import schedule.SchedulingApplication;
import schedule.model.Customer;

public class CustomerInfoController {

	private Stage stage;
	private Customer customer, newCustomer;

	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField address1Field;
	@FXML
	private TextField address2Field;
	@FXML
	private TextField cityField;
	@FXML
	private TextField postalCodeField;
	@FXML
	private TextField phoneField;
	@FXML
	private ComboBox<String> countryComboBox;

	private List<String> countries;

	public CustomerInfoController() {}

	/**
	 * The initialize method is called after the constructor by JavaFX
	 */
	@FXML
	private void initialize() {

	}

	/**
	 * Set up the customer information dialog window with a reference to the (possible) customer that was selected
	 * @param stage the stage for the Customer Info Controller
	 * @param customer the customer that was selected, null if no customer was selected
	 */
	public void setupDialog(Stage stage, Customer customer) {
		this.stage = stage;
		this.customer = customer;

		countries = SchedulingApplication.getAllCountries();
		countries.sort((a, b) -> a.compareTo(b));
		countryComboBox.getItems().addAll(countries);

		initTextFields(customer);
	}

	/**
	 * Initialize all the text fields with customer information if one was provided
	 * @param customer
	 */
	private void initTextFields(Customer customer) {
		if(customer == null) return;

		firstNameField.setText(customer.getFirstName());
		lastNameField.setText(customer.getLastName());
		address1Field.setText(customer.getAddress1());
		address2Field.setText(customer.getAddress2());
		postalCodeField.setText(customer.getPostalCode());
		phoneField.setText(customer.getPhone());
		cityField.setText(customer.getCity());
		countryComboBox.getSelectionModel().select(customer.getCountry());
	}

	public Customer getNewCustomer() {
		return newCustomer;
	}

	@FXML
	private void handleSave() {

		// get all the entered information, convert null values to empty strings
		String firstName = (firstNameField.getText() == null) ? "" : firstNameField.getText();
		String lastName = (lastNameField.getText() == null) ? "" : lastNameField.getText();
		String address1 = (address1Field.getText() == null) ? "" : address1Field.getText();
		String address2 = (address2Field.getText() == null) ? "" : address2Field.getText();
		String postalCode = (postalCodeField.getText() == null) ? "" : postalCodeField.getText();
		String phone = (phoneField.getText() == null) ? "" : phoneField.getText();
		String city = (cityField.getText() == null) ? "" : cityField.getText();
		String country = (countryComboBox.getSelectionModel().getSelectedItem() == null) ?
				"" : countryComboBox.getSelectionModel().getSelectedItem();

		try {
			newCustomer = new Customer(firstName, lastName,	address1, address2, postalCode, phone, city, country);

			if (customer ==  null) { // add a customer
				addCustomer(newCustomer);
			} else { // update a customer
				updateCustomer(customer, newCustomer);
			}

		} catch (IllegalArgumentException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(lang.getString("missingInfoTitle"));
			alert.setHeaderText(e.getMessage());
			alert.initOwner(stage);
			alert.showAndWait();
			return;
		}

		stage.close();
	}

	@FXML
	private void handleCancel() {
		stage.close();
	}

}
