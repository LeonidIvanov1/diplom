package datalayer;

import java.util.List;

import datalayer.data.GroupDiscipline;
import datalayer.data.StudentData;
import datalayer.data.User;

/**
 * The interface provides methods for implementing User DAO
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
     * @param libCardNumber
     */
    void addStudent(String userFullName, String login, String password,
                    String group, String libCardNumber);

    /**
     * Add new teacher
     *
     * @param userFullName
     * @param login
     * @param password
     * @param groupDisciplineData
     */
    void addTeacher(String userFullName, String login, String password, List<GroupDiscipline> groupDisciplineData);

    /**
     * Delete user
     *
     * @param user   -- application user
     * @param userID
     */
    void deleteUser(int userID);

    /**
     * Change user
     *
     * @param user   -- application user
     * @param userID
     */
    void changeUser(int userID, String userFullname, String userPassword);

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
     * @param startPos
     * @param endPos
     * @return user list
     */
    List<User> getUsers(int startPos, int endPos, String searchData);

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

    User getUserByLogin(String login);

    int getUserCount(String searchData);

    List<User> getStudentsInGroup(int groupID);

    User getUserByID(int userID);

    List<GroupDiscipline> getTeacherDisciplines(int id);

    boolean changeAdministrator(int userID, String fullName, String password);

    boolean changeLibrarian(int userID, String fullName, String password);

    boolean changeTeacher(int userID, String fullName, String password, List<GroupDiscipline> groupDisciplines);

    boolean changeStudent(int userID, String fullName, String password, int group, String libCardNumber);

    StudentData getStudentData(int id);
}
