package sqlasgn;

import java.sql.*;

/**
 * Your implementation of the <code>SudokuSQLClient</code> interface.
 *
 * @author <your login> 
 */
final class StudentSudokuSQLClient implements SudokuSQLClient {
	private Connection conn;
	public StudentSudokuSQLClient() throws Exception{
		Class.forName("org.sqlite.JDBC");
	    conn = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
	    Statement stat = conn.createStatement();
	    stat.executeUpdate("drop table if exists constraints;");
	    stat.executeUpdate("create table constraints(location INT PRIMARY KEY, number INT);");
		
		
	}

	/**
	 * Below are some snippets of JDBC code that may prove useful
	 * 
	 * For more sample JDBC code, check out http://www.zentus.com/sqlitejdbc/
	 * 
     * ---
     * 
     *      // INITIALIZE THE CONNECTION
     *      Class.forName("org.sqlite.JDBC");
     *      Connection conn = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
     *
     * ---
     * 
     * ResultSet rs = stat.executeQuery("select * from people;");
	 * while (rs.next()) {
	 * 	System.out.println("name = " + rs.getString("name"));
	 * 	System.out.println("job = " + rs.getString("occupation"));
	 * }
	 * 
	 */

	/**
	 * @see sqlasgn.SudokuSQLClient#addConstraint(int, int)
	 */
	public void addConstraint(int loc, int num) throws SQLException {
		// TODO Auto-generated method stub
	
	    
	    PreparedStatement prep = conn.prepareStatement("insert into constraints values(?,?);");
	    prep.setInt(1, loc);
	    prep.setInt(2, num);
	    prep.executeUpdate();
	    
	    
	}

	/**
	 * @see sqlasgn.SudokuSQLClient#removeConstraint(int)
	 */
	public void removeConstraint(int loc) throws SQLException {
		// TODO Auto-generated method stub
		
	    PreparedStatement prep = conn.prepareStatement("delete from constraints where location = ? ;");
	    prep.setInt(1, loc);
	    
	    prep.executeUpdate();
	    
	}

	/**
	 * @see sqlasgn.SudokuSQLClient#solve()
	 */
	public CellStatistic[][] solve() {
		// TODO Auto-generated method stub
		Statement stat;
		CellStatistic[][] ra = new CellStatistic[4][4];
		
		
		try {
			stat = conn.createStatement();
			ResultSet result = stat.executeQuery("SELECT min(number) as n_min, max(number) as n_max, count(location) as l_c, location FROM (SELECT location,number,sol_id FROM allsolutions, (SELECT sol_id as new_id, count(sol_id) as id_count FROM allsolutions,constraints WHERE allsolutions.location = constraints.location AND allsolutions.number = constraints.number GROUP BY sol_id) " +
					",(SELECT count(location) as n_c FROM constraints ) WHERE n_c = id_count AND new_id  = sol_id) GROUP BY location");
			
			while (result.next()){
				int loc = result.getInt("location");
				ra[CellStatistic.LocToCol(loc)][CellStatistic.LocToRow(loc)] = new CellStatistic(result.getInt("n_min"),result.getInt("n_max"),result.getInt("l_c"));
				
				
				
				//System.out.println(result.getInt("sol_id"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		
		//return null;
		return ra;
	}

	/**
	 * @see sqlasgn.SudokuSQLClient#updateConstraint(int, int)
	 */
	public void updateConstraint(int loc, int newNum) throws SQLException {
		
		// TODO Auto-generated method stub
		
	    PreparedStatement prep = conn.prepareStatement("update constraints set int = ? where location = ? ;");
	    prep.setInt(1, newNum);
	    prep.setInt(2, loc);
	    
	    prep.executeUpdate();
	    
	}
}
