package Model;

import java.sql.Date;
import java.sql.ResultSet;

import org.postgresql.util.PGInterval;

public class WizytyPlanowane implements Table {

	@Override
	public ResultSet getContents() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean deleteItem (int id) {
		//TODO
		return false;
	}
	
	public boolean insertItem (int pacjentId, int lekarzId, String cel, Date date) {
		//TODO
		return false;
	}
	
	public boolean moveToWizytyOdbyte () {
		//TODO
		return false;
	}
	
	public boolean moveToWizytyOdbyte (PGInterval interval) {
		//TODO
		return false;
	}

}
