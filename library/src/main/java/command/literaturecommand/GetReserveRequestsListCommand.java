package command.literaturecommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.LiteratureDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class GetReserveRequestsListCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        LiteratureDAO literatureDAO = DAOServletManager.getLiteratureDAO(request);
        int id = Integer.parseInt(request.getParameter("user_id"));
        return new ValueObject(true, literatureDAO.getReserveRequestList(id), null, null);
    }
}
