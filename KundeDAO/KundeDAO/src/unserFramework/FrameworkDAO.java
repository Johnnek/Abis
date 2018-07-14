package unserFramework;

import java.sql.*;
import java.util.*;

import ArtikelDAO.Artikel;

public abstract class FrameworkDAO {

	protected HashMap<Long, T> cache = new HashMap<Long, T>();
	protected Connection db;
	
	//private static FrameworkDAO instance = new FrameworkDAO();
	
	public void getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			db = DriverManager.getConnection("jdbc:mysql://localhost:3306/xdb?user=abis&password=abis");
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
		
	//Insert
	
	//nur wenn der Key noch nicht in der HashMap enthalten ist, wird dieser der HashMap hinzugefügt
	public void create(T o)throws SQLException {
		if(cache.containsKey(o) ) throw new SQLException("schluessel" + o.getKey() + "bei insert schon in DB enthalten");
		cache.put(doInsert(o), o);
	}
	
	//SQL Statement zum Einfügen eines Objektes in eine Datenbank wird individuell erstellt
	protected abstract String insertStatement();
	//Das explizite Einfügen in eine Datenbank erfolgt in dieser Funktion, die abhängig vom expliziten Objekt ist
	protected abstract long doInsert(T o)throws SQLException;
	
	//Read
	//SQl Statement zum Suchen eines Objektes in einer Datenbank wird individuell erstellt
	protected abstract String findStatement();
	//Das Suchen eines Objektes erfolgt über dessen ID, welche jedes Objekts besitzt und der Primary Key in der Datenbank ist
	public T read(long pK)throws SQLException{
		T result = (T)cache.get(pK);
		
		if(result != null)
			return result;
		
		PreparedStatement findStatement = null;
		try {
			getConnection();
			findStatement = db.prepareStatement(findStatement());
			findStatement.setLong(1,  pK);
			ResultSet rs = findStatement.executeQuery();
			rs.next();
			result = (T)load(rs);
			return result;
		}catch(SQLException e) {
			throw new SQLException("Fehler bei abstractFind. " + e.toString());
		}
	}
	
	//Wenn ein Objekt in einer Datenbank gefunden wird, jedoch nicht in der HashMap vorhanden ist, wird das Objekt in die HashMap geladen mittels des Primary Keys
	protected T load(ResultSet rs)throws SQLException{
		Long pK = new Long(rs.getLong(1));
		if(cache.containsKey(pK))
			return cache.get(pK);
		T result = doLoad(pK, rs);
		cache.put(pK, result);
		return result;
	}
	
	//Die Funktion zum Laden eines expliziten Objekts muss für jeweiliges erstellt werden
	protected abstract T doLoad(Long pK, ResultSet rs)throws SQLException;
	
	
	//Update
	//SQL Statement zum Updaten eines Objektes in einer Datenbank wird individuell erstellt
	protected abstract String updateStatement();
	
	//nur wenn der Primary Key in der HashMap vorhanden ist, wird dieser in die Datenbank geschrieben.
	public void update(T o) throws SQLException{
		if(!cache.containsKey(o.getKey())) throw new SQLException ("Schl�ssel " + o.getKey() + "nicht enthalten");
		cache.put(doUpdate(o), o);
	}
	
	//Funktion für das Einfügen eines expliziten Objektes in eine Datenbank
	protected abstract Long doUpdate(T o) throws SQLException;
	
	//Delete
	//SQL Statement zum Löschen eines Objektes in einer Datenbank wird individuell erstellt
	protected abstract String deleteStatement();
	
	//Funktion zum Löschen eines Objektes mittels Primary Key
	public void delete(T o) {
		PreparedStatement deleteStatement = null;
		try {
		deleteStatement = db.prepareStatement(deleteStatement());
		deleteStatement.setLong(1, o.getKey());
		deleteStatement.execute();
		cache.remove(o.getKey());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

