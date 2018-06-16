package Model;

import java.sql.ResultSet;

public class Specjalizacje implements Table {

	@Override
	public ResultSet getContents() {
		return Database.executeQuery("SELECT * FROM specjalizacje;");
	}
	
	public boolean deleteItem (String name) {
		return Database.executeUpdate("DELETE FROM specjalizacje WHERE nazwa = '" + name + "';") != 0;
	}
	
	public boolean insertItem (String name) {
		String sql = "INSERT INTO specjalizacje VALUES ("
				+ "DEFAULT, "
				+ "'" + name + "');";
		return Database.executeUpdate(sql) != 0;
	}

}
