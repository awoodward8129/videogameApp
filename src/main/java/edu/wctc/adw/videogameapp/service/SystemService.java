package edu.wctc.adw.videogameapp.service;


import edu.wctc.adw.videogameapp.entity.System;
import edu.wctc.adw.videogameapp.repository.SystemRepository;
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
@Repository("systemService")
@Transactional(readOnly = true)
public class SystemService {
    private transient final Logger LOG = LoggerFactory.getLogger(SystemService.class);
    
    @Autowired
    private SystemRepository systemRepo;
    
    @Autowired
    private VideogameRepository videogameRepo;

    public SystemService() {
    }
    
    public List<System> findAll() {
        return systemRepo.findAll();
    }
    
    public System findById(String id) {
        return systemRepo.findOne(new Integer(id));
    }
    
      public System findByIdAndFetchGamesEagerly(String id){
    Integer systemId = new Integer(id);
    return systemRepo.findByIdAndFetchGamesEagerly(systemId);
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(System system) {
        LOG.debug("Deleting book: " + system.getSystemName());
        systemRepo.delete(system);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public System edit(System system) {
        return systemRepo.saveAndFlush(system);
    }
    
}
