package Model;

import java.sql.Date;
import java.sql.ResultSet;

public class AnkietyLekarze implements Table {

	@Override
	public ResultSet getContents() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean deleteItem (int id) {
		//TODO
		return false;
	}
	
	public boolean insertItem (int lekarzId, Date date, int uprzejmosc, int opanowanie, int informacyjnosc, int dokladnosc_badan) {
		//TODO
		return false;
	}
	
	public ResultSet alphabeticRanking () {
		//TODO
		return null;
	}
	
	public ResultSet bestIn (String cecha) {
		//TODO
		return null;
	}
	
	public ResultSet bestAvg () {
		//TODO
		return null;
	}

}
