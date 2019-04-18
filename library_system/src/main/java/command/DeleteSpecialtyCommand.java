package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.SpecialtyDAO;
import servlet.DAOServletManager;

public class DeleteSpecialtyCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		deleteSpecialty(request);
		return new CommandRedirection(
				Resourcer.getString("path.page.main"),
				RedirectionType.FORWARD);

	}

	private void deleteSpecialty(HttpServletRequest request) {
		SpecialtyDAO specialtyDAO = DAOServletManager.getSpecialtyDAO(request);
		specialtyDAO.deleteSpecialty(
				request.getParameter(Resourcer.getString("par.specialtyName")));

	}

}
