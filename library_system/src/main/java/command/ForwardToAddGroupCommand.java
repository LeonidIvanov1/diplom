package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.DisciplineDAO;
import datalayer.SpecialtyDAO;
import servlet.DAOServletManager;

public class ForwardToAddGroupCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		setSpecialtyData(request);
		setDisciplineData(request);
		return new CommandRedirection(Resourcer.getString("path.page.group"),
				RedirectionType.FORWARD);
	}

	private void setSpecialtyData(HttpServletRequest request) {
		SpecialtyDAO specialtyDAO = DAOServletManager.getSpecialtyDAO(request);
		request.setAttribute(Resourcer.getString("atr.specialtyes"), specialtyDAO.getSpecialtyList());
	}

	private void setDisciplineData(HttpServletRequest request) {
		DisciplineDAO disciplineDAO = DAOServletManager.getDisciplineDAO(request);
		request.setAttribute(Resourcer.getString("atr.disciplines"), disciplineDAO.getDisciplinesList());
	}

	

}
