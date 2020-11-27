package gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import database.connection.DatabaseInterface;
import database.connection.Faculty;

public class FacultyTablePanel extends JPanel{
	private String[] columnNames;
	private Faculty[] faculty;
	private JTable jtable;
	
	
	public FacultyTablePanel (DatabaseInterface iface) {
		ArrayList<String[]> table = iface.queryTable("SELECT * FROM professor");
		this.columnNames = table.get(0);
		table.remove(0);
		this.faculty = new Faculty[table.size()];
		for (int i = 0; i < table.size(); i++) {
			this.faculty[i] = new Faculty(table.get(i));
		}
		Object[][] data = new Object[table.size()][table.get(0).length];
		for(int i=0; i<table.size(); i++) {
			for(int j=0; j<table.get(i).length; j++) {
				data[i][j] = table.get(i)[j];
			}
		}
		//Table stuff
		jtable = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(jtable);
		this.add(scrollPane);
				
	}
}
