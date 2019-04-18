package datalayer.data;

/**
 * This class describes the application user.
 *
 */
public class User {

	/**
	 * User login
	 */
	private String login;
	/**
	 * User password
	 */
	private String password;
	/**
	 * User full name
	 */
	private String fullName;
	/**
	 * User status
	 */
	private Status status;
	/**
	 * User role
	 */
	private Role role;

	/**
	 * Default constructor of class User
	 */
	public User() {
		login = null;
		password = null;
		fullName = null;
		status = null;
		role = null;
	}

	/**
	 * Constructor of class User
	 * 
	 * @param login    -- user login
	 * @param password -- user password
	 * @param fullName -- user full name
	 * @param status   -- user status
	 * @param role     -- user role
	 */
	public User(String login, String password, String fullName, Status status,
			Role role) {
		this.login = login;
		this.password = password;
		this.fullName = fullName;
		this.status = status;
		this.role = role;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

}
