package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import DatabaseObjects.SecurityCode;
import SecurityProtokol.*;
import java.sql.*;

public class Server implements Runnable {

	private static final int PORT = 1234;
	private static ServerSocket serverSocket = null;
	private static Socket connection = null;
	private static Connection DatabaseConnection = null;
	private static ObjectInputStream socketIn = null;
	private static ObjectOutputStream socketOut = null;
	private static MySqlConnectionPool pool = new MySqlConnectionPool("root", "Desislav166");
	
	@Override
	public void run() {
		try {						
			serverSocket = new ServerSocket(PORT);
			ServerFrame.writeMessageInStatusArea(" Server is started....");
			DatabaseConnection = pool.getConnection();
			while(true) {
				ServerFrame.writeMessageInStatusArea(" Waiting for clients...");
				connection = serverSocket.accept();
				String userName = InetAddress.getLocalHost().getHostName();
				ServerFrame.writeMessageInStatusArea(" client " + userName + " is connected!");
				socketOut = new ObjectOutputStream(connection.getOutputStream());
				socketIn =  new ObjectInputStream(connection.getInputStream());
				authorizeClient();
			}
		 } catch (IOException | ClassNotFoundException | SQLException e) {
				ServerFrame.writeMessageInStatusArea(" Error: " + e.getMessage());
		 }   	
	}

	public static ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	private static void authorizeClient() throws IOException, ClassNotFoundException, SQLException {
		SecurityCode code = getCodeForAuthorization();
		socketOut.writeObject(new CodeForAuthorization(code.getEncryptedCode()));
		CodeForAuthorization decryptedCode = (CodeForAuthorization) socketIn.readObject();
		if(decryptedCode.getCode().equalsIgnoreCase(code.getDecryptedCode())) {
		   socketOut.writeObject(new Authorization(" Connected successfully to server!", true));
		   new Thread(new ClientThred(pool.getConnection(), socketIn, socketOut)).start();
		} else {
		   socketOut.writeObject(new Authorization(" Connection to server was unsuccessfully!", false));
		}
	}

	private static SecurityCode getCodeForAuthorization() throws SQLException {
		String query = "SELECT * FROM Security_Codes WHERE code_id = 2";
		PreparedStatement statement = DatabaseConnection.prepareStatement(query);
		ResultSet result = statement.executeQuery(query);
		SecurityCode code = null;
			while (result.next()) {
			 code = new SecurityCode(result.getInt(1), result.getString(2), result.getString(3));
			}
		return code;
	}	
}
