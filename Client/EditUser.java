package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import DatabaseObjects.User;
import Request.Request;

public class EditUser extends JFrame {

	private JPanel contentPane;
	private static JTextField addressField;
	private static JTextField nameField;
	private static JTextField EGNField;
	private static JTextField phoneField;
	private ObjectInputStream socketIn = null;
	private ObjectOutputStream socketOut = null;

	/**
	 * Launch the application.
	 */
	public static void main(ObjectInputStream socketIn, ObjectOutputStream socketOut, int user_id) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					socketOut.writeObject(new Request(SQLStatements.SELECT_USER + user_id));
					EditUser frame = new EditUser(socketIn, socketOut, user_id);
					frame.setVisible(true);	
					setToolTipText(socketIn);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EditUser(ObjectInputStream socketIn, ObjectOutputStream socketOut, int user_id) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 374, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		this.socketIn = socketIn;
		this.socketOut = socketOut;
		
		EGNField = new JTextField();
		EGNField.setBounds(39, 120, 281, 20);
		panel.add(EGNField);
		EGNField.setColumns(10);
		
		phoneField = new JTextField();
		phoneField.setBounds(39, 186, 281, 20);
		panel.add(phoneField);
		phoneField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setBounds(39, 59, 281, 20);
		panel.add(nameField);
		nameField.setColumns(10);
		
		JButton EditButton = new JButton("Save");
		EditButton.setBounds(126, 303, 89, 23);
		panel.add(EditButton);
		
		
		// add behaviour
		EditButton.addActionListener(new ActionListener() {
															
			@Override
			public void actionPerformed(ActionEvent e) {
			  String name =	nameField.getText();
			  String egn = EGNField.getText();
			  String phone = phoneField.getText();
			  String address = addressField.getText();
			  try {
				socketOut.writeObject(new User(user_id, egn, name, phone, address));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			  System.exit(0);
		  }			
		});
		
		
		JLabel nameLabel = new JLabel("User Name: ");
		nameLabel.setBounds(39, 43, 111, 14);
		panel.add(nameLabel);
		
		JLabel EGNLabel = new JLabel("User EGN: ");
		EGNLabel.setBounds(39, 104, 111, 14);
		panel.add(EGNLabel);
		
		JLabel phoneLabel = new JLabel("User phone: ");
		phoneLabel.setBounds(39, 171, 98, 14);
		panel.add(phoneLabel);
		
		JLabel addressLabel = new JLabel("User address: ");
		addressLabel.setBounds(39, 240, 111, 14);
		panel.add(addressLabel);
		
		addressField = new JTextField();
		addressField.setBounds(39, 255, 281, 20);
		panel.add(addressField);
		addressField.setColumns(10);
	}

	private static void setToolTipText(ObjectInputStream socketIn) {
		try {
			User user = (User) socketIn.readObject();
			nameField.setText(user.getName());
			EGNField.setText(user.getEGN());
			phoneField.setText(user.getPhone());
			addressField.setText(user.getAddress());
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	

}
