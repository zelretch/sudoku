package sqlasgn;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * 
 * @author dignatof
 */
final class SudokuGUI extends JFrame {

	private class CtlModeListener implements ActionListener {
		private SudokuNumMode _newMode;
		
		public CtlModeListener(SudokuNumMode newMode) {
			_newMode = newMode;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			_ctlMode = _newMode;
		}
	}
	
	/**
	 * @author dignatof
	 */
	private class StatListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new SudokuStatReport(_sqlc.solve());
		}
	}
	
	/**
	 * @author dignatof
	 */
	private class SudokuBox extends JButton {
		private class SBListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				if(!_boxMode.equals(_ctlMode)) {
					if(SudokuNumMode.NONE.equals(_ctlMode)) {
						try {
							_sqlc.removeConstraint(_boxLoc);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					} else if(SudokuNumMode.NONE.equals(_boxMode)) {
						try {
							_sqlc.addConstraint(_boxLoc, _ctlMode.toNum());
						} catch (SQLException e) {
							e.printStackTrace();
						}
					} else {
						try {
							_sqlc.updateConstraint(_boxLoc, _ctlMode.toNum());
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					_boxMode = _ctlMode;
					updateLabel();
				}
			}
		}
		static final long serialVersionUID = 0;
		SudokuNumMode _boxMode;
		
		int _boxLoc;
		
		public SudokuBox(int boxloc) {
			super();
			this.setPreferredSize(new Dimension(40,40));
			_boxMode = SudokuNumMode.NONE;
			_boxLoc = boxloc;
			this.addActionListener(new SBListener());
			updateLabel();
		}
		
		private void updateLabel() {
			if(SudokuNumMode.NONE.equals(_boxMode)) {
				this.setText("_");
			} else {
				this.setText(""+_boxMode.toNum());
			}
		}
	}
	
	private enum SudokuNumMode { NONE (-1), ONE (1), TWO(2), THREE(3), FOUR(4);
		public static SudokuNumMode forInt(int i) {
			if(1 == i) {
				return ONE;
			} else if(2 == i) {
				return TWO;
			} else if(3 == i) {
				return THREE; 
			} else if(4 == i) {
				return FOUR;
			} else return NONE;
		}
		private int _num;
		
		SudokuNumMode(int i) {
			_num = i;
		}
		public int toNum() { return _num; }
	}
	
	static final long serialVersionUID = 0;
	
	public static void main(String[] argv) throws Exception {
		SudokuSQLClient del = new StudentSudokuSQLClient();
		new SudokuGUI(del);
	}
	private SudokuSQLClient _sqlc;
	private JRadioButton[] ctl_rbuttons;
	private ButtonGroup ctl_group;
	private SudokuNumMode _ctlMode;;
	
	/**
	 * To run the program, construct a SudokuGUI object using your
	 * implementation of the SudokuSQLClient interface.
	 * 
	 * @param sqlc
	 *            Your implementation of the SudokuSQLClient interface
	 */
	public SudokuGUI(SudokuSQLClient sqlc) {
		super("SQL Sudoku Solver");
		_sqlc = sqlc;
		_ctlMode = SudokuNumMode.NONE;
		ctl_rbuttons = new JRadioButton[5];
		ctl_group = new ButtonGroup();
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		Container content = getContentPane();
	    content.setLayout(new BorderLayout());
	    
	    JButton getStat = new JButton("Gather Statistics");
	    getStat.addActionListener(new StatListener());
	    content.add(getStat,BorderLayout.SOUTH);
	    
	    JPanel cont = new JPanel();
	    cont.setLayout(new GridLayout(4,4));
	    content.add(cont, BorderLayout.CENTER);
	    cont.setSize(new Dimension(160,160));
		
		for(int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				cont.add(new SudokuBox(row * 4 + col));
			}
		}
		
		JPanel ctlPane = new JPanel();
		ctlPane.setLayout(new GridLayout(5,1));
		content.add(ctlPane,BorderLayout.WEST);
		
		for(int i = 0; i < 5; i++) {
			if(i == 0) {
				ctl_rbuttons[i] = new JRadioButton("None");
				ctl_rbuttons[i].addActionListener(new CtlModeListener(SudokuNumMode.NONE));
			} else {
				ctl_rbuttons[i] = new JRadioButton(i+"");
				ctl_rbuttons[i].addActionListener(new CtlModeListener(SudokuNumMode.forInt(i)));
			}
			ctl_group.add(ctl_rbuttons[i]);
			ctlPane.add(ctl_rbuttons[i]);
		}
		
		this.setVisible(true);
	}
	
}
