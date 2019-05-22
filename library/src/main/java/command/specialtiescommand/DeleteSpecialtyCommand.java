package command.specialtiescommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.SpecialtyDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class DeleteSpecialtyCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("specialty_id"));
        SpecialtyDAO specialtyDAO = DAOServletManager.getSpecialtyDAO(request);
        specialtyDAO.deleteSpecialty(id);
        return new ValueObject(true, null, null, null);
    }
}
