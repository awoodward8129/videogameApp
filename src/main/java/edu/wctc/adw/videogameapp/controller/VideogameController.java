/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.adw.videogameapp.controller;

//import edu.wctc.adw.videogameapp.model.DAOStrategy;
//import edu.wctc.adw.videogameapp.model.DbStrategy;
//import edu.wctc.adw.videogameapp.model.Videogame;
//import edu.wctc.adw.videogameapp.model.VideogameDAO;
//import edu.wctc.adw.videogameapp.model.VideogameService;
import edu.wctc.adw.videogameapp.entity.Videogame;
import edu.wctc.adw.videogameapp.service.VideogameService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
    private static final String AJAX_DELETE = "deleteAjax";
    private int userCount = 0;
    private static final String AJAX_LIST_ACTION = "listAjax";
     private static final String AJAX_FINDBY_ID = "findByIdAjax";

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
//     @Inject
//    private VideogameFacade videogameService;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter(ACTION_PARAM);
        PrintWriter out = response.getWriter();

        Videogame game = null;

        try {
            HttpSession session = request.getSession();

            ServletContext sctx = getServletContext();
            WebApplicationContext ctx
                    = WebApplicationContextUtils.getWebApplicationContext(sctx);
            VideogameService videogameService = (VideogameService) ctx.getBean("videogameService");

            if (session.isNew()) {
                userCount++;
            }
//            ctx.setAttribute("count",  userCount);
            Integer recordsAddCounter = (Integer) session.getAttribute("counter");
            if (recordsAddCounter == null) {
                recordsAddCounter = 0;
                session.setAttribute("counter", recordsAddCounter);
            }

            if (action.equals(ACTION_REDIRECT)) {
                response.sendRedirect(REDIRECT_PAGE);
                return;
            } else if (action.equals(LIST_ACTION)) {
                List<Videogame> games = videogameService.findAll();
                request.setAttribute("games", games);
                destination = LIST_PAGE;
            } else if (action.equals(ADD_BUTTON)) {
                destination = ADD_PAGE;
            } else if (action.equals(EDIT_DELETE_BUTTON)) {
                List values = getParameters(request);
                String gameId = (String) values.get(0);
                request.setAttribute("gameId", gameId);
                String title = (String) values.get(1);
                request.setAttribute("title", title);
                String system = (String) values.get(2);
                request.setAttribute("system", system);
                String logDate = (String) values.get(3);
                request.setAttribute("logDate", logDate);
                String price = (String) values.get(4);
                request.setAttribute("price", price);
                String image = (String) values.get(5);
                request.setAttribute("image", image);
                destination = EDIT_DELETE_PAGE;
            } else if (action.equals(ADD_ACTION)) {

                recordsAddCounter = recordsAddCounter + 1;
                session.setAttribute("counter", recordsAddCounter);
//               List values = getParameters(request);

                game = new Videogame(0);
                game.setTitle(request.getParameter("title"));
                Long price = Long.valueOf(request.getParameter("price"));
                game.setPrice(price);
                game.setImageUrl(request.getParameter("imageUrl"));
                game.setSystem(request.getParameter("system"));

//              DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
//                Date date = format.parse(request.getParameter("logDate"));
//              game.setLogDate(date);
                videogameService.edit(game);

                List<Videogame> games = videogameService.findAll();
                request.setAttribute("games", games);
                destination = LIST_PAGE;

            }
            if (action.equals(AJAX_LIST_ACTION)) {
                List<Videogame> videogames = videogameService.findAll();
                JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

                videogames.forEach((videogameObj) -> {
                    jsonArrayBuilder.add(Json.createObjectBuilder()
                            .add("videogameId", videogameObj.getVideogameId())
                            .add("title", videogameObj.getTitle())
                            .add("logDate", videogameObj.getLogDate().toString())
                            .add("system", videogameObj.getSystem())
                            .add("imageUrl", videogameObj.getImageUrl())
                            .add("price", videogameObj.getPrice())
                    );
                });

                JsonArray authorsJson = jsonArrayBuilder.build();
                response.setContentType("application/json");
                out.write(authorsJson.toString());
                out.flush();
                return; // must not continue at bottom!
//         else if (action.equals(DELETE_OR_EDIT_ACTION)) {
//               String submitType =request.getParameter("submit");
//               if(submitType.equals(DELETE_ACTION)){
//               String gameId = request.getParameter("gameId");       
//               videogameService.deleteByGameId("videogame", gameId);
//                 }
//              else if (submitType.equals(UPDATE_ACTION)){
//                videogameService.updateRecord("videogame", getParameters( request), "videogame_id", getParameters( request).get(0));
//                }
//            getListOfVideogamesWithListPageDestination(request, videogameService);
//           }
            }  else if (action.equals(AJAX_FINDBY_ID))
               {
                    out = response.getWriter();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = request.getReader();
                    try {
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } finally {
                        br.close();
                    }

                    String jsonPayload = sb.toString();
                    JsonReader reader = Json.createReader(new StringReader(jsonPayload));
                    JsonObject videogameIdObj = reader.readObject();
                    Videogame videogameObj = videogameService.findById(videogameIdObj.getString("videogameId"));
                    JsonObjectBuilder builder = Json.createObjectBuilder()
                            .add("videogameId", videogameObj.getVideogameId())
                            .add("title", videogameObj.getTitle())
                            .add("logDate", videogameObj.getLogDate().toString())
                            .add("system", videogameObj.getSystem())
                            .add("imageUrl", videogameObj.getImageUrl())
                            .add("price", videogameObj.getPrice());
                    

                    JsonObject authorJson = builder.build();
                    response.setContentType("application/json");
                    out.write(authorJson.toString());
                    out.flush();
                    return;
               }  else if (action.equals(AJAX_DELETE))
               {
                     out = response.getWriter();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = request.getReader();
                    try {
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } finally {
                        br.close();
                    }

                    String jsonPayload = sb.toString();
                    JsonReader reader = Json.createReader(new StringReader(jsonPayload));
                    JsonObject videogameIdObj = reader.readObject();
                    Videogame videogameObj = videogameService.findById(videogameIdObj.getString("videogameId"));
                    
                      
                       JsonObjectBuilder builder = Json.createObjectBuilder()
                            .add("videogameId", videogameObj.getVideogameId())
                                .add("title", videogameObj.getTitle())
                            .add("logDate", videogameObj.getLogDate().toString())
                            .add("system", videogameObj.getSystem())
                            .add("imageUrl", videogameObj.getImageUrl())
                            .add("price", videogameObj.getPrice());

                    JsonObject authorJson = builder.build();
                    response.setContentType("application/json");
                    out.write(authorJson.toString());
                    out.flush();
                    return;
                  
                       
               
                   
                 
               }
            
                    else {
                request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                destination = LIST_PAGE;
            }
        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }

    private List getParameters(HttpServletRequest request) {
        List values = new ArrayList();
//               String gameId = request.getParameter("videogameId");
        String title = request.getParameter("title");
        String system = request.getParameter("system");
        String date = request.getParameter("logDate");
        String price = request.getParameter("price");
        String image = request.getParameter("imageUrl");
//               values.add(gameId);
        values.add(title);
        values.add(system);
        values.add(date);
        values.add(price);
        values.add(image);
        return values;
    }

//    private void getListOfVideogamesWithListPageDestination(HttpServletRequest request, VideogameService vgs) throws Exception{
//               List<Videogame> games = null;
//                games = vgs.getAllGames();
//                request.setAttribute("games", games);
//                destination = LIST_PAGE;
//    }
//    private VideogameService injectDependenciesAndGetVideogameService() throws Exception {
//        Class dbClass = Class.forName(dbStrategyClassName);
//        DbStrategy db = (DbStrategy) dbClass.newInstance();
//        DAOStrategy videogameDao = null;
//        Class daoClass = Class.forName(daoClassName);
//         Constructor constructor =null;
//        try{
//        constructor = daoClass.getConstructor(new Class[]{
//            DbStrategy.class, String.class, String.class, String.class, String.class
//        });
//        }catch(NoSuchMethodException nsme){
//        }
//        
//      if (constructor != null) {
//            Object[] constructorArgs = new Object[]{
//                db, driverClass, url, userName, password
//            };
//            videogameDao = (DAOStrategy) constructor
//                    .newInstance(constructorArgs);
//      }else{
//            Context ctx = new InitialContext();
//            DataSource ds = (DataSource) ctx.lookup("jdbc/videogame_db");
//            constructor = daoClass.getConstructor(new Class[]{
//                DataSource.class, DbStrategy.class
//            });
//            Object[] constructorArgs = new Object[]{
//                ds, db
//            };
//
//            videogameDao = (DAOStrategy) constructor
//                    .newInstance(constructorArgs);
//      }
//            
//             return new VideogameService(videogameDao);
//      }
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
