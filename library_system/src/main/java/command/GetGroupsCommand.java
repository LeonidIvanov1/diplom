package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.GroupDAO;
import servlet.DAOServletManager;

public class GetGroupsCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		setGroupData(request);

		return new CommandRedirection(Resourcer.getString("path.page.groups"),
				RedirectionType.FORWARD);

	}

	private void setGroupData(HttpServletRequest request) {
		GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
		request.setAttribute(Resourcer.getString("atr.groups"),
				groupDAO.getGroupList());
	}

}
