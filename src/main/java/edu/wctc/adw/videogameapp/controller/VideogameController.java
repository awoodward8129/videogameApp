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
    private static final String LIST_ACTION = "list";
    private static final String ADD_BUTTON = "addButton";
    private static final String EDIT_DELETE_BUTTON = "editDeleteButton";
      private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";
    private static final String ACTION_REDIRECT = "redirect";
    
    
        // Get init params from web.xml
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbStrategyClassName;
    private String daoClassName;
    private DbStrategy db;
    private VideogameDAO videogameDao;
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
    
                try {
            
            VideogameService videogameService = injectDependenciesAndGetVideogameService();
        
         HttpSession session = request.getSession();
        ServletContext ctx = request.getServletContext();
        
       if(action.equals("session")){
    
        String fontColor = request.getParameter("fontColor");
        
        if(action != null && action.equals("end")) {
            session.invalidate();
            if(destination.equalsIgnoreCase("home")) {
                response.sendRedirect("index.jsp");
            } else {
                response.sendRedirect("testsession.jsp");
            }
        } else {
            String color = request.getParameter("color");
            // Session scope is per user
            session.setAttribute("color", color);
            
            // in JSP the ServletContext is referred to as 'application'
            // and as applicatio-wide scope
            if(fontColor != null && fontColor.length() > 0) {
                ctx.setAttribute("fontColor", fontColor);
            }
            
           getListOfVideogamesWithListPageDestination(request, videogameService);
        }
     
       }
        
        



            
            if (action.equals(LIST_ACTION)) {
                
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
                request.setAttribute("image", price);
               destination = EDIT_DELETE_PAGE;
            }
            else if(action.equals(ADD_ACTION)){
                List values = getParameters(request);
             values.remove(0);
               videogameService.insertRecord("videogame",  values);
               getListOfVideogamesWithListPageDestination(request, videogameService);
             }
            else if (action.equals(DELETE_ACTION)) {
                
              String submitType =request.getParameter("submit");
              if(submitType.equals("delete")){
             String gameId = request.getParameter("gameId");       
             videogameService.deleteByGameId("videogame", gameId);
             
             
              }else if (submitType.equals("update")){
                  
             videogameService.updateRecord("videogame", getParameters( request), "videogame_id", getParameters( request).get(0));
              }
             getListOfVideogamesWithListPageDestination(request, videogameService);
                
            } else {
                // no param identified in request, must be an error
                request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                destination = LIST_PAGE;
            }
            
        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        // Forward to destination page
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
        // Use Liskov Substitution Principle and Java Reflection to
        // instantiate the chosen DBStrategy based on the class name retrieved
        // from web.xml
        Class dbClass = Class.forName(dbStrategyClassName);
        // Note that DBStrategy classes have no constructor params
        DbStrategy db = (DbStrategy) dbClass.newInstance();

            // Use Liskov Substitution Principle and Java Reflection to
        // instantiate the chosen DAO based on the class name retrieved above.
        // This one is trickier because the available DAO classes have
        // different constructor params
        DAOStrategy videogameDao = null;
        Class daoClass = Class.forName(daoClassName);
         Constructor constructor =null;
        try{
     constructor = daoClass.getConstructor(new Class[]{
            DbStrategy.class, String.class, String.class, String.class, String.class
        });
        }catch(NoSuchMethodException nsme){
        
        }
            // This will be null if using connectin pool dao because the
        // constructor has a different number and type of arguments
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
        // Get init params from web.xml
        //I'll use getServletContext().get
        driverClass = getServletContext().getInitParameter("driverClass");
        url = getServletContext().getInitParameter("url");
        userName = getServletContext().getInitParameter("userName");
        password = getServletContext().getInitParameter("password");
        dbStrategyClassName = this.getServletContext().getInitParameter("dbStrategy");
        daoClassName = this.getServletContext().getInitParameter("videogameDao");

        // You can't do the Java Reflection stuff here because exceptions
        // are thrown that can't be handled by this stock init() method
        // because the method signature can't be modified -- remember, you 
        // are overriding the method.
    }

}
