package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.UserDAO;
import servlet.DAOServletManager;

public class DeleteUserCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		deleteUser(request);
		return new CommandRedirection(Resourcer.getString("path.page.main"),
				RedirectionType.FORWARD);
	}

	private void deleteUser(HttpServletRequest request) {
		UserDAO userDAO = DAOServletManager.getUserDAO(request);
		if (!checkDelete(request)) {
			userDAO.deleteUser(
					request.getParameter(Resourcer.getString("par.deleteUser")));
		}
	}

	private boolean checkDelete(HttpServletRequest request) {
		String activeUserLogin =  (String) request.getSession()
				.getAttribute(Resourcer.getString("atr.login"));
		return activeUserLogin.equals(request
						.getParameter(Resourcer.getString("par.deleteUser")));
	}

}
