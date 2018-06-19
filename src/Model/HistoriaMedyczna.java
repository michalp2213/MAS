package Model;

import java.sql.Date;
import java.util.ArrayList;

public class HistoriaMedyczna implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * FROM historia_medyczna;");
	}
	
	public boolean deleteItem (int pacjentId, int wydarzenieId, Date from) {
		String sql = "DELETE FROM historia_medyczna WHERE "
				+ "id_pacjenta = " + pacjentId + ","
				+ "id_wydarzenia = " + wydarzenieId + ","
				+ "od = '" + from + "';";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean updateItem (int pacjentId, int wydarzenieId, Date from,
			int newPacjentId, int newWydarzenieId, Integer newWizytaId, Date newFrom, Date newTo) {
		String sql = "UPDATE historia_medyczna SET ("
				+ "id_pacjenta, "
				+ "id_wydarzenia, "
				+ "od, "
				+ "do, "
				+ "wizyta) = ("
				+ newPacjentId + ", "
				+ newWydarzenieId + ", "
				+ "'" + newFrom + "', "
				+ "'" + newTo + "', "
				+ newWizytaId + ") WHERE "
				+ "id_pacjenta = " + pacjentId + " AND "
				+ "id_wydarzenia = " + wydarzenieId + " AND "
				+ "od = '" + from + "';";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (int pacjentId, int wydarzenieId, Integer wizytaId, Date from, Date to) {
		String sql = "INSERT INTO historia_medyczna VALUES ("
				+ pacjentId + ","
				+ wydarzenieId + ","
				+ wizytaId + ","
				+ "'" + from + "',"
				+ "'" + to + "');";
		return Database.executeUpdate(sql) != 0;
	}

}
