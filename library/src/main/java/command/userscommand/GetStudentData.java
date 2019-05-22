package command.userscommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.UserDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class GetStudentData implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("user_id"));
        UserDAO userDAO = DAOServletManager.getUserDAO(request);
        return new ValueObject(true, userDAO.getStudentData(id), null, null);
    }
}
