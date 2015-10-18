/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.adw.videogameapp.model;

import java.util.List;

/**
 *
 * @author Alex
 */
public interface DAOStrategy {

    List<Videogame> getAllVideogames() throws Exception;
  void deleteRecordById(String tableName, Object value) throws Exception;
   void insertRecord(String tableName,  List values)throws Exception;
   void updateRecord(String tableName, List colValues, String whereField, Object whereValue)throws Exception;
    
}
