package model;

import java.util.ArrayList;
import view.InputOutput;

public class User {
	// Variables
	private String firstName, lastName, email, cpr, address, telephone, username, password, userType;
	private int userId;

	public User() {
	}

	public User(String fname, String lname, String email, String cpr, String address, String telephone, int userId, String userType) {
		this.firstName = fname;
		this.lastName = lname;
		this.email = email;
		this.cpr = cpr;
		this.address = address;
		this.telephone = telephone;
		this.userId = userId;
		this.userType = userType;
		this.username = setUsername();
		this.password = setPassword();
	}
	
	/* Method that creates user by asking for input (First name, Last name etc.)
	 * Email and CPR is checked using Regular Expression, Telephone is checked for length only
	 * UserId and UserType are set automatically (UserType can be changed)
	 * Username and Password are made by using a combination of First name, Last name and CPR
	 */
	public void createUser(ArrayList<User> userList) {
		this.firstName = InputOutput.inputDetails("first name");
		this.lastName = InputOutput.inputDetails("last name");
		this.email = InputOutput.inputDetails("email", "emailRegex");
		this.cpr = InputOutput.inputDetails("cpr", "\\d{6}-\\d{4}");
		this.address = InputOutput.inputDetails("address");
		this.telephone = InputOutput.inputDetails("telephone", 8);
		this.userId = userList.size() + 1;
		this.userType = "Customer";
		this.username = setUsername();
		this.password = setPassword();
		}

	// Getters
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getCprNumber() {
		return cpr;
	}

	public int getUserId() {
		return userId;
	}

	public String getAddress() {
		return address;
	}

	public String getTelephone() {
		return telephone;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	// Setters
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCprNumber(String cprNumber) {
		this.cpr = cprNumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String setUsername() {
		return firstName.substring(0, 1) + lastName.substring(0, 3);
	}

	public String setPassword() {
		return lastName.substring(0, 3) + cpr.substring(7);
	}
}
