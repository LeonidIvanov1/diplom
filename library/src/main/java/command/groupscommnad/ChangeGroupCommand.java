package command.groupscommnad;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import command.ActionCommand;
import command.ValueObject;
import datalayer.GroupDAO;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ChangeGroupCommand implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        int specialtyID = Integer.parseInt(request.getParameter("specialty"));
        int groupID = Integer.parseInt(request.getParameter("group_id"));

        GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
        String jsonString = request.getParameter("discipline");
        Gson gson = new GsonBuilder().create();
        List<String> list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
        }.getType());
        groupDAO.changeGroup(name, specialtyID, list, groupID);
        return new ValueObject(true, null, null, null);
    }
}
