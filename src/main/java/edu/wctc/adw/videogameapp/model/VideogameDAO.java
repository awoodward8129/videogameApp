/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.adw.videogameapp.model;

import java.util.ArrayList;
import java.util.List;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Alex
 */
public class VideogameDAO implements DAOStrategy {
   
    private DbStrategy db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    public VideogameDAO(DbStrategy db, String driverClass, String url, String userName, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    VideogameDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Videogame> getAllVideogames() throws Exception{
    db.openConnection(this.driverClass, this.url, this.userName, this.password);
    List<Videogame> records = new ArrayList<>();
    List<Map<String,Object>> rawData = db.findAllRecords("videogame");
    for(Map rawRec: rawData){
    Videogame game = new Videogame();
    
    Object obj = rawRec.get("videogame_id");
    Integer i = Integer.parseInt(obj.toString());
    game.setGameId(i);
    
    obj = rawRec.get("title");
    String t = obj.toString();
    game.setTitle(t);
    
    obj = rawRec.get("system");
    String s = obj.toString();
    game.setSystem(s);
    
    
    obj = rawRec.get("log_date");
    Date d= (Date) obj;
    game.setLogDate(d);
    
        obj = rawRec.get("price");
    Double doub = Double.parseDouble(obj.toString());
    game.setPrice(doub);
    
    obj = rawRec.get("image_url");
    String image = obj.toString();
    game.setImage(image);
    
    records.add(game);
    }
    
    db.closeConnection();
    return records;
    }
 

    @Override
    public void deleteRecordById(String tableName, Object value) throws Exception {
       
        String column ="videogame_id";
            db.openConnection(this.driverClass, this.url, this.userName, this.password);
            db.deleteRecordById(tableName, column, value);
            db.closeConnection();
    }
    
       @Override
    public void insertRecord(String tableName , List values) throws Exception {
           db.openConnection(this.driverClass, this.url, this.userName, this.password);
         
        List columns= new ArrayList();
               columns.add("title");
               columns.add("system");
               columns.add("log_date");
               columns.add("price");
               columns.add("image_url");
               

            db.insertRecord(tableName, columns, values);
            db.closeConnection();
               

    }
    
    @Override
    public void updateRecord(String tableName, List values, String whereField, Object whereValue) throws Exception {
          db.openConnection(this.driverClass, this.url, this.userName, this.password);
            List columns= new ArrayList();
               columns.add("videogame_id");     
               columns.add("title");
               columns.add("system");
               columns.add("log_date");
                 columns.add("price");
               columns.add("image_url");

           
            db.updateRecord(tableName, columns, values, whereField,whereValue);
            db.closeConnection();
    }
    
    public static void main(String[] args) throws Exception {
        
        VideogameDAO dao = new VideogameDAO(new MySqlDbStrategy(),"com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/videogame_db", "root", "admin");

           List<Videogame> games = dao.getAllVideogames();
       for(Videogame g :games){
           System.out.println(g);
       }
       
       
       
       
         List videogameValues = new ArrayList();

           System.out.println("-----------");
        videogameValues.add(34);
        videogameValues.add("i'm SEXY AS HELL! AND UPDATED!");
        videogameValues.add("Playstation");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            date = sdf.parse("21/12/2012");
            videogameValues.add(date);
         videogameValues.add((Double)12.000);
           videogameValues.add("www.NEWimage.com");
        
//          dao.insertRecord("videogame", videogameValues);
    dao.updateRecord("videogame", videogameValues, "videogame_id", 34);
          List<Videogame> games2 = dao.getAllVideogames();
       for(Videogame b :games2){
           System.out.println(b);
       }
    }

    

 
}
