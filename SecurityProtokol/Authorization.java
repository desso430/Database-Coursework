package SecurityProtokol;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Authorization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4467605542880933606L;
	protected LocalDate dateOfAuthorization;
	protected LocalTime timeOfAuthorization;
	private boolean isAuthorizated = false;
	private String message;
//	private int numberOfAuthorization;
//  private int serverID;

	
	public Authorization(String message, boolean isAuthorizated) {
		this.dateOfAuthorization = LocalDate.now();
		this.timeOfAuthorization = LocalTime.now();
		this.isAuthorizated = isAuthorizated;
		this.message = message;
	}

	
	public LocalDate getDateOfAuthorization() {
		return dateOfAuthorization;
	}
	public LocalTime getTimeOfAuthorization() {
		return timeOfAuthorization;
	}
	public String getAuthorizationInformation() {
		return message;
	}
	public boolean isAuthorizated() {
		return isAuthorizated;
	}

	
	@Override
	public String toString() {
		return "Authorization [dateOfAuthorization=" + dateOfAuthorization
				+ ", timeOfAuthorization=" + timeOfAuthorization
				+ ", isAuthorizated=" + isAuthorizated + ", message=" + message
				+ "]";
	}
	
}
