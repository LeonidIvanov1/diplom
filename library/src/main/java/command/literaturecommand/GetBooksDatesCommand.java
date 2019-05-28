package command.literaturecommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.LiteratureDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class GetBooksDatesCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("user_id"));
        LiteratureDAO literatureDAO = DAOServletManager.getLiteratureDAO(request);
        return new ValueObject(true, literatureDAO.getLiteratureDates(id), null, null);
    }
}
