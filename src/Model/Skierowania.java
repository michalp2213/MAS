package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class Skierowania implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents(int...args) throws SQLException {	

		String sql = "SELECT * FROM skierowania";
		
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
	
	public ArrayList<ArrayList<String>> niceGetContents(int...args) throws SQLException {	

		String sql = "SELECT nr_skierowania, "
				+ "wizyta, "
				+ "(SELECT nazwa FROM specjalizacje WHERE id_specjalizacji = skierowania.specjalizacja) AS specjalizacja, "
				+ "(SELECT nazwa FROM cele_wizyty WHERE id_celu = skierowania.cel_skierowania) AS cel, "
				+ "opis FROM skierowania";
		
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
	
	public boolean deleteItem (String id) throws SQLException {
		return Database.executeUpdate("DELETE FROM skierowania WHERE nr_skierowania = " + id + ";") != 0;
	}
	
	public boolean updateItem (String id, String newWizytaId, String newSpecId, String newCelId, String newDesc) throws SQLException {
		String sql = "UPDATE skierowania SET (wizyta, specjalizacja, cel_skierowania, opis) = ("
				+ newWizytaId + ", "
				+ newSpecId + ", "
				+ newCelId + ", "
				+ Tables.nullCheck(newDesc) + ") WHERE nr_skierowania = " + id + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (String wizytaId, String specjalizacjaId, String celId, String desc) throws SQLException {
		String sql = "INSERT INTO skierowania VALUES ("
				+ "DEFAULT, "
				+ wizytaId + ", "
				+ specjalizacjaId + ", "
				+ celId + ", "
				+ Tables.nullCheck(desc) + ");";
		return Database.executeUpdate(sql) != 0;
	}

}
