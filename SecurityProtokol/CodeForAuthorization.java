package SecurityProtokol;

import java.io.Serializable;

public class CodeForAuthorization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6113654130599569952L;
	private String code;

	public CodeForAuthorization(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
