package command.specialtiescommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.SpecialtyDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;

public class ChangeSpecialtyCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("specialty_id"));
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        float parameter = Float.parseFloat(request.getParameter("parameter"));
        String description = request.getParameter("description");
        SpecialtyDAO specialtyDAO = DAOServletManager.getSpecialtyDAO(request);
        specialtyDAO.changeSpecialty(name, code, parameter, description, id);
        return new ValueObject(true, null, null, null);
    }
}
