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
	
	public boolean deleteItem (String id) throws SQLException {
		return Database.executeUpdate("DELETE FROM ankiety_lekarze WHERE id_ankiety = " + id + ";") != 0;
	}
	
	public boolean updateItem (String id, String newLekarzId, String newDate, String newUprzejmosc, String newOpanowanie, String newInformacyjnosc, String newDokladnosc_badan) throws SQLException {
		String strNewDate = newDate == null ? "NULL" : "'" + newDate + "'";
		String sql = "UPDATE ankiety_lekarze SET "
				+ "(id_lekarza, data, uprzejmosc, opanowanie, informacyjnosc, dokladnosc_badan) = ("
				+ newLekarzId + ", "
				+ strNewDate + ", "
				+ newUprzejmosc + ", "
				+ newOpanowanie + ", "
				+ newInformacyjnosc + ", "
				+ newDokladnosc_badan + ") "
				+ "WHERE id_ankiety = " + id + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (String lekarzId, String date, String uprzejmosc, String opanowanie, String informacyjnosc, String dokladnosc_badan) throws SQLException {
		String strDate = date == null ? "NULL" : "'" + date + "'";
		String sql = "INSERT INTO ankiety_lekarze(id_lekarza, data, uprzejmosc, opanowanie, informacyjnosc, dokladnosc_badan) VALUES ("
				+ lekarzId + ","
				+  strDate + ","
				+ uprzejmosc + ","
				+ opanowanie + ","
				+ informacyjnosc + ","
				+ dokladnosc_badan + ");";
		return Database.executeUpdate(sql) != 0;
	}
	
	public ArrayList<ArrayList<String>> alphabeticRanking (String from, String to) throws SQLException {
		String fromStr = from == null ? "NULL" : "'" + from + "'";
		String toStr = to == null ? "NULL" : "'" + to + "'";
		return Database.executeQuery("SELECT * from ranking_alfabetyczny (" + fromStr + ", " + toStr + ");");
	}
	
	public ArrayList<ArrayList<String>> bestIn (String from, String to, String cecha) throws SQLException {
		String fromStr = from == null ? "NULL" : "'" + from + "'";
		String toStr = to == null ? "NULL" : "'" + to + "'";
		String cechaStr = cecha == null ? "NULL" : "'" + cecha + "'";
		return Database.executeQuery("SELECT * from ranking_cecha (" + fromStr + ", " + toStr + ", " + cechaStr + ");");
	}
	
	public ArrayList<ArrayList<String>> bestAvg (String from, String to, String specjalizacjaId) throws SQLException {
		String fromStr = from == null ? "NULL" : "'" + from + "'";
		String toStr = to == null ? "NULL" : "'" + to + "'";
		return Database.executeQuery("SELECT * from ranking_specjalizacje (" + fromStr + ", " + toStr + ", " + specjalizacjaId + ");");
	}

}
