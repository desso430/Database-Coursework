package SecurityProtokol;

public class UnauthorizedAccess extends Authorization {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -7504283417715263430L;
	private String reason;
	
	public UnauthorizedAccess(String reason) {
		super();
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

}
