package command;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

public class ForwardToAddSpecialtyCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {

		return new CommandRedirection(
				Resourcer.getString("path.page.specialty"),
				RedirectionType.FORWARD);
	}

}
