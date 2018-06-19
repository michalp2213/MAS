package Model;

import java.util.ArrayList;

public class Role implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents () {
		return Database.executeQuery("SELECT * FROM role;");
	}
	
	public boolean deleteItem (String name) {
		return Database.executeUpdate("DELETE FROM role WHERE nazwa = '" + name + "';") != 0;
	}
	
	public boolean updateItem (String oldName, String newName) {
		return Database.executeUpdate("UPDATE role SET nazwa = '" + newName + "' WHERE nazwa = '" + oldName + "';") != 0;
	}
		
	public boolean insertItem (String name) {
		String sql = "INSERT INTO role VALUES ("
				+ "DEFAULT, "
				+ "'" + name + "');";
		return Database.executeUpdate(sql) != 0;
	}
}
