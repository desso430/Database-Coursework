package DatabaseObjects;

public class SecurityCode {

	private int code_id;
	private String encryptedCode;
	private String decryptedCode;
	
	
	public SecurityCode(int code_id, String encryptedCode, String decryptedCode) {
		this.code_id = code_id;
		this.encryptedCode = encryptedCode;
		this.decryptedCode = decryptedCode;
	}
	
	public int getCode_id() {
		return code_id;
	}
	public String getEncryptedCode() {
		return encryptedCode;
	}
	public String getDecryptedCode() {
		return decryptedCode;
	}
	

	@Override
	public String toString() {
		return "SecurityCode [code_id=" + code_id + ", encryptedCode="
				+ encryptedCode + ", decryptedCode=" + decryptedCode + "]";
	}
	
}
