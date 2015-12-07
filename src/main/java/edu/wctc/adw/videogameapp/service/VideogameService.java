package edu.wctc.adw.videogameapp.service;


import edu.wctc.adw.videogameapp.entity.Videogame;
import edu.wctc.adw.videogameapp.repository.VideogameRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a special Spring-enabled service class that delegates work to 
 * various Spring managed repository objects using special spring annotations
 * to perform dependency injection, and special annotations for transactions.
 * It also uses SLF4j to provide logging features.
 * 
 * @author jlombardo
 */
@Repository("videogameService")
@Transactional(readOnly = true)
public class VideogameService {

    private transient final Logger LOG = LoggerFactory.getLogger(VideogameService.class);

    @Autowired
    private VideogameRepository videogameRepo;


    public VideogameService() {
    }

    public List<Videogame> findAll() {
        return videogameRepo.findAll();
    }

    public Videogame findById(String id) {
        return videogameRepo.findOne(new Integer(id));
    }
    
 

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(Videogame game) {
        LOG.debug("Deleting author: " + game.getTitle());
       videogameRepo.delete(game);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Videogame edit(Videogame game) {
        return videogameRepo.saveAndFlush(game);
    }
}