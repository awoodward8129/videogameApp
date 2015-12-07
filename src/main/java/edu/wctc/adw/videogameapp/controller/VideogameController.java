/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.adw.videogameapp.controller;

import edu.wctc.adw.videogameapp.entity.System;
import edu.wctc.adw.videogameapp.entity.Videogame;
import edu.wctc.adw.videogameapp.service.SystemService;
import edu.wctc.adw.videogameapp.service.VideogameService;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
        private static final String LIST_REST_PAGE = "listGameRest.html";
        private static final String ACTION_LIST_REST = "listRest";
    private int userCount = 0;

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
        PrintWriter out = response.getWriter();

        Videogame game = null;

        try {
            HttpSession session = request.getSession();
            ServletContext sctx = request.getServletContext();

            WebApplicationContext ctx
                    = WebApplicationContextUtils.getWebApplicationContext(sctx);
            VideogameService videogameService = (VideogameService) ctx.getBean("videogameService");
            SystemService systemService = (SystemService) ctx.getBean("systemService");
            if (session.isNew()) {
                userCount++;
            }
            sctx.setAttribute("count", userCount);
            Integer recordsAddCounter = (Integer) session.getAttribute("counter");
            if (recordsAddCounter == null) {
                recordsAddCounter = 0;
                session.setAttribute("counter", recordsAddCounter);
            }

            if (action.equals(ACTION_REDIRECT)) {
                response.sendRedirect(REDIRECT_PAGE);
                return;
            } else if (action.equals(LIST_ACTION)) {
                getListOfVideogamesWithListPageDestination(request, videogameService);
            } else if (action.equals(ADD_BUTTON)) {
                destination = ADD_PAGE;
            } else if (action.equals(EDIT_DELETE_BUTTON)) {
                String gameId = request.getParameter("gameId");

                game = videogameService.findById(gameId);
                request.setAttribute("game", game);
                
                      List<System> systems = systemService.findAll();
                    request.setAttribute("systems", systems);
                destination = EDIT_DELETE_PAGE;
            } else if (action.equals(ADD_ACTION)) {

                recordsAddCounter = recordsAddCounter + 1;
                session.setAttribute("counter", recordsAddCounter);

                game = new Videogame(0);
                game.setTitle(request.getParameter("title"));
                game.setSystem(request.getParameter("system"));
                long l = Long.parseLong(request.getParameter("price"));
                game.setPrice(l);
                  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String woot = request.getParameter("logDate");
                    Date date = sdf.parse(woot);
                    game.setLogDate(date);
                game.setLogDate(date);

                game.setImageUrl(request.getParameter("image"));
             
               System  system = systemService.findById(request.getParameter("systemId"));
                
                game.setSystemId(system);
                videogameService.edit(game);
                getListOfVideogamesWithListPageDestination(request, videogameService);
            } else if (action.equals(DELETE_OR_EDIT_ACTION)) {
                
                String gameId = request.getParameter("gameId");
                            game = videogameService.findById(gameId);
          
                String submitType = request.getParameter("submit");
                if (submitType.equals(DELETE_ACTION)) {

                    videogameService.remove(game);
                } else if (submitType.equals(UPDATE_ACTION)) {

                    game.setTitle(request.getParameter("title"));
                    game.setSystem(request.getParameter("system"));
                    long l = Long.parseLong(request.getParameter("price"));
                    game.setPrice(l);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String woot = request.getParameter("logDate");
                    Date date = sdf.parse(woot);
                    game.setLogDate(date);
                    game.setImageUrl(request.getParameter("image"));
                    System system = systemService.findById(request.getParameter("systemId"));
                     game.setSystemId(system);
                    videogameService.edit(game);
                }
                getListOfVideogamesWithListPageDestination(request, videogameService);
            } else if(action.equals(ACTION_LIST_REST)){
            
            destination = LIST_REST_PAGE;
            } else {
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


    private void getListOfVideogamesWithListPageDestination(HttpServletRequest request, VideogameService vgs) throws Exception {

        List games = vgs.findAll();
        request.setAttribute("games", games);
        destination = LIST_PAGE;
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

}
