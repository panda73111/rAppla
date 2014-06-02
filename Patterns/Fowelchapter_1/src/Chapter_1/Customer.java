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
			// thisAmount = each.getMovie().getCharge(this);

			frequentRenterPoints += each.getMovie().getFrequentRenterPoints(
					each);

			// show figures for this rental
			result += "\t" + each.getMovie().getTitle() + "\t" + "\t"
					+ each.getDaysRented() + "\t"
					+ String.valueOf(each.getMovie()._price.getCharge(each))
					+ "\n";

			totalAmount += each.getMovie()._price.getCharge(each);
		}
		// add footer lines
		result += "Amount owed is " + String.valueOf(getTotalCharge()) + "\n";
		result += "You earned "
				+ String.valueOf(getTotalFrequentRenterPoints())
				+ " frequent renter points";
		return result;
	}

	private double getTotalCharge() {
		double result = 0;
		Enumeration enum_rentals = rentals.elements();
		while (enum_rentals.hasMoreElements()) {
			Rental each = (Rental) enum_rentals.nextElement();
			result += each.getMovie()._price.getCharge(each);
		}
		return result;
	}

	private int getTotalFrequentRenterPoints() {
		int result = 0;
		Enumeration enum_rentals = rentals.elements();
		while (enum_rentals.hasMoreElements()) {
			Rental each = (Rental) enum_rentals.nextElement();
			result += each.getMovie().getFrequentRenterPoints(each);
		}
		return result;
	}

	private double amountFor(Rental aRental) {
		return aRental.getMovie()._price.getCharge(aRental);
	}

	public String htmlStatement() {
		Enumeration enum_rentals = rentals.elements();
		String result = "<H1>Rentals for <EM>" + getName() + "</EM></H1><P>\n";
		while (enum_rentals.hasMoreElements()) {
			Rental each = (Rental) enum_rentals.nextElement();
			// show figures for each rental
			result += each.getMovie().getTitle() + ": "
					+ String.valueOf(each.getMovie()._price.getCharge(each))
					+ "<BR>\n";
		}
		// add footer lines result += "<P>You
		result += "<P>You owe <EM>" + String.valueOf(getTotalCharge())
				+ "</EM><P>\n";
		result += "On this rental you earned <EM>"
				+ String.valueOf(getTotalFrequentRenterPoints())
				+ "</EM> frequent renter points<P>";
		return result;
	}
}
