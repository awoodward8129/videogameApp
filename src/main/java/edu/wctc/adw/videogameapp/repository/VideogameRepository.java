package edu.wctc.adw.videogameapp.repository;

import edu.wctc.adw.videogameapp.entity.Videogame;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "videogames", path = "videogames")
public interface VideogameRepository extends JpaRepository<Videogame, Integer>, Serializable {
    
    


    

}
