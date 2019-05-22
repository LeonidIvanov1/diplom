package command.specialtiescommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.SpecialtyDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class AddSpecialtyCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        float parameter = Float.parseFloat(request.getParameter("parameter"));
        String description = request.getParameter("description");
        SpecialtyDAO specialtyDAO = DAOServletManager.getSpecialtyDAO(request);
        specialtyDAO.addSpecialty(name, code, parameter, description);
        return new ValueObject(true, null, null, null);
    }
}
