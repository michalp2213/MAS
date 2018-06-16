package Model;

import java.sql.ResultSet;

public class Role implements Table {

	@Override
	public ResultSet getContents () {
		return Database.executeQuery("SELECT * FROM role;");
	}
	
	public boolean deleteItem (String name) {
		return Database.executeUpdate("DELETE FROM role WHERE nazwa = '" + name + "';") != 0;
	}
		
	public boolean insertItem (String name) {
		String sql = "INSERT INTO role VALUES ("
				+ "DEFAULT, "
				+ "'" + name + "');";
		return Database.executeUpdate(sql) != 0;
	}
}
