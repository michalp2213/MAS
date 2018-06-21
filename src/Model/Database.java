package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

	public static ArrayList <ArrayList <String>> executeQuery(String sql) throws SQLException {
		Statement stmt = null;
		try {
			stmt = dbConn.createStatement();
			ResultSet res = stmt.executeQuery (sql);
			ResultSetMetaData meta = res.getMetaData();
			
			ArrayList<ArrayList <String>> arr = new ArrayList<>();
			
			int numCols = meta.getColumnCount();
			
			arr.add (new ArrayList <String> ());
			for (int i = 1; i <= numCols; ++ i) {
				arr.get(0).add (meta.getColumnName(i));
			}
			int row = 1;
			
			while (res.next()) {
				arr.add (new ArrayList<>());
				for (int i = 1; i <= numCols; ++ i) {
					arr.get(row).add (res.getString(i));
				}
				++ row;
			}

			return arr;
		} finally {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
	}
	
	public static int executeUpdate(String sql) throws SQLException {
		Statement stmt = null;
		try {
			stmt = dbConn.createStatement();
			return stmt.executeUpdate(sql);
		} finally {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
	}
}
