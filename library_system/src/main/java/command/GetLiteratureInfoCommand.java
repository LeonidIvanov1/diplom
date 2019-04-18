package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.LiteratureDAO;
import servlet.DAOServletManager;

public class GetLiteratureInfoCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		setLiteratureInfo(request);
		return new CommandRedirection(Resourcer.getString("path.page.book"),
				RedirectionType.FORWARD);
	}

	private void setLiteratureInfo(HttpServletRequest request) {
		LiteratureDAO literatureDAO = DAOServletManager
				.getLiteratureDAO(request);
		String isbn = request.getParameter("literatureIsbn");
		request.setAttribute(Resourcer.getString("atr.literatureInfo"),
				literatureDAO.getLiteratureInfo(isbn));

	}

}
