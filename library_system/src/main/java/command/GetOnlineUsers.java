package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.UserDAO;
import servlet.DAOServletManager;

public class GetOnlineUsers implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		getOnlineUsers(request);
		return new CommandRedirection(Resourcer.getString("path.page.users"),
				RedirectionType.FORWARD);
	}

	private void getOnlineUsers(HttpServletRequest request) {
		UserDAO userDAO = DAOServletManager.getUserDAO(request);
		request.setAttribute(Resourcer.getString("atr.users"),
				userDAO.getOnlineUsers());
	}
}
