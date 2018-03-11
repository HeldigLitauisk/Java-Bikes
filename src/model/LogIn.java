package model;

import java.util.ArrayList;
import view.InputOutput;

public class LogIn {

	public LogIn() {
	}
	
	/*The main login method which, validates username and password entered by user (allowing 3 attempts)
	 *It also returns the ID of the user if the login process is sucessfully done.
	 *The unique ID will be used through out the application to make bookings,register car etc.*/
	public int userLogIn(ArrayList<User> userList) {
		InputOutput.systemMessage(14);
		int failedAttempts = 0;
		// Loop to check the number of tries the customer has done (at 3 the application exits)
		while (failedAttempts < 3) {
			String loginUserName = InputOutput.inputDetails("Username");
			String loginPassword = InputOutput.inputDetails("Password");
			// Call the isValidUserNamePassword method to check if the username and password is in the arraylist
			if (validateLogIn(loginUserName, loginPassword, userList)) { 
				failedAttempts = 4;
				return setUniqueId(loginPassword, userList);
			} else {
				failedAttempts += 1;
				if (failedAttempts == 3) {
					InputOutput.systemMessage(5);
				} else {
					InputOutput.systemMessage(2);
				}
			}
		}
		return 0;
	}
	
	/* Method which is called on above to validate username and password
	 * based on already registered ones. Usernames and passwords are not imported from external file 
	 * but are created after importing (for security reasons)*/
	public boolean validateLogIn(String loginUserName, String loginPassword, ArrayList<User> userList) {
		for (User e : userList) {
			if (loginUserName.equals(e.getUsername()) && loginPassword.equals(e.getPassword())) {
				return true;
			}
		}
		return false;
	}
	
	// This method returns a unique ID if the application user succesfully logged in
	public int setUniqueId(String password, ArrayList<User> userList) {
		int x = 0;
		for (User e : userList) {
			if (e.getPassword().equals(password)) {
				x = e.getUserId();
			}
		}
		return x;
	}
}
