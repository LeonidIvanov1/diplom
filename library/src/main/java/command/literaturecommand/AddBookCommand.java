package command.literaturecommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.LiteratureDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class AddBookCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String year = request.getParameter("year");
        String isbn = request.getParameter("isbn");
        String description = request.getParameter("description");
        String rental = request.getParameter("rental");
        int status = Integer.parseInt(request.getParameter("status"));
        int type = Integer.parseInt(request.getParameter("type"));


        LiteratureDAO literatureDAO = DAOServletManager.getLiteratureDAO(request);
        literatureDAO.addLiterature(name, author, year, isbn, description, rental, status, type);

        return new ValueObject(true, null, null, null);
    }
}
