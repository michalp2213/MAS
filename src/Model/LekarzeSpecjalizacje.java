package Model;

import java.util.ArrayList;

public class LekarzeSpecjalizacje implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * from lekarze_specjalizacje;");
	}
	
	public boolean deleteItem (String specjalizacjaName, String lekarzId) {
		String sql = "DELETE FROM lekarze_specjalizacje WHERE "
				+ "id_lekarza = " + lekarzId
				+ " AND (SELECT nazwa FROM specjalizacje WHERE "
					+ "id_specjalizacji = lekarze_specjalizacje.id_specjalizacji"
				+ ") = '" + specjalizacjaName + "';";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean updateItem (String oldSpecId, String oldLekarzId, String newSpecId, String newLekarzId) {
		String sql = "UPDATE lekarze_specjalizacje SET id_specjalizacji = " + newSpecId
				+ ", id_lekarza = " + newLekarzId + " WHERE id_specjalizacji = " + oldSpecId
				+ " AND id_lekarza = " + oldLekarzId + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (String specjalizacjaName, String lekarzId) {
		String sql = "INSERT INTO lekarze_specjalizacje VALUES ("
				+ lekarzId + ","
				+ "(SELECT id_specjalizacji FROM specjalizacje WHERE "
					+ "nazwa = '" + specjalizacjaName + "'));";
		return Database.executeUpdate(sql) != 0;
	}
	
	public ArrayList<ArrayList<String>> getSpecjalizacje (String lekarzId) {
		return Database.executeQuery("SELECT * FROM specjalizacje_lekarza(" + lekarzId + ");");
	}

}
