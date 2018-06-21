package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class CeleWizyty implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents(int... args) throws SQLException {
		String sql = "SELECT * FROM cele_wizyty";
		
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
		return Database.executeUpdate("DELETE FROM cele_wizyty WHERE nazwa = " + Tables.nullCheck(name) + ";") != 0;
	}
	
	public boolean updateItem (String oldName, String newName) throws SQLException {
		return Database.executeUpdate("UPDATE cele_wizyty SET nazwa = " 
						+ Tables.nullCheck(newName) 
						+ " WHERE nazwa = " + Tables.nullCheck(oldName) + ";") != 0;
	}
	
	public boolean insertItem (String name) throws SQLException {
		return Database.executeUpdate("INSERT INTO cele_wizyty VALUES (DEFAULT, " + Tables.nullCheck(name) + ");") != 0;
	}

}
