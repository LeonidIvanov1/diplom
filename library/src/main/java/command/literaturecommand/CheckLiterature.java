package command.literaturecommand;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import command.ActionCommand;
import command.ValueObject;
import datalayer.GroupDAO;
import datalayer.data.Discipline;
import datalayer.data.Group;
import datalayer.data.GroupDiscipline;
import datalayer.data.Specialty;
import datalayer.data.literature.LiteratureCollection;
import servlet.DAOServletManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CheckLiterature implements ActionCommand {
    /**
     * Command execution method
     *
     * @param request -- HttpServletRequest
     * @return -- execution result
     */
    @Override
    public ValueObject execute(HttpServletRequest request) {
        int specialtyID = Integer.parseInt(request.getParameter("specialty"));
        int disciplineID = Integer.parseInt(request.getParameter("discipline"));
        Gson gson = new GsonBuilder().create();
        String jsonString = request.getParameter("literatureCollection");
        List<LiteratureCollection> list = gson.fromJson(jsonString, new TypeToken<List<LiteratureCollection>>() {
        }.getType());
        GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
        List<Group> groups = groupDAO.getGroupsWithDisciplines(specialtyID, disciplineID);
        return new ValueObject(true, checkLiteratureFgos(groups, list), null, null);
    }

    private boolean checkLiteratureFgos(List<Group> groups, List<LiteratureCollection> literatureCollections) {
        boolean result = false;
        int studentsCount = 0;
        int literatureCount = 0;
        float standardParameter = groups.get(0).getSpecialty().getStandardParameter();
        for (Group group : groups) {
            studentsCount += group.getStudents().size();
        }
        for (LiteratureCollection literatureCollection : literatureCollections) {
            literatureCount += literatureCollection.getInStockCount();
        }

        return (literatureCount / studentsCount) > standardParameter;
    }
}
