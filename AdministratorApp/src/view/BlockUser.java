package view;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import service.LoggerFactory;
import service.RESTService;
import service.SOAPService;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BlockUser extends JFrame {

	private static final long serialVersionUID = -6862737911341356836L;

	private static Logger LOGGER = LoggerFactory.getLogger(BlockUser.class.getName());

	private JPanel contentPane;
	private JList<String> list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BlockUser frame = new BlockUser();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BlockUser() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 175, 253);
		contentPane.add(panel);
		panel.setLayout(null);

		list = new JList<>();
		list.setBounds(12, 13, 151, 227);
		panel.add(list);

		JButton btnBlock = new JButton("Block");
		btnBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Choose user from list first.");
				} else {
					String id = list.getSelectedValue().split(" ")[0];
					LOGGER.config("Blocking user: "+id);
				    boolean response=SOAPService.blockUser(id);
				    if(response) {
				    	JOptionPane.showMessageDialog(null, "User blocled.");
				    }else {
				    	JOptionPane.showMessageDialog(null, "Error blocking user.");
				    }
				}
			}
		});
		btnBlock.setBounds(255, 111, 97, 25);
		contentPane.add(btnBlock);
	}

	public void updateList() {
		JSONArray all;
		ArrayList<String> usersArray = new ArrayList<>();

		all = RESTService.getAllUsers();
		if (all != null) {
			for (int i = 0; i < all.length(); i++) {
				JSONObject obj = all.getJSONObject(i);
				usersArray.add(obj.getString("id") + " " +obj.getString("firstName") + " " + obj.get("lastName"));
			}
		}
		list.removeAll();
		list.setListData(usersArray.toArray(new String[usersArray.size()]));

	}
}
