package DatabaseObjects;

import java.io.Serializable;

public class TrafficForUser implements Serializable {

	private static final long serialVersionUID = 5634184264765490991L;
	private int user_id;
	private String date;
	private String sentData;
	private String receiveData;
	
	
	public TrafficForUser(int user_id, String date, String sentData, String receiveData) {
		this.user_id = user_id;
		this.date = date;
		this.sentData = sentData;
		this.receiveData = receiveData;
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSentData() {
		return sentData;
	}
	public void setSentData(String sentData) {
		this.sentData = sentData;
	}
	public String getReceiveData() {
		return receiveData;
	}
	public void setReceiveData(String receiveData) {
		this.receiveData = receiveData;
	}
}
