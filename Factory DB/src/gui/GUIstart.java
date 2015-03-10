/**
 * TCSS 491 SPRING 2015
 * Mickey Johnson
 * Final DB project
 */


package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import main.Driver;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This code was automatically created using the windowbuilder extension in eclipse.
 * @author Mickey Johnson
 *
 */
public class GUIstart {

	private static JFrame frmDbApp;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	
	private String address;
	private String username;
	private char[] password;

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIstart window = new GUIstart();
					window.frmDbApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIstart() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDbApp = new JFrame();
		frmDbApp.setTitle("DB APP");
		frmDbApp.setResizable(false);
		frmDbApp.setBounds(100, 100, 450, 300);
		frmDbApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDbApp.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblServerAddress = new JLabel("Server address:");
		frmDbApp.getContentPane().add(lblServerAddress, "4, 4, right, default");
		
		textField = new JTextField();
		textField.setText("localhost:3306/johnson_mickey_db");
		frmDbApp.getContentPane().add(textField, "6, 4, fill, default");
		textField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		frmDbApp.getContentPane().add(lblUsername, "4, 6, right, default");
		
		textField_1 = new JTextField();
		frmDbApp.getContentPane().add(textField_1, "6, 6, fill, default");
		textField_1.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		frmDbApp.getContentPane().add(lblPassword, "4, 8, right, default");
		
		passwordField = new JPasswordField();
		frmDbApp.getContentPane().add(passwordField, "6, 8, fill, default");
		
		JButton btnLogin = new JButton("login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				address = textField.getText();
				username = textField_1.getText();
				password = passwordField.getPassword();
				Driver.attemptLogin(address, username, password);
			}
		});
		frmDbApp.getContentPane().add(btnLogin, "4, 10");
	}

	public static void dispose() {
		frmDbApp.dispose();
		
	}
}
