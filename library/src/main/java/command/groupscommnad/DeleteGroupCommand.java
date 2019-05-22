package command.groupscommnad;

import command.ActionCommand;
import command.ValueObject;
import datalayer.GroupDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class DeleteGroupCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("group_id"));
        GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
        groupDAO.deleteGroup(id);
        return new ValueObject(true,null, null, null);
    }
}
