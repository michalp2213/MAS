package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class WizytyOdbyte implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents(int...args) throws SQLException {	
		
		String sql = "SELECT * FROM wizyty_odbyte";
		
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
	
	public boolean updateItem (String id, String newPacjentId, String newLekarzId, String newCel, String newSpecjalizacja, String newDate, String newInt) throws SQLException {
		String sql = "UPDATE wizyty_odbyte SET (id_pacjenta, id_lekarza, cel, specjalizacja, data, czas_trwania) = ("
				+ newPacjentId + ", "
				+ newLekarzId + ", "
				+ newCel + ", "
				+ newSpecjalizacja + ", "
				+ Tables.nullCheck(newDate) + ", "
				+ newInt + ") WHERE id_wizyty = " + id + ";";
		return Database.executeUpdate(sql) != 0;
	}

}
