package Model;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
	Connection dbConn = null;
	String addr;
	
	public Database (String serverAddr) {
		addr = serverAddr;
	}
	
	public void connect (String uname, String passwd) {
		//TODO
	}
	
	public void disconnect () {
		//TODO
	}
	
	public void executeQuery (String sql) {
		//TODO
	}
}
