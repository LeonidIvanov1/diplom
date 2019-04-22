package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.DisciplineDAO;
import datalayer.GroupDAO;
import datalayer.SpecialtyDAO;
import servlet.DAOServletManager;
import datalayer.data.Group;

public class GetGroupInfoCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		setSpecialtyData(request);
		setDisciplineData(request);
		setGroupInfo(request);
		return new CommandRedirection(Resourcer.getString("path.page.group"),
				RedirectionType.FORWARD);
	}

	private void setGroupInfo(HttpServletRequest request) {
		GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
		Group group = groupDAO.getGroupInfo(
				request.getParameter(Resourcer.getString("par.groupid")));
		setGroupDisciplines(request, group.getName());
		request.setAttribute(Resourcer.getString("atr.groupinfo"), group);
	}

	private void setSpecialtyData(HttpServletRequest request) {
		SpecialtyDAO specialtyDAO = DAOServletManager.getSpecialtyDAO(request);
		request.setAttribute(Resourcer.getString("atr.specialtyes"),
				specialtyDAO.getSpecialtyList());
	}

	private void setDisciplineData(HttpServletRequest request) {
		DisciplineDAO disciplineDAO = DAOServletManager
				.getDisciplineDAO(request);
		request.setAttribute(Resourcer.getString("atr.disciplines"),
				disciplineDAO.getDisciplinesList());

	}

	private void setGroupDisciplines(HttpServletRequest request,
			String groupName) {
		DisciplineDAO disciplineDAO = DAOServletManager
				.getDisciplineDAO(request);
		request.setAttribute(Resourcer.getString("atr.groupdisciplines"),
				disciplineDAO.getGroupDisciplines(groupName));
	}

}
