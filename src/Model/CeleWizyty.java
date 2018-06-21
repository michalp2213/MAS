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
		String nameStr = name == null ? "NULL" : "'" + name + "'";
		return Database.executeUpdate("DELETE FROM cele_wizyty WHERE nazwa = " + nameStr + ";") != 0;
	}
	
	public boolean updateItem (String oldName, String newName) throws SQLException {
		String oldNameStr = oldName == null ? "NULL" : "'" + oldName + "'";
		String newNameStr = newName == null ? "NULL" : "'" + newName + "'";
		return Database.executeUpdate("UPDATE cele_wizyty SET nazwa = " + newNameStr + " WHERE nazwa = " + oldNameStr + ";") != 0;
	}
	
	public boolean insertItem (String name) throws SQLException {
		String nameStr = name == null ? "NULL" : "'" + name + "'";
		return Database.executeUpdate("INSERT INTO cele_wizyty VALUES (DEFAULT, " + nameStr + ");") != 0;
	}

}
