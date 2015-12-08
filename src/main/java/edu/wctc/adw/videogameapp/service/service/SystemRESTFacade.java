/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.adw.videogameapp.service.service;

import javax.persistence.EntityManager;
import edu.wctc.adw.videogameapp.entity.System;
import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alex
 */
@Stateless
@Path("v1/systems")

public class SystemRESTFacade extends AbstractFacade<System> {
    @PersistenceContext(unitName = "edu.wctc.adw_videogameApp_war_1.0-SNAPSHOTPU")
    protected EntityManager entityManager;

    public SystemRESTFacade() {
        super(System.class);
    }

      @POST
    @Override
    @Consumes({"application/json"})
    public void create(System entity) {
        super.create(entity);
    }
    

    @PUT
    @Consumes({ "application/json"})
    @Transactional
    public void edit(System entity) {
        entityManager.merge(entity);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void remove(@PathParam("id") Integer id) {
        System entity = entityManager.getReference(System.class, id);
        entityManager.remove(entity);
    }

    @GET
    @Path("{id}")
    @Produces({ "application/json"})
    @Transactional
    public System find(@PathParam("id") Integer id) {
        return entityManager.find(System.class, id);
    }

    @GET
    @Produces({"application/json"})
    @Transactional
    public List<System> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{max}/{first}")
    @Produces({ "application/json"})
    @Transactional
    public List<System> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    @Transactional
    public String countREST() {
        try {
            Query query = entityManager.createQuery("SELECT count(o) FROM System AS o");
            return query.getSingleResult().toString();
        } finally {
            entityManager.close();
        }
    }

    private List<System> find(boolean all, int maxResults, int firstResult) {
        try {
            Query query = entityManager.createQuery("SELECT object(o) FROM System AS o");
            if (!all) {
                query.setMaxResults(maxResults);
                query.setFirstResult(firstResult);
            }
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
