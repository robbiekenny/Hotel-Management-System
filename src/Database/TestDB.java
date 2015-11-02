package Database;
/**
 * @author Mark Lordan
 * @author Robert Kenny
 * @author Derek Mulhern
 * @author Thomas Murphy
 */
public class TestDB {
	public static void main(String[] args) {

		DropTables dt = new DropTables();
		dt.dropTables();
		CreateTables t = new CreateTables();
		t.buildTitanFallTables();
		t.getHotel();
		t.getUsers();

	}
}