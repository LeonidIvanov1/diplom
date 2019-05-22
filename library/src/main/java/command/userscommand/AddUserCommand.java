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

public class AddUserCommand implements ActionCommand {

    public static final String ROLE = "role";
    public static final String FIO = "fio";
    public static final String PASSWORD = "password";
    public static final String LOGIN = "login";
    public static final String GROUP = "student_group";
    public static final String LIB_CARD = "lib_card";

    private String role;
    private String fio;
    private String login;
    private String password;

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
        login = request.getParameter(LOGIN);
        password = request.getParameter(PASSWORD);
        String message = "Пользователь был успешно добавлен";
        String error = "При добавлении пользователя была допущена ошибка";
        boolean result = false;
        switch (Role.valueOf(role.toUpperCase())) {
            case ADMIN:
                result = addAdministrator(request);
                break;
            case LIBRARIAN:
                result = addLibrarian(request);
                break;
            case TEACHER:
                result = addTeacher(request);
                break;
            case STUDENT:
                result = addStudent(request);
                break;
        }
        if (result) {
            error = null;
        }
        return new ValueObject(true, null, message, error);
    }

    private boolean addStudent(HttpServletRequest request) {
        try {
            UserDAO userDAO = DAOServletManager.getUserDAO(request);
            String group = request.getParameter(GROUP);
            String libCardNumber = request.getParameter(LIB_CARD);
            userDAO.addStudent(fio, login, password, group, libCardNumber);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean addTeacher(HttpServletRequest request) {
        Gson gson = new GsonBuilder().create();
        String jsonString = request.getParameter("groupDiscipline");
        List<GroupDiscipline> list = gson.fromJson(jsonString, new TypeToken<List<GroupDiscipline>>(){}.getType());
        try {
            UserDAO userDAO = DAOServletManager.getUserDAO(request);
            userDAO.addTeacher(fio, login, password, list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean addLibrarian(HttpServletRequest request) {
        try {
            UserDAO userDAO = DAOServletManager.getUserDAO(request);
            userDAO.addLibrarian(fio, login, password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean addAdministrator(HttpServletRequest request) {
        try {
            UserDAO userDAO = DAOServletManager.getUserDAO(request);
            userDAO.addAdministrator(fio, login, password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
