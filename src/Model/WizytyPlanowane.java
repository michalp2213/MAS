package Model;

import java.util.ArrayList;

public class WizytyPlanowane implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents(int...args) {	
		
		String sql = "SELECT * FROM wizyty_planowane ORDER BY ";
				
		for (int i = 0; i < args.length; ++ i) {
			sql += args [i];
			if (i != args.length - 1)
				sql += ", ";
		}
		sql += ";";
	
		return Database.executeQuery(sql);
	}
	
	public boolean deleteItem (String id) {
		return Database.executeUpdate("DELETE FROM wizyty_planowane WHERE id_wizyty = " + id + ";") != 0;
	}
	
	public boolean updateItem (String id, String newPacjentId, String newLekarzId, String newCel, String newSpecjalizacja, String newDate, String newInt) {
		String sql = "UPDATE wizyty_planowane SET (id_pacjenta, id_lekarza, cel, specjalizacja, data, szacowany_czas) = ("
				+ newPacjentId + ", "
				+ newLekarzId + ", "
				+ newCel + ", "
				+ newSpecjalizacja + ", "
				+ "'" + newDate + "', "
				+ newInt + ") WHERE id_wizyty = " + id + ";";
		return Database.executeUpdate(sql) != 0;
	}

	public boolean insertItem (String pacjentId,  String cel, String specjalizacja, String date) {
		String sql = "INSERT INTO terminarz VALUES ("
				+ pacjentId + ", "
				+ "(SELECT id_celu FROM cele_wizyty WHERE nazwa = '" + cel + "'), "
				+ "(SELECT id_specjalizacji FROM specjalizacje WHERE nazwa = '" + specjalizacja + "'), "
				+ "'" + date + "');";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean moveToWizytyOdbyte (String id) {
		if (Database.executeUpdate("INSERT INTO wizyty_odbyte (id_pacjenta, id_lekarza, cel, specjalizacja, data, czas_trwania) "
				+ "SELECT id_pacjenta, id_lekarza, cel, specjalizacja, data, szacowany_czas as czas_trwania "
				+ "FROM wizyty_planowane WHERE id_wizyty = " + id + ";") == 0) {
			return false;
		}
		return this.deleteItem(id);
	}
	
	public boolean moveToWizytyOdbyte (String id, String interval) {
		Database.executeUpdate("UPDATE wizyty_planowane SET szacowany_czas = " + interval + "WHERE id_wizyty = " + id + ";");
		return this.moveToWizytyOdbyte(id);
	}

}
