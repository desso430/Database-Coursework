package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JScrollPane;

import SecurityProtokol.Authorization;
import SecurityProtokol.AuthorizedAccess;
import SecurityProtokol.Decrypt;
import SecurityProtokol.CodeForAuthorization;
import SecurityProtokol.UnauthorizedAccess;

public class Connect extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7278797836442511463L;
	private JPanel contentPane = new JPanel();
	private JPanel panel = new JPanel();
	private JTextArea ProgramStatusArea = new JTextArea();
	private JButton ContinueButton = new JButton("Continue");
	private JButton closeButton = new JButton("Close");
	
	
	/**
	 * Launch the application.
	 */
	public static void main(ObjectInputStream socketIn, ObjectOutputStream socketOut) {
		Connect frame = new Connect(" Connect ", socketIn, socketOut);
		frame.setVisible(true);	
		frame.connect(socketIn, socketOut);
	}

	/**
	 * Create the frame.
	 */
	public Connect(String name, ObjectInputStream socketIn, ObjectOutputStream socketOut) {
		super(name);
		buildFrame();
		
		setContinueButton(panel);		
		setProgramStatusArea(panel);		
		setCloseButton(panel);
	}

	private void buildFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 355, 287);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
	}

	private void setContinueButton(JPanel panel) {
		ContinueButton.setEnabled(false);
		ContinueButton.setBounds(33, 201, 89, 23);
		panel.add(ContinueButton);
		
		// add behaviour
		ContinueButton.addActionListener(new ActionListener() {
															
			@Override
			public void actionPerformed(ActionEvent e) {
			
			}			
		});
	}

	private void setProgramStatusArea(JPanel panel) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 24, 309, 153);
		panel.add(scrollPane);	
		ProgramStatusArea.setWrapStyleWord(true);
		ProgramStatusArea.setEditable(false);
		scrollPane.setViewportView(ProgramStatusArea);
	}

	private void setCloseButton(JPanel panel) {
		closeButton.setBounds(209, 201, 89, 23);
		panel.add(closeButton);
		
		// add behaviour
		closeButton.addActionListener(new ActionListener() {
																	
			@Override
			public void actionPerformed(ActionEvent e) {
			  System.exit(0);
			}			
		});
	}
	
	private void printMessage(String message) {
		if(message != null && !message.equals("")) {
			ProgramStatusArea.append(message + "\n");
		}
	}
	
	private void connect(ObjectInputStream socketIn, ObjectOutputStream socketOut) {
		printMessage(" Please wait a few seconds to connect to server! ");
		printMessage(" Waiting for encrypted code...");
		try {
			
			CodeForAuthorization code = (CodeForAuthorization) socketIn.readObject();	// gets the encrypted code from server for authorization
			printMessage(" Decrypting the code...");
			String encryptedCode = new Decrypt(code.getCode()).getEncryptedText();
			socketOut.writeObject(new CodeForAuthorization(encryptedCode));  // send decrypted code to server for authorization
			printMessage(" Waiting for authorization from server ...");
			Authorization answer = (Authorization) socketIn.readObject();
			
			if(answer instanceof AuthorizedAccess) {
				printMessage(((AuthorizedAccess) answer).getAdditionalInformation());
				ContinueButton.setEnabled(true);
			} else {
				printMessage(((UnauthorizedAccess) answer).getReason());
			}
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}
