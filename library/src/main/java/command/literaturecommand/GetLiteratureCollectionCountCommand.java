package command.literaturecommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.LiteratureDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class GetLiteratureCollectionCountCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        String search = request.getParameter("search_data");
        LiteratureDAO literatureDAO = DAOServletManager.getLiteratureDAO(request);
        return new ValueObject(true, literatureDAO.getLiteratureCollectionCount(search), null, null);
    }
}
