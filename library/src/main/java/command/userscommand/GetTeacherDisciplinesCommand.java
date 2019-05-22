package command.userscommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.UserDAO;
import datalayer.data.GroupDiscipline;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetTeacherDisciplinesCommand implements ActionCommand {

    public static final String USER_ID = "user_id";

    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter(USER_ID));
        UserDAO userDAO = DAOServletManager.getUserDAO(request);
        List<GroupDiscipline> groupDisciplines = userDAO.getTeacherDisciplines(id);
        return new ValueObject(true, groupDisciplines, null, null);
    }
}
