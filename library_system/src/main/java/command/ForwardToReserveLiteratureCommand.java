package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.DisciplineDAO;
import datalayer.GroupDAO;
import datalayer.LiteratureDAO;
import servlet.DAOServletManager;
import datalayer.data.literature.LiteratureCollection;

public class ForwardToReserveLiteratureCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		setLiteratureCollection(request);
		setGroupData(request);
		setDisciplineData(request);
		return new CommandRedirection(Resourcer.getString("path.page.reserve"),
				RedirectionType.FORWARD);
	}

	private void setDisciplineData(HttpServletRequest request) {
		DisciplineDAO disciplineDAO = DAOServletManager
				.getDisciplineDAO(request);
		String teacherLogin = (String) request.getSession()
				.getAttribute(Resourcer.getString("atr.login"));
		request.setAttribute(Resourcer.getString("atr.disciplines"),
				disciplineDAO.getTeacherDisciplines(teacherLogin));
	}

	private void setGroupData(HttpServletRequest request) {
		GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
		String teacherLogin = (String) request.getSession()
				.getAttribute(Resourcer.getString("atr.login"));
		request.setAttribute(Resourcer.getString("atr.groups"),
				groupDAO.getTeacherGroups(teacherLogin));
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
	
	 

}
