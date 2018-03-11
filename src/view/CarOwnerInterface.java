package view;

public class CarOwnerInterface {

	/* Runs a while loop until car owner chooses a correct menu option Choice is
	 * allowed to be only integer. Exceptions are being handled in inputDetails() */
	public static int carOwnerMenuView() {
		int choice = 0;
		while (choice < 1 || choice > 3) {
			InputOutput.systemMessage(9);
			System.out.println("|1| Manage your cars");
			System.out.println("|2| Register a car");
			System.out.println("|3| Return to Main Menu");
			System.out.println("------------------------------------------");
			choice = InputOutput.inputDetails();
		}
		return choice;
	}

	public static int managingView() {
		int choice = 0;
		while (choice < 1 || choice > 3) {
			InputOutput.systemMessage(9);
			System.out.println("|1| Check when this car is booked");
			System.out.println("|2| Toggle car's availability");
			System.out.println("|3| Delete car completely");
			System.out.println("------------------------------------------");
			choice = InputOutput.inputDetails();
		}
		return choice;
	}
}
