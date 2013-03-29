package sqlasgn;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Displays cell statistics in a nice GUI.
 * 
 * @author dignatof
 */
final class SudokuStatReport extends JFrame {
	static final long serialVersionUID = 0;
	
	/**
	 * Class constructor.
	 * 
	 * @param stats
	 *            The array of statistics to display
	 */
	public SudokuStatReport(CellStatistic[][] stats) {
		super("SQL Sudoku Statistics Report");
		this.setSize(400,400);
		
		Container cont = this.getContentPane();
		cont.setLayout(new GridLayout(4,4));
		
		if (stats == null || stats.length != 4 || stats[0].length != 4)
			throw new IllegalArgumentException("Statistics must be a 4x4 array!");

		for(int row = 0; row < 4; row++) {
			for(int col = 0; col < 4; col++) {
				JPanel sub_cont = new JPanel();
				sub_cont.setLayout(new BorderLayout());
				cont.add(sub_cont);
				JLabel newLabel;
				if(null == stats[col][row]) {
					newLabel = new JLabel("!");
					this.setTitle("!!! NO SOLUTIONS FOUND !!!");
					newLabel.setVerticalAlignment(SwingConstants.CENTER);
					newLabel.setHorizontalAlignment(SwingConstants.CENTER);
					sub_cont.add(newLabel, BorderLayout.CENTER);
				} else {
					String label_str = ""+stats[col][row].getLow();
					if(1 != stats[col][row].getQuantity()) {
						label_str += "-" +stats[col][row].getHigh()
							+" ["+stats[col][row].getQuantity()+"]";
					}
					newLabel = new JLabel(label_str);
					newLabel.setVerticalAlignment(SwingConstants.CENTER);
					newLabel.setHorizontalAlignment(SwingConstants.CENTER);
					sub_cont.add(newLabel, BorderLayout.CENTER);

				}
				
			}
		}
		
		this.setVisible(true);
	}
}
