package Model;

import java.sql.Date;
import java.sql.ResultSet;

public class Pacjenci implements Table {

	@Override
	public ResultSet getContents() {
		return Database.executeQuery("SELECT * from pacjenci;");
	}
	
	public boolean deleteItem (int id) {
		return Database.executeUpdate("DELETE FROM pacjenci WHERE id_pacjenta = " + id + ";") != 0;
	}
	
	public boolean insertItem (String name, String surname, String pesel, String passportNo, Date birthDate, String sex) {
		String sql = "INSERT INTO pacjenci VALUES ("
				+ "DEFAULT, "
				+ "'" + name + "', "
				+ "'" + surname + "', "
				+ "'" + pesel + "', "
				+ "'" + passportNo + "', "
				+ birthDate + ", "
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
