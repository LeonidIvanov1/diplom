package command.userscommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.GroupDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class GetGroupsCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
        return new ValueObject(true, groupDAO.getGroupList(), null, null);
    }
}
