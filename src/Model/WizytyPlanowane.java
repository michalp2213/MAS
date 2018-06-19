package Model;

import java.sql.Date;
import java.util.ArrayList;

import org.postgresql.util.PGInterval;

public class WizytyPlanowane implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * FROM wizyty_planowane;");
	}
	
	public boolean deleteItem (int id) {
		return Database.executeUpdate("DELETE FROM wizyty_planowane WHERE id_wizyty = " + id + ";") != 0;
	}
	
	public boolean updateItem (int id, int newPacjentId, int newLekarzId, int newCel, int newSpecjalizacja, Date newDate, PGInterval newInt) {
		String sql = "UPDATE wizyty_planowane SET (id_pacjenta, id_lekarza, cel, specjalizacja, data, szacowany_czas) = ("
				+ newPacjentId + ", "
				+ newLekarzId + ", "
				+ newCel + ", "
				+ newSpecjalizacja + ", "
				+ "'" + newDate + "', "
				+ newInt + ") WHERE id_wizyty = " + id + ";";
		return Database.executeUpdate(sql) != 0;
	}

	public boolean insertItem (int pacjentId,  String cel, String specjalizacja, Date date) {
		String sql = "INSERT INTO terminarz VALUES ("
				+ pacjentId + ", "
				+ "(SELECT id_celu FROM cele_wizyty WHERE nazwa = '" + cel + "'), "
				+ "(SELECT id_specjalizacji FROM specjalizacje WHERE nazwa = '" + specjalizacja + "'), "
				+ "'" + date + "');";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean moveToWizytyOdbyte (int id) {
		if (Database.executeUpdate("INSERT INTO wizyty_odbyte (id_pacjenta, id_lekarza, cel, specjalizacja, data, czas_trwania) "
				+ "SELECT id_pacjenta, id_lekarza, cel, specjalizacja, data, szacowany_czas as czas_trwania "
				+ "FROM wizyty_planowane WHERE id_wizyty = " + id + ";") == 0) {
			return false;
		}
		return this.deleteItem(id);
	}
	
	public boolean moveToWizytyOdbyte (int id, PGInterval interval) {
		Database.executeUpdate("UPDATE wizyty_planowane SET szacowany_czas = " + interval + "WHERE id_wizyty = " + id + ";");
		return this.moveToWizytyOdbyte(id);
	}

}
