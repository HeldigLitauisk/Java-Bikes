package data;

import java.time.Duration;
import java.time.MonthDay;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import data.ReadAndWrite;
import model.*;
import view.InputOutput;

public class BookingDatabase {
	private ArrayList<Booking> bookingList = new ArrayList<Booking>();
	private ArrayList<Car> availableCars = new ArrayList<Car>();

	// Default constructor that also fills the bookingList with bookings in the external file
	public BookingDatabase() {
		bookingList = getAllBooking();
	}

	// getter
	public ArrayList<Booking> getBookingList() {
		return bookingList;
	}

	public ArrayList<Car> getAvailableCars() {
		return availableCars;
	}

	// Add booking to the Arraylist
	public void addBooking(Booking newBooking) {
		bookingList.add(newBooking);
	}

	// creates booking object from external files
	public Booking getBooking(String line) {
		Booking bookingFromFile = new Booking();
		String[] values = line.split(";");
		bookingFromFile.setMonthDay(MonthDay.of((Integer.parseInt(values[0])), (Integer.parseInt(values[1]))));
		bookingFromFile.setYear(Year.of(Integer.parseInt(values[2]))); 
		bookingFromFile.setDuration(Duration.ofMinutes(Integer.parseInt(values[3]))); 
		bookingFromFile.setTimeSlot(values[4]);
		bookingFromFile.setCustomerId(Integer.parseInt(values[5]));
		bookingFromFile.setCarId(Integer.parseInt(values[6]));
		return bookingFromFile;
	}

	// Fills an ArrayList of bookings
	public ArrayList<Booking> getAllBooking() {
		Scanner input = ReadAndWrite.readDetails("booking.txt");
		// checking each line
		while (input.hasNextLine()) {
			bookingList.add(getBooking(input.nextLine()));
		}
		return bookingList;
	}

	/*
	 * Returns car IDs which are booked at a specific time slot Car IDs are used
	 * as the common variable between booking data and car data For this purpose
	 * an ArrayList of Integers were created, which store carIDs that are not
	 * available at the time chosen by the user. Only cars that are set
	 * available for booking by the Car Owner and non-booked cars are shown
	 */
	public ArrayList<Integer> bookedCars(Booking bookingInProgress, ArrayList<Car> carList) {
		ArrayList<Integer> bookedCars = new ArrayList<Integer>();
		// Goes through each car and only adds unavailable carIds to the
		// bookedCars ArrayList
		for (Car c : carList) {
			if (c.getAvailability() == false)
				bookedCars.add(c.getCarId());
		}
		//Goes through each booking and only adds carIds which are booked on a
		// specified date and time slot
		for (Booking e : bookingList) {
			if ((e.getYear().equals(bookingInProgress.getYear()))
					&& (e.getMonthDay().equals(bookingInProgress.getMonthDay()))
					&& (e.getTimeSlot().equals(bookingInProgress.getTimeSlot()))) {
				bookedCars.add(e.getCarId());
			}
		}
		return bookedCars;
	}
	/*Method that fills an ArrayList with object from another ArrayList
	 * Used so that the original carsList would not be modified throughout
	 * the filtering process
	 */
	public ArrayList<Car> cloneCarList(ArrayList<Car> carList) {
		availableCars = new ArrayList<Car>();
		for (Car e: carList) {
			availableCars.add(e);
		}
		return availableCars;
	}

	// Prints out all cars which are available at a specific time slot
	public ArrayList<Car> availableCars(ArrayList<Integer> bookedCars, ArrayList<Car> carList) {
		availableCars = cloneCarList (carList);
		// Goes through each car ID in booked cars, compare to IDs in carList,
		// remove Car object with matching IDs that way only available cars are printed out
		for (Integer e : bookedCars) {
			for (Car c : availableCars) {
				if (e == c.getCarId()) {
					availableCars.remove(c);
					break;
				}
			}
		}
		return availableCars;
	}

	/* Returns car brands for a customer to choose from that are available at
	 * the time slot given by the customer
	 */
	public String returnBrands(ArrayList<Car> availableCars) {
		String brands = "";
		for (Car e : availableCars) {
			if (!brands.toLowerCase().contains(e.getCarBrand().toLowerCase())) {
				brands += e.getCarBrand() + " ";
			}
		}
		return brands;
	}

	// Filter Available cars by brand
	public ArrayList<Car> sortByBrand(ArrayList<Car> availableCarList, String brand) {
		ArrayList<Car> filteredByBrand = new ArrayList<Car>();
		for (Car e : availableCarList) {
			if (e.getCarBrand().toLowerCase().contains(brand.toLowerCase())) {
				filteredByBrand.add(e);
			}
		}
		return filteredByBrand;
	}

	// Sort Available cars by price
	public void sortByPrice(ArrayList<Car> availableCarList) {
		Collections.sort(availableCarList);
	}

	public void selectCarById(Booking currentBooking) {
		boolean input = true;
		while (input) {
			int choice = Integer.parseInt(InputOutput.inputDetails("carId"));
			for (Car e : availableCars) {
				if (e.getCarId() == choice) {
					currentBooking.setCarId(choice);
					input = false;
					break;
				}
			}
		}
	}

	public double calculatePrice(Booking currentBooking) {
		for (Car e : availableCars) {
			if (currentBooking.getCarId() == e.getCarId()) {
				// parsing Duration into double
				double duration = Double.parseDouble(String.valueOf(currentBooking.getDuration().toMinutes()));
				return e.getDailyPrice() / 1440 * duration;
			}
		}
		return 0.0;
	}

	public ArrayList<Booking> whenBooked(int carId) {
		ArrayList<Booking> whenBooked = new ArrayList<Booking>();
		for (Booking e : bookingList) {
			if (e.getCarId() == carId) {
				whenBooked.add(e);
			}
		}
		return whenBooked;
	}

	public void displayBookings(ArrayList<Booking> bookings) {
		for (Booking e : bookings) {
			System.out.println(e.toString());
		}
		if (bookings.isEmpty()) {
			InputOutput.systemMessage(11);
		}
	}

	// Method for writing Car Arraylist in external file
	public void writeBookingToFile() {
		ReadAndWrite.emptyFile("booking.txt");
		for (Booking e : bookingList) {
			String bookingDetails = e.getMonthDay().getMonthValue() + ";" + e.getMonthDay().getDayOfMonth() + ";"
					+ e.getYear() + ";" + e.getDuration().toMinutes() + ";" + e.getTimeSlot() + ";" + e.getCustomerId()
					+ ";" + e.getCarId() + ";";
			ReadAndWrite.writeDetails("booking.txt", bookingDetails);
		}
	}
}
