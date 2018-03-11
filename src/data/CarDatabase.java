package data;

import java.util.ArrayList;
import java.util.Scanner;

import data.ReadAndWrite;
import model.*;

public class CarDatabase {
	private ArrayList<Car> carList = new ArrayList<Car>();
	
	//default constructor that also fills carList with cars from external file
	public CarDatabase() {
		carList = getAllCar();
	}
	
	//getter
	public ArrayList<Car> getCarList() {
		return carList;
	}
	
	// creates car object from external files
	public Car getCars(String line) {
		Car carFromFile = new Car();
		String[] values = line.split(";");
		carFromFile.setCarId(Integer.parseInt(values[0]));
		carFromFile.setCarBrand(values[1]);
		carFromFile.setCarColor(values[2]);
		carFromFile.setDailyPrice(Double.parseDouble(values[3]));
		carFromFile.setLocation(values[4]);
		carFromFile.setCarOwnerId(Integer.parseInt(values[5]));
		carFromFile.setAvailability(Boolean.parseBoolean(values[6]));
		return carFromFile;
	}
	
	// Fills an ArrayList of objects 
		public ArrayList<Car> getAllCar() {
			Scanner input = ReadAndWrite.readDetails("cars.txt");
			// checking each line
			while (input.hasNextLine()) {
				carList.add(getCars(input.nextLine()));
			}
			return carList;
		}
	
	// method to display any car Arraylist
	 public void displayCars(ArrayList<Car> carList) {
		System.out.println("\nThese are the cars available:");
		for (Car e: carList) {
			System.out.println(e.toString());
		}
	 }
	 
	 // Method for writing Car Arraylist in external file
	 public void writeCarToFile() {
		 ReadAndWrite.emptyFile("cars.txt");
		 for (Car e: carList) {
			 String carDetails = e.getCarId() + ";" + e.getCarBrand() + ";" + e.getCarColor() + ";" + e.getDailyPrice() + ";" + e.getLocation() + ";" + e.getCarOwnerId() + ";" + e.getAvailability() + ";";
			 ReadAndWrite.writeDetails("cars.txt", carDetails );
		 } 
	 }
	 
	 /*Method that returns a Car Object from carID that was assigned to booking in progress
	  *It is used to retrieve data on a Car (e.g. brand,price etc.)
	  */
	 public Car carFromBooking(Booking bookingInProgress) {
		 Car car = new Car();
		 for (Car e: carList) {
			 if (e.getCarId() == bookingInProgress.getCarId()) {
				 car = e;
			 }
		 }
		 return car;
	 }
	 /*Method that returns a Car Object from carID
	  *It is used to retrieve data on a Car (e.g. brand,price etc.)
	  *used in the Manage Car process
	  */
	 public Car carFromCarId(int carId) {
		 Car car = new Car();
		 for (Car e: carList) {
			 if (e.getCarId() == carId) {
				 return e;
			 }
		 }
		 return car;
	 }
	 
	 /*Returns and prints the carList of a Car owner
	  * Compares the uniqueId (of the session) to carOwner Id´s in the car ArrayList
	  */
	 public ArrayList<Car> getOwnersCar(int uniqueId) {
		 ArrayList<Car> ownersCars = new ArrayList<Car>();
		 for (Car e: carList) {
			 if (e.getCarOwnerId() == uniqueId) {
				 ownersCars.add(e);
				 System.out.println(e.toString());
			 }
		 }
		 return ownersCars;
	 }
}
