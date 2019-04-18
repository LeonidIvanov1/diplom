package command;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Interface provides methods for working with the command
 */
public interface ActionCommand {
	/**
	 * Command execution method
	 * 
	 * @param request -- HttpServletRequest
	 * @return -- execution result
	 */
	CommandRedirection execute(HttpServletRequest request);
}
