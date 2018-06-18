package Model;

import java.util.ArrayList;

public class WydarzeniaMedyczne implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * FROM wydarzenia_medyczne;");
	}
	
	public boolean deleteItem (String name) {
		return Database.executeUpdate("DELETE FROM wydarzenia_medyczne WHERE nazwa = '" + name + "';") != 0;
	}
	
	public boolean updateItem (String oldName, String newName) {
		return Database.executeUpdate("UPDATE wydarzenia_medyczne SET nazwa = " + newName + " WHERE nazwa = " + oldName + ";") != 0;
	}
	
	public boolean insertItem (String name) {
		String sql = "INSERT INTO wydarzenia_medyczne VALUES ("
				+ "DEFAULT, "
				+ "'" + name + "');";
		return Database.executeUpdate(sql) != 0;
	}

}
