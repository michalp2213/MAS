package Model;

import java.sql.Date;
import java.sql.ResultSet;

public class HistoriaMedyczna implements Table {

	@Override
	public ResultSet getContents() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean deleteItem (int pacjentId, int wydarzenieId, Date from) {
		//TODO
		return false;
	}
	
	public boolean insertItem (int pacjentId, int wydarzenieId, Date from, Date to, int wizytaId) {
		//TODO
		return false;
	}

}
