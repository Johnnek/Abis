package unserFramework;

import java.sql.*;
import java.util.*;

import ArtikelDAO.Artikel;

public abstract class FrameworkDAO {

	protected HashMap<Long, T> cache = new HashMap<Long, T>();
	protected Connection db;
	
	//private static FrameworkDAO instance = new FrameworkDAO();
	
	protected void getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			db = DriverManager.getConnection("jdbc:mysql://localhost:3306/xdb?user=abis&password=abis");
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//ToDo
	/*
	 * T ist unsere abstrakte Klasse als Vorlage für z.B. ein Artikel oder Kunde, 
	 * welche immer ein primaryKey(Long) besitzt.
	*/
	
	//getInstance() für spezifische Klasse
	//public abstract Object getInstance();
	
	//Insert
	protected abstract String insertStatement();
	
	public void create(T o)throws SQLException {
		Long id = o.getKey();
		if(cache.containsKey(o) ) throw new SQLException("schluessel" + o.getKey() + "bei insert schon in DB enthalten");
		cache.put(doInsert(o), o);
	}
	
	protected abstract long doInsert(T o)throws SQLException;
	
	//Read
	protected abstract String findStatement();
	
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
	
	protected Object load(ResultSet rs)throws SQLException{
		Long pK = new Long(rs.getLong(1));
		if(cache.containsKey(pK))
			return cache.get(pK);
		T result = doLoad(pK, rs);
		cache.put(pK, result);
		return result;
	}
	
	protected abstract T doLoad(Long pK, ResultSet rs)throws SQLException;
	
	
	//Update
	protected abstract String updateStatement();
	
	public void update(T o) throws SQLException{
		if(!cache.containsKey(o.getKey())) throw new SQLException ("Schl�ssel " + o.getKey() + "nicht enthalten");
		cache.put(doUpdate(o), o);
	}
	
	protected abstract Long doUpdate(T o) throws SQLException;
	
	//Delete
	protected abstract String deleteStatement();
	
	public void delete(T o) {
		PreparedStatement deleteStatement = null;
		try {
		deleteStatement = db.prepareStatement(deleteStatement());
		deleteStatement.setLong(1, o.getKey());
		deleteStatement.executeQuery();
		cache.remove(o.getKey());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

