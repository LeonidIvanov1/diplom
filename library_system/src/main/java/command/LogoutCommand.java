package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

public class LogoutCommand implements ActionCommand {

	public CommandRedirection execute(HttpServletRequest request) {
		String page = Resourcer.getString("path.page.index");
		request.getSession().invalidate();
		return new CommandRedirection(page, RedirectionType.SENDREDIRECT);
	}
}