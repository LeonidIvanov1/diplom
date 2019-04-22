package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

public class ForwardToAddLiteratureCommand implements ActionCommand {

	public CommandRedirection execute(HttpServletRequest request) {
		return new CommandRedirection(Resourcer.getString("path.page.book"),
				RedirectionType.FORWARD);
	}

}
