package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.User;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import service.ActivityUtil;
import service.ChatRequestService;
import service.ChatServerThread;
import service.ListOfUsersListener;
import service.LoggerFactory;
import service.NotificationService;
import service.RESTService;
import service.ScreenReceiver;
import service.SecureNetworkService;

import java.awt.event.ActionListener;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Main extends JFrame {

	private static final long serialVersionUID = -4946938932210300950L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RESTService.class.getName());
	
	public static SecureNetworkService service;
	public static ChatRequestService requestService;

	public static String USERID;
	public static boolean isRunning = true;

	private JPanel contentPane;
	private JTextField textField;
	private JList<User> list;
	private Activity activity = new Activity();
	private CreateAccount account=new CreateAccount();
	private BlockUser blockUser=new BlockUser();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Main frame = new Main();
					LoginDialog loginDialog = new LoginDialog(frame);
					loginDialog.setVisible(true);
					if (!loginDialog.isSucceded()) {
						frame.dispose();
					} else {
						frame.setVisible(true);
					}
					
					requestService=new ChatRequestService();
					new ChatServerThread().start();
					requestService.login(Main.USERID, "admin", "admin");
					new ListOfUsersListener(frame.list).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		service = new SecureNetworkService();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 595, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel listOfUsers = new JPanel();
		listOfUsers.setBounds(5, 25, 212, 301);
		contentPane.add(listOfUsers);
		listOfUsers.setLayout(null);
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(12, 13, 188, 280);
				listOfUsers.add(scrollPane);
		
				list = new JList<>();
				scrollPane.setViewportView(list);

		JPanel optionPanel = new JPanel();
		optionPanel.setBounds(218, 25, 359, 301);
		contentPane.add(optionPanel);
		optionPanel.setLayout(null);

		JButton btnMonitor = new JButton("Monitor");
		btnMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Choose user from list first.");
				}else if(ScreenReceiver.STREAMING) {
					JOptionPane.showMessageDialog(null, "Cant start streaming: already streaming...");
				}
				else {
					requestService.monitor(list.getSelectedValue().getId());
				}
			}
		});
		btnMonitor.setBounds(133, 87, 75, 61);
		optionPanel.add(btnMonitor);

		JButton btnActivity = new JButton("Activity");
		btnActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Choose user from list first.");
				} else {
					String id = list.getSelectedValue().getId();
					LOGGER.config("Getting activity for user: "+id);
					activity.setTableData(ActivityUtil.getActivity(id));
					activity.getFrame().setVisible(true);
				}
			}
		});
		btnActivity.setBounds(133, 168, 75, 61);
		optionPanel.add(btnActivity);

		JPanel notificationPanel = new JPanel();
		notificationPanel.setBounds(0, 339, 577, 62);
		contentPane.add(notificationPanel);
		notificationPanel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(12, 13, 438, 36);
		notificationPanel.add(textField);
		textField.setColumns(10);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText() != null) {
					if (NotificationService.sendNotification(textField.getText())) {
						JOptionPane.showMessageDialog(null, "Notification sent.");
						textField.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "Notification cannot be sent.");
					}
				}
			}
		});
		btnSend.setBounds(462, 13, 103, 36);
		notificationPanel.add(btnSend);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 565, 26);
		contentPane.add(menuBar);
		
		JMenu mnAccount = new JMenu("Account");
		menuBar.add(mnAccount);
		
		JMenuItem mntmCreateAccount = new JMenuItem("Create Account");
		mntmCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				account.setVisible(true);
			}
		});
		mnAccount.add(mntmCreateAccount);
		
		JMenuItem mntmBlockAccount = new JMenuItem("Block Account");
		mntmBlockAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				blockUser.updateList();
				blockUser.setVisible(true);
			}
		});
		mnAccount.add(mntmBlockAccount);
	}
}
