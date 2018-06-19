package Model;

import java.sql.Date;
import java.util.ArrayList;

public class AnkietyLekarze implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * FROM ankiety_lekarze;");
	}
	
	public boolean deleteItem (int id) {
		return Database.executeUpdate("DELETE FROM ankiety_lekarze WHERE id_ankiety = " + id + ";") != 0;
	}
	
	public boolean updateItem (int id, int newLekarzId, Date newDate, Integer newUprzejmosc, Integer newOpanowanie, Integer newInformacyjnosc, Integer newDokladnosc_badan) {
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
	
	public boolean insertItem (int lekarzId, Date date, Integer uprzejmosc, Integer opanowanie, Integer informacyjnosc, Integer dokladnosc_badan) {
		String sql = "INSERT INTO ankiety_lekarze(id_lekarza, data, uprzejmosc, opanowanie, informacyjnosc, dokladnosc_badan) VALUES ("
				+ lekarzId + ","
				+ "'" + date + "',"
				+ uprzejmosc + ","
				+ opanowanie + ","
				+ informacyjnosc + ","
				+ dokladnosc_badan + ");";
		return Database.executeUpdate(sql) != 0;
	}
	
	public ArrayList<ArrayList<String>> alphabeticRanking (Date from, Date to, int specjalizacjaId) {
		return Database.executeQuery("SELECT * from ranking_alfabetyczny ('" + from + "'::date, '" + to + "'::date, " + specjalizacjaId + ");");
	}
	
	public ArrayList<ArrayList<String>> bestIn (Date from, Date to, String cecha) {
		return Database.executeQuery("SELECT * from ranking_cecha ('" + from + "', '" + to + "', " + cecha + ");");
	}
	
	public ArrayList<ArrayList<String>> bestAvg (Date from, Date to, int specjalizacjaId) {
		return Database.executeQuery("SELECT * from ranking_specjalizacje ('" + from + "', '" + to + "', " + specjalizacjaId + ");");
	}

}
