package controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Contact;
import model.repository.ContactRepository;

/**
 * Servlet implementation class DeleteContactController
 */
public class ContactUpdateController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(ContactUpdateController.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactUpdateController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ContactRepository contactRepository = ContactRepository.getInstance();
        String id = request.getParameter("id");

        if (id != null && !id.equals("")) {
            //Load contact
            Contact contact = contactRepository.getContact(request.getParameter("id"));

            if (contact == null) {
                request.setAttribute("message", "El usuario que intenta editar no existe");
                request.getRequestDispatcher("contactlist").forward(request, response);
            } else {
                log.log(Level.FINE, "Processing GET request: contact" + contact.getName() + " loaded.");

                //Send contact to contactEditView
                request.setAttribute("contact", contact);
                request.getRequestDispatcher("/contactEditView.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("message", "El contacto que intenta editar no existe");
            request.getRequestDispatcher("/contactlist").forward(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContactRepository contactRepository = ContactRepository.getInstance();
        String id = request.getParameter("id");
        String nombre = request.getParameter("name");
        String telefono = request.getParameter("phone");

        if (nombre == null || nombre.equals("") || telefono == null || telefono.equals("")) {
            request.setAttribute("message", "Los datos introducidos no son válidos");
        } else {
            //Load contact
            Contact contact = contactRepository.getContact(id);

            if (contact == null) {
                request.setAttribute("message", "El usuario que intenta editar no existe");
                request.getRequestDispatcher("contactlist").forward(request, response);
            } else {
                //Update attributes
                contact.setName(nombre);
                contact.setTelephone(telefono);

                //Update contact
                contactRepository.updateContact(contact);
                request.setAttribute("message", "Contacto actualizado con éxito");
                log.log(Level.FINE, "Processing POST request: contact" + contact.getId() + " updated.");
            }
        }

        request.getRequestDispatcher("contactlist").forward(request, response);
    }

}
