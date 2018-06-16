package Model;

import java.sql.ResultSet;

public class Pracownicy implements Table {

	@Override
	public ResultSet getContents() {
		return Database.executeQuery("SELECT * FROM pracownicy;");
	}

	public boolean deleteItem (int id) {
		return Database.executeUpdate("DELETE FROM pracownicy WHERE id_pracownika = " + id + ";") != 0;
	}
		
	public boolean insertItem (String name, String surname, String pesel) {
		String sql = "INSERT INTO pracownicy VALUES ("
				+ "DEFAULT, "
				+ "'" + name + "', "
				+ "'" + surname + "', "
				+ "'" + pesel + "');";
		return Database.executeUpdate(sql) != 0;
	}
}
