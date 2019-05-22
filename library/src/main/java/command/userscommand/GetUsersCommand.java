package command.userscommand;

import command.ActionCommand;
import command.ValueObject;
import datalayer.UserDAO;
import datalayer.data.User;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetUsersCommand implements ActionCommand {

    public static final String START = "start";
    public static final String END = "end";
    public static final String SEARCH_DATA = "search_data";

    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        return new ValueObject(true, getUsers(request), null, null);
    }

    private List<User> getUsers(HttpServletRequest request) {
        int startPos = Integer.valueOf(request.getParameter(START));
        int endPos = Integer.valueOf(request.getParameter(END));
        UserDAO userDAO = DAOServletManager.getUserDAO(request);
        String searchData = request.getParameter(SEARCH_DATA);
        return userDAO.getUsers(startPos, endPos, searchData);
    }
}
