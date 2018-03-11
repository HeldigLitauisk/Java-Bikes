package model;

import view.InputOutput;

public class Payment {
	
	public Payment() {
	}

	/* The pay method allows user to enter a credit card number and validates
	 * that number using a separate method.
	 */
	public void pay() {
		boolean valid = false;
		do {
			valid = checkCreditCard(InputOutput.inputDetails("Credit Card"));
		} while (valid == false);
	}
	
	/* This method checks a given credit card number using Luhn algorithm
	 * returns true or false */ 
	public boolean checkCreditCard(String cardNumber) {
        int digits = cardNumber.length();
        int oddOrEven = digits & 1;
        long sum = 0;
        for (int count = 0; count < digits; count++) {
            int digit = 0;
            try {
                digit = Integer.parseInt(cardNumber.charAt(count) + "");
            } catch(NumberFormatException e) {
                return false;
            }
            if (((count & 1) ^ oddOrEven) == 0) { 
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        return (sum == 0) ? false : (sum % 10 == 0);
	} 
}
