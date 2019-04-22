package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.SpecialtyDAO;
import servlet.DAOServletManager;

public class GetSpecialtyInfoCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		setSpecialtyInfo(request);
		return new CommandRedirection(
				Resourcer.getString("path.page.specialty"),
				RedirectionType.FORWARD);
	}

	private void setSpecialtyInfo(HttpServletRequest request) {
		SpecialtyDAO specialtyDAO = DAOServletManager.getSpecialtyDAO(request);
		String groupName = request.getParameter(Resourcer.getString("par.specialtyid"));
		request.setAttribute(Resourcer.getString("atr.specialtyInfo"),
				specialtyDAO.getSpecialtyInfo(groupName));

	}

}
