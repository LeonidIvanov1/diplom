package command.groupscommnad;

import command.ActionCommand;
import command.ValueObject;
import datalayer.GroupDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class GetDisciplineGroupsCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("discipline_id"));
        int specialtyId = Integer.parseInt(request.getParameter("specialty_id"));
        GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
        return new ValueObject(true, groupDAO.getGroupsWithDisciplines(specialtyId, id), null, null);
    }
}
