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
	
	public ArrayList<ArrayList<String>> niceGetContents(int... args) throws SQLException {		
		String sql = "SELECT (imie || ' ' || nazwisko || ' (' || p.id_pacjenta || ')') AS pacjent,"
				+ "w.nazwa AS wydarzenie, "
				+ "wizyta, "
				+ "od, "
				+ "\"do\" FROM historia_medyczna h natural join pacjenci p natural join wydarzenia_medyczne w";
		
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
				+ "id_pacjenta = " + pacjentId + "and "
				+ "id_wydarzenia = " + wydarzenieId + "and "
				+ "od = " + fromStr + ";";
		System.out.println(sql);
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean updateItem (String pacjentId, String wydarzenieId, String from,
			String newPacjentId, String newWydarzenieId, String newWizytaId, String newFrom, String newTo) throws SQLException {
		String sql = "UPDATE historia_medyczna SET ("
				+ "id_pacjenta, "
				+ "id_wydarzenia, "
				+ "od, "
				+ "\"do\", "
				+ "wizyta) = ("
				+ newPacjentId + ", "
				+ newWydarzenieId + ", "
				+ Tables.nullCheck(newFrom) + ", "
				+ Tables.nullCheck(newTo) + ", "
				+ newWizytaId + ") WHERE "
				+ "id_pacjenta = " + pacjentId + " AND "
				+ "id_wydarzenia = " + wydarzenieId + " AND "
				+ "od = " + Tables.nullCheck(from) + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (String pacjentId, String wydarzenieId, String wizytaId, String from, String to) throws SQLException {
		String sql = "INSERT INTO historia_medyczna VALUES ("
				+ pacjentId + ", "
				+ wydarzenieId + ", "
				+ wizytaId + ", "
				+ Tables.nullCheck(from) + ", "
				+ Tables.nullCheck(to) + ");";
		return Database.executeUpdate(sql) != 0;
	}

}
