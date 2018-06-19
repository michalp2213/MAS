package Model;

import java.sql.Date;
import java.util.ArrayList;

public class PacjenciLPK implements Table {

    @Override
    public ArrayList<ArrayList<String>> getContents() {
        return Database.executeQuery("SELECT * from pacjenci_lpk;");
    }

    public boolean deleteItem (int id, Date from) {
        return Database.executeUpdate("DELETE FROM pacjenci_lpk WHERE id_pacjenta = " + id +
                " and od = '" + from + "';") != 0;
    }
    
    public boolean updateItem (int oldPacjentId, Date oldFrom,
    		int newPacjentId, int newLekarzId, Date newFrom, Date newTo) {
    	String sql = "UPDATE pacjenci_lpk SET (id_pacjenta, id_lekarza, od, do) = ("
    			+ newPacjentId + ", "
    			+ newLekarzId + ", "
    			+ "'" + newFrom + "', "
    			+ "'" + newTo + "') WHERE "
    			+ "id_pacjenta = " + oldPacjentId + " AND "
    			+ "od = '" + oldFrom + "';";
    	return Database.executeUpdate(sql) != 0;
    }

    public boolean insertItem (int id_pacjenta, int id_lekarza) {
        String sql = "INSERT INTO pacjenci VALUES ("
                + "'" + id_pacjenta + "', "
                + "'" + id_lekarza + "', "
                + "now(), NULL);";
        return Database.executeUpdate(sql) != 0;
    }

}
