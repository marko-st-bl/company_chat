package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import service.SOAPService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateAccount extends JFrame {

	private static final long serialVersionUID = 8447117996218325085L;
	
	private JPanel contentPane;
	private JTextField textFirstName;
	private JTextField textLastName;
	private JTextField textUsername;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAccount frame = new CreateAccount();
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
	public CreateAccount() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstName.setBounds(106, 26, 86, 16);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLastName.setBounds(106, 55, 86, 16);
		contentPane.add(lblLastName);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setBounds(106, 84, 86, 16);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(106, 113, 86, 16);
		contentPane.add(lblPassword);
		
		textFirstName = new JTextField();
		textFirstName.setBounds(204, 23, 116, 22);
		contentPane.add(textFirstName);
		textFirstName.setColumns(10);
		
		textLastName = new JTextField();
		textLastName.setColumns(10);
		textLastName.setBounds(204, 52, 116, 22);
		contentPane.add(textLastName);
		
		textUsername = new JTextField();
		textUsername.setColumns(10);
		textUsername.setBounds(204, 81, 116, 22);
		contentPane.add(textUsername);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(204, 110, 116, 22);
		contentPane.add(passwordField);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String firstName=textFirstName.getText();
				String lastName=textLastName.getText();
				String username=textUsername.getText();
				String password=new String(passwordField.getPassword());
				if(firstName!=null && lastName!=null&&username!=null&&password!=null) {
					boolean response=SOAPService.createAccount(firstName, lastName, username, password);
					if(response) {
						JOptionPane.showMessageDialog(null, "Account created.");
					}else {
						JOptionPane.showMessageDialog(null, "Error creating account.");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Empty fields not allowed.");
				}
			}
		});
		btnCreate.setBounds(145, 174, 116, 25);
		contentPane.add(btnCreate);
	}

}
