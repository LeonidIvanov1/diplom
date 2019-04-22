package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.LiteratureDAO;
import servlet.DAOServletManager;

public class GetLiteratureCollectionsCommand implements ActionCommand {

	public CommandRedirection execute(HttpServletRequest request) {
		setLiteratureCollections(request);
		return new CommandRedirection(Resourcer.getString("path.page.literature"),
				RedirectionType.FORWARD);
	}

	private void setLiteratureCollections(HttpServletRequest request) {
		LiteratureDAO literatureDAO = DAOServletManager
				.getLiteratureDAO(request);
		request.setAttribute(Resourcer.getString("atr.literatureCollections"),
				literatureDAO.getLiteratureCollections());

	}
}
