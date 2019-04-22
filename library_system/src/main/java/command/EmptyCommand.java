package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

/**
 * Class describe empty command
 * 
 * @author Admin
 *
 */
public class EmptyCommand implements ActionCommand {
	private static final RedirectionType REDIRECTION_TYPE = RedirectionType.FORWARD;


	public CommandRedirection execute(HttpServletRequest request) {
		String page = Resourcer.getString("path.page.login");
		return new CommandRedirection(page, REDIRECTION_TYPE);
	}
}
