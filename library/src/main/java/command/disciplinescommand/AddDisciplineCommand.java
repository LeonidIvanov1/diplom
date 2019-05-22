package command.disciplinescommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.DisciplineDAO;
import datalayer.data.Discipline;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class AddDisciplineCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        DisciplineDAO disciplineDAO = DAOServletManager.getDisciplineDAO(request);
        disciplineDAO.addDiscipline(name);
        return new ValueObject(true, null, null, null);
    }
}
