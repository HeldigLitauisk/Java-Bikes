package model;

import model.User;
import java.util.ArrayList;
import data.BookingDatabase;

public class Customer extends User {
	boolean loyalCustomer;

	public Customer() {
	}
	
	public Customer(String fname, String lname, String email, String cpr, String address, String telephone, int userId, String userType) {
		super(fname, lname, email, cpr, address, telephone, userId, userType);
		this.loyalCustomer = calculateLoyalty();
	}
	
	public boolean isLoyalCustomer() {
		return loyalCustomer;
	}

	public void setLoyalCustomer(boolean loyalCustomer) {
		this.loyalCustomer = loyalCustomer;
	}
	
	/* A customer can acquire a loyal customer status if he has made
	 * 3 or more bookings with ShareUs. This method counts the number of bookings
	 * made by a customer
	 */
	public boolean calculateLoyalty() {
		BookingDatabase bookingData = new BookingDatabase();
		ArrayList<Booking> newBookingList = bookingData.getBookingList();
		int count = 0;
		for (Booking e: newBookingList ) {
			if (e.getCustomerId() == this.getUserId()) {
				count += 1;
			}
		}
		if (count >= 3) {
			return true;
		}
		return false;
	}
}
