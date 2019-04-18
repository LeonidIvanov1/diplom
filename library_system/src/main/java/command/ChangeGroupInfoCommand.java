package command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.GroupDAO;
import servlet.DAOServletManager;

public class ChangeGroupInfoCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		changeGroupInfo(request);
		return new CommandRedirection(Resourcer.getString("path.page.main"),
				RedirectionType.FORWARD);
	}

	private void changeGroupInfo(HttpServletRequest request) {
		GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
		List<String> disciplines = new ArrayList<String>();
		if (request.getParameterValues(
				Resourcer.getString("par.groupDisciplines")) != null) {
			disciplines = Arrays.asList(request.getParameterValues(
					Resourcer.getString("par.groupDisciplines")));
		}
		groupDAO.changeGroup(
				request.getParameter(Resourcer.getString("par.groupName")),
				request.getParameter(Resourcer.getString("par.groupSpecialty")),
				disciplines);
	}

}
