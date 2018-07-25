package ArtikelDAO;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import unserFramework.FrameworkDAO;
import unserFramework.T;

public class ArtikelDAO extends FrameworkDAO {

	private static ArtikelDAO instance = new ArtikelDAO();
	
	public void getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			db = DriverManager.getConnection("jdbc:mysql://localhost:3306/xdb?user=abis&password=abis");
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static ArtikelDAO getInstance() {
		return instance;
	}

	@Override
	protected String insertStatement() {
		return "INSERT INTO artikel VALUES(?, ?, ?)";
	}

	@Override
	protected long doInsert(T t) throws SQLException {
		PreparedStatement insertStatement = null;
		insertStatement = db.prepareStatement(insertStatement());
		insertStatement.setLong(1, t.getArtikelNummer());
		insertStatement.setString(2, t.getBezeichnung());
		insertStatement.setDouble(3, t.getPreis());
		insertStatement.execute();
		return t.getArtikelNummer();
	}

	@Override
	protected String findStatement() {
		return "SELECT Artikelnr, Bezeichnung, Preis FROM artikel WHERE artikelnr=?";
	}

	@Override
	protected T doLoad(Long pK, ResultSet rs) throws SQLException {
		Long anr = new Long(rs.getLong(1));
		String bez = rs.getString(2);
		Double pr = rs.getDouble(3);
		Artikel result = new Artikel(anr, bez, pr);
		return result;
	}

	@Override
	protected String updateStatement() {
		return  "UPDATE artikel SET Bezeichnung = ?, Preis = ? WHERE artikelnr = ?";
	}

	@Override
	protected Long doUpdate(T o) throws SQLException {
		PreparedStatement updateStatement = null;
		updateStatement = db.prepareStatement(updateStatement());
		updateStatement.setLong(1, o.getArtikelNummer());
		updateStatement.setString(2, o.getBezeichnung());
		updateStatement.setDouble(3, o.getPreis());
		updateStatement.execute();
		return o.getArtikelNummer();
	}

	@Override
	protected String deleteStatement() {
		return "DELETE FROM artikel WHERE artikelnr = ?";
	}
}
