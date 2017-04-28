package schedule.model;

import static schedule.SchedulingApplication.addAddress;
import static schedule.SchedulingApplication.lang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import static schedule.SchedulingApplication.URL;
import static schedule.SchedulingApplication.PASSWORD;
import static schedule.SchedulingApplication.USER_NAME;

/**
 * Note: I've not included the active field from the database. I have assumed a value of 1 in the active
 * column represents an active customer and a value of 0 represents an inactive customer that does not
 * need to be accessed.
 * @author Aaron Echols
 *
 */
public class Customer {

	private StringProperty name = new SimpleStringProperty();
	private StringProperty firstName = new SimpleStringProperty();
	private StringProperty lastName = new SimpleStringProperty();
	private StringProperty address1 = new SimpleStringProperty();
	private StringProperty address2 = new SimpleStringProperty();
	private StringProperty postalCode = new SimpleStringProperty();
	private StringProperty phone = new SimpleStringProperty();
	private StringProperty city = new SimpleStringProperty();
	private StringProperty country = new SimpleStringProperty();

	public static String[] splitName(String fullName) {
		if (!fullName.contains(" ")) {
			return new String[] {"", fullName };
		}

		String firstName = fullName.substring(0, fullName.indexOf(" "));
		String lastName = fullName.substring(fullName.indexOf(" ") + 1);

		return new String[]{ firstName, lastName };
	}

	/**
	 * Get the ID of a customer in the database
	 * @param customer the customer to find in the database
	 * @return the id number of the customer in the database, -1 if the customer was not found
	 */
	public static int getCustomerId(Customer customer) {
		int customerId = -1;

		try ( Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD) ) {

			String sql = "SELECT customerId FROM customer "
					+ "WHERE customerName = ? "
					+ "AND addressId = ? "
					+ "AND active = 1";
			PreparedStatement prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, customer.getName());
			prepstmt.setInt(2, addAddress(customer.getAddress1(), customer.getAddress2(),
					customer.getPostalCode(), customer.getPhone(),
					customer.getCity(), customer.getCountry()));
			ResultSet result = prepstmt.executeQuery();

			if(result.next()) {
				customerId = result.getInt("customerId");
			}
		} catch (SQLException e){
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}

		return customerId;
	}

	/**
	 * Get a customer from the database
	 * @param customerId the id of the customer to get
	 * @return the customer found in the database, null if the id was not found
	 */
	public static Customer getCustomer(int customerId) {
		Customer customer = null;

		try ( Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD) ) {

			String sql = "SELECT customerName, address, address2, postalCode, phone, city, country "
					+ "FROM customer AS cu, address AS ad, city AS ci, country AS co "
					+ "WHERE customerId = ? "
					+ "AND cu.addressId = ad.addressId "
					+ "AND ad.cityId = ci.cityId "
					+ "AND ci.countryId = co.countryId";
			PreparedStatement prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, customerId);
			ResultSet result = prepstmt.executeQuery();

			if(result.next()) {
				String name = result.getString("customerName");
				String address1 = result.getString("address");
				String address2 = result.getString("address2");
				String postalCode = result.getString("postalCode");
				String phone = result.getString("phone");
				String city = result.getString("city");
				String country = result.getString("country");
				customer = new Customer(name, address1, address2, postalCode, phone, city, country);
			}
		} catch (SQLException e){
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		return customer;
	}

	@Override
	public String toString() {
		return getName();
	}
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Customer other = (Customer) obj;

		if(!getName().equals(other.getName())) return false;
		if(!getAddress1().equals(other.getAddress1())) return false;
		if(!getAddress2().equals(other.getAddress2())) return false;
		if(!getPostalCode().equals(other.getPostalCode())) return false;
		if(!getPhone().equals(other.getPhone())) return false;
		if(!getCity().equals(other.getCity())) return false;
		if(!getCountry().equals(other.getCountry())) return false;

		return true;
	}

	public String getName() {
		return name.get();
	}
	public String getFirstName() {
		return firstName.get();
	}
	public String getLastName() {
		return lastName.get();
	}
	public String getAddress1() {
		return address1.get();
	}
	public String getAddress2() {
		return address2.get();
	}
	public String getPostalCode() {
		return postalCode.get();
	}
	public String getPhone() {
		return phone.get();
	}
	public String getCity() {
		return city.get();
	}
	public String getCountry() {
		return country.get();
	}

	public void setName(String name) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException(lang.getString("name") + " " + lang.getString("missingInfoMessage"));
		} else {
			this.name.set(name);
			String[] names = splitName(name);
			firstName.set(names[0]);
			lastName.set(names[1]);
		}
	}
	public void setFirstName(String firstName) {
		if(firstName == null || firstName.isEmpty()) {
			throw new IllegalArgumentException(lang.getString("firstName") + " " + lang.getString("missingInfoMessage"));
		} else {
			this.firstName.set(firstName);
			String[] names = splitName(getName());
			name.set(firstName + " " + names[1]);
		}
	}
	public void setLastName(String lastName) {
		if(lastName == null || lastName.isEmpty()) {
			throw new IllegalArgumentException(lang.getString("lastName") + " " + lang.getString("missingInfoMessage"));
		} else {
			this.lastName.set(lastName);
			String[] names = splitName(getName());
			name.set(names[0] + " " + lastName);
		}
	}
	public void setAddress1(String address1) {
		if(address1 == null || address1.isEmpty()) {
			throw new IllegalArgumentException(lang.getString("address1") + " " + lang.getString("missingInfoMessage"));
		} else {
			this.address1.set(address1);
		}
	}
	public void setAddress2(String address2) {
		this.address2.set(address2);
	}
	public void setPostalCode(String postalCode) {
		if(postalCode == null || postalCode.isEmpty()) {
			throw new IllegalArgumentException(lang.getString("postalCode") + " " + lang.getString("missingInfoMessage"));
		} else {
			this.postalCode.set(postalCode);
		}
	}
	public void setPhone(String phone) {
		if(phone == null || phone.isEmpty()) {
			throw new IllegalArgumentException(lang.getString("phone") + " " + lang.getString("missingInfoMessage"));
		} else {
			this.phone.set(phone);
		}
	}
	public void setCity(String city) {
		if(city == null || city.isEmpty()) {
			throw new IllegalArgumentException(lang.getString("city") + " " + lang.getString("missingInfoMessage"));
		} else {
			this.city.set(city);
		}
	}
	public void setCountry(String country) {
		if(country == null) {
			throw new IllegalArgumentException(lang.getString("country") + " " + lang.getString("missingInfoMessage"));
		} else {
			this.country.set(country);
		}
	}

	public StringProperty nameProperty() {
		return name;
	}
	public StringProperty firstNameProperty() {
		return firstName;
	}
	public StringProperty lastNameProperty() {
		return lastName;
	}
	public StringProperty address1Property() {
		return address1;
	}
	public StringProperty address2Property() {
		return address2;
	}
	public StringProperty postalCodeProperty() {
		return postalCode;
	}
	public StringProperty phoneProperty() {
		return phone;
	}
	public StringProperty cityProperty() {
		return city;
	}
	public StringProperty countryProperty() {
		return country;
	}

	public Customer(String name, String address1, String address2, String postalCode, String phone, String city, String country) {
		setName(name);
		setAddress1(address1);
		setAddress2(address2);
		setPostalCode(postalCode);
		setPhone(phone);
		setCity(city);
		setCountry(country);
	}
	public Customer(String firstName, String lastName, String address1, String address2, String postalCode, String phone, String city, String country) {
		this(firstName + " " + lastName, address1, address2, postalCode, phone, city, country);
	}
}
