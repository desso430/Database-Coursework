package DatabaseObjects;

import java.sql.Date;

public class UserDataInformation {
	private int userId;
	private String IP;
	private String macAddress;
	private String internetPlan;
	private Date dateOfPaying;
	private String usedInternetTraffic;
	
	
	public UserDataInformation(int userId, String IP, String macAddress, String internetPlan, Date dateOfPaying, String usedInternetTraffic) {
		this.userId = userId;
		this.IP = IP;
		this.macAddress = macAddress;
		this.internetPlan = internetPlan;
		this.dateOfPaying = dateOfPaying;
		this.usedInternetTraffic = usedInternetTraffic;
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getInternetPlan() {
		return internetPlan;
	}
	public void setInternetPlan(String internetPlan) {
		this.internetPlan = internetPlan;
	}
	public Date getDateOfPaying() {
		return dateOfPaying;
	}
	public void setDateOfPaying(Date dateOfPaying) {
		this.dateOfPaying = dateOfPaying;
	}
	public String getUsedInternetTraffic() {
		return usedInternetTraffic;
	}
	public void setUsedInternetTraffic(String usedInternetTraffic) {
		this.usedInternetTraffic = usedInternetTraffic;
	}


	@Override
	public String toString() {
		return "UserDataInformation [userId=" + userId + ", IP=" + IP
				+ ", macAddress=" + macAddress + ", internetPlan="
				+ internetPlan + ", dateOfPaying=" + dateOfPaying
				+ ", usedInternetTraffic=" + usedInternetTraffic + "]";
	}

}
