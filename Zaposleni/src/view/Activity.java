package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class Activity {

	private JFrame frmUserActivity;
	private String columnNames[]= {"SessionID","Login time","Logout time","Active time"};
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Activity window = new Activity();
					window.frmUserActivity.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Activity() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUserActivity = new JFrame();
		frmUserActivity.setTitle("User Activity");
		frmUserActivity.setBounds(100, 100, 548, 300);
		frmUserActivity.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 518, 253);
		frmUserActivity.getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 494, 227);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			columnNames
		));
	}
	
	public JFrame getFrame() {
		return frmUserActivity;
	}
	public void setTableData(String[] data) {
		DefaultTableModel model=(DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for(int i=0;i<data.length;i+=4) {
			model.addRow(new Object[]{data[i],data[i+1],data[i+2],data[i+3]});
		}
		
	}
}
