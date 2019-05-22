package command.userscommand;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import command.ActionCommand;
import command.ValueObject;
import datalayer.UserDAO;
import datalayer.data.GroupDiscipline;
import datalayer.data.Role;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ChangeUserCommand implements ActionCommand {

    public static final String ROLE = "role";
    public static final String FIO = "fio";
    public static final String PASSWORD = "password";
    public static final String LOGIN = "login";
    public static final String GROUP = "student_group";
    public static final String LIB_CARD = "lib_card";
    public static final String USER_ID = "user_id";

    private String role;
    private String fio;
    private String login;
    private String password;
    private int userID;

    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        role = request.getParameter(ROLE);
        fio = request.getParameter(FIO);
        password = request.getParameter(PASSWORD);
        userID = Integer.parseInt(request.getParameter(USER_ID));
        String message = "Пользователь был успешно изменен";
        String error = "При изменение пользователя произошла ошибка";
        boolean result = false;
        switch (Role.valueOf(role.toUpperCase())) {
            case ADMIN:
                result = changeAdministrator(request);
                break;
            case LIBRARIAN:
                result = changeLibrarian(request);
                break;
            case TEACHER:
                result = changeTeacher(request);
                break;
            case STUDENT:
                result = changeStudent(request);
                break;
        }
        if (result) {
            error = null;
        }
        return new ValueObject(true, null, message, error);
    }

    private boolean changeStudent(HttpServletRequest request) {
        try {
            UserDAO userDAO = DAOServletManager.getUserDAO(request);
            int group = Integer.parseInt(request.getParameter(GROUP));
            String libCardNumber = request.getParameter(LIB_CARD);
            userDAO.changeStudent(userID, fio, password, group, libCardNumber);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean changeTeacher(HttpServletRequest request) {
        Gson gson = new GsonBuilder().create();
        String jsonString = request.getParameter("groupDiscipline");
        List<GroupDiscipline> list = gson.fromJson(jsonString, new TypeToken<List<GroupDiscipline>>() {
        }.getType());
        try {
            UserDAO userDAO = DAOServletManager.getUserDAO(request);
            userDAO.changeTeacher(userID, fio, password, list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean changeLibrarian(HttpServletRequest request) {
        try {
            UserDAO userDAO = DAOServletManager.getUserDAO(request);
            userDAO.changeLibrarian(userID, fio, password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean changeAdministrator(HttpServletRequest request) {
        try {
            UserDAO userDAO = DAOServletManager.getUserDAO(request);
            userDAO.changeAdministrator(userID, fio, password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
