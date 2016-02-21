package gui;

import java.awt.EventQueue;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;

import main.Driver;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class NewGuiMain {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewGuiMain window = new NewGuiMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NewGuiMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Orders", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		String[] columnNames = {"Order Number",
								"Customer Name",
								"Date Required"};
		
		Object[][] data = new Object[0][0];
		table = new JTable(data, columnNames);
		updateTable();
		
		scrollPane.setViewportView(table);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JButton btnNewButton = new JButton("Create Order");
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		panel_4.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Complete Order");
		panel_4.add(btnNewButton_1);
		
		JButton btnDeleteOrder = new JButton("Delete Order");
		panel_4.add(btnDeleteOrder);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_2, null);
	}
	
	private void updateTable() {
		int c1Width = 90;
		String[] columnNames = {"Order Number",
				"Customer Name",
				"Date Required"};
		
		ResultSet res = Driver.getJobInfo();
		Object[][] data = new Object[Driver.getNumJobs()][3];
		
		for (int i = 0; i < Driver.getNumJobs(); i++) {
			try {
				res.next();
				if (res.getDate("DateShipped") == null) {
					data[i][0] = res.getInt("OrderNumber");
					data[i][1] = res.getString("Customer");
					data[i][2] = res.getDate("DateRequired");
				} else {
					i--;
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		table.setModel(new GUITableModel(columnNames, data));
		
		table.getColumnModel().getColumn(0).setMinWidth(c1Width);
		table.getColumnModel().getColumn(0).setMaxWidth(c1Width);
		table.getColumnModel().getColumn(2).setMinWidth(c1Width);
		table.getColumnModel().getColumn(2).setMaxWidth(c1Width);
	}

	

	private class GUITableModel extends AbstractTableModel {
	    private String[] columnNames;
	    private Object[][] data;
	
	    public GUITableModel(String[] columns, Object[][] newData) {
	    	columnNames = columns;
	    	data = newData;
	    }
	    
	    public int getColumnCount() {
	        return columnNames.length;
	    }
	
	    public int getRowCount() {
	        return data.length;
	    }
	
	    public String getColumnName(int col) {
	        return columnNames[col];
	    }
	
	    public Object getValueAt(int row, int col) {
	        return data[row][col];
	    }
	    
	    public boolean isCellEditable(int row, int col) {
	    	return false;
	    }
	
	    public void setValueAt(Object value, int row, int col) {
	        data[row][col] = value;
	        fireTableCellUpdated(row, col);
	    }
	}
}
