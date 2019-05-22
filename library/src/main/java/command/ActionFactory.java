package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

/**
 * 
 * Class provides methods for defining commands
 */
public class ActionFactory {
	public ActionCommand defineCommand(HttpServletRequest request) {
		ActionCommand current = new EmptyCommand();
		String action = request
				.getParameter("command");
		if (action == null || action.isEmpty()) {

			return current;
		}
		try {
			CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
			current = currentEnum.getCurrentCommand();
		} catch (IllegalArgumentException e) {
			request.setAttribute(Resourcer.getString("atr.wrongAction"),
					action + Resourcer.getString("message.wrongaction"));
		}
		return current;
	}
}