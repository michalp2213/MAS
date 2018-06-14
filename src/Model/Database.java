package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

public class Database {
	private static Connection dbConn = null;
	private static String addr;

	public static void setServerAddr(String serverAddr) {
		addr = serverAddr;
	}

	public static void connect(String uname, String passwd) throws IOException {
		try {
			Class.forName("org.postgresql.Driver");
			dbConn = DriverManager.getConnection(addr, uname, passwd);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			throw new IOException(e);
		}
	}

	public static void disconnect() {
		try {
			dbConn.close();
			dbConn = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public static ResultSet executeQuery(String sql) {
		Statement stmt = null;
		try {
			stmt = dbConn.createStatement();
			return stmt.executeQuery (sql);
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
	
	public static int executeUpdate(String sql) {
		Statement stmt = null;
		try {
			stmt = dbConn.createStatement();
			return stmt.executeUpdate(sql);
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
		return 0;
	}
}
