package command.literaturecommand;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import command.ActionCommand;
import command.ValueObject;
import datalayer.DisciplineDAO;
import datalayer.LiteratureDAO;
import datalayer.data.literature.LiteratureCollection;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CreateReserveRequest implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        Gson gson = new GsonBuilder().create();
        String jsonString = request.getParameter("literatureCollection");
        List<LiteratureCollection> list = gson.fromJson(jsonString, new TypeToken<List<LiteratureCollection>>(){}.getType());
        int groupID = Integer.parseInt(request.getParameter("group_id"));
        int teacher = Integer.parseInt(request.getParameter("teacher"));
        DisciplineDAO disciplineDAO = DAOServletManager.getDisciplineDAO(request);

        int discipline = disciplineDAO.getDisciplineIDByName(request.getParameter("discipline"));
        LiteratureDAO literatureDAO = DAOServletManager.getLiteratureDAO(request);
        literatureDAO.createReserveRequest(groupID, teacher, discipline,list );
        return new ValueObject(true, null, null, null);
    }
}
