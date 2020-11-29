package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.connection.DatabaseInterface;
import database.connection.Faculty;

public class TablePanel extends JPanel{
	private String[] columnNames;
	private ArrayList<String[]> table;
	private String tableQuery;
	
	private JTable jtable;
	protected JButton addButton;
	protected JButton removeButton;
	protected JButton refreshButton;
	private DefaultTableModel tableModel;
	
	protected DatabaseInterface iface;
	
	public TablePanel (DatabaseInterface iface, String query) {
		this.iface = iface;
		this.tableQuery = query;
		
		jtable = new JTable();
		tableModel = new DefaultTableModel();
		jtable.setModel(tableModel);
		
		table = iface.queryTable(this.tableQuery);
		this.columnNames = table.get(0);
		
		//add columnNames to table
		for (String name : this.columnNames) {
			tableModel.addColumn(name);
		}
		
		table.remove(0);
		for (int i = 0; i < table.size(); i++) {
			tableModel.addRow(table.get(i));
		}
		
		this.addButton = new JButton("Add");
		this.removeButton = new JButton("Remove");
		this.refreshButton = new JButton("Refresh");
		
		
		this.removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeFacultyEvent();
			}
		});
		this.refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}
		});
		
		//vertival panel
		//contains the table and the text boxes
		JScrollPane scrollPane = new JScrollPane(jtable);
		this.add(scrollPane);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(refreshButton);
		this.add(buttonPanel);
				
	}
	
	private void refreshTable() {
		//remove all the rows
		System.out.println("refresh");
		if (tableModel.getRowCount() > 0) {
		    for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
		    	tableModel.removeRow(i);
		    }
		}
		//run querey and add rows to table
		table = iface.queryTable(this.tableQuery);
		table.remove(0);
		for (int i = 0; i < table.size(); i++) {
			tableModel.addRow(table.get(i));
		}
		//refresh the table
		tableModel.fireTableDataChanged();
	}
	
	private void removeFacultyEvent() {
		//get the selected row from jtable
		
//		Converting Vector to Object Array 
		if (jtable.getSelectedColumn() != -1) {
	        Object[] objArray = tableModel.getDataVector().elementAt(jtable.getSelectedRow()).toArray();
//	        Convert Object[] to String[] 
	        String[] array = Arrays.copyOf(objArray, 
	                                       objArray.length, 
	                                       String[].class);
			Faculty f = new Faculty(array);
			iface.removeEntry(f);
			this.refreshTable();
//			System.out.println(f.removeStatement());
		}
	}
}
