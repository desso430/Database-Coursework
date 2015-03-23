package SecurityProtokol;

public class AuthorizedAccess extends Authorization {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5936889086118800588L;
	private String message;

	public AuthorizedAccess(String information) {
		super();
		this.message = information;
	}

	public String getAdditionalInformation() {
		return message;
	}
		
}
