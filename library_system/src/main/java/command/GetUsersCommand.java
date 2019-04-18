package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.UserDAO;
import servlet.DAOServletManager;

/**
 * Class describe get users command
 * 
 * @author Admin
 *
 */
public class GetUsersCommand implements ActionCommand {

	/**
	 * Redirection type
	 */
	private static final RedirectionType REDIRECTION_TYPE = RedirectionType.FORWARD;


	public CommandRedirection execute(HttpServletRequest request) {
		setUsersData(request);
		return new CommandRedirection(Resourcer.getString("path.page.users"),
				REDIRECTION_TYPE);
	}

	private void setUsersData(HttpServletRequest request) {
		UserDAO userDAO = DAOServletManager.getUserDAO(request);
		request.setAttribute(Resourcer.getString("atr.users"),
				userDAO.getUsers());
	}

	

}
