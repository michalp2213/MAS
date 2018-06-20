package Model;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.postgresql.util.PGInterval;

public class WizytyOdbyte implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * FROM wizyty_odbyte;");
	}
	
	public boolean updateItem (String id, String newPacjentId, String newLekarzId, String newCel, String newSpecjalizacja, String newDate, String newInt) {
		String sql = "UPDATE wizyty_odbyte SET (id_pacjenta, id_lekarza, cel, specjalizacja, data, czas_trwania) = ("
				+ newPacjentId + ", "
				+ newLekarzId + ", "
				+ newCel + ", "
				+ newSpecjalizacja + ", "
				+ "'" + newDate + "', "
				+ newInt + ") WHERE id_wizyty = " + id + ";";
		return Database.executeUpdate(sql) != 0;
	}

}
