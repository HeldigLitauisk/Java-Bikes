package model;
import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;

public class Booking {
	private MonthDay monthDay;
	private Year year;
	private Duration duration;
	private String timeSlot;
	private int customerId, carId;
	
	public Booking() {
	}
	
	public Booking(MonthDay monthDay, Year year, String timeSlot, Duration duration, int customerId, int carId) {
		this.monthDay = monthDay;
		this.year = year;
		this.timeSlot = timeSlot;
		this.duration = duration;
		this.customerId = customerId;
		this.carId = carId;
	}
	
	/* Method that creates a booking from input by customer (Date,Duration and Timeslot)
	 * The customer Id is set to the ID of the customer logged in.
	 * Car Id is set to 0, but will be changed after the customer chooses a car
	 */
	public void createBooking(String bookingDetails, int uniqueId) {
		String[] values = bookingDetails.split(";");
		this.monthDay = (MonthDay.of((Integer.parseInt(values[0])), (Integer.parseInt(values[1]))));
		this.year = (Year.of(Integer.parseInt(values[2])));
		this.duration = (Duration.ofMinutes(Integer.parseInt(values[3]))); 
		this.timeSlot = values[4]; 
		this.customerId = uniqueId;
		this.carId = 0; 
	}
		
	//getters
	public MonthDay getMonthDay() {
		return monthDay;
	}

	public Year getYear() {
		return year;
	}

	public Duration getDuration() {
		return duration;
	}
	
	public String getTimeSlot() {
		return timeSlot;
	}

	public int getCustomerId() {
		return customerId;
	}

	public int getCarId() {
		return carId;
	}
	
	//setters
	public void setMonthDay(MonthDay monthDay) {
		this.monthDay = monthDay;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}
	
	/*This method prints out a booking that was made
	 * 	It is used to print out bookings for a Car Owner 
	 *  Overrides the toString method in Object class
	 */
	@Override
	public String toString() {
		return "Car #" + carId + " has been booked on " + getMonthDay().getMonth().getDisplayName(TextStyle.FULL,Locale.ENGLISH) + " " + getMonthDay().getDayOfMonth() + ", " + year + " for " + duration.toMinutes() + " minutes in the " + timeSlot;
	}
}
