/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.adw.videogameapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Alex
 */
public class MySqlDbStrategy implements DbStrategy {
 
    
        private static boolean DEBUG=false;
        private String driverClass;
	private String url;
	private String userName;
	private String password;
        private Connection conn;
    
    @Override
    public void openConnection(String driverClass, String url, 
            String userName, String password) throws Exception {
            Class.forName (driverClass);
            conn = DriverManager.getConnection(url,userName, password);
    }
    
    @Override
    public void closeConnection()throws SQLException {
        conn.close();
    }
    
    @Override
    public List<Map<String,Object>> findAllRecords(String tableName) throws SQLException{
        List<Map<String,Object>> recordList = new ArrayList();
        String sql ="SELECT * FROM " + tableName;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (rs.next()){
            Map<String,Object> record = new HashMap<>();
            for(int i=1; i <= columnCount; i++){
                record.put(metaData.getColumnName(i), rs.getObject(i));
            }
            recordList.add(record);
        }  
        return recordList;
    }
    
    @Override
    public void deleteRecordById(String tableName, String column, Object value) throws Exception{

        String sql ="DELETE FROM " + tableName + " WHERE " + column + "= ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
         stmt.setObject(1, value);
         stmt.execute();
    }
    
  @Override
    public void insertRecord(String tableName, List columns, List values) throws Exception {
                        PreparedStatement pstmt = null;
			pstmt = buildInsertStatement(conn,tableName,columns);
                        int index = 1;
                        for(int i=0; i <= values.size()-1; i++){
                        final Object obj= values.get(i);
                         pstmt.setObject(index++, obj);
                        }
			pstmt.executeUpdate();
        
    }
    
      @Override
    public void updateRecord(String tableName, List colDescriptors, List colValues, String whereField, Object whereValue) throws Exception {
                PreparedStatement pstmt = null;
		
			pstmt = buildUpdateStatement(conn,tableName,colDescriptors,whereField); 
			final Iterator i=colValues.iterator();
			int index = 1;
			boolean doWhereValueFlag = false;
                        
			Object obj = null;
			while( i.hasNext() || doWhereValueFlag) {
				if(!doWhereValueFlag){ obj = i.next();}
                                   pstmt.setObject(index++, obj);
				if(doWhereValueFlag){ break;} 
				if(!i.hasNext() ) {          
					doWhereValueFlag = true;
					obj = whereValue;
				}
			}

			 pstmt.executeUpdate();

    }
    
    private PreparedStatement buildInsertStatement(Connection conn_loc, String tableName, List colDescriptors)
	throws SQLException {
		StringBuffer sql = new StringBuffer("INSERT INTO ");
		(sql.append(tableName)).append(" (");
                for(int y=0; y < colDescriptors.size(); y++ ){
                    (sql.append(  colDescriptors.get(y).toString() )).append(", ");
                }
		sql = new StringBuffer( (sql.toString()).substring( 0,(sql.toString()).lastIndexOf(", ") ) + ") VALUES (" );
		for( int j = 0; j < colDescriptors.size(); j++ ) {
			sql.append("?, ");
		}
		final String finalSQL=(sql.toString()).substring(0,(sql.toString()).lastIndexOf(", ")) + ")";
		return conn_loc.prepareStatement(finalSQL);
	}
    
    
    	private PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName, List colDescriptors, String whereField)
	throws SQLException {
		StringBuffer sql = new StringBuffer("UPDATE ");
		(sql.append(tableName)).append(" SET ");
                for(int y=0; y < colDescriptors.size(); y++ ){
                    (sql.append( colDescriptors.get(y).toString() )).append(" = ?, ");
                }
		sql = new StringBuffer( (sql.toString()).substring( 0,(sql.toString()).lastIndexOf(", ") ) );
		((sql.append(" WHERE ")).append(whereField)).append(" = ?");
		final String finalSQL=sql.toString();
		
                System.out.println(finalSQL);
                return conn_loc.prepareStatement(finalSQL);
	}
    
    
    public static void main(String[] args) throws Exception {
          MySqlDbStrategy db = new MySqlDbStrategy();
          db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/videogame_db", "root", "admin");
          List videogameValues = new ArrayList();
          List colDescription = new ArrayList();
              List<Map<String,Object>> records = db.findAllRecords("videogame");
           for (Map record :records ){
              System.out.println(record);
          //    author=Bob Jones, book_id=2,
          }
           System.out.println("-----------");
//          db.deleteRecordById("videogame", "videogame_id", "2");
           Videogame videogame = new Videogame();
           videogame.setGameId(65);
         videogame.setTitle("I have an image tag and a price!");
           videogame.setSystem("Playstation 2000");
           SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            date = sdf.parse("21/12/2012");
            
           videogame.setLogDate(date);
           videogame.setPrice(12.00);
           videogame.setImage("www.image.com");
           
           
           colDescription.add("title");
            colDescription.add("system");
            colDescription.add("log_date");
            colDescription.add("price");
            colDescription.add("image_url");
        videogameValues.add(videogame.getTitle());
        videogameValues.add(videogame.getSystem());
         videogameValues.add(videogame.getLogDate());
       videogameValues.add(videogame.getPrice());
       videogameValues.add(videogame.getImage());
//          db.insertRecord("videogame", colDescription, videogameValues);
          db.updateRecord("videogame", colDescription, videogameValues, "videogame_id", 65);
          List<Map<String,Object>> records2 = db.findAllRecords("videogame");
          for (Map record :records2 ){
              System.out.println(record);
          }
        db.closeConnection();
    }

  public void openConnection(DataSource ds) throws Exception{
  
   conn = ds.getConnection();
  }

    
}
