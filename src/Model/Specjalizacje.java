package Model;

import java.util.ArrayList;

public class Specjalizacje implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * FROM specjalizacje;");
	}
	
	public boolean deleteItem (String name) {
		return Database.executeUpdate("DELETE FROM specjalizacje WHERE nazwa = '" + name + "';") != 0;
	}
	
	public boolean updateItem (String oldName, String newName) {
		return Database.executeUpdate("UPDATE specjalizacje SET nazwa = '" + newName + "' WHERE nazwa = '" + oldName + "';") != 0;
	}
	
	public boolean insertItem (String name) {
		String sql = "INSERT INTO specjalizacje VALUES ("
				+ "DEFAULT, "
				+ "'" + name + "');";
		return Database.executeUpdate(sql) != 0;
	}

}
