/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Journal;
import entity.Subject;
import entity.Person;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.JournalFacade;
import session.PersonFacade;
import session.SubjectFacade;

/**
 *
 * @author User
 */
@WebServlet(name = "LoginServlet",loadOnStartup = 1, urlPatterns = {
    "/index",
    "/listPersons",
    "/listSubjects",
    "/listJournals",
})
public class LoginServlet extends HttpServlet {
@EJB private PersonFacade personFacade;
@EJB private SubjectFacade subjectFacade;
@EJB private JournalFacade journalFacade;
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
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if(null != request.getServletPath())switch (request.getServletPath()) {
                case "/showLogin":
                    request.setAttribute("info", "Привет из LoginServlet");
                    request.getRequestDispatcher("/showLogin.jsp")
                            .forward(request, response);
                    break;
                case "/listPersons":
                    List<Person> listPersons= personFacade.findAll();
                    request.setAttribute("listPersons", listPersons);
                    request.setAttribute("info", "Привет из personServlet");
                    request.getRequestDispatcher("/listPersons.jsp")
                            .forward(request, response);
                    break;
                case "/index":
                    request.getRequestDispatcher("/index.jsp")
                            .forward(request, response);
                    break;
                case "/listSubjects":
                    List<Subject> listSubjects= subjectFacade.findAll();
                    request.setAttribute("listSubjects", listSubjects);
                    request.setAttribute("info", "Привет из subjectsServlet");
                    request.getRequestDispatcher("/listSubjects.jsp")
                            .forward(request, response);
                    break;
                case "/listJournals":
                    List<Journal> listJournals= journalFacade.findAll();
                    request.setAttribute("listJournals", listJournals);
                    request.setAttribute("info", "Привет из LoginServlet");
                    request.getRequestDispatcher("/listJournals.jsp")
                            .forward(request, response);
                    break;
                /*case "/login":
                    String login = request.getParameter("login");
                    String password = request.getParameter("password");
                    User user = userFacade.findByLogin(login);
                    if(user == null){
                        request.setAttribute("info", "нет такого пользователя или неправельный пароль");
                        request.getRequestDispatcher("/showLogin")
                                .forward(request, response);
                    }
                    if(!password.equals(user.getPassword())){
                        request.setAttribute("info", "нет такого пользователя или неправельный пароль");
                        request.getRequestDispatcher("/showLogin")
                                .forward(request, response);
                    }
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                    session.setAttribute("info", "Привет "+user.getPerson().getFirstame());
                    
                    request.getRequestDispatcher("/index")
                            .forward(request, response);
                    break;
                case"/logout":
                    session = request.getSession(false);
                    if(session != null){
                        session.invalidate();
                    }
                    request.setAttribute("info", "Вы вышли");
                    request.getRequestDispatcher("/index")
                            .forward(request, response);
                    break;*/
                default:
                    break;
            }
        }

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
