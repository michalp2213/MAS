package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class HistoriaMedyczna implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents(int... args) throws SQLException {		
		String sql = "SELECT * FROM historia_medyczna";
		
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
	
	public boolean deleteItem (String pacjentId, String wydarzenieId, String from) throws SQLException {
		String fromStr = from == null ? "NULL" : "'" + from + "'";
		String sql = "DELETE FROM historia_medyczna WHERE "
				+ "id_pacjenta = " + pacjentId + ","
				+ "id_wydarzenia = " + wydarzenieId + ","
				+ "od = " + fromStr + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean updateItem (String pacjentId, String wydarzenieId, String from,
			String newPacjentId, String newWydarzenieId, String newWizytaId, String newFrom, String newTo) throws SQLException {
		String fromStr = from == null ? "NULL" : "'" + from + "'";
		String newFromStr = newFrom == null ? "NULL" : "'" + newFrom + "'";
		String newToStr = newTo == null ? "NULL" : "'" + newTo + "'";
		String sql = "UPDATE historia_medyczna SET ("
				+ "id_pacjenta, "
				+ "id_wydarzenia, "
				+ "od, "
				+ "do, "
				+ "wizyta) = ("
				+ newPacjentId + ", "
				+ newWydarzenieId + ", "
				+ newFromStr + ", "
				+ newToStr + ", "
				+ newWizytaId + ") WHERE "
				+ "id_pacjenta = " + pacjentId + " AND "
				+ "id_wydarzenia = " + wydarzenieId + " AND "
				+ "od = " + fromStr + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (String pacjentId, String wydarzenieId, String wizytaId, String from, String to) throws SQLException {
		String fromStr = from == null ? "NULL" : "'" + from + "'";
		String toStr = to == null ? "NULL" : "'" + to + "'";
		String sql = "INSERT INTO historia_medyczna VALUES ("
				+ pacjentId + ","
				+ wydarzenieId + ","
				+ wizytaId + ","
				+ fromStr + ","
				+ toStr + ");";
		return Database.executeUpdate(sql) != 0;
	}

}
