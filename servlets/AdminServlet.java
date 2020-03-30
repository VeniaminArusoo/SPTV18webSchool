/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Person;
import entity.Journal;
import entity.Subject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.PersonFacade;
import session.JournalFacade;
import session.SubjectFacade;

/**
 *
 * @author User
 */
@WebServlet(name = "AdminServlet",loadOnStartup = 1, urlPatterns = {
    "/addPerson",
    "/showNewPerson",
    "/addSubject",
    "/showNewSubject",
    "/addJournal",
    "/showNewJournal",
})
public class AdminServlet extends HttpServlet {
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
            switch (request.getServletPath()) {
                case "/showNewSubject":
                    request.getRequestDispatcher("/showNewSubject.jsp")
                            .forward(request, response);
                    break;
                case "/addSubject":
                    String subjectName = request.getParameter("subjectName");
                    String hours = request.getParameter("hours");
                    if(subjectName == null || "".equals(subjectName)
                            || hours == null || "".equals(hours)){
                        request.setAttribute("subjectName", subjectName);
                        request.setAttribute("hours", hours);
                        request.setAttribute("info", "Заполните все поля!");
                        request.getRequestDispatcher("/showNewSubject.jsp")
                                .forward(request, response);
                }
                Subject subject = new Subject();
                subject.setSubjectName(subjectName);
                subject.setHours(Integer.parseInt(hours));
                subjectFacade.create(subject);
                request.setAttribute("info", "Предмет \""+subject.getSubjectName()+"\" добавлен в базу");
                request.getRequestDispatcher("/index")
                        .forward(request, response);
                    break;
                case "/showNewPerson":
                    request.getRequestDispatcher("/showNewPerson.jsp")
                            .forward(request, response);
                    break;
                case "/addPerson":
                    String firstName = request.getParameter("firstName");
                    String secondName = request.getParameter("secondName");
                    String status = request.getParameter("status");
                    if(firstName == null || "".equals(firstName)
                            || secondName == null || "".equals(secondName)
                            || status == null || "".equals(status)){
                        request.setAttribute("firstName", firstName);
                        request.setAttribute("lastName", secondName);
                        request.setAttribute("phone", status);
                        request.setAttribute("info", "Заполните все поля!");
                        request.getRequestDispatcher("/showNewPerson.jsp")
                                .forward(request, response);
                }
                Person person = null;
                try{
                    person = new Person(firstName, secondName, status);
                    personFacade.create(person);
                } catch(Exception e) {
                    if(person != null){
                        personFacade.remove(person);
                    }
                }
                request.setAttribute("info", "Человек \""+person.getFirstName()+" "+
                    person.getSecondName()+" "+
                    person.getStatus()+"\" добавлена в базу");
                request.getRequestDispatcher("/index")
                        .forward(request, response);
                    break;
                case "/showNewJournal":
                List<Person> listPersons = personFacade.findAll();
                List<Subject> listSubjects = subjectFacade.findAll();
                request.setAttribute("listPersons", listPersons);
                request.setAttribute("listSubjects", listSubjects);
                request.getRequestDispatcher("/showNewJournal.jsp")
                        .forward(request, response);
                    break;
                case "/addJournal":
                String personId = request.getParameter("personId");
                String subjectId = request.getParameter("subjectId");
                if(personId == null || "".equals(personId)
                        || subjectId == null || "".equals(subjectId)){
                    request.setAttribute("personId", personId);
                    request.setAttribute("subjectId", subjectId);
                    request.setAttribute("info", "Выберите и читателя и книгу");
                }
                request.getRequestDispatcher("/index")
                            .forward(request, response);
                    break;
                /*case "/showNewJournal":
                List<Subject> listSubjects = subjectFacade.findAll();
                List<Person> listPersons = personFacade.findAll();
                request.setAttribute("listSubjects", listSubjects);
                request.setAttribute("listPersons", listPersons);
                request.getRequestDispatcher("/showNewJournal.jsp")
                        .forward(request, response);
                break;*/
                /*case "/showNewJournal":
                String person = request.getParameter("person");
                String subject = request.getParameter("subject");
                if(readerId == null || "".equals(readerId)
                        || bookId == null || "".equals(bookId)){
                    request.setAttribute("readerId", readerId);
                    request.setAttribute("bookId", bookId);
                    request.setAttribute("info", "Выберите и читателя и книгу");
                    request.getRequestDispatcher("/showTakeBook")
                            .forward(request, response);
                }
                reader = readerFacade.find(Long.parseLong(readerId));
                book = bookFacade.find(Long.parseLong(bookId));
                Calendar c = Calendar.getInstance();
                if(book.getBookNum()-1 >= 0){
                    History history = new History(book, reader, c.getTime(), null);
                    book.setBookNum(book.getBookNum()-1);
                    bookFacade.edit(book);
                    historyFacade.create(history);
                    request.setAttribute("info", "Читателю "
                                            + reader.getFirstname()
                                            + " "
                                            + reader.getLastname()
                                            + " выдана книга: "
                                            + book.getTitle()
                                    );
                }else{
                    request.setAttribute("info", "Нет в наличии данной книги");
                }
                request.getRequestDispatcher("/index")
                            .forward(request, response);
                break;
                case "/showReturnBook":
                    List<History> listHistories = historyFacade.findByReturnNull();
                    request.setAttribute("listHistories", listHistories);
                    request.getRequestDispatcher("/showReturnBook.jsp")
                    .forward(request, response);
                break;
                case "/returnBook":
                    String historyId = request.getParameter("historyId");
                    if(historyId == null || "".equals(historyId)){
                        request.setAttribute("info", "Выберите книгу!");
                        request.getRequestDispatcher("/showReturnBook.jsp")
                        .forward(request, response);
                break;
                }
                History history = historyFacade.find(Long.parseLong(historyId));
                book = history.getBook();
                boolean isNotReturnBook = book.getQuantity() <= book.getBookNum()+1;
                if(!isNotReturnBook){
                request.setAttribute("info", "Все книги с таким названием уже возвращены!");
                request.getRequestDispatcher("/showReturnBook")
                .forward(request, response);
                break;
                }
                book.setBookNum(book.getBookNum()+1);
                bookFacade.edit(book);
                history.setReturnBook(Calendar.getInstance().getTime());
                historyFacade.edit(history);
                request.setAttribute("info", "Книга возвращена!");
                request.getRequestDispatcher("/showReturnBook")
                .forward(request, response);
                break;*/
                
            
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
