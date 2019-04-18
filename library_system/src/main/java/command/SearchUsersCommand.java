package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.UserDAO;
import servlet.DAOServletManager;

public class SearchUsersCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		searchUsers(request);
		return new CommandRedirection(Resourcer.getString("path.page.users"),
				RedirectionType.FORWARD);
	}

	private void searchUsers(HttpServletRequest request) {
		UserDAO userDAO = DAOServletManager.getUserDAO(request);
		request.setAttribute(Resourcer.getString("atr.users"),
				userDAO.searchUsers(request
						.getParameter(Resourcer.getString("par.search"))));
	}
}
