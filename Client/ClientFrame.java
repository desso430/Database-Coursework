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
	private static JTextArea statisticsArea = new JTextArea();
	private static JComboBox<String> selectBox = new JComboBox<String>();

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
		contentPane.setLayout(null);
		
		setStatisticsArea();	
		setEditButton();	
		setShowButton();
		setSelectBox(users);
	}


	private void setSelectBox(String[] users) {
		selectBox.setModel(new DefaultComboBoxModel<String>(users));
		selectBox.setBounds(31, 32, 202, 20);
		contentPane.add(selectBox);
	}

	private void setShowButton() {
		JButton showButton = new JButton("Show statistic");
		showButton.setToolTipText("press to show statistic...");
		showButton.setBounds(263, 31, 127, 23);
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
		editButton.setToolTipText("press to edit user info...\r\n");
		editButton.setBounds(413, 31, 127, 23);
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
		scrollPane.setBounds(10, 82, 530, 498);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(statisticsArea);
		statisticsArea.setEditable(false);
	}
	
	private static void clearStatisticsArea() {
		statisticsArea.setText("");
	}
	
	public static void updateSelectBox(String[] users) {
		selectBox.setModel(new DefaultComboBoxModel<String>(users));
	}
	
	static void printStatistic(ArrayList<TrafficForUser> info) {
		 clearStatisticsArea();
		for(Iterator<TrafficForUser> it = info.iterator(); it.hasNext();) {
			TrafficForUser user = it.next();
			statisticsArea.append(" Date: " + user.getDate() + "    Sent data: " + user.getSentData()  + "    Receive data: " + user.getReceiveData() + "\n");
		}
	}
}
