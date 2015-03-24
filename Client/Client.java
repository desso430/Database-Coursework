package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import DatabaseObjects.*;
import Request.Request;
import SecurityProtokol.*;

public class Client {
	
	private static final int port = 1234;
	private static final String host = "localhost";
	private static Socket connection = null;
	static ObjectInputStream socketIn = null;
	static ObjectOutputStream socketOut = null;
	
	public static void main(String[] args) {
	 try {
		try {
			ClientFrame.writeProgramStatus(" Connecting to server...");
			connection = new Socket(host, port);
		  } catch (IOException e) {
			ClientFrame.writeProgramStatus(" Can't connect to surver");
	        return;
		  }
		
		setStreams();
    	authorization(socketIn, socketOut);
    	ClientFrame.main(getAllUsers());
	 } catch (Exception e) {
		 ClientFrame.writeProgramStatus(" Error: " + e.getMessage());
	 }
	}

	private static String[] getAllUsers() throws IOException, ClassNotFoundException {
		SendRequest(RequestCodes.CODE_FOR_ALL_USERS, 0);
		Users users = (Users) socketIn.readObject();
		return users.getUsers();
	}

	private static void setStreams() throws IOException {
		socketOut = new ObjectOutputStream(connection.getOutputStream());
		socketIn =  new ObjectInputStream(connection.getInputStream());	
		socketOut.flush();
	}
	
	static void SendRequest(String requst, int id) {
		try {
			socketOut.writeObject(new Request(requst, id));
		} catch (IOException e) {
			ClientFrame.writeProgramStatus(" Error: " + e.getMessage());
		}
	}	
	
	static ArrayList<TrafficForUser> getUserStatistic(int user_id) {
		SendRequest(RequestCodes.CODE_FOR_USER_STATISTIC,user_id);
		TrafficInformation info = null;
		try {
			info = (TrafficInformation) socketIn.readObject();
		} catch (ClassNotFoundException | IOException e) {
			ClientFrame.writeProgramStatus(" Error: " + e.getMessage());
		}
       return info.getList();	
	}
	
	static User getUser(int user_id) {
		SendRequest(RequestCodes.CODE_FOR_ONE_USER, user_id);
		User user = null;
		try {
			user = (User) socketIn.readObject();
		} catch (ClassNotFoundException | IOException e) {
			ClientFrame.writeProgramStatus(" Error: " + e.getMessage());
		}
		return user;		
	}
	
	static void updateUser(User user) {
		try {
			socketOut.writeObject(user);
			ClientFrame.updateSelectBox(getAllUsers());
		} catch (IOException | ClassNotFoundException e) {
			ClientFrame.writeProgramStatus(" Error: " + e.getMessage());
		}
	}
	
	static void addNewUser(User user) {
		try {
			socketOut.writeObject(user);
			ClientFrame.updateSelectBox(getAllUsers());
		} catch (IOException | ClassNotFoundException e) {
			ClientFrame.writeProgramStatus(" Error: " + e.getMessage());
		}
	}
	
	private static void authorization(ObjectInputStream socketIn, ObjectOutputStream socketOut) {
		try {			
		    ClientFrame.writeProgramStatus(" Waitnig for authorization from server...");
			CodeForAuthorization code = (CodeForAuthorization) socketIn.readObject();	// gets the encrypted code from server for authorization
			String encryptedCode = new Decrypt(code.getCode()).getEncryptedText();
			socketOut.writeObject(new CodeForAuthorization(encryptedCode));  // send decrypted code to server for authorization
			Authorization answer = (Authorization) socketIn.readObject();
			
			ClientFrame.writeProgramStatus(answer.getAuthorizationInformation());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
    } 
}
