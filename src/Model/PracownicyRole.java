package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class PracownicyRole implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents(int...args) throws SQLException {	

		String sql = "SELECT * FROM pracownicy_role";
		
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

	public boolean deleteItem (String roleName, String pracownikId) throws SQLException {
		String sql = "DELETE FROM pracownicy_role WHERE "
				+ "id_pracownika = " + pracownikId
				+ " AND (SELECT nazwa FROM role WHERE id_roli = pracownicy_role.id_roli) = " + Tables.nullCheck(roleName) + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean updateItem (String oldRolaId, String oldPracownikId, String newRolaId, String newPracownikId) throws SQLException {
		String sql = "UPDATE pracownicy_role SET (id_roli, id_pracownika) = ("
				+ newRolaId + ", "
				+ newPracownikId + ") WHERE "
				+ "id_roli = " + oldRolaId + " AND id_pracownika = " + oldPracownikId + ";";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (String roleName, String pracownikId) throws SQLException {
		String sql = "INSERT INTO pracownicy_role VALUES ("
				+ "(SELECT id_roli FROM role WHERE nazwa = " + Tables.nullCheck(roleName) + "), "
				+ pracownikId + ");";
		return Database.executeUpdate(sql) != 0;
	}
	
	public ArrayList<ArrayList<String>> getRole (String pracownikId) throws SQLException {
		return Database.executeQuery("SELECT role_pracownika (" + pracownikId + ");");
	}
}
