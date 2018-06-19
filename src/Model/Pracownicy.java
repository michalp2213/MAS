package Model;

import java.util.ArrayList;

public class Pracownicy implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * FROM pracownicy;");
	}

	public boolean deleteItem (int id) {
		return Database.executeUpdate("DELETE FROM pracownicy WHERE id_pracownika = " + id + ";") != 0;
	}
	
	public boolean updateItem (int id, String newName, String newSurname, String newPesel) {
		String sql = "UPDATE pracownicy SET (imie, nazwisko, pesel) = ("
				+ "'" + newName + "', "
				+ "'" + newSurname + "', "
				+ "'" + newPesel + "') WHERE id_pracownika = " + id + ";";
		return Database.executeUpdate(sql) != 0;
	}
		
	public boolean insertItem (String name, String surname, String pesel) {
		String sql = "INSERT INTO pracownicy VALUES ("
				+ "DEFAULT, "
				+ "'" + name + "', "
				+ "'" + surname + "', "
				+ "'" + pesel + "', "
				+ "now(), "
				+ "NULL);";
		return Database.executeUpdate(sql) != 0;
	}
}
