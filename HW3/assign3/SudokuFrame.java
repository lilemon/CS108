package assign3;

import javax.swing.*;

import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

public class SudokuFrame extends JFrame {

	public SudokuFrame() {
		super("Sudoku Solver");
		setLayout(new BorderLayout(4, 4));
		
		final JTextArea puzzle = new JTextArea(15, 20);
		final JTextArea solution = new JTextArea(15, 20);
		puzzle.setBorder(new TitledBorder("Puzzle"));
		solution.setBorder(new TitledBorder("Solution"));
		
		// Putting button in the center looks better to me
		add(puzzle, BorderLayout.WEST);
		add(solution, BorderLayout.EAST);

		JPanel panel = new JPanel();
		JButton check = new JButton("Check");
		panel.add(check);
		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Sudoku s = new Sudoku(Sudoku.textToGrid(puzzle.getText()));
					int count = s.solve();
					puzzle.setText(s.toString());
					solution.setText(s.getSolutionText() + "Solutions: "
							+ count + "\nElapsed: " + s.getElapsed() + "ms\n");
				} catch (IllegalArgumentException iae) {
					solution.setText("Invalid Grid");
				} catch (Exception err) {
					solution.setText("Parsing Error");
				}
			}
		});
		final JCheckBox auto = new JCheckBox("Auto Check");
		panel.add(auto);

		puzzle.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				solve();
			}

			public void insertUpdate(DocumentEvent arg0) {
				solve();
			}

			public void removeUpdate(DocumentEvent arg0) {
				solve();
			}

			public void solve() {
				if (auto.isSelected()) {
					try {
						Sudoku s = new Sudoku(Sudoku.textToGrid(puzzle
								.getText()));
						int count = s.solve();
						String ct;
						if (count == 100)
							ct = count + "+";
						else
							ct = count + "";
						solution.setText(s.getSolutionText() + "Solutions: "
								+ ct + "\nElapsed: " + s.getElapsed()
								+ "ms\n");
					} catch (IllegalArgumentException iae) {
						solution.setText("Invalid Grid");
					} catch (Exception err) {
						solution.setText("Parsing Error");
					}
				}
			}
		});

		add(panel, BorderLayout.SOUTH);
		
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {
		}

		SudokuFrame frame = new SudokuFrame();
	}

}
