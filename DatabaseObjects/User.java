package DatabaseObjects;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -7003467833488005874L;
	private int user_id;
	private String name;
	private String EGN;
	private String phone;
	private String address;
	
	
	public User() {
		user_id = 0;
		name = "";
		EGN = "";
		phone = "";
		address = "";
	}

	public User(String EGN, String name, String phone, String address) {
		this.EGN = EGN;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}
	
	public User(int user_id, String EGN, String name, String phone, String address) {
		this.user_id = user_id;
		this.EGN = EGN;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}
	
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEGN() {
		return EGN;
	}
	public void setEGN(String eGN) {
		EGN = eGN;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", name=" + name + ", EGN=" + EGN
				+ ", phone=" + phone + ", address=" + address + "]";
	}
}
