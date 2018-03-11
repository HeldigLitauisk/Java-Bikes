package model;

public class CarOwner extends User {

	public CarOwner() {
	}

	/* Used for creating CarOwner object while reading from file. Discounts are
	 * applied if car is booked by someone of CarOwner instance. You can become
	 * CarOwner if you have at least one car for rent */
	public CarOwner(String fname, String lname, String email, String cpr, String address, String telephone, int userId,
			String userType) {
		super(fname, lname, email, cpr, address, telephone, userId, userType);
	}
}
