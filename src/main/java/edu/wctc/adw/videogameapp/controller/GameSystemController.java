package edu.wctc.adw.videogameapp.controller;

import edu.wctc.adw.videogameapp.entity.System;
import edu.wctc.adw.videogameapp.entity.Videogame;
import edu.wctc.adw.videogameapp.service.SystemService;

import edu.wctc.adw.videogameapp.service.VideogameService;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
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
 * The main controller for videogame-related activities
 *
 * @videogame jlombardo
 *
 * String className = getServletContext(). getInitParameter("dbStrategy"); Class
 * dbClass = Class.forName(ClassName); DBStrategy db (DBStrategy)
 * dbClass.newInstance();
 */
//@WebServlet(name = "systemController", urlPatterns = {"/systemController"})
public class GameSystemController extends HttpServlet {

    // NO MAGIC NUMBERS!
    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/systemList.jsp";
    private static final String ADD_PAGE = "/addSystem.jsp";
    private static final String EDIT_DELETE_PAGE = "/editDeleteSystem.jsp";
    private static final String LIST_ACTION = "list";
    private static final String ADD_BUTTON = "addButton";
    private static final String EDIT_DELETE_BUTTON = "editDeleteButton";
    private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";
    private static final String ACTION_REDIRECT = "redirect";

        // Get init params from web.xml
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

        System system = null;
        Videogame videogame = null;

        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        SystemService systemService = (SystemService) ctx.getBean("systemService");
        VideogameService videogameService = (VideogameService) ctx.getBean("videogameService");

        try {

            if (action.equals(LIST_ACTION)) {

                getListOfSystemsWithListPageDestination(request, systemService);

            } else if (action.equals(ADD_BUTTON)) {
                destination = ADD_PAGE;
            } else if (action.equals(EDIT_DELETE_BUTTON)) {

                String systemId = request.getParameter("systemId");

//       
                       system = systemService.findByIdAndFetchGamesEagerly(systemId);
                       if(system == null){
                       system = systemService.findById(systemId);
                       }

                request.setAttribute("system", system);
                List<Videogame> videogames = videogameService.findAll();
                request.setAttribute("videogames", videogames);

                destination = EDIT_DELETE_PAGE;

            } else if (action.equals(ADD_ACTION)) {
                String systemName = request.getParameter("systemName");

                system = new System(0);
                system.setSystemName(systemName);

                systemService.edit(system);

                this.getListOfSystemsWithListPageDestination(request, systemService);
            } else if (action.equals(DELETE_ACTION)) {

                String systemId = request.getParameter("systemId");
                
          
                system = systemService.findById(systemId);

                String submitType = request.getParameter("submit");
                if (submitType.equals("delete")) {

                    systemService.remove(system);
                    getListOfSystemsWithListPageDestination(request, systemService);
                } else if (submitType.equals("update")) {

                    String systemName = request.getParameter("systemName");

                    system.setSystemName(systemName);

                    systemService.edit(system);
                    getListOfSystemsWithListPageDestination(request, systemService);
                }

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

    private void getListOfSystemsWithListPageDestination(HttpServletRequest request, SystemService ss) throws Exception {

        List<System> systems = systems = ss.findAll();
        request.setAttribute("systems", systems);
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
