package command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.GroupDAO;
import servlet.DAOServletManager;

public class AddGroupCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		addGroup(request);
		return new CommandRedirection(Resourcer.getString("path.page.main"),
				RedirectionType.FORWARD);
	}

	private void addGroup(HttpServletRequest request) {
		GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
		String groupName = request
				.getParameter(Resourcer.getString("par.groupName"));
		String specialty = request
				.getParameter(Resourcer.getString("par.groupSpecialty"));
		List<String> disciplines = new ArrayList<String>();
		if (request.getParameterValues(
				Resourcer.getString("par.groupDisciplines")) != null) {
			disciplines = Arrays.asList(request.getParameterValues(
					Resourcer.getString("par.groupDisciplines")));
		}
		groupDAO.addGroup(groupName, specialty, disciplines);
	}

}
