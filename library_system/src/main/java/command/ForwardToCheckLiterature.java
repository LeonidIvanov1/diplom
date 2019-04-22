package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.DisciplineDAO;
import datalayer.LiteratureDAO;
import datalayer.SpecialtyDAO;
import servlet.DAOServletManager;
import datalayer.data.literature.LiteratureCollection;

public class ForwardToCheckLiterature implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		setSpecialitiesData(request);
		setDisciplineData(request);
		setLiteratureCollection(request);
		return new CommandRedirection(Resourcer.getString("path.page.reserve"),
				RedirectionType.FORWARD);
	}

	private void setDisciplineData(HttpServletRequest request) {
		DisciplineDAO disciplineDAO = DAOServletManager
				.getDisciplineDAO(request);
		request.setAttribute(Resourcer.getString("atr.disciplines"),
				disciplineDAO.getDisciplinesList());
	}

	private void setLiteratureCollection(HttpServletRequest request) {

		LiteratureDAO literatureDAO = DAOServletManager
				.getLiteratureDAO(request);
		LiteratureCollection literatureCollection = literatureDAO
				.getLiteratureCollection(request.getParameter("literatureName"),
						request.getParameter("literatureAuthor"),
						request.getParameter("literatureYear"));
		request.setAttribute(Resourcer.getString("atr.literatureCollection"),
				literatureCollection);

	}

	private void setSpecialitiesData(HttpServletRequest request) {
		SpecialtyDAO specialtyDAO = DAOServletManager.getSpecialtyDAO(request);
		request.setAttribute(Resourcer.getString("atr.specialtyes"),
				specialtyDAO.getSpecialtyList());
	}

}
