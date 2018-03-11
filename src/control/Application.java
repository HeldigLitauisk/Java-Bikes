package control;

import data.*;
import model.*;
import view.*;

public class Application {
	private int uniqueId = 0;
	private UserDatabase userData = new UserDatabase();
	private CarDatabase carData = new CarDatabase();
	private LogIn logIn = new LogIn();
	private BookingDatabase bookingData = new BookingDatabase();
	private Booking bookingInProgress = new Booking();

	public Application() {
	}

	/* The FIRST MENU in the system. Allows user to continue as a Customer/Car Owner
	 * or Manager. Also it allows user to choose an option to quit 
	 * (Writes Bookings, Users, Cars data to file when program quits)
	 *  */
	public void applicationProcess() {
		int choice = UserInterface.menuChoice();
		switch (choice) {
		case 1:
			customerMenu();
			break;
		case 2:
			managerMenu();
			break;
		case 3:
			closingProcess();
			break;
		default:
			break;
		}
	}

	/* The SECOND MENU in the system. Runs next UserInterface and allows user to
	 * choose an option to register an account, login, or quit the application
	 * Register an account and login redirects to the MAIN MENU, after succesful login
	 * */
	public void customerMenu() {
		int choice = UserInterface.customerMenuView();
		switch (choice) {
		case 1:
			// Allows user to create a new account and sets UserId based on the size of the user Database.
			User newUser = new User();
			newUser.createUser(userData.getUserList());
			// Adds new user to the User ArrayList
			userData.getUserList().add(newUser);
			// Runs LogIn procedure and assigns user with uniqueID to trace him
			uniqueId = logIn.userLogIn(userData.getUserList());
			// Run post registration process
			customerSecondMenu();
			break;
		case 2:
			// Runs LogIn procedure and assigns user with uniqueID to trace him during session
			uniqueId = logIn.userLogIn(userData.getUserList());
			// Run post login process
			customerSecondMenu();
			break;
		case 3:
			// Exit the application
			closingProcess();
			break;
		default:
			break;
		}
	}

	/* Runs the Main MENU after logging in as a Customer. Returns to this menu after
	 * Customer or Car Owner completes their objectives
	 * Prompts Customer to rent a car or to become a Car Owner to manage his cars */
	public void customerSecondMenu()  {
		boolean quit = false;
		while (!quit) {
			try {
				Thread.sleep(500);
				int choice = UserInterface.customerSecondMenuView(userData.getUser(uniqueId));
				switch (choice) {
				case 1:
					rentCar();
					break;
				case 2:
					carOwnerMenu();
					break;
				case 3:
					closingProcess();
					break;
				default:
					break;
				}
				// Catching exception during the sleep
			} catch (InterruptedException ex) {
			}
		}
	}

	/* Runs Car Owner Menu , where the Car Owner can display his cars or
	 * register a new car (Works for Customers and Car Owners alike)
	 * also if the third option is chosen it returns to MAIN MENU */
	public void carOwnerMenu() {
		int choice = CarOwnerInterface.carOwnerMenuView();
		switch (choice) {
		case 1:
			// Displays All Cars belonging to the Car Owner, prompts to choose car
			choice = InputOutput.selectOwnerCar(carData.getOwnersCar(uniqueId));
			manageCar(choice);
			break;
		case 2:
			// Creates a car based on Car Owner's or Customer's input
			Car newCar = new Car();
			newCar.createCar(carData.getCarList(), userData.getUser(uniqueId));
			// Adds created car to ArrayList.
			carData.getCarList().add(newCar);
			// Information for Car Owners regarding car's location
			InputOutput.systemMessage(8);
			// If Customer adds a car, he becomes a Car Owner the next time system loads.
			userData.getUser(uniqueId).setUserType("Owner");
			break;
		case 3:
			// returns to main menu
			break;
		}
	}

	/* MANAGE CAR is a MENU where Car Owner manages a chosen car.
	 * If the car owner has no cars (carId = 0) this menu return to MAIN MENU
	 * It displays options for managing car such removing car, toggling availability (making it unavailable to book)
	 * or shows the time slots that are booked */
	public void manageCar(int carId) {
		if (carId != 0) {
			int choice = CarOwnerInterface.managingView();
			switch (choice) {
			case 1:
				// Displays all times when one of your chosen cars is booked
				bookingData.displayBookings(bookingData.whenBooked(carId));
				break;
			case 2:
				// Allows to toggle selected car's availability
				carData.carFromCarId(carId).toggleAvailability();
				break;
			case 3:
				// Removes car completely from ArrayList
				carData.getCarList().remove(carData.carFromCarId(carId));
				break;
			}
		}
	}
	
	/* Starts renting procedure. Where customer can select date, time slot and duration.
	 * Afterwards all cars available are shown in a list. The customer can then move on to
	 * choosing a car.
	 * */
	public void rentCar()  {
		// Saves booking details to Booking object from Customer input
		bookingInProgress.createBooking(UserInterface.customerTimeSlot(), uniqueId);
		// Prints Customer all available Cars for the chosen time
		bookingData.availableCars(bookingData.bookedCars(bookingInProgress, carData.getCarList()),
				carData.getCarList());
		// Displays next menu for sorting/filtering cars
		choiceMenu();
	}

	/* Displays options for Customer to sort/filter through available cars and
	 * select a car. It starts the confirmation process after the customer selects a car */
	public void choiceMenu() {
		int choice = UserInterface.choiceMenuView();
		switch (choice) {
		case 1:
			// Filter available cars by brand
			String inputBrand = InputOutput.inputBrand(bookingData.returnBrands(bookingData.getAvailableCars()));
			// Display filtered cars
			carData.displayCars(bookingData.sortByBrand(bookingData.getAvailableCars(), inputBrand));
			// Customer selects cars from the displayed cars
			bookingData.selectCarById(bookingInProgress);
			// Start confirmation method
			confirmation();
			break;
		case 2:
			// Sort available cars by price
			bookingData.sortByPrice(bookingData.getAvailableCars());
			// Display sorted cars
			carData.displayCars(bookingData.getAvailableCars());
			// Customer selects cars from the displayed cars
			bookingData.selectCarById(bookingInProgress);
			// Start confirmation method
			confirmation();
			break;
		case 3:
			// Customer can Choose another time slot
			rentCar();
			break;
		case 4:
			break;
		default:
			break;
		}
	}

	/* Asks Customer for a confirmation of the booking with printed price including
	 * discount for loyal Customers. After Confirmation a receipt is printed out for the customer */
	public void confirmation() {
		// Calculates price of the car for the duration of the rent and applies discount
		double price = userData.loyalty(bookingData.calculatePrice(bookingInProgress), userData.getUser(uniqueId));
		// Ask for input from user and prints price for Customer
		int choice = UserInterface.confirmationView(price, carData.carFromBooking(bookingInProgress),
				bookingInProgress);
		switch (choice) {
		case 1:
			// Initiates payment method
			Payment payment = new Payment();
			payment.pay();
			// If pay is successful adds the final Booking to the ArrayList
			bookingData.getBookingList().add(bookingInProgress);
			// Prints the receipt for the Customer
			UserInterface.receipt(userData.getUser(uniqueId));
			break;
		case 2:
			// Returns to Main menu
			break;
		default:
			break;
		}
	}

	/* Starts LogIn for manager */
	public void managerMenu() {
		uniqueId = logIn.userLogIn(userData.getUserList());
		// Only proceeds to next step if User is an instance of Manager
		if (userData.getUser(uniqueId) instanceof Manager) {
			customerSecondMenu();
		} else {
			InputOutput.systemMessage(6);
			closingProcess();
		}
	}

	/* When User decides to quit Application writes all User, Booking, Cars data
	 * from ArrayList to external files */
	public void closingProcess() {
		InputOutput.systemMessage(4);
		carData.writeCarToFile();
		userData.writeUserToFile();
		bookingData.writeBookingToFile();
		System.exit(0);
	}
}
