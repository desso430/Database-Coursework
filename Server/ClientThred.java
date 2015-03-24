package Server;

import java.io.IOException;

import Request.Request;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Client.RequestCodes;
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
			while(true) {
				request = (Request) socketIn.readObject();
				
				if(request.getRequestCode().equals(RequestCodes.CODE_FOR_ALL_USERS))
					socketOut.writeObject(getUsers(getData(SQLStatements.SELECT_ALL_USERS)));
				if(request.getRequestCode().equals(RequestCodes.CODE_FOR_USER_STATISTIC))
					socketOut.writeObject(getStatisticForUser(getData(SQLStatements.SELECT_STATISTIC_FOR_USER + request.getId()))); 
				if(request.getRequestCode().equals(RequestCodes.CODE_FOR_ONE_USER)) {
					socketOut.writeObject(getUser(getData(SQLStatements.SELECT_USER + request.getId())));
					updateUser((User) socketIn.readObject());
				}		
			}
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
		try {
			PreparedStatement statement = databaseConnection.prepareStatement(SQLStatements.EDIT_USER_STATEMENT);
			statement.setString(1, user.getName());
			statement.setString(2, user.getEGN());
			statement.setString(3, user.getPhone());
			statement.setString(4, user.getAddress());
			statement.setInt(5, user.getUser_id());
			statement.execute();
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
