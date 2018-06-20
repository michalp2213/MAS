package Model;

import java.sql.Date;
import java.util.ArrayList;

public class AnkietyLekarze implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * FROM ankiety_lekarze;");
	}
	
	public boolean deleteItem (String id) {
		return Database.executeUpdate("DELETE FROM ankiety_lekarze WHERE id_ankiety = " + id + ";") != 0;
	}
	
	public boolean updateItem (String id, String newLekarzId, String newDate, String newUprzejmosc, String newOpanowanie, String newInformacyjnosc, String newDokladnosc_badan) {
		String sql = "UPDATE ankiety_lekarze SET "
				+ "(id_lekarza, data, uprzejmosc, opanowanie, informacyjnosc, dokladnosc_badan) = ("
				+ newLekarzId + ", "
				+ "'" + newDate + "', "
				+ newUprzejmosc + ", "
				+ newOpanowanie + ", "
				+ newInformacyjnosc + ", "
				+ newDokladnosc_badan + ") "
				+ "WHERE id_ankiety = " + id + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (String lekarzId, String date, String uprzejmosc, String opanowanie, String informacyjnosc, String dokladnosc_badan) {
		String sql = "INSERT INTO ankiety_lekarze(id_lekarza, data, uprzejmosc, opanowanie, informacyjnosc, dokladnosc_badan) VALUES ("
				+ lekarzId + ","
				+ "'" + date + "',"
				+ uprzejmosc + ","
				+ opanowanie + ","
				+ informacyjnosc + ","
				+ dokladnosc_badan + ");";
		return Database.executeUpdate(sql) != 0;
	}
	
	public ArrayList<ArrayList<String>> alphabeticRanking (String from, String to) {
		String fromStr = from == null ? "NULL" : "'" + from + "'";
		String toStr = to == null ? "NULL" : "'" + to + "'";
		return Database.executeQuery("SELECT * from ranking_alfabetyczny (" + fromStr + ", " + toStr + ");");
	}
	
	public ArrayList<ArrayList<String>> bestIn (String from, String to, String cecha) {
		String fromStr = from == null ? "NULL" : "'" + from + "'";
		String toStr = to == null ? "NULL" : "'" + to + "'";
		return Database.executeQuery("SELECT * from ranking_cecha (" + fromStr + ", " + toStr + ", '" + cecha + "');");
	}
	
	public ArrayList<ArrayList<String>> bestAvg (String from, String to, String specjalizacjaId) {
		String fromStr = from == null ? "NULL" : "'" + from + "'";
		String toStr = to == null ? "NULL" : "'" + to + "'";
		return Database.executeQuery("SELECT * from ranking_specjalizacje (" + fromStr + ", " + toStr + ", " + specjalizacjaId + ");");
	}

}
