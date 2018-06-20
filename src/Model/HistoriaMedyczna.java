package Model;

import java.util.ArrayList;

public class HistoriaMedyczna implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents(int... args) {		
		String sql = "SELECT * FROM historia_medyczna ORDER BY ";
				
		for (int i = 0; i < args.length; ++ i) {
			sql += args [i];
			if (i != args.length - 1)
				sql += ", ";
		}
		sql += ";";
	
		return Database.executeQuery(sql);
	}
	
	public boolean deleteItem (String pacjentId, String wydarzenieId, String from) {
		String sql = "DELETE FROM historia_medyczna WHERE "
				+ "id_pacjenta = " + pacjentId + ","
				+ "id_wydarzenia = " + wydarzenieId + ","
				+ "od = '" + from + "';";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean updateItem (String pacjentId, String wydarzenieId, String from,
			String newPacjentId, String newWydarzenieId, String newWizytaId, String newFrom, String newTo) {
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
	
	public boolean insertItem (String pacjentId, String wydarzenieId, String wizytaId, String from, String to) {
		String sql = "INSERT INTO historia_medyczna VALUES ("
				+ pacjentId + ","
				+ wydarzenieId + ","
				+ wizytaId + ","
				+ "'" + from + "',"
				+ "'" + to + "');";
		return Database.executeUpdate(sql) != 0;
	}

}
