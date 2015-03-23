package Request;

import java.io.Serializable;

public class Request implements Serializable{

	private String request;
	private String typeOfRequest;

	public Request(String request) {
		this.request = request;
	}


	public String getQuery() {
		return request;
	}
	public void setQuery(String query) {
		this.request = query;
	}
	
	
	@Override
	public String toString() {
		return "Request [query=" + request + "]";
	}
}
