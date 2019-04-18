package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import resourcebundledemo.Resourcer;

import datalayer.UserDAO;
import servlet.DAOServletManager;

/**
 * Class describe login command
 *
 */
public class LoginCommand implements ActionCommand {

	/**
	 * Redirection type
	 */
	private static final RedirectionType REDIRECTION_TYPE = RedirectionType.FORWARD;

	/**
	 * Login
	 */
	private String login;
	/**
	 * Password
	 */
	private String password;
	/**
	 * UserDAO
	 */
	private UserDAO userDAO;

	/**
	 * 
	 */

	public CommandRedirection execute(HttpServletRequest request) {
		initializeLoginData(request);
		String pagePath = tryLogin(request);
		return new CommandRedirection(pagePath, REDIRECTION_TYPE);
	}

	/**
	 * Initialize login and password
	 * 
	 * @param request -- HttpServletRequest
	 */
	private void initializeLoginData(HttpServletRequest request) {
		login = request.getParameter(Resourcer.getString("par.login"));
		password = request.getParameter(Resourcer.getString("par.password"));
		userDAO = DAOServletManager.getUserDAO(request);
	}

	/**
	 * Try to login in application. If authorization was successful, returns
	 * main page path, else returns login page path.
	 * 
	 * @param request
	 * @return
	 */
	private String tryLogin(HttpServletRequest request) {
		String pagePath = "";
		if (checkLoginData()) {
			setSessionData(request);
			loginInApllication();
			pagePath = Resourcer.getString("path.page.main");
		} else {
			request.setAttribute("errorLoginPassMessage",
					Resourcer.getString("message.loginerror"));
			pagePath = Resourcer.getString("path.page.login");
		}
		return pagePath;
	}

	/**
	 * login in application
	 */
	private void loginInApllication() {
		userDAO.login(login);
	}

	/**
	 * Set session data 
	 * @param request
	 */
	private void setSessionData(HttpServletRequest request) {
		HttpSession session = request.getSession();
		setUserLoginInSession(session);
		setUserRoleInSession(session);
		setUserFullNameInSession(session);
		
	}

	/**
	 * Set user full name in session
	 * @param session
	 */
	private void setUserFullNameInSession(HttpSession session) {
		session.setAttribute(Resourcer.getString("atr.fullname"), userDAO.getUserFullName(login));
	}
	/**
	 * Set user login in session
	 * @param session
	 */
	private void setUserLoginInSession(HttpSession session) {
		session.setAttribute(Resourcer.getString("atr.login"), login);
	}

	/**
	 * Set user role in session
	 * @param session
	 */
	private void setUserRoleInSession(HttpSession session) {
		session.setAttribute("role", userDAO.getUserRole(login));
	}

	/**
	 * Check login data
	 * 
	 * @return true, if password is suitable for login
	 */
	private boolean checkLoginData() {
		return userDAO.checkAuthorization(login, password);
	}
}