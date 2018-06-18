package Model;

import java.sql.Date;
import java.util.ArrayList;

import org.postgresql.util.PGInterval;

public class WizytyOdbyte implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * FROM wizyty_odbyte;");
	}
	
	public boolean updateItem (int id, int newPacjentId, int newLekarzId, int newCel, Date newDate, PGInterval newInt) {
		String sql = "UPDATE wizyty_odbyte SET (id_pacjenta, id_lekarza, cel, data, czas_trwania) = ("
				+ newPacjentId + ", "
				+ newLekarzId + ", "
				+ newCel + ", "
				+ newDate + ", "
				+ newInt + ") WHERE id_wizyty = " + id + ";";
		return Database.executeUpdate(sql) != 0;
	}

}
