package edu.wctc.adw.videogameapp.repository;


import edu.wctc.adw.videogameapp.entity.System;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Alex
 */
public interface SystemRepository extends JpaRepository<System, Integer>, Serializable {
    
    @Query("SELECT s FROM System s JOIN FETCH s.videogameSet WHERE s.systemId = (:id)")
    public System findByIdAndFetchSystemsEagerly(@Param("id") Integer id);
    
    

//public Object[] findAllWithNameOnly();
}
