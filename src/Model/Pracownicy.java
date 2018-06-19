package Model;

import java.sql.Date;
import java.util.ArrayList;

public class Pracownicy implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * FROM pracownicy;");
	}

	public boolean deleteItem (int id) {
		return Database.executeUpdate("DELETE FROM pracownicy WHERE id_pracownika = " + id + ";") != 0;
	}
	
	public boolean updateItem (int id, String newName, String newSurname, String newPesel, Date newZatrudniony_od, Date newZatrudniony_do) {
		String zatrudniony_doStr = newZatrudniony_do == null ? "NULL" : "'" + newZatrudniony_do + "'";
		String sql = "UPDATE pracownicy SET (imie, nazwisko, pesel, zatrudniony_od, zatrudniony_do) = ("
				+ "'" + newName + "', "
				+ "'" + newSurname + "', "
				+ "'" + newPesel + "', " 
				+ "'" + newZatrudniony_od + "', " 
				+ zatrudniony_doStr + ") WHERE id_pracownika = " + id + ";";
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
