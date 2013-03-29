package sqlasgn;

/**
 * @author dignatof
 */
public class CellStatistic {

	/**
	 * Convert a location value used in the database to a row for the GUI
	 * 
	 * @param loc
	 *            a location value used in the database
	 * @return the column indicated by the argument location
	 */
	public static int LocToCol(int loc) {
		return loc % 4;
	}

	/**
	 * Convert a location value used in the database to a row for the GUI
	 * 
	 * @param loc
	 *            a location value used in the database
	 * @return the row indicated by the argument location
	 */
	public static int LocToRow(int loc) {
		return loc / 4;
	}

	private int _low;
	private int _high;
	private int _quantity;

	/**
	 * Capture all of the relavant statistics for display in the GUI
	 * 
	 * @param low
	 *            the least value the particular box can hold
	 * @param high
	 *            the greatest value the particular box can hold
	 * @param quantity
	 *            the number of distinct values the box can hold
	 */
	public CellStatistic(int low, int high, int quantity) {
		_low = low;
		_high = high;
		_quantity = quantity;
	}

	public int getHigh() {
		return _high;
	}

	public int getLow() {
		return _low;
	}

	public int getQuantity() {
		return _quantity;
	}
}
