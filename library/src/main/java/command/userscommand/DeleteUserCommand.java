package command.userscommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.UserDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class DeleteUserCommand implements ActionCommand {

    public static final String USER_ID = "user_id";

    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        UserDAO userDAO = DAOServletManager.getUserDAO(request);
        int id = Integer.parseInt(request.getParameter(USER_ID));
        userDAO.deleteUser(id);
        return new ValueObject(true, null, null, null);
    }
}
