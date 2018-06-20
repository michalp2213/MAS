package Model;

import java.util.ArrayList;

public class PacjenciLPK implements Table {

    @Override
    public ArrayList<ArrayList<String>> getContents() {
        return Database.executeQuery("SELECT * from pacjenci_lpk;");
    }

    public boolean deleteItem (String id, String from) {
        return Database.executeUpdate("DELETE FROM pacjenci_lpk WHERE id_pacjenta = " + id +
                " and od = '" + from + "';") != 0;
    }
    
    public boolean updateItem (String oldPacjentId, String oldFrom,
    		String newPacjentId, String newLekarzId, String newFrom, String newTo) {
    	String sql = "UPDATE pacjenci_lpk SET (id_pacjenta, id_lekarza, od, do) = ("
    			+ newPacjentId + ", "
    			+ newLekarzId + ", "
    			+ "'" + newFrom + "', "
    			+ "'" + newTo + "') WHERE "
    			+ "id_pacjenta = " + oldPacjentId + " AND "
    			+ "od = '" + oldFrom + "';";
    	return Database.executeUpdate(sql) != 0;
    }

    public boolean insertItem (String id_pacjenta, String id_lekarza) {
        String sql = "INSERT INTO pacjenci VALUES ("
                + "'" + id_pacjenta + "', "
                + "'" + id_lekarza + "', "
                + "now(), NULL);";
        return Database.executeUpdate(sql) != 0;
    }

}
