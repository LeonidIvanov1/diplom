package command.userscommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.UserDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class GetUserCountCommand implements ActionCommand {

    public static final String SEARH_DATA = "search_data";

    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        UserDAO userDAO = DAOServletManager.getUserDAO(request);
        String data = request.getParameter(SEARH_DATA);
        return new ValueObject(true,userDAO.getUserCount(data),null, null);
    }
}
