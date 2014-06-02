package Chapter_1;

public abstract class Price {
	abstract int getPriceCode();

	abstract double getCharge(int daysRented);

	int getFrequentRenterPoints(int daysRented) {
		return 1;
	}

	double getCharge(Rental rental) {
		double result = 0;
		switch (rental.getMovie().getPriceCode()) {
		case Movie.REGULAR:
			result += 2;
			if (rental.getDaysRented() > 2)
				result += (rental.getDaysRented() - 2) * 1.5;
			break;
		case Movie.NEW_RELEASE:
			result += rental.getDaysRented() * 3;
			break;
		case Movie.CHILDRENS:
			result += 1.5;
			if (rental.getDaysRented() > 3)
				result += (rental.getDaysRented() - 3) * 1.5;
			break;
		}
		return result;
	}
}

class ChildrensPrice extends Price {

	double getCharge(int daysRented) {
		double result = 1.5;
		if (daysRented > 3)
			result += (daysRented - 3) * 1.5;
		return result;
	}

	int getPriceCode() {
		return Movie.CHILDRENS;
	}
}

class NewReleasePrice extends Price {

	int getFrequentRenterPoints(int daysRented) {
		return (daysRented > 1) ? 2 : 1;
	}

	double getCharge(int daysRented) {
		return daysRented * 3;
	}

	int getPriceCode() {
		return Movie.NEW_RELEASE;
	}
}

class RegularPrice extends Price {
	double getCharge(int daysRented) {
		double result = 2;
		if (daysRented > 2)
			result += (daysRented - 2) * 1.5;
		return result;
	}

	int getPriceCode() {
		return Movie.REGULAR;
	}
}
