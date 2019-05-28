package command.literaturecommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.LiteratureDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class DeleteReserveRequestCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        int reserveID = Integer.parseInt(request.getParameter("reserve_id"));
        LiteratureDAO literatureDAO = DAOServletManager.getLiteratureDAO(request);
        literatureDAO.deleteReserveRequest(reserveID);
        return new ValueObject(true, null, null, null);
    }
}
