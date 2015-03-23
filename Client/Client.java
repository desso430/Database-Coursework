package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;

import DatabaseObjects.TrafficInformation;
import DatabaseObjects.Users;
import Request.Request;

public class Client {
	
	private static final int port = 1234;
	private static final String host = "localhost";
	private static ResultSet result = null;
	private static Socket connection = null;
	static ObjectInputStream socketIn = null;
	static ObjectOutputStream socketOut = null;
	
	public static void main(String[] args) {
	 try {
		try {
			connection = new Socket(host, port);
	
		  } catch (IOException e) {
			System.err.println(" Can't connect to surver");
	        return;
		  }
		
		setStreams();
		Connect.main(socketIn, socketOut);
		
		socketOut.writeObject(new Request(SQLStatements.SELECT_ALL_USERS));
		Users users = (Users) socketIn.readObject();
		ClientFrame.main(users.getUsers());
		TrafficInformation info = (TrafficInformation) socketIn.readObject();
        ClientFrame.printStatistic(info.getList());
	 } catch (Exception e) {
		 e.printStackTrace();
	 }
	}

	private static void setStreams() throws IOException {
		socketOut = new ObjectOutputStream(connection.getOutputStream());
		socketIn =  new ObjectInputStream(connection.getInputStream());	
		socketOut.flush();
	}
	
	static void SendRequest(String query) {
		try {
			socketOut.writeObject(new Request(query));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
