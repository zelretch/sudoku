package sqlasgn;

import java.sql.SQLException;

/**
 * @author dignatof
 */
public interface SudokuSQLClient {

	/**
	 * Called when the user constrains a previously unconstrained box. This
	 * function should execute a SQL query that adds a row to constraints_login
	 * whose location is loc and whose number is num
	 * 
	 * @param loc
	 *            The location of the box being constrained
	 * @param num
	 *            The number to be added at the given location
	 * @throws SQLException
	 */
	public void addConstraint(int loc, int num) throws SQLException;

	/**
	 * Called when the user removes the constraint from a box. This function
	 * should execute a SQL query that removes the appropriate constraint from
	 * the constraints table.
	 * 
	 * @param loc
	 *            The location of the box that is being de-constrained
	 * @throws SQLException
	 */
	public void removeConstraint(int loc) throws SQLException;

	/**
	 * Called when the user changes the constraint on a box from one number to
	 * another. This function should execute a SQL query that modifies the
	 * constraint in-place to indicate that loc holds newNum.
	 * 
	 * @param loc
	 *            The location of the box whose constraint is changing
	 * @param newNum
	 *            The new value the box is being constrained to be
	 * @throws SQLException
	 */
	public void updateConstraint(int loc, int newNum) throws SQLException;

	/**
	 * Called when the user tries to solve the puzzle/gather statistics. This
	 * function should execute a query (or series of queries) that uses the two
	 * tables to figure out which solution(s) satisfy the constraint table
	 * (Watch out for the case where the constraint table is empty!). This
	 * function returns a 4 x 4 array of CellStatistics (the first dimension is
	 * the box column, and the second dimension is box row (see
	 * CellStatistic.LocToCol and CellStatistic.LocToRow). For example, the
	 * statistics for the box in column 1, row 3 is stored in the result indexed
	 * at [1][3]. Try to use the database and SQL to do as much of the work as
	 * possible here. In particular, it is not acceptable to read the table of
	 * constraints into your Java program, then construct a query over just the
	 * AllSolutions table using those constraints.
	 * 
	 * @return Two dimensional array of CellStatistic Objects
	 */
	public CellStatistic[][] solve();
}
