package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.LiteratureDAO;
import servlet.DAOServletManager;

public class SearchLiteratureCollectionsCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		searchLiteratureCollections(request);
		return new CommandRedirection(
				Resourcer.getString("path.page.literature"),
				RedirectionType.FORWARD);
	}

	private void searchLiteratureCollections(HttpServletRequest request) {
		LiteratureDAO literatureDAO = DAOServletManager
				.getLiteratureDAO(request);
		request.setAttribute(Resourcer.getString("atr.literatureCollections"),
				literatureDAO.searchLiteratureCollections(request
						.getParameter(Resourcer.getString("par.search"))));

	}

}
