package command.userscommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.UserDAO;
import datalayer.data.User;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class GetUserInfoCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        UserDAO userDAO = DAOServletManager.getUserDAO(request);
        int userID = Integer.parseInt(request.getParameter("user_id"));
        User user = userDAO.getUserByID(userID);
        return new ValueObject(true, user, null, null);
    }
}
