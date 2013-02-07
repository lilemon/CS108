package assign3;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.table.JTableHeader;

import java.sql.*;

class MetropolisFrame extends JFrame { // the real table model
	private MetropolisModel model; // censor model
	private JTable table; // standard JTables

	JButton addButton;
	JButton searchButton;
	JButton deleteButton;
	JComboBox<String> populationBox;
	JComboBox<String> matchBox;
	JComponent container;

	Box searchPanel;
	JTextField metropolis;
	JTextField continent;
	JTextField population;
	JPanel searchOption;

	// Listener for changes in the text field
	private class FieldListener implements DocumentListener {
		/*
		 * We get these three notifications on changes in the text field.
		 */
		public void insertUpdate(DocumentEvent e) {
			sendToModel();
		}

		public void changedUpdate(DocumentEvent e) {
			sendToModel();
		}

		public void removeUpdate(DocumentEvent e) {
			sendToModel();
		}

		// In all cases, send the new text to the censorModel
		public void sendToModel() {
			// censorModel.setCensor(censorText.getText());
		}
	}

	public MetropolisFrame(String title) throws SQLException {
		super(title);
		container = (JComponent) getContentPane();
		container.setLayout(new BorderLayout());

		Box panel = Box.createVerticalBox();

		JPanel input = new JPanel();
		input.setLayout(new FlowLayout());
		input.add(new JLabel("Metropolis:"));
		metropolis = new JTextField(10);
		input.add(metropolis);
		input.add(new JLabel("Continent"));
		continent = new JTextField(10);
		input.add(continent);
		input.add(new JLabel("Population"));
		population = new JTextField(10);
		input.add(population);

		container.add(input, BorderLayout.NORTH);
		container.add(panel, BorderLayout.EAST);

		model = new MetropolisModel();
		
		table = new JTable(model);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(300, 200));
		container.add(scrollpane, BorderLayout.CENTER);
		model.addColumn("Metropolis");
		model.addColumn("Continent");
		model.addColumn("Population");
		model.init();

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		addButton = new JButton("Add");
		buttonPanel.add(addButton);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.add(metropolis.getText(),
						continent.getText(), population.getText());
			}
		});
		buttonPanel.add(Box.createVerticalStrut(5));

		searchButton = new JButton("Search");
		buttonPanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet rs = model.search(metropolis.getText(),
						continent.getText(), population.getText(),
						populationBox.getSelectedIndex() != 0,
						matchBox.getSelectedIndex() != 0);
				if (rs != null) {
					try {
						while (rs.next()) {
							model.addRow(rs);
						}
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error reading from database");
						e1.printStackTrace();
					}
				}
			}
		});
		buttonPanel.add(Box.createVerticalStrut(5));
		
		searchOption = new JPanel();
		searchOption.setLayout(new BoxLayout(searchOption, BoxLayout.Y_AXIS));
		String[] populationString = { "Population Less Than",
				"Population Larger Than" };
		populationBox = new JComboBox<String>(populationString);
		String[] matchString = { "Partial Match", "Exact Match" };
		matchBox = new JComboBox<String>(matchString);
		searchOption.add(populationBox);
		searchOption.add(Box.createVerticalStrut(5));
		searchOption.add(matchBox);
		searchOption.setBorder(new TitledBorder("Search Options"));
		searchOption.setLayout(new BoxLayout(searchOption, BoxLayout.Y_AXIS));
		buttonPanel.add(searchOption);

		buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		panel.add(buttonPanel, BorderLayout.EAST);
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	static public void main(String[] args) throws SQLException {
		new MetropolisFrame("Metro Viewer");
	}
}