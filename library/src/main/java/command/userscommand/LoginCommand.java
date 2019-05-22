package command.userscommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import command.ActionCommand;
import command.ValueObject;
import datalayer.data.User;

import datalayer.UserDAO;
import resourcebundledemo.Resourcer;
import servlet.DAOServletManager;

/**
 * Class describe login command
 */
public class LoginCommand implements ActionCommand {


    String message;
    String error;
    boolean access;

    public ValueObject execute(HttpServletRequest request) {
        checkLogin(request);
        String value = getJSONData(getLoginigUser(request));
        if (access) {
            return new ValueObject(access, getLoginigUser(request), message, error);
        } else {
            return new ValueObject(access, null, message, error);
        }

    }

    private void checkLogin(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserDAO userDAO = DAOServletManager.getUserDAO(request);
        if (userDAO.checkAuthorization(login, password)) {
            userDAO.login(login);
            HttpSession session = request.getSession();
            session.setAttribute(Resourcer.getString("atr.login"), login);

            message = "Пользователь вошел в сеть";
            access = true;
        } else {
            error = "Логин и пароль не совпадают";
            access = false;
        }
    }

    private User getLoginigUser(HttpServletRequest request) {
        UserDAO userDAO = DAOServletManager.getUserDAO(request);
        return userDAO.getUserByLogin(request.getParameter("login"));
    }

    private String getJSONData(User user) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = new Gson();
        return gson.toJson(user);
    }


}