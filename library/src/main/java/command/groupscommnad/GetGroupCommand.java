package command.groupscommnad;

import command.ActionCommand;
import command.ValueObject;
import datalayer.GroupDAO;
import datalayer.data.Group;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class GetGroupCommand implements ActionCommand {

    public static final String GROUP_ID = "group_id";

    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
        int groupID = Integer.parseInt(request.getParameter(GROUP_ID));
        return new ValueObject(true, groupDAO.getGroupInfo(groupID), null, null);
    }
}
