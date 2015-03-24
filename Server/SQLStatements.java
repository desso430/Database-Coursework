package Server;

public final class SQLStatements {
	static final String SELECT_ALL_USERS = " SELECT user_id, name FROM users ";
	static final String SELECT_USER = "SELECT * FROM users WHERE user_id = ";
	static final String SELECT_STATISTIC_FOR_USER = "SELECT * FROM Used_Internet_Traffic WHERE user_id = ";
	static final String EDIT_USER_STATEMENT = " UPDATE users "
                                            + " SET name = ? , egn = ? , phone = ? , address = ? "
                                            + " WHERE user_id = ? ";
	
	static final String ADD_NEW_USER_STATEMENT = " INSERT INTO users(egn, name, phone, address) "
			                                    +" VALUES (?, ?, ?, ?) ";	
}
