package view;

import java.time.format.TextStyle;
import java.util.Locale;
import model.*;
import java.time.*;

public class UserInterface {

	/* Run a while loop until user chooses a correct menu option
	 * Choice is allowed to be only integer. Exceptions are being handled in inputDetails();
	 */
	public static int menuChoice()  {
		int answer = 0;
		while (answer < 1 || answer > 3) {
				// Menu choices
				System.out.println("------------------------------------------");
				System.out.println("          WELCOME TO SHAREUS! ");
				System.out.println("------------------------------------------");
				System.out.println("");
				System.out.println("What do you want to do?");
				System.out.println("Choose a number, indicating an option!");
				System.out.println("------------------------------------------");
				System.out.println("|1| Continue as a customer");
				System.out.println("|2| Continue as a manager");
				System.out.println("|3| Exit");
				System.out.println("------------------------------------------");
				answer = InputOutput.inputDetails();
		}
		return answer;
	}

	public static int customerMenuView() {
		int choice = 0;
		while (choice < 1 || choice > 3) {
				System.out.println("------------------------------------------");
				System.out.println("          WELCOME CUSTOMER! ");
				System.out.println("------------------------------------------");
				System.out.println("");
				System.out.println("What do you want to do?");
				System.out.println(" Choose a number, indicating an option!");
				System.out.println("------------------------------------------");
				System.out.println("|1| Register an account");
				System.out.println("|2| Login as existing customer");
				System.out.println("|3| Exit");
				System.out.println("------------------------------------------");
				choice = InputOutput.inputDetails();
		}
		return choice;
	}

	public static int customerSecondMenuView(User user) {
		int answer = 0;
		while (answer < 1 || answer > 3) {
				// Menu choices
				System.out.println("------------------------------------------");
				System.out.println("         WELCOME BACK, " + user.getFirstName().toUpperCase());
				System.out.println("------------------------------------------");
				System.out.println("");
				System.out.println("What do you want to do?");
				System.out.println("Choose a number, indicating an option");
				System.out.println("------------------------------------------");
				System.out.println("|1| Rent a Car");
				System.out.println("|2| For Car owners");
				System.out.println("|3| Sign out");
				System.out.println("------------------------------------------");
				answer = InputOutput.inputDetails();
		}
		return answer;
	}
	/* This method presents a booking registration panel for dates,duration and timeslot
	 * The user can input his desired choices here. Which will be used in the booking process
	 */
	public static String customerTimeSlot() {
		int year = 0, month = 0, day = 0, duration = 0;
		LocalDate localDate = LocalDate.now();
		String timeslot = "";
		while (year < 2017 || year > 2019) {
			System.out.println("------------------------------------------");
			System.out.println("         SELECT A YEAR UNTIL 2019");
			System.out.println("------------------------------------------");
			System.out.println("");
			year = InputOutput.inputDetails();
		}
		while (month < 1 || month > 12 || (localDate.getYear() == year && localDate.getMonthValue() > month)) {
			System.out.println("------------------------------------------");
			System.out.println("         SELECT A MONTH [1-12]");
			System.out.println("------------------------------------------");
			System.out.println("");
			month = InputOutput.inputDetails();
		}
		while (day < 1 || day > Month.of(month).length(false)
				|| (localDate.getMonthValue() == month && localDate.getDayOfMonth() > day)) {
			System.out.println("------------------------------------------");
			System.out.println("         SELECT A DAY [1-" + Month.of(month).length(false) + "]");
			System.out.println("------------------------------------------");
			System.out.println("");
			day = InputOutput.inputDetails();
		}
		do {
			System.out.println("------------------------------------------");
			System.out.println("    (MORNING, AFTERNOON, EVENING) ");
			System.out.println("------------------------------------------");
			System.out.println("");
			timeslot = InputOutput.inputDetails("selected TIMESLOT");
		} while (!timeslot.toLowerCase().equals("morning") && !timeslot.toLowerCase().equals("afternoon")
				&& !timeslot.toLowerCase().equals("evening"));

		do {
			System.out.println("------------------------------------------");
			System.out.println("         SELECT A DURATION");
			System.out.println("       (MIN. 15 MINS AND MAX. 4HOURS)");
			System.out.println("------------------------------------------");
			System.out.println("");
			duration = InputOutput.inputDetails();
			if (duration <= 4) {
				duration = duration * 60;
			}
		} while (duration < 15 || duration > 240);
		String time = month + ";" + day + ";" + year + ";" + duration + ";" + timeslot;
		return time;
	}

	public static int choiceMenuView() {
		int choice = 0;
		while (choice < 1 || choice > 4) {
				InputOutput.systemMessage(9);
				System.out.println("|1| Filter available cars by brand");
				System.out.println("|2| Sort available cars ascending by price");
				System.out.println("|3| Choose another timeslot");
				System.out.println("|4| Return to Main Menu");
				System.out.println("------------------------------------------");
				choice = InputOutput.inputDetails();
		}
		return choice;
	}

	/* This method return a booking confirmation screen for the Customer.
	 * He can choose to move on with the booking or return
	 */
	public static int confirmationView(double price, Car car, Booking booking){
		int choice = 0;
		while (choice < 1 || choice > 2) {
				System.out.println("--------------------------------------------------");
				System.out.println("WOULD YOU LIKE TO CONFIRM BOOKING OF THIS " + car.getCarBrand() + "?");
				System.out.println("WITH THE FOLLOWING SPECIFICATIONS:");
				System.out.println("Car ID: " + car.getCarId());
				System.out.println("Car Color: " + car.getCarColor());
				System.out.println("Car Location: " + car.getLocation());
				System.out.println("On the following day: "
						+ booking.getMonthDay().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " "
						+ booking.getMonthDay().getDayOfMonth());
				System.out.println("The price for the duration of the booking is: $" + price);
				System.out.println("--------------------------------------------------");
				System.out.println("Would you like to book it?");
				System.out.println("1: YES     2: NO");
				choice = InputOutput.inputDetails();
		}
		return choice;
	}
	// This method prints a receipt for the customer after payment is done
	public static void receipt(User user) {
		System.out.println("--------------------------------------------------");
		System.out.println("   THANK YOU " + user.getFirstName().toUpperCase() + " FOR BOOKING WITH SHARE US!   ");
		System.out.println("We have sent your receipt to your email address (" + user.getEmail() + ")");
	}

	public static void managerMenuView()  {
		InputOutput.systemMessage(1);
	}
}
