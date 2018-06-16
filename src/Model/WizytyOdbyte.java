package Model;

import java.sql.ResultSet;

public class WizytyOdbyte implements Table {

	@Override
	public ResultSet getContents() {
		return Database.executeQuery("SELECT * FROM wizyty_odbyte;");
	}

}
