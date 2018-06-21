package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class WizytyPlanowane implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents(int...args) throws SQLException {	
		
		String sql = "SELECT * FROM wizyty_planowane";
		
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

	public ArrayList<ArrayList<String>> niceGetContents(int...args) throws SQLException {	
		
		String sql = "SELECT w.id_wizyty, "
				+ "(p.imie || ' ' || p.nazwisko || ' (' || p.id_pacjenta || ')') AS pacjent, "
				+ "(l.imie || ' ' || l.nazwisko || ' (' || l.id_pracownika || ')') AS lekarz, "
				+ "(SELECT nazwa FROM cele_wizyty WHERE id_celu = w.cel) AS cel, "
				+ "(SELECT nazwa FROM specjalizacje WHERE id_specjalizacji = w.specjalizacja) AS specjalizacja, "
				+ "data, "
				+ "szacowany_czas FROM wizyty_planowane w natural join pacjenci p join pracownicy l on w.id_lekarza = l.id_pracownika";
		
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
		return Database.executeUpdate("DELETE FROM wizyty_planowane WHERE id_wizyty = " + id + ";") != 0;
	}
	
	public boolean updateItem (String id, String newPacjentId, String newLekarzId, String newCel, String newSpecjalizacja, String newDate, String newInt) throws SQLException {
		String sql = "UPDATE wizyty_planowane SET (id_pacjenta, id_lekarza, cel, specjalizacja, data, szacowany_czas) = ("
				+ newPacjentId + ", "
				+ newLekarzId + ", "
				+ newCel + ", "
				+ newSpecjalizacja + ", "
				+ Tables.nullCheck(newDate) + ", '"
				+ newInt + "') WHERE id_wizyty = " + id + ";";
		return Database.executeUpdate(sql) != 0;
	}

	public boolean insertItem (String pacjentId,  String cel, String specjalizacja, String date) throws SQLException {
		String sql = "INSERT INTO terminarz VALUES ("
				+ pacjentId + ", "
				+ "(SELECT id_celu FROM cele_wizyty WHERE nazwa = " + Tables.nullCheck(cel) + "), "
				+ "(SELECT id_specjalizacji FROM specjalizacje WHERE nazwa = " + Tables.nullCheck(specjalizacja) + "), "
				+ Tables.nullCheck(date) + ");";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean moveToWizytyOdbyte (String id) throws SQLException {
		if (Database.executeUpdate("INSERT INTO wizyty_odbyte (id_pacjenta, id_lekarza, cel, specjalizacja, data, czas_trwania) "
				+ "SELECT id_pacjenta, id_lekarza, cel, specjalizacja, data, szacowany_czas as czas_trwania "
				+ "FROM wizyty_planowane WHERE id_wizyty = " + id + ";") == 0) {
			return false;
		}
		return this.deleteItem(id);
	}
	
	public boolean moveToWizytyOdbyte (String id, String interval) throws SQLException {
		Database.executeUpdate("UPDATE wizyty_planowane SET szacowany_czas = " + Tables.nullCheck(interval) + " WHERE id_wizyty = " + id + ";");
		return this.moveToWizytyOdbyte(id);
	}

}
