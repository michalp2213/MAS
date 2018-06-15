package Model;

import java.sql.ResultSet;

public class LekarzeSpecjalizacje implements Table {

	@Override
	public ResultSet getContents() {
		return Database.executeQuery("SELECT * from lekarze_specjalizacje;");
	}
	
	public boolean deleteItem (String specjalizacjaName, int lekarzId) {
		String sql = "DELETE FROM lekarze_specjalizacje WHERE "
				+ "id_lekarza = " + lekarzId
				+ " AND (SELECT nazwa FROM specjalizacje WHERE "
					+ "id_specjalizacji = lekarze_specjalizacje.id_specjalizacji"
				+ ") = " + specjalizacjaName + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (String specjalizacjaName, int lekarzId) {
		String sql = "INSERT INTO lekarze_specjalizacje VALUES ("
				+ lekarzId + ","
				+ "(SELECT id_specjalizacji FROM specjalizacje WHERE "
					+ "nazwa = " + specjalizacjaName + "));";
		return false;
	}
	
	public ResultSet getSpecjalizacje (int lekarzId) {
		return Database.executeQuery("SELECT * FROM specjalizacje_lekarza(" + lekarzId + ");");
	}

}
