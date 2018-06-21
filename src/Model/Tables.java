package Model;

public class Tables {
	static String nullCheck (String str) {
		if (str == null || str.equals("null")) // java is crap
			return "NULL";
		return "'" + str + "'";
	}
	
	public static Pacjenci pacjenci = new Pacjenci();
	public static HistoriaMedyczna historia_medyczna = new HistoriaMedyczna();
	public static WydarzeniaMedyczne wydarzenia_medyczne = new WydarzeniaMedyczne();
	public static WizytyPlanowane wizyty_planowane = new WizytyPlanowane();
	public static WizytyOdbyte wizyty_odbyte = new WizytyOdbyte();
	public static CeleWizyty cele_wizyty = new CeleWizyty();
	public static Skierowania skierowania = new Skierowania();
	public static Pracownicy pracownicy = new Pracownicy();
	public static PracownicyRole pracownicy_role = new PracownicyRole();
	public static Role role = new Role();
	public static LekarzeSpecjalizacje lekarze_specjalizacje = new LekarzeSpecjalizacje();
	public static Specjalizacje specjalizacje = new Specjalizacje();
	public static AnkietyLekarze ankiety_lekarze = new AnkietyLekarze();
	public static PacjenciLPK pacjenci_lpk = new PacjenciLPK();
}
