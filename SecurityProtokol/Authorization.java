package SecurityProtokol;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Authorization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4467605542880933606L;
	protected LocalDate dateOfAuthorization;
	protected LocalTime timeOfAuthorization;

	public Authorization() {
		this.dateOfAuthorization = LocalDate.now();
		this.timeOfAuthorization = LocalTime.now();
	}

	public LocalDate getDateOfAuthorization() {
		return dateOfAuthorization;
	}
	public LocalTime getTimeOfAuthorization() {
		return timeOfAuthorization;
	}
	
}
