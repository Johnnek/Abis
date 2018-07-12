package unserFramework;

import java.sql.*;
import java.util.*;

public abstract class FrameworkDAO {

	private HashMap<Long, Object> cache = new HashMap<Long,Object>();
	private Connection db;
	
	//private static FrameworkDAO instance = new FrameworkDAO();
	
	protected void getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			db = DriverManager.getConnection("jdbc:mysql://localhost:3306/xdb?user=abis&password=abis");
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
//	private static FrameworkDAO getInstance() {
//		return instance;
//	}
	
	protected abstract Object getInstance();
	
	//Insert
	protected abstract String insertStatement();
	
	protected void create(T o)throws SQLException {
		Long id = getKey(o);
		if(cache.containsKey(o) ) throw new SQLException("schluessel" + getKey(o) + "bei insert schon in DB enthalten");
		cache.put(doInsert(o), o);
	}
	
	protected abstract long doInsert(T o)throws SQLException;
	
	//Read
	protected abstract String findStatement();
	
	protected Object read(long pK)throws SQLException {
		Object result = (Object)cache.get(pK);
		
		if(result != null)
			return result;
		
		PreparedStatement findStatement = null;
		try {
			getConnection();
			findStatement = db.prepareStatement(findStatement());
			findStatement.setLong(1,  pK);
			ResultSet rs = findStatement.executeQuery();
			rs.next();
			result = load(rs);
			return result;
		}catch(SQLException e) {
			throw new SQLException("Fehler bei abstractFind. " + e.toString());
		}
	}
	
	private Object load(ResultSet rs)throws SQLException {
		Long pK = new Long(rs.getLong(1));
		if(cache.containsKey(pK))
			return cache.get(pK);
		Object result = doLoad(pK, rs);
		cache.put(pK, result);
		return result;
	}
	
	protected abstract Object doLoad(Long pK, ResultSet rs)throws SQLException;
	
	
	//Update
	protected abstract String updateStatement();
	
	private void update(T o) throws SQLException{
		if(!cache.containsKey(getKey(o))) throw new SQLException ("Schlüssel " + getKey(o) + "nicht enthalten");
		cache.put(doUpdate(o), o);
	}
	
	protected abstract Long doUpdate(T o) throws SQLException;
	
	//Delete
	protected abstract String deleteStatement();
	
	private void delete(T o) {
		PreparedStatement deleteStatement = null;
		try {
		deleteStatement = db.prepareStatement(deleteStatement());
		deleteStatement.setLong(1, getKey(o));
		deleteStatement.executeQuery();
		cache.remove(getLeKey(o));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

