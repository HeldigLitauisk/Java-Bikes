package model;

public class Manager extends User {
	
	public Manager() {
	}
	
	public Manager(String fname, String lname, String email, String cpr, String address, String telephone, int userId, String userType) {
		super(fname, lname, email, cpr, address, telephone, userId, userType);
	}	
}
