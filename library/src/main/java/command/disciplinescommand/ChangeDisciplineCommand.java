package command.disciplinescommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.DisciplineDAO;
import datalayer.data.Discipline;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;


public class ChangeDisciplineCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("discipline_id"));
        String name = request.getParameter("name");
        DisciplineDAO disciplineDAO = DAOServletManager.getDisciplineDAO(request);
        disciplineDAO.changeDiscipline(name, id);
        return new ValueObject(true, null, null, null);
    }
}
