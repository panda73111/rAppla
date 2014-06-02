package Chapter_1;

public class Movie {
	public static final int CHILDRENS = 2;
	public static final int REGULAR = 0;
	public static final int NEW_RELEASE = 1;
	private String title;
	private int priceCode;

	public Movie(String newtitle, int newpriceCode) {
		title = newtitle;
		setPriceCode(newpriceCode);
	}

	public int getPriceCode() {
		return _price.getPriceCode();
	}

	public void setPriceCode(int arg) {
		switch (arg) {
		case REGULAR:
			_price = new RegularPrice();
			break;
		case CHILDRENS:
			_price = new ChildrensPrice();
			break;
		case NEW_RELEASE:
			_price = new NewReleasePrice();
			break;
		default:
			throw new IllegalArgumentException("Incorrect Price Code");
		}
	}

	public String getTitle() {
		return title;
	}

	Price _price;

	int getFrequentRenterPoints(int daysRented) {
		// add frequent renter points
		// add bonus for a two day new release rental
		if ((daysRented == Movie.NEW_RELEASE) && daysRented > 1)
			return 2;
		else
			return 1;
	}

	int getFrequentRenterPoints(Rental rental) {
		return getFrequentRenterPoints(rental.daysRented);
	};
}