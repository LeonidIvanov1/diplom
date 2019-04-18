package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.SpecialtyDAO;
import servlet.DAOServletManager;

public class SearchSpecialtiesCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		searchSpecialties(request);
		return new CommandRedirection(
				Resourcer.getString("path.page.specialties"),
				RedirectionType.FORWARD);
	}

	private void searchSpecialties(HttpServletRequest request) {
		SpecialtyDAO specialtyDAO = DAOServletManager.getSpecialtyDAO(request);
		request.setAttribute(Resourcer.getString("atr.specialties"),
				specialtyDAO.searchSpecialties(request
						.getParameter(Resourcer.getString("par.search"))));
	}

}
