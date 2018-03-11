package view;

import java.util.Scanner;
import model.Car;
import java.util.ArrayList;

public class InputOutput {

	public InputOutput() {
	}

	/* Takes the input from the User. Uses regular expression to check if it only Integer. Handles exception */
	public static int inputDetails() throws NumberFormatException {
		int choice = 0;
		try {
			Scanner input = new Scanner(System.in);
			String details = input.nextLine();
			while (!details.matches("[0-9]+")) {
				systemMessage(3);
				details = input.nextLine();
			}
			choice = Integer.parseInt(details);
		} catch (NumberFormatException nfe) {
			InputOutput.systemMessage(3);
		}
		return choice;
	}

	/* Overloads inputDetails method
	 * Method that lets the user enter details that are String */
	public static String inputDetails(String details) {
		Scanner input = new Scanner(System.in);
		if (details.equals("carId")) {
			System.out.println("Enter your choice of car! (using " + details + "):");
			String carId = String.valueOf(inputDetails());
			return carId;
		} else if (details.equals("Credit Card")) {
			System.out.println("Enter your " + details + " details:");
			String userInput = input.nextLine();
			return userInput;
		} else {
			System.out.println("Enter your " + details + ":");
			return (input.nextLine());
		}
	}

	/* Overloads inputDetails method
	 * Method that prompts the user to enter input of specific length */
	public static String inputDetails(String details, int length) {
		System.out.println("Enter your " + details + ":");
		String number = String.valueOf(inputDetails());
		while (number.length() != length) {
			System.out.println(
					"Please enter a valid " + details + " number, that consists of exactly " + length + " characters.");
			number = String.valueOf(inputDetails());
		}
		return number;
	}

	/* Overloads inputDetails method
	 * Method that prompts the user for specific pattern (e.g. cpr pattern, email) */
	public static String inputDetails(String details, String pattern) {
		Scanner input = new Scanner(System.in);
		String value = "";
		if (details.toLowerCase().contains("cpr")) {
			// CPR is 6 characters followed by "-" followed by 4 characters, \\d{6}-\\d{4}
			System.out.println("CPR number (Format: 123456-1234): ");
			value = input.nextLine();
		}
		if (details.toLowerCase().contains("email")) {
			System.out.println("Email (Format: abc@xyz.abc): ");
			value = input.nextLine();
			//Regular expression, to check email address format
			String regexEmail = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
			pattern = regexEmail;
		}
		while (!value.matches(pattern)) {
			System.out.println("\nPlease enter your " + details + " in the right format: ");
			value = input.nextLine();
		}
		return value;
	}
	
	/* Parameter brand is a string of all available cars for User 
	 * It Allows User to enter car brand and checks 
	 * whether selected brand is among available cars 
	 * returns selected brand */
	public static String inputBrand(String brand)  {
		Scanner input = new Scanner(System.in);
		System.out.println(brand);
		systemMessage(7);
		String inputBrand = input.nextLine();
		while (!brand.toLowerCase().contains(inputBrand.toLowerCase()) || inputBrand.isEmpty()) {
			systemMessage(3);
			inputBrand = input.nextLine();
		}
		return inputBrand;
	}

	public static int inputDetailsPrice() {
		System.out.println("Enter a daily rental price for your car: ");
		return inputDetails();
	}

	/* Method that allows carOwner to choose an owned car
	 * from the list of his cars. If user doesn't have any cars returns 0 */
	public static int selectOwnerCar(ArrayList<Car> carList) {
		if (carList.isEmpty()) {
			InputOutput.systemMessage(11);
			return 0;
		}
		InputOutput.systemMessage(10);
		int choice = 0;
		boolean correct = false;
		while (!correct) {
			choice = inputDetails();
			for (Car e : carList) {
				if (e.getCarId() == choice) {
					correct = true;
					return choice;
				}
			}
			InputOutput.systemMessage(3);
		}
		return choice;
	}

	/* Method that prints out commonly repeated messages for the user */
	public static void systemMessage(int message) {
		switch (message) {
		case 1:
			System.out.println("You are logged in ");
			break;
		case 2:
			System.out.println("You are not logged in - you typed wrong password or username");
			break;
		case 3:
			System.out.println("Invalid input. Try again! ");
			break;
		case 4:
			System.out.println("You have left ShareUs");
			break;
		case 5:
			System.out.println("You have exceeded the number of tries! Please try again after a few hours");
			break;
		case 6:
			System.out.println("You don't have manager rights");
			break;
		case 7:
			System.out.println("Select a car brand!");
			break;
		case 8:
			System.out.println("The location of your car has been set to your address."
					+ "\n You can change it in \"Manage your cars\".");
			break;
		case 9:
			System.out.println("------------------------------------------");
			System.out.println("          WHAT WOULD YOU LIKE TO DO? ");
			System.out.println("------------------------------------------");
			System.out.println("");
			System.out.println("Choose a number, indicating an option!");
			System.out.println("");
			System.out.println("------------------------------------------");
			break;
		case 10:
			System.out.println("Select a car ID!");
			break;
		case 11:
			System.out.println("There are no records!");
			break;
		case 12:
			System.out.print("The availability of your car was changed to: ");
			break;
		case 13:
			System.out.println("The discount has been applied for your account");
			break;
		case 14: 
			System.out.println("------------------------------------------");
			System.out.println("              LOG IN ");
			System.out.println("------------------------------------------");
		default:
			break;
		}
	}
}
