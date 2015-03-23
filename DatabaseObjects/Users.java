package DatabaseObjects;

import java.io.Serializable;
import java.util.Arrays;
import java.lang.String;

public class Users implements Serializable {
	private String[] users;

	public Users(String[] users) {
		this.users = users;
	}

	public String[] getUsers() {
		return users;
	}

	@Override
	public String toString() {
		return "Users [users=" + Arrays.toString(users) + "]";
	}
}
