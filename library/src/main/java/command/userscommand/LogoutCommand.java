package command.userscommand;

import javax.servlet.http.HttpServletRequest;

import command.ActionCommand;
import command.ValueObject;
import datalayer.UserDAO;
import resourcebundledemo.Resourcer;
import servlet.DAOServletManager;

public class LogoutCommand implements ActionCommand {

	public ValueObject execute(HttpServletRequest request) {
		request.getSession().invalidate();

		return new ValueObject(true, null, null, null);
	}
}