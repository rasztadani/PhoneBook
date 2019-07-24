package phonebook;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	private final SimpleStringProperty firstName;
	private final SimpleStringProperty lastName;
	private final SimpleStringProperty email;
	private final SimpleStringProperty id;
	
	public Person() {
		this.firstName = new SimpleStringProperty("");
		this.lastName = new SimpleStringProperty("");
		this.email = new SimpleStringProperty("");
		this.id = new SimpleStringProperty("");
	}
	
	public Person(String lastName, String firstName, String email) {
		super();
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.email = new SimpleStringProperty(email);
		this.id = new SimpleStringProperty("");

	}
	
	public Person(Integer id, String lastName, String firstName, String email) {
		super();
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.email = new SimpleStringProperty(email);
		this.id = new SimpleStringProperty(String.valueOf(id));

	}
	
	public String getFirstName() {
		return firstName.get();
	}
	
	public void setFirstName(String v) {
		firstName.set(v);
	}
	
	public StringProperty firstNameProperty() {
		return firstName;
	}

	public String getLastName() {
		return lastName.get();
	}
	
	public void setLastName(String v) {
		lastName.set(v);
	}
	
	public StringProperty lastNameProperty() {
		return lastName;
	}
	
	public String getEmail() {
		return email.get();
	}
	
	public void setEmail(String v) {
		email.set(v);
	}
	
	public StringProperty emailProperty() {
		return email;
	}

	public String getId() {
		return id.get();
	}
	
	public void setId(String v) {
		id.set(v);
	}
	
	public StringProperty idProperty() {
		return id;
	}

}
