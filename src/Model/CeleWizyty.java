package Model;

import java.util.ArrayList;

public class CeleWizyty implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents(int... args) {
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
	
	public boolean deleteItem (String name) {
		return Database.executeUpdate("DELETE FROM cele_wizyty WHERE nazwa = '" + name + "';") != 0;
	}
	
	public boolean updateItem (String oldName, String newName) {
		return Database.executeUpdate("UPDATE cele_wizyty SET nazwa = '" + newName + "' WHERE nazwa = '" + oldName + "';") != 0;
	}
	
	public boolean insertItem (String name) {
		return Database.executeUpdate("INSERT INTO cele_wizyty VALUES (DEFAULT, '" + name + "');") != 0;
	}

}
