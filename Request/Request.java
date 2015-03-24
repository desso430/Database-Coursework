package Request;

import java.io.Serializable;

public class Request implements Serializable{

	private static final long serialVersionUID = -3824135469099089543L;
	private String requestCode;
	private int id;
	
	public Request(String requestCode, int id) {
		this.id = id;
		this.requestCode = requestCode;
	}

	public int getId() {
		return id;
	}
	public String getRequestCode() {
		return requestCode;
	}

	
	@Override
	public String toString() {
		return "Request [requestCode=" + requestCode + "]";
	}

}
