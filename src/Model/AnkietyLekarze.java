package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class AnkietyLekarze implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents(int... args) throws SQLException {
		String sql = "SELECT * FROM ankiety_lekarze";
		
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
		String sql = "SELECT id_ankiety, "
				+ "(SELECT imie || ' ' || nazwisko || ' (' || id_pracownika || ')' FROM pracownicy "
					+ "WHERE id_pracownika = ankiety_lekarze.id_lekarza) AS lekarz, "
				+ "data, "
				+ "uprzejmosc, "
				+ "opanowanie, "
				+ "informacyjnosc, "
				+ "dokladnosc_badan FROM ankiety_lekarze";
			
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
		return Database.executeUpdate("DELETE FROM ankiety_lekarze WHERE id_ankiety = " + id + ";") != 0;
	}
	
	public boolean updateItem (String id, String newLekarzId, String newDate, String newUprzejmosc, String newOpanowanie, String newInformacyjnosc, String newDokladnosc_badan) throws SQLException {
		String sql = "UPDATE ankiety_lekarze SET "
				+ "(id_lekarza, data, uprzejmosc, opanowanie, informacyjnosc, dokladnosc_badan) = ("
				+ newLekarzId + ", "
				+ Tables.nullCheck(newDate) + ", "
				+ newUprzejmosc + ", "
				+ newOpanowanie + ", "
				+ newInformacyjnosc + ", "
				+ newDokladnosc_badan + ") "
				+ "WHERE id_ankiety = " + id + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (String lekarzId, String date, String uprzejmosc, String opanowanie, String informacyjnosc, String dokladnosc_badan) throws SQLException {
		String sql = "INSERT INTO ankiety_lekarze(id_lekarza, data, uprzejmosc, opanowanie, informacyjnosc, dokladnosc_badan) VALUES ("
				+ lekarzId + ","
				+ Tables.nullCheck(date) + ","
				+ uprzejmosc + ","
				+ opanowanie + ","
				+ informacyjnosc + ","
				+ dokladnosc_badan + ");";
		return Database.executeUpdate(sql) != 0;
	}
	
	public ArrayList<ArrayList<String>> alphabeticRanking (String from, String to) throws SQLException {
		return Database.executeQuery("SELECT * from ranking_alfabetyczny (" + Tables.nullCheck(from) + ", " + Tables.nullCheck(to) + ");");
	}
	
	public ArrayList<ArrayList<String>> bestIn (String from, String to, String cecha) throws SQLException {
		return Database.executeQuery("SELECT * from ranking_cecha (" 
					+ Tables.nullCheck(from) + ", " 
					+ Tables.nullCheck(to) + ", " 
					+ Tables.nullCheck(cecha) + ");");
	}
	
	public ArrayList<ArrayList<String>> bestAvg (String from, String to, String specjalizacjaId) throws SQLException {
		return Database.executeQuery("SELECT * from ranking_specjalizacje (" 
						+ Tables.nullCheck(from) + ", " 
						+ Tables.nullCheck(to) + ", " 
						+ specjalizacjaId + ");");
	}

}
