package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class Pacjenci implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents(int... args) throws SQLException {	
		
		String sql = "SELECT * FROM pacjenci";
		
		if (args.length > 0)
			sql += " ORDER BY ";
				
		for (int i = 0; i < args.length; ++ i) {
			sql += args [i];
			if (i != args.length - 1)
				sql += ", ";
		}
		sql += ";";
	
		return Database.executeQuery(sql);
	}
	
	public boolean deleteItem (String id) throws SQLException {
		return Database.executeUpdate("DELETE FROM pacjenci WHERE id_pacjenta = " + id + ";") != 0;
	}
	
	public boolean updateItem (String id, String newName, String newSurname, String newPesel, String newPassportNo, String newBirthDate, String newSex) throws SQLException {
		String sql = "UPDATE pacjenci SET (imie, nazwisko, pesel, nr_paszportu, data_urodzenia, plec) = ("
				+ "'" + newName + "', "
				+ "'" + newSurname + "', "
				+ "'" + newPesel + "', "
				+ "'" + newPassportNo + "', "
				+ "'" + newBirthDate + "', "
				+ "'" + newSex + "') WHERE id_pacjenta = " + id + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (String name, String surname, String pesel, String passportNo, String birthDate, String sex) throws SQLException {
		String sql = "INSERT INTO pacjenci VALUES ("
				+ "DEFAULT, "
				+ "'" + name + "', "
				+ "'" + surname + "', "
				+ "'" + pesel + "', "
				+ "'" + passportNo + "', "
				+ "'" + birthDate + "', "
				+ "'" + sex + "');";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean setLPK (String pacjentId, String lekarzId) throws SQLException {
		String sql = "INSERT INTO pacjenci_lpk VALUES ("
				+ pacjentId + ","
				+ "now(),"
				+ lekarzId + ","
				+ "NULL);";
		return Database.executeUpdate(sql) != 0;
	}

}
