package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.LiteratureDAO;
import servlet.DAOServletManager;

public class AddLiteratureCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		addLiterature(request);
		return new CommandRedirection(
				Resourcer.getString("path.page.main"),
				RedirectionType.FORWARD);
	}

	private void addLiterature(HttpServletRequest request) {
		LiteratureDAO literatureDAO = DAOServletManager
				.getLiteratureDAO(request);

		String literatureName = request.getParameter("literatureName");
		String literatureAuthor = request.getParameter("literatureAuthor");
		String literatureYear = request.getParameter("literatureYear");
		String literatureDescription = request
				.getParameter("literatureDescription");
		String literatureIbn = request.getParameter("literatureISBN");
		
		literatureDAO.addLiterature(literatureName, literatureAuthor, literatureYear, literatureIbn, literatureDescription);
	}
}
