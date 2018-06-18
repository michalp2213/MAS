package Model;

import java.util.ArrayList;

public class CeleWizyty implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * FROM cele_wizyty;");
	}
	
	public boolean deleteItem (String name) {
		return Database.executeUpdate("DELETE FROM cele_wizyty WHERE nazwa = '" + name + "';") != 0;
	}
	
	public boolean updateItem (String oldName, String newName) {
		return Database.executeUpdate("UPDATE cele_wizyty SET nazwa = " + newName + " WHERE nazwa = " + oldName + ";") != 0;
	}
	
	public boolean insertItem (String name) {
		return Database.executeUpdate("INSERT INTO cele_wizyty VALUES (DEFAULT, '" + name + "');") != 0;
	}

}
