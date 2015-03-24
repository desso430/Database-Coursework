package Client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import DatabaseObjects.TrafficForUser;
import DatabaseObjects.User;


public class ClientFrame extends JFrame {
	private static final long serialVersionUID = -1633776426365690884L;
	private JPanel contentPane = new JPanel();
	private static JComboBox<String> selectBox = new JComboBox<String>();
	private static JTextArea programStatusArea = new JTextArea();
	private static JTextArea statisticsArea = new JTextArea();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] users) {				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							ClientFrame frame = new ClientFrame(users);
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
	public ClientFrame(String[] users) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 566, 630);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		setProgramStatus();
		setSelectBox(users);
		setShowButton();
		setEditButton();				
		setAddButton();
		setStatisticsArea();		
	}


	private void setSelectBox(String[] users) {		
		JLabel SelectUserLabel = new JLabel("Select User: ");
		SelectUserLabel.setBounds(295, 35, 91, 14);
		contentPane.add(SelectUserLabel);
		selectBox.setBounds(296, 51, 226, 20);
		selectBox.setModel(new DefaultComboBoxModel<String>(users));
		contentPane.add(selectBox);	
	}

	private void setAddButton() {
		JButton addButton = new JButton(" Add Use ");
		addButton.setBounds(343, 175, 138, 23);
		contentPane.add(addButton);
		
		// add behaviour
		addButton.addActionListener(new ActionListener() {
													
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.SendRequest(RequestCodes.CODE_FOR_ADD_NEW_USER, 0);
				AddNewUser.main();		
			}			
		});
	}

	private void setProgramStatus() {
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 276, 147);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);	
		JLabel ProgramStatusLabel = new JLabel("Program Status: ");
		ProgramStatusLabel.setBounds(10, 35, 99, 14);
		contentPane.add(ProgramStatusLabel);
		programStatusArea.setWrapStyleWord(true);
		programStatusArea.setEditable(false);
		scrollPane.setViewportView(programStatusArea);
	}

	private void setShowButton() {
		JButton showButton = new JButton("Show statistic");
		showButton.setBounds(343, 86, 138, 23);
		showButton.setToolTipText("press to show statistic...");
		contentPane.add(showButton);
		
		// add behaviour
		showButton.addActionListener(new ActionListener() {
											
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<TrafficForUser> info = Client.getUserStatistic(selectBox.getSelectedIndex() + 1);
				printStatistic(info);
			 }			
		});
	}

	private void setEditButton() {
		JButton editButton = new JButton("Edit user\r\n");
		editButton.setBounds(343, 130, 138, 23);
		editButton.setToolTipText("press to edit user info...\r\n");
		contentPane.add(editButton);
		
		// add behaviour
		editButton.addActionListener(new ActionListener() {
													
			@Override
			public void actionPerformed(ActionEvent e) {
				User user = Client.getUser(selectBox.getSelectedIndex()+1);
				EditUser.main(user);	
			}			
		});
	}

	private void setStatisticsArea() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 229, 530, 351);
		contentPane.add(scrollPane);
		JLabel UserStatisticsLabel = new JLabel("User Statistics: ");
		UserStatisticsLabel.setBounds(10, 209, 99, 14);
		contentPane.add(UserStatisticsLabel);
		statisticsArea.setWrapStyleWord(true);
		statisticsArea.setEditable(false);
		scrollPane.setViewportView(statisticsArea);
	}
	
	private static void clearStatisticsArea() {
		statisticsArea.setText("");
	}
	
	public static void updateSelectBox(String[] users) {
		selectBox.setModel(new DefaultComboBoxModel<String>(users));
	}
	
	public static void writeProgramStatus(String status) {
		if(status != null && !status.equals("")) {
			programStatusArea.append(status + "\n");
		}
	}
	
	
	static void printStatistic(ArrayList<TrafficForUser> info) {
		 clearStatisticsArea();
		for(Iterator<TrafficForUser> it = info.iterator(); it.hasNext();) {
			TrafficForUser user = it.next();
			statisticsArea.append(" Date: " + user.getDate() + "    Sent data: " + user.getSentData()  + "    Receive data: " + user.getReceiveData() + "\n");
		}
	}
}
