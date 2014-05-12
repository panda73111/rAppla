package Chapter_1;

import java.util.*;

class Customer {
	private String name;
	private Vector rentals = new Vector();

	public Customer(String newname) {
		name = newname;
	};

	public void addRental(Rental arg) {
		rentals.addElement(arg);
	};

	public String getName() {
		return name;
	};

	public String statement() {
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Enumeration enum_rentals = rentals.elements();
		String result = "Rental Record for " + this.getName() + "\n";
		result += "\t" + "Title" + "\t" + "\t" + "Days" + "\t" + "Amount"
				+ "\n";

		while (enum_rentals.hasMoreElements()) {
			double thisAmount = 0;
			Rental each = (Rental) enum_rentals.nextElement();
			// determine amounts for each line
			thisAmount = each.getMovie().getCharge(this);

			frequentRenterPoints += each.getMovie().getFrequentRenterPoints(this);

			// show figures for this rental
			result += "\t" + each.getMovie().getTitle() + "\t" + "\t"
					+ each.getDaysRented() + "\t"
					+ String.valueOf(each.getMovie().getCharge(this)) + "\n";

			totalAmount += each.getMovie().getCharge(this);
		}
		// add footer lines
		result += "Amount owed is " + String.valueOf(getTotalCharge()) + "\n";
		result += "You earned " + String.valueOf(getTotalFrequentRenterPoints())
				+ " frequent renter points";
		return result;
	}

	private double getTotalCharge() {
		double result = 0;
		Enumeration enum_rentals = rentals.elements();
		while (enum_rentals.hasMoreElements()) {
			Rental each = (Rental) enum_rentals.nextElement();
			result += each.getMovie().getCharge(this);
		}
		return result;
	}

	private int getTotalFrequentRenterPoints() {
		int result = 0;
		Enumeration enum_rentals = rentals.elements();
		while (enum_rentals.hasMoreElements()) {
			Rental each = (Rental) enum_rentals.nextElement();
			result += each.getMovie().getFrequentRenterPoints(this);
		}
		return result;
	}

	private double amountFor(Rental aRental) {
		return aRental.getMovie().getCharge(this);
	}

}
