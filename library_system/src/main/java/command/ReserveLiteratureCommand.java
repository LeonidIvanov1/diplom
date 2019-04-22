package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.GroupDAO;
import datalayer.LiteratureDAO;
import servlet.DAOServletManager;
import datalayer.data.Group;
import datalayer.data.literature.LiteratureCollection;

public class ReserveLiteratureCommand implements ActionCommand {

	public CommandRedirection execute(HttpServletRequest request) {
		reserveLiterature(request);
		return new CommandRedirection(Resourcer.getString("path.page.main"),
				RedirectionType.FORWARD);
	}

	private void reserveLiterature(HttpServletRequest request) {
		LiteratureDAO literatureDAO = DAOServletManager
				.getLiteratureDAO(request);

		if (checkReserve(request)) {
			LiteratureCollection literatureCollection = getLiteratureCollection(
					request);
			Group group = getReserveGroups(request);
			literatureDAO.reserveLiterature(literatureCollection.getName(),
					literatureCollection.getAuthor(),
					literatureCollection.getYear(), group.getName());
			request.setAttribute("result", Resourcer.getString("message.reserveGood"));
		} else {
			request.setAttribute("result", Resourcer.getString("message.reserveBad"));
		}
	}

	private boolean checkReserve(HttpServletRequest request) {
		LiteratureCollection literatureCollection = getLiteratureCollection(
				request);
		Group group = getReserveGroups(request);
		return !(group.getStudents().size() > literatureCollection
				.getInStockCount());
	}

	private Group getReserveGroups(HttpServletRequest request) {
		GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
		String groupName = request.getParameter("group");
		Group group = groupDAO.getGroupInfo(groupName);
		return group;
	}

	private LiteratureCollection getLiteratureCollection(
			HttpServletRequest request) {
		LiteratureDAO literatureDAO = DAOServletManager
				.getLiteratureDAO(request);
		String name = request.getParameter("literatureName");
		String author = request.getParameter("literatureAuthor");
		String year = request.getParameter("literatureYear");
		LiteratureCollection literatureCollection = literatureDAO
				.getLiteratureCollection(name, author, year);
		return literatureCollection;
	}

}
