/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.adw.videogameapp.controller;

import edu.wctc.adw.videogameapp.model.DAOStrategy;
import edu.wctc.adw.videogameapp.model.DbStrategy;
import edu.wctc.adw.videogameapp.model.Videogame;
import edu.wctc.adw.videogameapp.model.VideogameDAO;
import edu.wctc.adw.videogameapp.model.VideogameService;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author Alex
 */
@WebServlet(name = "videogameController", urlPatterns = {"/videogameController"})
public class VideogameController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/gameList.jsp";
    private static final String ADD_PAGE = "/addGame.jsp";
     private static final String EDIT_DELETE_PAGE = "/editDeleteGame.jsp";
       private static final String REDIRECT_PAGE = "redirect.jsp";
    private static final String LIST_ACTION = "list";
    private static final String ADD_BUTTON = "addButton";
    private static final String EDIT_DELETE_BUTTON = "editDeleteButton";
      private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
     private static final String DELETE_OR_EDIT_ACTION = "deleteOrEdit";
    private static final String ACTION_PARAM = "action";
    private static final String ACTION_REDIRECT = "redirect";
    private int userCount=0;
   
    
    
        // Get init params from web.xml
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbStrategyClassName;
    private String daoClassName;
     private String destination;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter(ACTION_PARAM);
      try 
      {
            VideogameService videogameService = injectDependenciesAndGetVideogameService();
            HttpSession session = request.getSession();
            ServletContext ctx = request.getServletContext();
        if(session.isNew()){
            userCount++;
         }
            ctx.setAttribute("count",  userCount);
            Integer recordsAddCounter = (Integer)session.getAttribute("counter");
         if (recordsAddCounter == null ) {
               recordsAddCounter = 0;
               session.setAttribute("counter", recordsAddCounter);
        }

        if(action.equals(ACTION_REDIRECT)){
           response.sendRedirect(REDIRECT_PAGE);
                return;
           }
        else if (action.equals(LIST_ACTION)) {
               getListOfVideogamesWithListPageDestination(request, videogameService);
           }
        else if (action.equals(ADD_BUTTON)) {
               destination = ADD_PAGE;
           }        
         else if(action.equals(EDIT_DELETE_BUTTON)){
               List values = getParameters( request);
                String gameId = (String)values.get(0);
               request.setAttribute("gameId", gameId);
               String title =  (String)values.get(1);
               request.setAttribute("title", title);
                String system =  (String)values.get(2);
                request.setAttribute("system", system);
                 String logDate =  (String)values.get(3);
                 request.setAttribute("logDate", logDate);
                 String price =  (String)values.get(4);
                request.setAttribute("price", price);
                String image =  (String)values.get(5);
                 request.setAttribute("image", image);
                  destination = EDIT_DELETE_PAGE;
           }
         else if(action.equals(ADD_ACTION)){

               recordsAddCounter = recordsAddCounter + 1;
               session.setAttribute("counter", recordsAddCounter);
               List values = getParameters(request);
               values.remove(0);
               videogameService.insertRecord("videogame", values);
               getListOfVideogamesWithListPageDestination(request, videogameService);
           }
         else if (action.equals(DELETE_OR_EDIT_ACTION)) {
               String submitType =request.getParameter("submit");
               if(submitType.equals(DELETE_ACTION)){
               String gameId = request.getParameter("gameId");       
               videogameService.deleteByGameId("videogame", gameId);
                 }
              else if (submitType.equals(UPDATE_ACTION)){
                videogameService.updateRecord("videogame", getParameters( request), "videogame_id", getParameters( request).get(0));
                }
            getListOfVideogamesWithListPageDestination(request, videogameService);
           }
        else {
               request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
               destination = LIST_PAGE;
           }
        } 
        catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }
    
    private List getParameters(HttpServletRequest request){
            List values= new ArrayList();
               String gameId = request.getParameter("gameId");
               String title =  request.getParameter("title");
               String system =  request.getParameter("system");
               String date = request.getParameter("logDate");
               String price = request.getParameter("price");
                String image = request.getParameter("image");
               values.add(gameId);
               values.add(title);
               values.add(system);
               values.add(date);
               values.add(price);
               values.add(image);
    return values;
    }
    
    private void getListOfVideogamesWithListPageDestination(HttpServletRequest request, VideogameService vgs) throws Exception{
               List<Videogame> games = null;
                games = vgs.getAllGames();
                request.setAttribute("games", games);
                destination = LIST_PAGE;
    }
     
    private VideogameService injectDependenciesAndGetVideogameService() throws Exception {
        Class dbClass = Class.forName(dbStrategyClassName);
        DbStrategy db = (DbStrategy) dbClass.newInstance();
        DAOStrategy videogameDao = null;
        Class daoClass = Class.forName(daoClassName);
         Constructor constructor =null;
        try{
        constructor = daoClass.getConstructor(new Class[]{
            DbStrategy.class, String.class, String.class, String.class, String.class
        });
        }catch(NoSuchMethodException nsme){
        }
        
      if (constructor != null) {
            Object[] constructorArgs = new Object[]{
                db, driverClass, url, userName, password
            };
            videogameDao = (DAOStrategy) constructor
                    .newInstance(constructorArgs);
      }else{
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/videogame_db");
            constructor = daoClass.getConstructor(new Class[]{
                DataSource.class, DbStrategy.class
            });
            Object[] constructorArgs = new Object[]{
                ds, db
            };

            videogameDao = (DAOStrategy) constructor
                    .newInstance(constructorArgs);
      }
            
             return new VideogameService(videogameDao);
      }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
          @Override
    public void init() throws ServletException {
    
        driverClass = getServletContext().getInitParameter("driverClass");
        url = getServletContext().getInitParameter("url");
        userName = getServletContext().getInitParameter("userName");
        password = getServletContext().getInitParameter("password");
        dbStrategyClassName = this.getServletContext().getInitParameter("dbStrategy");
        daoClassName = this.getServletContext().getInitParameter("videogameDao");
    }

}
