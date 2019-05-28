package command.userscommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.GroupDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class GetTeacherGroupsCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        int userID = Integer.parseInt(request.getParameter("user_id"));
        GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
        return new ValueObject(true, groupDAO.getTeacherDisciplines(userID), null, null);
    }
}
