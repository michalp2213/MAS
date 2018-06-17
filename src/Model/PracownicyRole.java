package Model;

import java.sql.ResultSet;
import java.util.ArrayList;

public class PracownicyRole implements Table {

	@Override
	public ArrayList<ArrayList<String>> getContents() {
		return Database.executeQuery("SELECT * FROM pracownicy_role;");
	}

	public boolean deleteItem (String roleName, int pracownikId) {
		String sql = "DELETE FROM pracownicy_role WHERE "
				+ "id_pracownika = " + pracownikId
				+ " AND (SELECT nazwa FROM role WHERE id_roli = pracownicy_role.id_roli) = '" + roleName + "';";
		return Database.executeUpdate(sql) != 0;
	}
	
	public boolean insertItem (String roleName, int pracownikId) {
		String sql = "INSERT INTO pracownicy_role VALUES ("
				+ "(SELECT id_roli FROM role WHERE nazwa = '" + roleName + "'), "
				+ pracownikId + ");";
		return Database.executeUpdate(sql) != 0;
	}
	
	public ArrayList<ArrayList<String>> getRole (int pracownikId) {
		return Database.executeQuery("SELECT role_pracownika (" + pracownikId + ");");
	}
}
