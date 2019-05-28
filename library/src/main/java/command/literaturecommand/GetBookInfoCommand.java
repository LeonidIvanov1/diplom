package command.literaturecommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.LiteratureDAO;
import datalayer.data.literature.Literature;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class GetBookInfoCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        int bookID = Integer.parseInt(request.getParameter("book_id"));
        LiteratureDAO literatureDAO = DAOServletManager.getLiteratureDAO(request);
        return new ValueObject(true, literatureDAO.getLiteratureInfo(bookID), null, null);
    }
}
