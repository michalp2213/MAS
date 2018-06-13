package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

public class Database {
	Connection dbConn = null;
	String addr;

	public Database(String serverAddr) {
		addr = serverAddr;
	}

	public void connect(String uname, String passwd) throws IOException {
		try {
			Class.forName("org.postgresql.Driver");
			dbConn = DriverManager.getConnection(addr, uname, passwd);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			throw new IOException(e);
		}
	}

	public void disconnect() throws IOException {
		try {
			dbConn.close();
			dbConn = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			throw new IOException(e);
		}
	}

	public ResultSet executeQuery(String sql) {
		Statement stmt = null;
		try {
			stmt = dbConn.createStatement();
			if (stmt.execute(sql)) {
				return stmt.getResultSet();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
		return null;
	}
}
