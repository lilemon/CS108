package assign3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractSpinnerModel;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/* 
 * CS108 Student: This file will be used to help the staff grade your assignment.
 * You can modify this file as much as you like, provided three restrictions:
 * 1) Do not change the class/file name
 * 		- The class/file name should be MetropolisModel
 * 2) Do not modify the MetropolisControl interface
 * 3) MetropolisModel must implement the MetropolisControl interface
 * 		- You can modify MetropolisModel to inherit/implement any additional class/interface
 */
public class MetropolisModel extends AbstractTableModel implements
		MetropolisControl {

	private List<String> colNames; // defines the number of cols
	private List<List<Object>> data; // one List for each row
	private Connection con;
	private Statement stmt;

	/**
	 * Creates a MetropolisModel based on the MyDB connection
	 * @throws SQLException	when error occurs with MyDB connection
	 * @return a MetroplisModel
	 */
	public MetropolisModel() throws SQLException {
		con = MyDB.getConnection();
		stmt = con.createStatement();
		colNames = new ArrayList<String>();
		data = new ArrayList<List<Object>>();
	}

	/**
	 * Search based on the current dataset
	 * @param metropolis String name of metropolis
	 * @param continent String name of continent
	 * @param population String name of population
	 * @return ResultSet from database of matching results
	 * @exception when data lookup went wrong or input invalid
	 */
	public ResultSet search(String metropolis, String continent,
			String population, boolean populationLargerThan, boolean exactMatch) {
		ResultSet ret = null;
		boolean param = false;
		String pre = "WHERE ";
		String op = "AND ";
		String comp;
		String match;
		String wrapL;
		String wrapR;
		String tail = "ORDER BY population DESC";
		
		if (populationLargerThan) {
			comp = "> ";
		} else {
			comp = "<= ";
		}
		
		if (exactMatch) {
			match = "= ";
			wrapL = "\"";
			wrapR = "\" ";
		} else {
			match = "LIKE ";
			wrapL = "\"%";
			wrapR = "%\" ";
		}
		
		String query = "SELECT * FROM metropolises ";
		if (!metropolis.isEmpty()) {
			query = query + pre;
			param = true;
			query = query + "metropolis " + match + wrapL + metropolis + wrapR;
		}
		if (!continent.isEmpty()) {
			if (!param) {
				param = true;
				query = query + pre;
			} else {
				query = query + op;
			}
			query = query + "continent " + match + wrapL + continent + wrapR; 
		}
		if (!population.isEmpty()) {
			try {
				Integer number = Integer.parseInt(population);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please enter a valid number for population");
				return null;
			}
			if (!param) {
				param = true;
				query = query + pre;
			} else {
				query = query + op;
			}
			query = query + "population " + comp + population + " "; 
		}
		query = query + tail;
		try {
			ret = stmt.executeQuery(query);
			ret.beforeFirst();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error searching in database");
			e.printStackTrace();
			return null;
		}
		data.clear();
		fireTableStructureChanged();
		return ret;
	}
	
	/**
	 * Adding data to database
	 * @param metropolis String name of metropolis
	 * @param continent String name of continent
	 * @param population String name of population
	 * @exception when data write went wrong or input invalid
	 */
	public void add(String metropolis, String continent, String population) {
		if (metropolis.isEmpty() || continent.isEmpty() || population.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please make sure no field is empty");
			return;
		}
		try {
			Integer number = Integer.parseInt(population);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Please enter a valid number for population");
			return;
		}
		
		String query = "INSERT INTO metropolises VALUES(\"" + metropolis + "\",\"" + continent + "\"," + population + ");";
		
		try {
			stmt.executeUpdate(query);
			init();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error writing to database");
			e.printStackTrace();
		}
	}
	
	/**
	 * @return number of columns
	 */
	public int getColumnCount() {
		return colNames.size();
	}

	/**
	 * @return number of rows
	 */
	public int getRowCount() {
		return data.size();
	}

	public String getColumnName(int col) {
		return colNames.get(col);
	}


	/**
	 * @param row row number
	 * @param col col number
	 * @return value of given row, col
	 */
	public Object getValueAt(int row, int col) {
		List<Object> rowList = data.get(row);
		Object result = null;
		if (col < rowList.size()) {
			result = rowList.get(col);
		}
		return (result);
	}

	/**
	 * default to non-editable
	 */
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	/**
	 * Add a column with column name
	 * @param name
	 */
	public void addColumn(String name) {
		colNames.add(name);
		fireTableStructureChanged();
	}

	/**
	 * Adds the given row, returns the new row index
	 * @param rs ResultSet of the current row
	 * @return row number
	 * @throws SQLException when data went wrong
	 */
	public int addRow(ResultSet rs) throws SQLException {
		List<Object> row = new ArrayList<Object>();
		row.add(rs.getString(1));
		row.add(rs.getString(2));
		row.add(rs.getString(3));
		data.add(row);
		fireTableRowsInserted(data.size() - 1, data.size() - 1);
		return (data.size() - 1);
	}

	/**
	 * Refreshes board
	 * @throws SQLException when data went wrong
	 */
	public void init() throws SQLException {
		data.clear();
		fireTableStructureChanged();
		ResultSet rs = stmt.executeQuery("SELECT * FROM metropolises ORDER BY population DESC");
		rs.beforeFirst();
		while (rs.next()) {
			List<Object> row = new ArrayList<Object>();
			row.add(rs.getString(1));
			row.add(rs.getString(2));
			row.add(rs.getString(3));
			data.add(row);
			fireTableRowsInserted(data.size() - 1, data.size() - 1);
		}
	}
}

interface MetropolisControl {
	/**
	 * Searches the Metropolis data-set for the provided search parameters.
	 * Returns the query results as a java.sql.ResultSet
	 * 
	 * @param metropolis
	 *            value of the metropolis field
	 * @param continent
	 *            value of the continent field
	 * @param population
	 *            value of the population field
	 * @param populationLargerThan
	 *            True if "Population Larger Than" has been selected
	 * @param exactMatch
	 *            True if "Exact Match" has been selected
	 * 
	 * @return resultSet Results for the given query
	 * @throws  
	 */
	public ResultSet search(String metropolis, String continent,
			String population, boolean populationLargerThan, boolean exactMatch);

	/**
	 * Adds the entry to the Metropolis data-set.
	 * 
	 * @param metropolis
	 *            value of the metropolis field
	 * @param continent
	 *            value of the continent field
	 * @param population
	 *            value of the population field
	 */
	public void add(String metropolis, String continent, String population);
}