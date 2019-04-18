package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.LiteratureDAO;
import servlet.DAOServletManager;

public class ChangeLiteratureCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		changeLiterature(request);
		return new CommandRedirection(
				Resourcer.getString("path.page.main"),
				RedirectionType.FORWARD);

	}

	private void changeLiterature(HttpServletRequest request) {
		LiteratureDAO literatureDAO = DAOServletManager
				.getLiteratureDAO(request);
		String literatureName = request.getParameter("literatureName");
		String literatureAuthor = request.getParameter("literatureAuthor");
		String literatureYear = request.getParameter("literatureYear");
		String literatureDescription = request
				.getParameter("literatureDescription");
		String literatureIbn = request.getParameter("literatureISBN");

		literatureDAO.changeLiteratureDAO(literatureName, literatureAuthor,
				literatureYear, literatureIbn, literatureDescription);
	}

}
