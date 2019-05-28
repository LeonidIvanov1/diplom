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
     * Removes group
     *
     * @param groupID -- group name
     */
    void deleteGroup(int groupID);


    /**
     * Add user to group
     *
     * @param login -- user login
     * @param group -- group name
     */
    void addUserToGroup(String login, String group);


    /**
     * Add group discipline
     *
     * @param groupID    -- group name
     * @param discipline -- discipline name
     */
    void addGroupDiscipline(int groupID, String discipline);

    /**
     * Returns group object
     *
     * @param groupName -- group name
     * @return
     */
    int getGroupIDByName(String groupName);

    /**
     * Returns group list after search
     *
     * @param searchData -- search data
     * @return
     */
    List<Group> searchGroups(String searchData);

    int getGroupsCount();

    Group getGroupInfo(int groupID);

    void addGroup(String name, List<String> list, int specialtyID);

    void changeGroup(String name, int specialtyID,
                     List<String> disciplines, int groupID);

    List<Group> getGroupsWithDisciplines(int specialtyID, int disciplineID);

    List<Group> getTeacherDisciplines(int userID);
}
