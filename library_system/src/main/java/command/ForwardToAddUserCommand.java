package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.DisciplineDAO;
import datalayer.GroupDAO;
import servlet.DAOServletManager;

public class ForwardToAddUserCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		setGroupsData(request);
		setDisciplinesData(request);
		return new CommandRedirection(Resourcer.getString("path.page.user"),
				RedirectionType.FORWARD);
	}

	private void setGroupsData(HttpServletRequest request) {
		GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
		request.setAttribute("groups", groupDAO.getGroupList());
	}

	private void setDisciplinesData(HttpServletRequest request) {
		DisciplineDAO disciplineDAO = DAOServletManager
				.getDisciplineDAO(request);
		request.setAttribute("disciplines", disciplineDAO.getDisciplinesList());
	}

}
