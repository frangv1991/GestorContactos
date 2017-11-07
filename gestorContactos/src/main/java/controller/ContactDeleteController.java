package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Contact;

import model.repository.ContactRepository;

/**
 * Servlet implementation class ContactDeleteController
 */
public class ContactDeleteController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(ContactUpdateController.class.getName());

    public ContactDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ContactRepository contactRepository = ContactRepository.getInstance();
        String id = request.getParameter("id");

        if (id != null && !id.equals("")) {
            //Load contact
            Contact contact = contactRepository.getContact(id);

            //Delete contact
            contactRepository.deleteContact(request.getParameter("id"));
            request.setAttribute("message", "Contact deleted successfully");
            log.log(Level.FINE, "Processing GET request: contact deleted.");
        }

        request.getRequestDispatcher("contactlist").forward(request, response);   
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
