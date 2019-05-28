package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import resourcebundledemo.Resourcer;

/**
 * Filter to check user authorization
 *
 */
public class AuthorizationFilter implements Filter {

	private static final String UTF_8 = "UTF-8";
	private static final String LOGIN_COMMAND = "login";


	public void destroy() {
	}

	/**
	 * Checks if a user is online, if not, redirects to the start page for login
	 */

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding(UTF_8);
		//checkSession(req, res, chain);
		chain.doFilter(req, res);

	}


	public void init(FilterConfig arg0) throws ServletException {
	}

	/**
	 * Redirect to login page
	 * 
	 * @param req -- Servlet request
	 * @param res -- Servlet response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirectWithError(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		req.setAttribute("errorLoginPassMessage",
				Resourcer.getString("message.authorizationerror"));
		req.getRequestDispatcher(Resourcer.getString("path.page.login"))
				.forward(req, res);
	}

	/**
	 * Check session availability. If session is null, checks login command. If
	 * session not null, checks login attribute availability. If it is, control
	 * is passed on along the chain, else redirect to login page.
	 * 
	 * @param req
	 * @param res
	 * @param chain
	 * @throws ServletException
	 * @throws IOException
	 */
	private void checkSession(ServletRequest req, ServletResponse res,
			FilterChain chain) throws ServletException, IOException {
		HttpSession session = ((HttpServletRequest) req).getSession(false);
		if (session == null) {
			checkLoginCommand(req, res, chain);
		} else if (session
				.getAttribute(Resourcer.getString("atr.login")) == null) {
			redirectWithError(req, res);
		} else {
			chain.doFilter(req, res);
		}
	}

	/**
	 * Check is the login command in the request. If it is, control is passed on
	 * along the chain, else redirect to login page.
	 * 
	 * @param req   -- Servlet request
	 * @param res   -- Servlet response
	 * @param chain -- filter chain
	 * @throws ServletException
	 * @throws IOException
	 */
	private void checkLoginCommand(ServletRequest req, ServletResponse res,
			FilterChain chain) throws ServletException, IOException {
		String command = req.getParameter(Resourcer.getString("par.command"));
		if (command == null || !command.equals(LOGIN_COMMAND)) {
			redirectWithError(req, res);
		} else {
			chain.doFilter(req, res);
		}
	}
}
