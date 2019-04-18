package command;

/**
 * 
 * Class describes the result of the command execution.
 */
public class CommandRedirection {
	/**
	 * Page path to redirect
	 */
	private String page;
	/**
	 * Redirection type
	 */
	private RedirectionType redirectionType;

	/**
	 * Constructor of class CommandRedirection
	 * 
	 * @param page            -- page path
	 * @param redirectionType -- redirection type
	 */
	public CommandRedirection(String page, RedirectionType redirectionType) {
		super();
		this.page = page;
		this.redirectionType = redirectionType;
	}

	/**
	 * Return page path
	 * 
	 * @return
	 */
	public String getPage() {
		return page;
	}

	/**
	 * Return redirection type
	 * 
	 * @return
	 */
	public RedirectionType getRedirectionType() {
		return redirectionType;
	}

}
