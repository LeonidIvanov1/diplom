package command.specialtiescommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.SpecialtyDAO;
import datalayer.data.Specialty;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class GetSpecialtyDisciplinesCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        int specialtyID = Integer.parseInt(request.getParameter("specialty_id"));
        SpecialtyDAO specialtyDAO = DAOServletManager.getSpecialtyDAO(request);
        return new ValueObject(true, specialtyDAO.getSpecialtyDisciplines(specialtyID), null, null);
    }
}
