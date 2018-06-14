package Model;

import java.sql.Date;
import java.sql.ResultSet;

public class HistoriaMedyczna implements Table {

	@Override
	public ResultSet getContents() {
		return Database.executeQuery("SELECT * FROM historia_medyczna;");
	}
	
	public boolean deleteItem (int pacjentId, int wydarzenieId, Date from) {
		String sql = "DELETE FROM historia_medyczna WHERE "
				+ "id_pacjenta = " + pacjentId + ","
				+ "id_wydarzenia = " + wydarzenieId + ","
				+ "od = " + from + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (int pacjentId, int wydarzenieId, Date from, Date to, int wizytaId) {
		String sql = "INSERT INTO historia_medyczna VALUES ("
				+ pacjentId + ","
				+ wydarzenieId + ","
				+ from + ","
				+ wizytaId + ","
				+ to + ");";
		return Database.executeUpdate(sql) != 0;
	}

}
