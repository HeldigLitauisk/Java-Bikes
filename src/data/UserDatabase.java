package data;

import java.util.ArrayList;
import java.util.Scanner;
import data.ReadAndWrite;
import model.*;
import view.InputOutput;

public class UserDatabase {
	private ArrayList<User> userList = new ArrayList<User>();

	// Default constructor that also fills userLIst with users from external file
	public UserDatabase() {
		userList = getAllUserList();
	}

	// Getter for ArrayList
	public ArrayList<User> getUserList() {
		return userList;
	}
	
	public User getCustomer(String line) {
		User userFromFile = new User();
		// Look for every ";" and turns the values into strings
		String[] values = line.split(";");
		if (values[7].equals("Manager")) {
			userFromFile = new Manager(values[0], values[1], values[2], values[3], values[4], values[5],
					Integer.parseInt(values[6]), values[7]);
		} else if (values[7].equals("Owner")) {
			userFromFile = new CarOwner(values[0], values[1], values[2], values[3], values[4], values[5],
					Integer.parseInt(values[6]), values[7]);
		} else {
			userFromFile = new Customer(values[0], values[1], values[2], values[3], values[4], values[5],
					Integer.parseInt(values[6]), values[7]);
		}
		userFromFile.setPassword();
		userFromFile.setUsername();
		return userFromFile;
	}

	// Get all customer details from a file
	public ArrayList<User> getAllUserList() {
		Scanner input = ReadAndWrite.readDetails("users.txt");
		// checking each line
		while (input.hasNextLine()) {
			userList.add(getCustomer(input.nextLine()));
		} // then added to a ArrayList
		return userList;
	}

	//This method returns a User object from userId 
	public User getUser(int uniqueId) {
		User user = new User();
		for (User e : userList) {
			if (e.getUserId() == uniqueId) {
				return e;
			}
		}
		return user;
	}

	//Applies discount if customer is loyal and rounds it to 2 decimals
		public double loyalty(double price, User user) {
			double newPrice = price;
			if (user instanceof Customer) {
				if (((Customer) user).isLoyalCustomer() == true) {
					InputOutput.systemMessage(13);
					newPrice = price * 0.75;
				}
			}
			if (user instanceof CarOwner) {
				InputOutput.systemMessage(13);
				newPrice = price * 0.66;
			}
			return (double) Math.round((newPrice) * 100d) / 100d;
		}

	// Method for writing arraylist into external file.
	public void writeUserToFile() {
		ReadAndWrite.emptyFile("users.txt");
		for (User e : userList) {
			String userDetails = e.getFirstName() + ";" + e.getLastName() + ";" + e.getEmail() + ";" + e.getCprNumber()
					+ ";" + e.getAddress() + ";" + e.getTelephone() + ";" + e.getUserId() + ";" + e.getUserType() + ";";
			ReadAndWrite.writeDetails("users.txt", userDetails);
		}
	}
}