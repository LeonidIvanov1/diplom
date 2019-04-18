package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.SpecialtyDAO;
import servlet.DAOServletManager;
import datalayer.data.Standart;

public class AddSpecialtyCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		addSpecialty(request);
		return new CommandRedirection(
				Resourcer.getString("path.page.main"),
				RedirectionType.FORWARD);
	}

	private void addSpecialty(HttpServletRequest request) {
		SpecialtyDAO specialtyDAO = DAOServletManager.getSpecialtyDAO(request);
		String specialtyName = request
				.getParameter(Resourcer.getString("par.specialtyName"));
		String standartDescription = request
				.getParameter(Resourcer.getString("par.standartDescription"));
		float standartParameter = Float.valueOf(request
				.getParameter(Resourcer.getString("par.standartParameter")));
		Standart standart = new Standart(standartDescription,
				standartParameter);
		specialtyDAO.addSpecialty(specialtyName, standart);
	}

}
