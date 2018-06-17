package Model;

import java.sql.ResultSet;
import java.util.ArrayList;

public class WizytyOdbyte implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * FROM wizyty_odbyte;");
	}

}
