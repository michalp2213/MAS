package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class Specjalizacje implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents(int...args) throws SQLException {	

		String sql = "SELECT * FROM specjalizacje";
		
		if (args.length > 0)
			sql += " ORDER BY ";
				
		for (int i = 0; i < args.length; ++ i) {
			sql += args [i];
			if (i != args.length - 1)
				sql += ", ";
		}
		sql += ";";
	
		return Database.executeQuery(sql);
	}
	
	public boolean deleteItem (String name) throws SQLException {
		return Database.executeUpdate("DELETE FROM specjalizacje WHERE nazwa = " + Tables.nullCheck(name) + ";") != 0;
	}
	
	public boolean updateItem (String oldName, String newName) throws SQLException {
		return Database.executeUpdate("UPDATE specjalizacje SET nazwa = " + Tables.nullCheck(newName) 
					+ " WHERE nazwa = " + Tables.nullCheck(oldName) + ";") != 0;
	}
	
	public boolean insertItem (String name) throws SQLException {
		String sql = "INSERT INTO specjalizacje VALUES ("
				+ "DEFAULT, "
				+ Tables.nullCheck(name) + ");";
		return Database.executeUpdate(sql) != 0;
	}

}
