/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.adw.videogameapp.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author Alex
 */
public class ConnPoolVideogameDAO implements DAOStrategy {

      private DataSource ds;
    private DbStrategy db;
    
      public ConnPoolVideogameDAO(DataSource ds, DbStrategy db) {
        this.ds = ds;
        this.db = db;
    }
    
    @Override
    public List<Videogame> getAllVideogames() throws Exception {
        db.openConnection(ds);
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
        db.openConnection(ds);
        db.deleteRecordById(tableName, column, value);
        db.closeConnection();
    }

    @Override
    public void insertRecord(String tableName, List values) throws Exception {
       db.openConnection(ds);
       
      
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
          db.openConnection(ds);
  
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
    
}
