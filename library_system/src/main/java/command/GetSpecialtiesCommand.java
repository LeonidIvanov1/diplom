package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.SpecialtyDAO;
import servlet.DAOServletManager;

public class GetSpecialtiesCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		setSpecialties(request);
		return new CommandRedirection(
				Resourcer.getString("path.page.specialties"),
				RedirectionType.FORWARD);
	}

	private void setSpecialties(HttpServletRequest request) {
		SpecialtyDAO specialtyDAO = DAOServletManager.getSpecialtyDAO(request);
		request.setAttribute(Resourcer.getString("atr.specialties"),
				specialtyDAO.getSpecialtyList());

	}

}
