package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class PacjenciLPK implements Table {

    @Override
    public ArrayList<ArrayList<String>> getContents(int... args) throws SQLException {	

		String sql = "SELECT * FROM pacjenci_lpk";
		
		if (args.length > 0)
			sql += " ORDER BY ";
				
				
		for (int i = 0; i < args.length; ++ i) {
			sql += args [i];
			if (i != args.length - 1)
				sql += ", ";
		}
		sql += ";";
	
		return Database.executeQuery(sql);
    }

    public ArrayList<ArrayList<String>> niceGetContents(int... args) throws SQLException {	

		String sql = "SELECT (p.imie || ' ' || p.nazwisko || ' (' || p.id_pacjenta || ')') AS pacjent, "
				+ "(l.imie || ' ' || l.nazwisko || ' (' || l.id_pracownika || ')') AS lekarz, "
				+ "od, "
				+ "\"do\" "
				+ "FROM pacjenci_lpk pl natural join pacjenci p join pracownicy l on l.id_pracownika = pl.id_lekarza";
		
		if (args.length > 0)
			sql += " ORDER BY ";
				
				
		for (int i = 0; i < args.length; ++ i) {
			sql += args [i];
			if (i != args.length - 1)
				sql += ", ";
		}
		sql += ";";
	
		return Database.executeQuery(sql);
    }

    public boolean deleteItem (String id, String from) throws SQLException {
        return Database.executeUpdate("DELETE FROM pacjenci_lpk WHERE id_pacjenta = " + id +
                " and od = " + Tables.nullCheck(from) + ";") != 0;
    }
    
    public boolean updateItem (String oldPacjentId, String oldFrom,
    		String newPacjentId, String newLekarzId, String newFrom, String newTo) throws SQLException {
    	String sql = "UPDATE pacjenci_lpk SET (id_pacjenta, id_lekarza, od, do) = ("
    			+ newPacjentId + ", "
    			+ newLekarzId + ", "
    			+ Tables.nullCheck(newFrom) + ", "
    			+ Tables.nullCheck(newTo) + ") WHERE "
    			+ "id_pacjenta = " + oldPacjentId + " AND "
    			+ "od = " + Tables.nullCheck(oldFrom) + ";";
    	return Database.executeUpdate(sql) != 0;
    }

    public boolean insertItem (String id_pacjenta, String id_lekarza) throws SQLException {
        String sql = "INSERT INTO pacjenci_lpk VALUES ("
                + id_pacjenta + ", "
                + id_lekarza + ", "
                + "now(), NULL);";
        return Database.executeUpdate(sql) != 0;
    }

}
