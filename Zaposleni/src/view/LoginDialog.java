package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import service.LoggerFactory;
import service.RESTService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.logging.Logger;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginDialog extends JDialog {
	
	private static final long serialVersionUID = 2714732377190678400L;

	private static final Logger LOGGER=LoggerFactory.getLogger(LoginDialog.class.getName());

	private final JPanel contentPanel = new JPanel();
	private JTextField txtEnterUsername;
	private JPasswordField passwordField;
	private boolean succeded=false;


	/**
	 * Create the dialog.
	 */
	public LoginDialog() {
		this(null);
		setTitle("Login");
	}
	public LoginDialog(Main parent) {
		super(parent,"Login",true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblUsername = new JLabel("Username:");
			lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
			lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));
			lblUsername.setBounds(143, 38, 116, 16);
			contentPanel.add(lblUsername);
		}
		{
			txtEnterUsername = new JTextField();
			txtEnterUsername.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					txtEnterUsername.setText("");
					txtEnterUsername.setForeground(new Color(0,0,0));
				}
			});
			txtEnterUsername.setForeground(new Color(192, 192, 192));
			txtEnterUsername.setFont(new Font("Arial", Font.PLAIN, 16));
			txtEnterUsername.setText("Enter username...");
			txtEnterUsername.setBounds(143, 67, 136, 22);
			contentPanel.add(txtEnterUsername);
			txtEnterUsername.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password:");
			lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
			lblPassword.setBounds(143, 102, 101, 16);
			contentPanel.add(lblPassword);
		}
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.setBounds(143, 131, 136, 22);
		contentPanel.add(passwordField);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnLogin = new JButton("Login");
				btnLogin.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String response=RESTService.login(txtEnterUsername.getText(), new String(passwordField.getPassword()));
						if(response!=null) {
							LOGGER.config("User: "+response+" connected.");
							succeded=true;
							Main.USERID=response;
							dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Invalid username/password!");
							succeded=false;
							txtEnterUsername.setText("");
							passwordField.setText("");
						}
					}
				});
				btnLogin.setActionCommand("OK");
				buttonPane.add(btnLogin);
				getRootPane().setDefaultButton(btnLogin);
			}
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						succeded=false;
						parent.dispose();
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public boolean isSucceded() {
		return succeded;
	}
	public void setSucceded(boolean succeded) {
		this.succeded = succeded;
	}
}
