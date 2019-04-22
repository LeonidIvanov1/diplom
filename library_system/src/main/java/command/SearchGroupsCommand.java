package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.GroupDAO;
import servlet.DAOServletManager;

public class SearchGroupsCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		searchGroups(request);
		return new CommandRedirection(Resourcer.getString("path.page.groups"),
				RedirectionType.FORWARD);
	}

	private void searchGroups(HttpServletRequest request) {
		GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
		request.setAttribute(Resourcer.getString("atr.groups"),
				groupDAO.searchGroups(request
						.getParameter(Resourcer.getString("par.search"))));

	}

}
