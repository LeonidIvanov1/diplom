package datalayer;

import java.util.List;

import datalayer.data.User;

/**
 * The interface provides methods for implementing User DAO
 *
 */
public interface UserDAO {

	/**
	 * Return users in group
	 * 
	 * @param groupName
	 * @return
	 */
	List<User> getUsersInGroup(String groupName);

	/**
	 * Return online users
	 * 
	 * @return
	 */
	List<User> getOnlineUsers();

	/**
	 * Add new administrator
	 * 
	 * @param user -- application user
	 */
	void addAdministrator(String userFullName, String login, String password);

	/**
	 * Add new librarian
	 * 
	 * @param userFullName
	 * @param login
	 * @param password
	 */
	void addLibrarian(String userFullName, String login, String password);

	/**
	 * Add new student
	 * 
	 * @param userFullName
	 * @param login
	 * @param password
	 * @param group
	 */
	void addStudent(String userFullName, String login, String password,
                    String group);

	/**
	 * Add new teacher
	 * 
	 * @param userFullName
	 * @param login
	 * @param password
	 * @param groups
	 * @param disciplines
	 */
	void addTeacher(String userFullName, String login, String password,
                    List<String> groups, List<String> disciplines);

	/**
	 * Delete user
	 * 
	 * @param user -- application user
	 */
	void deleteUser(String deleteUserLogin);

	/**
	 * Change user
	 * 
	 * @param user -- application user
	 */
	void changeUser(String userLogin, String userFullname, String userPassword);

	/**
	 * Return user info by user id
	 * 
	 * @param userID -- index in user list
	 * @return user
	 */
	User getUserInfo(String userLogin);

	/**
	 * Return user list
	 * 
	 * @return user list
	 */
	List<User> getUsers();

	/**
	 * Return user after search
	 * 
	 * @return user
	 */
	List<User> searchUsers(String searchData);

	/**
	 * Check authorization data
	 * 
	 * @param login    -- user login
	 * @param password --user password
	 * @return true, if password is suitable for login
	 */
	boolean checkAuthorization(String login, String password);

	/**
	 * Check online status user by login
	 * 
	 * @param login -- user login
	 * @return true, if user is online
	 */
	boolean checkOnline(String login);

	/**
	 * Login user in system
	 * 
	 * @param login -- user login
	 */
	void login(String login);

	/**
	 * Logout user from system
	 * 
	 * @param login -- user login
	 */
	void logout(String login);

	/**
	 * Return user role by login
	 * 
	 * @param login -- user login
	 * @return user login
	 */
	String getUserRole(String login);

	/**
	 * Return user full name by login
	 * 
	 * @param login -- user login
	 * @return user full name
	 */
	String getUserFullName(String login);
}
