package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Table {
	public ArrayList <ArrayList <String>> getContents (int... args) throws SQLException;
}
