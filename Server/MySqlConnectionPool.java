package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public final class MySqlConnectionPool {

	private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/DBHomework";
    private static final int MAX_POOL_SIZE = 10;  // Maximum number of connections that the pool can have
    private static final int INITIAL_POOL_SIZE = 5;  // Number of connections that should be created initially
	private BlockingQueue<Connection> pool;
    private int currentPoolSize;  // Number of connections generated so far
    private String DatabaseUser;
    private String DatabasePassword;
    
    public MySqlConnectionPool(String username,String password) {
    	pool = new LinkedBlockingQueue<Connection>(MAX_POOL_SIZE);
        DatabaseUser = username;
        DatabasePassword = password;
        initialiseThePool();
    }
    
    
    public int getCurrentPoolSize() {
		return pool.size();
	}
   
    
    private void initialiseThePool() {
        // 1. Attempt to load the driver class
        try {
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

        // 2. Create and pool connections
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            createNewConnection();
        }
    }

    
    private synchronized void createNewConnection() {
        if (currentPoolSize == MAX_POOL_SIZE) {
            return;
        }

        Connection connection = null;
		try {
			connection = DriverManager.getConnection(DATABASE_URL, DatabaseUser, DatabasePassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        pool.offer(connection);
        currentPoolSize++;
    }

	public Connection getConnection() {
		Connection connection = null;
    	try {
    		connection = pool.take();
    		createNewConnection();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return connection;
    }   
}

