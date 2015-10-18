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

/**
 *
 * @author Alex
 */
public class VideogameService {
    
    DAOStrategy dao;

    public VideogameService(DAOStrategy dao) {
        this.dao = dao;
    }
    
    public List<Videogame> getAllGames() throws Exception{
    return dao.getAllVideogames();
    }
    
    
    public void deleteByGameId(String tableName, Object value) throws Exception{
    dao.deleteRecordById( tableName, value);
    }
    
     public void insertRecord(String tableName , List values) throws Exception{
    dao.insertRecord(tableName, values);
    }
    
     public void updateRecord(String tableName, List colValues, String whereField, Object whereValue) throws Exception{
     dao.updateRecord(tableName, colValues, whereField, whereValue);
     }
    public static void main(String[] args) throws Exception { 
        VideogameService service = new VideogameService(new VideogameDAO(new MySqlDbStrategy(),"com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/videogame_db", "root", "admin"));
      List<Videogame> games = service.getAllGames();
       for(Videogame g :games){
           System.out.println(g);
       }
       
       
       
       
         List videogameValues = new ArrayList();

           System.out.println("-----------");
        videogameValues.add(36);
        videogameValues.add("BALLS IN YOUR FACE ");
        videogameValues.add("Playstation 4");
//        videogameValues.add("04-04-2002");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            date = sdf.parse("21/12/2012");
            videogameValues.add(date);
         videogameValues.add((Double)12.000);
           videogameValues.add("www.NEWimage.com");
        
//          service.insertRecord("videogame", videogameValues);
          
          service.updateRecord("videogame", videogameValues ,"videogame_id", 36);
    
          List<Videogame> games2 = service.getAllGames();
       for(Videogame b :games2){
           System.out.println(b);
       }
    }
}
