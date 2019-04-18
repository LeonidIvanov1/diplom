package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.LiteratureDAO;
import servlet.DAOServletManager;

public class DeleteLiteratureCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		deleteLiterature(request);
		return new CommandRedirection(Resourcer.getString("path.page.main"),
				RedirectionType.FORWARD);
	}

	private void deleteLiterature(HttpServletRequest request) {
		LiteratureDAO literatureDAO = DAOServletManager
				.getLiteratureDAO(request);
		String isbn = request.getParameter("deleteLiterature");
		literatureDAO.deleteLiterature(isbn);
	}

}
