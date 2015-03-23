package Client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import DatabaseObjects.TrafficForUser;


public class ClientFrame extends JFrame {
	private static final long serialVersionUID = -1633776426365690884L;
	private JPanel contentPane = new JPanel();
	private static JTextArea statisticsArea = new JTextArea();
	private JComboBox selectBox = new JComboBox();
//	private String[] options = new String[] {"All paid users", "All not paid uers", "Biggest internet traffic"};

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setSelectBox(String[] users) {
		selectBox.setModel(new DefaultComboBoxModel(users));
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
				Client.SendRequest(SQLStatements.SELECT_STATISTIC_FOR_USER + (selectBox.getSelectedIndex()+1));	
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
				EditUser.main(Client.socketIn, Client.socketOut, (selectBox.getSelectedIndex()+1));	
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
	
//	private void printStatistic(String statistic) {
//		if(statistic != null && !statistic.equals("")) {
//			statisticsArea.append(statistic);
//		}
//	}
	
	static void printStatistic(ArrayList<TrafficForUser> info) {
		for(Iterator<TrafficForUser> it = info.iterator(); it.hasNext();) {
			TrafficForUser user = it.next();
			statisticsArea.append(" Date: " + user.getDate() + "    Sent data: " + user.getSentData()  + "    Receive data: " + user.getReceiveData() + "\n");
		}
	}
}
