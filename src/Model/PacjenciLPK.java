package Model;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PacjenciLPK implements Table {

    @Override
    public ArrayList<ArrayList<String>> getContents() {
        return Database.executeQuery("SELECT * from pacjenci_lpk;");
    }

    public boolean deleteItem (int id, Date from) {
        return Database.executeUpdate("DELETE FROM pacjenci_lpk WHERE id_pacjenta = " + id +
                " and od = " + from) != 0;
    }

    public boolean insertItem (int id_pacjenta, int id_lekarza) {
        String sql = "INSERT INTO pacjenci VALUES ("
                + "'" + id_pacjenta + "', "
                + "'" + id_lekarza + "', "
                + "now(), NULL);";
        return Database.executeUpdate(sql) != 0;
    }

}
