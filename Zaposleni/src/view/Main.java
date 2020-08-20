package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.User;
import service.ActivityUtil;
import service.ChatRequestService;
import service.ChatService;
import service.ChatThread;
import service.SecureNetworkService;
import service.StreamUtil;
import service.ListOfUsersListener;
import service.LoggerFactory;
import service.MessagesService;
import service.RESTService;
import service.FileService;
import service.NotificationListener;
import service.PropertiesUtil;
import service.UserFilesListener;

import java.awt.Color;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.BoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Component;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.ComponentOrientation;
import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main extends JFrame {

	private static final long serialVersionUID = -403678459467789178L;

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class.getName());
	private static final Properties PROP = PropertiesUtil.loadProperties();

	public static String USERID;
	public static User user;
	
	public static ChatRequestService requestService;
	public static SecureNetworkService service;
	public static MessagesService messageService;
	
	public static boolean stream = false;
	public static ArrayList<User> users=new ArrayList<>();
	private File file;
	private JTextPane textPane;
	private JPanel contentPane;
	private Activity activity = new Activity();
	private ChangePassword passwordFrame = new ChangePassword();
	private JTextField txtMessage;
	private JFileChooser fc;
	private JLabel labelNotification;
	private JLabel lblNoFileChoosed;
	private JList<String> listReceivedFiles;
	private JList<User> usersList;
	private JComboBox<User> comboBoxUsers;


	public static boolean isRunning = true;

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
					if (loginDialog.isSucceded()) {
						frame.setVisible(true);
					} else {
						System.exit(0);
					}
					requestService=new ChatRequestService();
					messageService=new MessagesService(frame.textPane,frame.txtMessage);
					
					new ChatThread().start();
					
					new NotificationListener(frame.labelNotification).start();
					new UserFilesListener(frame.listReceivedFiles).start();
					new ListOfUsersListener(frame.usersList, frame.comboBoxUsers).start();
					
					new ChatService(frame.usersList).start();	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public Main() throws IOException {
		service = new SecureNetworkService();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				LOGGER.config("Window closed");
				isRunning = false;
				if(StreamUtil.STREAMING) {
					StreamUtil.STREAMING=false;
				}
				service.disconnect();
				RESTService.logout(Main.USERID);
				e.getWindow().dispose();
			}
		});
		setBounds(100, 100, 840, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel background = new JPanel();
		background.setBounds(0, 0, 822, 467);
		contentPane.add(background);
		background.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.setAutoscrolls(true);
		menuBar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		menuBar.setAlignmentY(Component.CENTER_ALIGNMENT);
		menuBar.setBounds(0, 0, 822, 26);
		background.add(menuBar);

		JMenu mnOptions = new JMenu("Options");
		mnOptions.setHorizontalTextPosition(SwingConstants.RIGHT);
		mnOptions.setHorizontalAlignment(SwingConstants.RIGHT);
		mnOptions.setFont(new Font("Arial", Font.PLAIN, 15));
		mnOptions.setAlignmentX(Component.RIGHT_ALIGNMENT);
		menuBar.add(mnOptions);

		JMenuItem mntmActivity = new JMenuItem("Activity");
		mntmActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				activity.setTableData(ActivityUtil.getActivity(Main.USERID));
				activity.getFrame().setVisible(true);

			}
		});
		mnOptions.add(mntmActivity);

		JMenuItem mntmChangePassword = new JMenuItem("Change password");
		mntmChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				passwordFrame.setVisible(true);
			}
		});
		mnOptions.add(mntmChangePassword);

		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RESTService.logout(USERID);
				isRunning = false;
				System.exit(NORMAL);
			}
		});
		mnOptions.add(mntmLogout);

		JPanel listOfUsers = new JPanel();
		listOfUsers.setBackground(new Color(0, 0, 255));
		listOfUsers.setBounds(0, 73, 197, 394);
		background.add(listOfUsers);
		listOfUsers.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 174, 368);
		listOfUsers.add(scrollPane);

		usersList = new JList<>();
		
		scrollPane.setViewportView(usersList);
		usersList.setForeground(new Color(255, 255, 255));
		usersList.setBackground(new Color(0, 0, 255));

		JPanel messages = new JPanel();
		messages.setBackground(new Color(255, 255, 255));
		messages.setBounds(197, 73, 477, 394);
		background.add(messages);
		messages.setLayout(null);

		JPanel send = new JPanel();
		send.setBorder(null);
		send.setBackground(new Color(255, 255, 255));
		send.setBounds(0, 347, 477, 47);
		messages.add(send);
		send.setLayout(null);

		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER) {
					if (usersList.isSelectionEmpty()) {
						JOptionPane.showMessageDialog(null, "You must select receiver!");
					}else if(txtMessage.getText().equals("")){
						
					}else {
						requestService.sendMessage(Main.USERID, usersList.getSelectedValue().getId(), txtMessage.getText());
						txtMessage.setText("");
					}
				}
			}
		});
		txtMessage.setBorder(null);
		txtMessage.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtMessage.setText("");
				txtMessage.setForeground(new Color(0, 0, 0));

			}
		});
		txtMessage.setToolTipText("");
		txtMessage.setForeground(new Color(128, 128, 128));
		txtMessage.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMessage.setText("Type your message here...");
		txtMessage.setBounds(12, 0, 354, 40);
		send.add(txtMessage);
		txtMessage.setColumns(10);

		JLabel label = new JLabel(new ImageIcon(PROP.getProperty("send.icon")));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (usersList.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "You must select receiver!");
				} else if(txtMessage.getText().equals("")){
				} else {
					requestService.sendMessage(Main.USERID, usersList.getSelectedValue().getId(), txtMessage.getText());
					txtMessage.setText("");
				}
			}
		});
		label.setBounds(397, 0, 56, 40);
		send.add(label);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 13, 453, 318);
		scrollPane_2.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

		     BoundedRangeModel brm = scrollPane_2.getVerticalScrollBar().getModel();
		     boolean wasAtBottom = true;

		     public void adjustmentValueChanged(AdjustmentEvent e) {
		        if (!brm.getValueIsAdjusting()) {
		           if (wasAtBottom)
		              brm.setValue(brm.getMaximum());
		        } else
		           wasAtBottom = ((brm.getValue() + brm.getExtent()) == brm.getMaximum());

		     }
		  });  
		messages.add(scrollPane_2);
		
		textPane = new JTextPane();
		scrollPane_2.setViewportView(textPane);

		JPanel files = new JPanel();
		files.setBackground(new Color(0, 0, 128));
		files.setBounds(674, 73, 148, 394);
		background.add(files);
		files.setLayout(null);

		JPanel sendFile = new JPanel();
		sendFile.setBackground(new Color(0, 0, 128));
		sendFile.setBounds(0, 0, 148, 196);
		files.add(sendFile);
		sendFile.setLayout(null);

		comboBoxUsers = new JComboBox<>();
		comboBoxUsers.setBounds(12, 46, 105, 22);
		sendFile.add(comboBoxUsers);

		JLabel lblChooseUser = new JLabel("ChooseUser");
		lblChooseUser.setForeground(new Color(255, 255, 255));
		lblChooseUser.setBounds(12, 13, 124, 16);
		sendFile.add(lblChooseUser);

		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = fc.showDialog(send, "Choose");
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile();
					lblNoFileChoosed.setText(file.getName());
				}
			}
		});
		btnBrowse.setBounds(12, 81, 105, 25);
		sendFile.add(btnBrowse);

		lblNoFileChoosed = new JLabel("No file choosed");
		lblNoFileChoosed.setForeground(new Color(255, 255, 255));
		lblNoFileChoosed.setBounds(12, 120, 124, 16);
		sendFile.add(lblNoFileChoosed);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileService.sendFile(file,((User)comboBoxUsers.getSelectedItem()).getId());
			}
		});
		btnSend.setBounds(12, 158, 124, 25);
		sendFile.add(btnSend);

		JSeparator separator = new JSeparator();
		separator.setBounds(12, 194, 124, 2);
		sendFile.add(separator);

		JPanel downloadFile = new JPanel();
		downloadFile.setBackground(new Color(0, 0, 128));
		downloadFile.setBounds(0, 196, 148, 198);
		files.add(downloadFile);
		downloadFile.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 13, 124, 121);
		downloadFile.add(scrollPane_1);

		listReceivedFiles = new JList<>();
		scrollPane_1.setViewportView(listReceivedFiles);

		JButton btnDownload = new JButton("Download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = (String) listReceivedFiles.getSelectedValue();
				if (name != null) {
					fc = new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = fc.showDialog(send, "Select Folder");
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File dir = fc.getSelectedFile();
						FileService.downloadFile(name, dir);
					}
				}
			}
		});
		btnDownload.setBounds(12, 149, 124, 25);
		downloadFile.add(btnDownload);

		JPanel notification = new JPanel();
		notification.setBackground(new Color(255, 255, 255));
		notification.setBounds(0, 26, 822, 46);
		background.add(notification);
		notification.setLayout(null);

		labelNotification = new JLabel("");
		labelNotification.setBounds(12, 5, 697, 41);
		labelNotification.setHorizontalTextPosition(SwingConstants.CENTER);
		labelNotification.setHorizontalAlignment(SwingConstants.CENTER);
		labelNotification.setFont(new Font("Arial", Font.PLAIN, 22));
		labelNotification.setForeground(new Color(255, 0, 0));
		labelNotification.setBackground(new Color(255, 255, 255));
		notification.add(labelNotification);
	}
}
