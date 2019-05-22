package command.disciplinescommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.DisciplineDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class GetDisciplinesIDCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        DisciplineDAO disciplineDAO = DAOServletManager.getDisciplineDAO(request);
        return new ValueObject(true, disciplineDAO.getDisciplinesID(), null, null);
    }
}
