package Model;

import java.util.ArrayList;

public class Skierowania implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents(int...args) {	

		String sql = "SELECT * FROM skierowania ORDER BY ";
				
		for (int i = 0; i < args.length; ++ i) {
			sql += args [i];
			if (i != args.length - 1)
				sql += ", ";
		}
		sql += ";";
	
		return Database.executeQuery(sql);
	}
	
	public boolean deleteItem (String id) {
		return Database.executeUpdate("DELETE FROM skierowania WHERE nr_skierowania = " + id + ";") != 0;
	}
	
	public boolean updateItem (String id, String newWizytaId, String newSpecId, String newCelId, String newDesc) {
		String sql = "UPDATE skierowania SET (id_wizyty, id_specjalizacji, id_celu, opis) = ("
				+ newWizytaId + ", "
				+ newSpecId + ", "
				+ newCelId + ", "
				+ "'" + newDesc + "') WHERE nr_skierowania = " + id + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (String wizytaId, String specjalizacjaId, String celId, String desc) {
		String sql = "INSERT INTO skierowania VALUES ("
				+ "DEFAULT, "
				+ wizytaId + ", "
				+ specjalizacjaId + ", "
				+ celId + ", "
				+ "'" + desc + "');";
		return Database.executeUpdate(sql) != 0;
	}

}
