package Server;

import java.io.IOException;
import Request.Request;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import DatabaseObjects.*;
import java.sql.*;


public class ClientThred implements Runnable {

	private ObjectInputStream socketIn = null;
	private ObjectOutputStream socketOut = null;
	private Connection databaseConnection = null;
	private Request request = null;
	
	public ClientThred(Connection databaseConnection, ObjectInputStream socketIn, ObjectOutputStream socketOut) {
		this.databaseConnection = databaseConnection;
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}

	@Override
	public void run() {			
		try {
			
			request = (Request) socketIn.readObject();
			socketOut.writeObject(getUsers(getData(request.getQuery())));
			
			request = (Request) socketIn.readObject();
			socketOut.writeObject(getStatisticForUser(getData(request.getQuery())));
			
			request = (Request) socketIn.readObject();
			socketOut.writeObject(getUser(getData(request.getQuery())));
			
			updateUser((User) socketIn.readObject());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private ResultSet getData(String query) {
		ResultSet result = null;
		try {
			PreparedStatement statement = databaseConnection.prepareStatement(query);
			result = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	  return result;
	}
	
	private void updateUser(User user) {
	    String EDIT_USER_STATEMENT = " UPDATE users "
             	                   + " SET name = ? , egn = ? , phone = ? , address = ? "
               	                   + " WHERE user_id = ?";
		try {
			PreparedStatement statement = databaseConnection.prepareStatement(EDIT_USER_STATEMENT);
			statement.setString(1, user.getName());
			statement.setString(2, user.getEGN());
			statement.setString(3, user.getPhone());
			statement.setString(4, user.getAddress());
			statement.setInt(5, user.getUser_id());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private Users getUsers(ResultSet result) {
		ArrayList<String> users = new ArrayList<String>();
		try {
			while(result.next()) {
				users.add(result.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	  return new Users(users.toArray(new String[users.size()]));
	}
	
	private TrafficInformation getStatisticForUser(ResultSet result) {
		TrafficInformation info = new TrafficInformation();
		try {
			while(result.next()) {
				info.add(new TrafficForUser(result.getShort(1), result.getString(2), result.getString(3), result.getString(4)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	  return info;
	}

	private User getUser(ResultSet result) {
		User user = null;
		try {
			while(result.next()) {
		    	user = new User(result.getInt(1), result.getString(2),  result.getString(3), result.getString(4), result.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
}
