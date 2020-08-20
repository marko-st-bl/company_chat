package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import service.RESTService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangePassword extends JFrame {

	private static final long serialVersionUID = -5677719777024026450L;
	
	private JPanel contentPane;
	private JPasswordField passwordField_current;
	private JPasswordField passwordField_new;
	private JPasswordField passwordField_confirm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePassword frame = new ChangePassword();
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
	public ChangePassword() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(81, 56, 111, 16);
		contentPane.add(lblPassword);

		passwordField_current = new JPasswordField();
		passwordField_current.setBounds(218, 54, 114, 19);
		contentPane.add(passwordField_current);

		JLabel lblNewPassword = new JLabel("New password");
		lblNewPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewPassword.setBounds(81, 95, 126, 16);
		contentPane.add(lblNewPassword);

		JLabel lblConfirmPassword = new JLabel("Confirm password");
		lblConfirmPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmPassword.setBounds(81, 135, 126, 16);
		contentPane.add(lblConfirmPassword);

		passwordField_new = new JPasswordField();
		passwordField_new.setBounds(218, 92, 114, 19);
		contentPane.add(passwordField_new);

		passwordField_confirm = new JPasswordField();
		passwordField_confirm.setBounds(218, 132, 114, 19);
		contentPane.add(passwordField_confirm);

		JButton btnChangePassword = new JButton("Change password");
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(new String(passwordField_new.getPassword()));
				System.out.println(new String(passwordField_confirm.getPassword()));
				if (new String(passwordField_new.getPassword()).equals(new String(passwordField_confirm.getPassword()))) {
					if (RESTService.changePassword(new String(passwordField_current.getPassword()),
							new String(passwordField_new.getPassword()), Main.USERID)) {
						JOptionPane.showMessageDialog(null, "Password succesfuly changed!");
					} else {
						JOptionPane.showMessageDialog(null, "Cannot Change Password!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Password doesn't match!");
				}
				passwordField_current.setText("");
				passwordField_new.setText("");
				passwordField_confirm.setText("");
			}
		});
		btnChangePassword.setBounds(285, 215, 135, 25);
		contentPane.add(btnChangePassword);
	}
}
