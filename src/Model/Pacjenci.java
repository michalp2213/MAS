package Model;

import java.sql.Date;
import java.sql.ResultSet;

public class Pacjenci implements Table {

	@Override
	public ResultSet getContents() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean deleteItem (int id) {
		//TODO
		return false;
	}
	
	public boolean insertItem (String name, String surname, String pesel, String passportNo, Date birthDate, String sex) {
		//TODO
		return false;
	}
	
	public boolean setLPK (int pacjentId, int lekarzId) {
		//TODO
		return false;
	}

}
