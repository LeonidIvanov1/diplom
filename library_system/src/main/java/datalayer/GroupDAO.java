package datalayer;

import java.util.List;

import datalayer.data.Group;

public interface GroupDAO {

	/**
	 * Returns groups list
	 * 
	 * @return
	 */
	List<Group> getGroupList();

	/**
	 * Add new group
	 * 
	 * @param name        -- group name
	 * @param specialty   -- specialty name
	 * @param disciplines -- disciplines list
	 */
	void addGroup(String name, String specialty, List<String> disciplines);

	/**
	 * Removes group
	 * 
	 * @param name -- group name
	 */
	void deleteGroup(String name);

	/**
	 * Change group
	 * 
	 * @param name        -- group name
	 * @param specialty   -- specialty name
	 * @param disciplines -- discipline list
	 */
	void changeGroup(String name, String specialty, List<String> disciplines);

	/**
	 * Add user to group
	 * 
	 * @param login -- user login
	 * @param group -- group name
	 */
	void addUserToGroup(String login, String group);

	/**
	 * Return student group
	 * 
	 * @param login -- user login
	 * @return
	 */
	Group getStudentGroup(String login);

	/**
	 * Return teacher groups
	 * 
	 * @param login -- user login
	 * @return
	 */
	List<Group> getTeacherGroups(String login);

	/**
	 * Change student group
	 * 
	 * @param login     -- user login
	 * @param groupName -- group name
	 */
	void changeStudentGroup(String login, String groupName);

	/**
	 * Change teacher group
	 * 
	 * @param login      -- user login
	 * @param groupsName -- groups name
	 */
	void changeTeacherGroups(String login, List<String> groupsName);

	/**
	 * Add group discipline
	 * 
	 * @param groupName  -- group name
	 * @param discipline -- discipline name
	 */
	void addGroupDiscipline(String groupName, String discipline);

	/**
	 * Returns group object
	 * 
	 * @param groupName -- group name
	 * @return
	 */
	Group getGroupInfo(String groupName);

	/**
	 * Returns group list after search
	 * 
	 * @param searchData -- search data
	 * @return
	 */
	List<Group> searchGroups(String searchData);
}
