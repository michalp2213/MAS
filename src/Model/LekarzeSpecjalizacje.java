package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class LekarzeSpecjalizacje implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents(int... args) throws SQLException {	

		String sql = "SELECT * FROM lekarze_specjalizacje";
		
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
	
	public ArrayList<ArrayList<String>> niceGetContents(int... args) throws SQLException {	

		String sql = "SELECT (p.imie || ' ' || p.nazwisko || ' (' || p.id_pracownika || ')') AS lekarz, "
				+ "string_agg(s.nazwa, ', ') AS specjalizacje "
				+ "FROM lekarze_specjalizacje ls natural join specjalizacje s join pracownicy p on ls.id_lekarza = p.id_pracownika "
				+ "GROUP BY p.id_pracownika";
		
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
	
	public boolean deleteItem (String specjalizacjaName, String lekarzId) throws SQLException {
		String specjalizacjaStr = specjalizacjaName == null ? "NULL" : "'" + specjalizacjaName + "'";
		String sql = "DELETE FROM lekarze_specjalizacje WHERE "
				+ "id_lekarza = " + lekarzId
				+ " AND (SELECT nazwa FROM specjalizacje WHERE "
					+ "id_specjalizacji = lekarze_specjalizacje.id_specjalizacji"
				+ ") = " + specjalizacjaStr + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean updateItem (String oldSpecId, String oldLekarzId, String newSpecId, String newLekarzId) throws SQLException {
		String sql = "UPDATE lekarze_specjalizacje SET id_specjalizacji = " + newSpecId
				+ ", id_lekarza = " + newLekarzId + " WHERE id_specjalizacji = " + oldSpecId
				+ " AND id_lekarza = " + oldLekarzId + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (String specjalizacjaName, String lekarzId) throws SQLException {
		String sql = "INSERT INTO lekarze_specjalizacje VALUES ("
				+ lekarzId + ","
				+ "(SELECT id_specjalizacji FROM specjalizacje WHERE "
					+ "nazwa = " + Tables.nullCheck(specjalizacjaName) + "));";
		return Database.executeUpdate(sql) != 0;
	}
	
	public ArrayList<ArrayList<String>> getSpecjalizacje (String lekarzId) throws SQLException {
		return Database.executeQuery("SELECT * FROM specjalizacje_lekarza(" + lekarzId + ");");
	}

}
