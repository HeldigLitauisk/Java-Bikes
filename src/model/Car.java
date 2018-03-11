package model;

import java.util.ArrayList;
import view.InputOutput;

public class Car implements Comparable<Car>{
	
	// Variables for Car class
	private String carBrand, carColor;
	private int carId, carOwnerId;
	private double dailyPrice;
	private String location;
	private boolean availability;
	
	// Default constructor, that constructs a car with default values
	public Car() {
	}
	
	public Car(int carId, String carBrand, String carColor, double dailyPrice, String location, int carOwnerId, boolean availability) {
		this.carId = carId;
		this.carBrand = carBrand;
		this.carColor = carColor;
		this.dailyPrice = dailyPrice;
		this.location = location;
		this.carOwnerId = carOwnerId;
		this.availability = availability;
	}
	
	/* Method that lets user create a car by input (Brand, Color,Daily price)
	 * Car ID is set by assigning a valid random number
	 * Location and the Car Owner´s ID are set by taking the unique ID of the session
	 * Availability is set to be true, the Owner can change it in a separate menu
	 */
	public void createCar(ArrayList<Car> carList, User carOwner) {
		this.carId = validCarId(carList);
		this.carBrand = InputOutput.inputDetails("car's brand");
		this.carColor = InputOutput.inputDetails("car's color");
		this.dailyPrice = InputOutput.inputDetailsPrice();
		this.location = carOwner.getAddress();
		this.carOwnerId = carOwner.getUserId();
		this.availability = true;
	}
	
	//Getters
	public String getCarBrand() {
		return carBrand;
	}

	public String getCarColor() {
		return carColor;
	}

	public int getCarId() {
		return carId;
	}

	public double getDailyPrice() {
		return dailyPrice;
	}

	public String getLocation() {
		return location;
	}
	
	public int getCarOwnerId() {
		return carOwnerId;
	}
	
	public boolean getAvailability() {
		return availability;
	}

	// setters
	public void setCarBrand(String carType) {
		this.carBrand = carType;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public void setDailyPrice(double dailyPrice) {
		this.dailyPrice = dailyPrice;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setCarOwnerId(int carOwnerId) {
		this.carOwnerId = carOwnerId;
	}
	
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
		
	/*	This method lets Car Owner change the availability of his car
	 * 	Availability set to false means it cannot be searched or booked at any time
	 */
	public void toggleAvailability() {
		this.availability = !getAvailability();
		InputOutput.systemMessage(12);
		System.out.println(getAvailability());
	}

	/* This method generates a random carId, which is checked
	 * against carId´s already stored. Only assigns an un-used number as Car ID
	 */
	public int validCarId(ArrayList<Car> carList) {
		boolean found = true;
		int carId;
		do {
			carId = (int) (10000.0 * Math.random()) + 1;
			for (Car e : carList) {
				if (carId == e.getCarId()) {
					found = true;
					break;
				} else {
					found = false;
				}
			}
		} while (found);
		return carId;
	}
	
	//Method that prints out a car or a number of cars
	@Override
	public String toString() {
		return "[Car ID: " + carId + ", Brand: " + carBrand + ", Color: " + carColor + ", dailyPrice: " + dailyPrice
				 + "]";	
	}

	/* Overrides default method. Used to sort Car objects by price
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
		@Override
		public int compareTo(Car other) {
			if (dailyPrice == other.dailyPrice)
				return 0;
			else if (dailyPrice > other.dailyPrice)
				return 1;
			else
				return -1;
		}
}
