package edu.wctc.adw.videogameapp.repository;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import edu.wctc.adw.videogameapp.entity.Videogame;

/**
 *
 * @author jlombardo
 */
public interface VideogameRepository extends JpaRepository<Videogame, Integer>, Serializable {
    
    


    

}
