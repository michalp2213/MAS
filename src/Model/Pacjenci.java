package Model;

import java.sql.Date;
import java.util.ArrayList;

public class Pacjenci implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * from pacjenci;");
	}
	
	public boolean deleteItem (int id) {
		return Database.executeUpdate("DELETE FROM pacjenci WHERE id_pacjenta = " + id + ";") != 0;
	}
	
	public boolean updateItem (int id, String newName, String newSurname, String newPesel, String newPassportNo, Date newBirthDate, String newSex) {
		String sql = "UPDATE pacjenci SET (imie, nazwisko, pesel, nr_paszportu, data_urodzenia, plec) = ("
				+ "'" + newName + "', "
				+ "'" + newSurname + "', "
				+ "'" + newPesel + "', "
				+ "'" + newPassportNo + "', "
				+ "'" + newBirthDate + "', "
				+ "'" + newSex + "') WHERE id_pacjenta = " + id + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (String name, String surname, String pesel, String passportNo, Date birthDate, String sex) {
		String sql = "INSERT INTO pacjenci VALUES ("
				+ "DEFAULT, "
				+ "'" + name + "', "
				+ "'" + surname + "', "
				+ "'" + pesel + "', "
				+ "'" + passportNo + "', "
				+ "'" + birthDate + "', "
				+ sex + ");";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean setLPK (int pacjentId, int lekarzId) {
		String sql = "INSERT INTO pacjenci_lpk VALUES ("
				+ pacjentId + ","
				+ "now(),"
				+ lekarzId + ","
				+ "NULL);";
		return Database.executeUpdate(sql) != 0;
	}

}
