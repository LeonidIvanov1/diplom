package command.disciplinescommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.DisciplineDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class GetDisciplineCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("discipline_id"));
        DisciplineDAO disciplineDAO = DAOServletManager.getDisciplineDAO(request);
        return new ValueObject(true, disciplineDAO.getDisciplineInfo(id), null, null);
    }
}
