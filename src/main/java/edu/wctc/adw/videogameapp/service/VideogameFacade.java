/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.adw.videogameapp.service;

import edu.wctc.adw.videogameapp.entity.Videogame;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alex
 */
@Stateless
public class VideogameFacade extends AbstractFacade<Videogame> {
    @PersistenceContext(unitName = "edu.wctc.adw_videogameApp_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VideogameFacade() {
        super(Videogame.class);
    }
    
}
