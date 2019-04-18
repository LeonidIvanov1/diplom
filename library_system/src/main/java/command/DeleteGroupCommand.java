package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.GroupDAO;
import servlet.DAOServletManager;

public class DeleteGroupCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		deleteGroup(request);
		return new CommandRedirection(Resourcer.getString("path.page.main"),
				RedirectionType.FORWARD);
	}

	private void deleteGroup(HttpServletRequest request) {
		GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
		groupDAO.deleteGroup(request.getParameter("deleteGroup"));
	}

}
