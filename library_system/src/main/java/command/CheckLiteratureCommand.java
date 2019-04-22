package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.LiteratureDAO;
import servlet.DAOServletManager;
import datalayer.data.literature.LiteratureCollection;

public class CheckLiteratureCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		setLiteratureInfo(request);
		if (checkLiteratureCommand(request)) {
			request.setAttribute("result",
					Resourcer.getString("message.checkGood"));
		} else {
			request.setAttribute("result",
					Resourcer.getString("message.checkBad"));
		}
		return new CommandRedirection(Resourcer.getString("path.page.main"),
				RedirectionType.FORWARD);
	}

	private boolean checkLiteratureCommand(HttpServletRequest request) {
		LiteratureDAO literatureDAO = DAOServletManager
				.getLiteratureDAO(request);
		String name = request
				.getParameter(Resourcer.getString("par.literatureName"));
		String author = request
				.getParameter(Resourcer.getString("par.literatureAuthor"));
		String year = request
				.getParameter(Resourcer.getString("par.literatureYear"));
		String discipline = request
				.getParameter(Resourcer.getString("par.checkDisciplines"));
		String specialty = request
				.getParameter(Resourcer.getString("par.checkSpecialty"));
		return literatureDAO.checkLiterature(name, author, year, discipline,
				specialty);
	}

	private void setLiteratureInfo(HttpServletRequest request) {

		LiteratureDAO literatureDAO = DAOServletManager
				.getLiteratureDAO(request);
		LiteratureCollection literatureCollection = literatureDAO
				.getLiteratureCollection(request.getParameter("literatureName"),
						request.getParameter("literatureAuthor"),
						request.getParameter("literatureYear"));
		request.setAttribute(Resourcer.getString("atr.literatureCollection"),
				literatureCollection);

	}
}
