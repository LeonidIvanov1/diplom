package datalayer.oracledb;

import datalayer.data.*;
import datalayer.*;
import resourcebundledemo.Resourcer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class provides methods for Oracle DB User DAO
 */
public class OracleUserDAO implements UserDAO {

    /**
     * Connection to Oracle DB
     */
    private Connection connection;

    /**
     * Constructor of OracleAdministratorDAO class
     *
     * @param connection -- connection to Oracle DB
     */
    public OracleUserDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Check authorization data
     *
     * @param login    -- user login
     * @param password --user password
     * @return true, if password is suitable for login
     */

    public boolean checkAuthorization(String login, String password) {
        boolean result = false;
        if (login == null || login.trim().equals("") || password == null
                || password.trim().equals("")) {
            return result;
        } else {
            PreparedStatement ps;
            ResultSet rs = null;
            try {
                ps = connection.prepareStatement(
                        Resourcer.getString("sql.getPassword"));
                ps.setString(1, login);
                rs = ps.executeQuery();
                String pas = "";
                if (rs.next()) {
                    pas = rs.getString(1);
                }
                result = password.equals(pas);
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    /**
     * Login user in system
     *
     * @param login -- user login
     */
    public void login(String login) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.login"));
            ps.setString(1, login);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logout user from system
     *
     * @param login -- user login
     */
    public void logout(String login) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.logout"));
            ps.setString(1, login);
            ps.executeQuery();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Check online status user by login
     *
     * @param login -- user login
     * @return true, if user is online
     */
    public boolean checkOnline(String login) {
        boolean result = false;
        if (login == null || login.trim().equals("")) {
            return false;
        } else {
            PreparedStatement ps;
            ResultSet rs = null;
            try {
                ps = connection
                        .prepareStatement(Resourcer.getString("sql.getStatus"));
                ps.setString(1, login);
                rs = ps.executeQuery();
                String status = "";
                if (rs.next()) {
                    status = rs.getString(1);
                }
                result = status.equalsIgnoreCase(Status.ONLINE.toString());
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    /**
     * Return user role by login
     *
     * @param login -- user login
     * @return user login
     */
    public String getUserRole(String login) {
        PreparedStatement ps;
        ResultSet rs = null;
        String role = "";
        try {
            ps = connection
                    .prepareStatement(Resourcer.getString("sql.getuserrole"));
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (rs.next()) {
                role = rs.getString(1);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return role;
    }

    public String getUserFullName(String login) {
        PreparedStatement ps;
        ResultSet rs = null;
        String fullName = "";
        try {
            ps = connection.prepareStatement(
                    Resourcer.getString("sql.getuserfullname"));
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (rs.next())
                fullName = rs.getString(1);
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fullName;
    }

    @Override
    public User getUserByLogin(String login) {
        User user = null;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getUserByLogin"));
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String password = rs.getString(3);
                String fullName = rs.getString(4).trim();
                DAOFactory df = DAOFactory.getInstance(DBType.ORACLE);
                Role role = Role.valueOf(rs.getString(8));
                Status status = Status.valueOf(rs.getString(10));
                user = new User(login, password, fullName, status, role, id);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int getUserCount(String data) {
        StringBuilder search = new StringBuilder();
        search.append("%").append(data.toUpperCase()).append("%");
        PreparedStatement ps;
        ResultSet rs;
        int count = 0;
        try {

            ps = connection.prepareStatement(Resourcer.getString("sql.getUsersCount"));
            ps.setString(1, search.toString());
            ps.setString(2, search.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<User> getStudentsInGroup(int groupID) {
        List<User> students = new ArrayList<>();
        List<Integer> studentsID = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;

        try {

            ps = connection.prepareStatement(Resourcer.getString("sql.getStudentsID"));
            ps.setInt(1, groupID);
            rs = ps.executeQuery();
            while (rs.next()) {
                studentsID.add(rs.getInt(1));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Integer studID : studentsID) {
            students.add(getUserByID(studID));
        }
        return students;
    }

    public User getUserByID(int userID) {
        User user = null;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getUserByID"));
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String login = rs.getString(2);
                String password = rs.getString(3);
                String fullName = rs.getString(4).trim();
                DAOFactory df = DAOFactory.getInstance(DBType.ORACLE);
                Role role = Role.valueOf(rs.getString(8));
                Status status = Status.valueOf(rs.getString(10));
                user = new User(login, password, fullName, status, role, id);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<GroupDiscipline> getTeacherDisciplines(int id) {
        List<GroupDiscipline> groupDisciplines = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getTeacherGroupDiscipline"));
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                groupDisciplines.add(new GroupDiscipline(rs.getString(1), rs.getString(2)));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupDisciplines;
    }

    @Override
    public boolean changeAdministrator(int userID, String fullName, String password) {
        CallableStatement cs;
        String login = getUserByID(userID).getLogin();
        try {

            cs = connection.prepareCall(Resourcer.getString("sql.changeAdministrator"));
            cs.setString(1, login);
            cs.setString(2, password);
            cs.setString(3, fullName);
            cs.setInt(4, userID);
            cs.executeUpdate();
            cs.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean changeLibrarian(int userID, String fullName, String password) {
        CallableStatement cs;
        String login = getUserByID(userID).getLogin();
        try {

            cs = connection.prepareCall(Resourcer.getString("sql.changeLibrarian"));
            cs.setString(1, login);
            cs.setString(2, password);
            cs.setString(3, fullName);
            cs.setInt(4, userID);
            cs.executeUpdate();
            cs.close();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changeTeacher(int userID, String fullName, String password, List<GroupDiscipline> groupDisciplines) {
        CallableStatement cs;
        String login = getUserByID(userID).getLogin();
        try {

            cs = connection.prepareCall(Resourcer.getString("sql.changeTeacher"));
            cs.setString(1, login);
            cs.setString(2, password);
            cs.setString(3, fullName);
            cs.setInt(4, userID);
            cs.executeUpdate();
            cs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        deleteTeacherData(userID);

        for (GroupDiscipline groupDiscipline : groupDisciplines) {
            addTeacherGroupDisciplineData(login, groupDiscipline.getGroup(), groupDiscipline.getDiscipline());
        }
        return true;
    }

    public void deleteTeacherData(int userID) {
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.deleteTeacherInfo"));
            ps.setInt(1, userID);
            ps.executeQuery();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean changeStudent(int userID, String fullName, String password, int group, String libCardNumber) {
        CallableStatement cs;
        String login = getUserByID(userID).getLogin();
        try {

            cs = connection.prepareCall(Resourcer.getString("sql.changeStudent"));
            cs.setString(1, login);
            cs.setString(2, password);
            cs.setString(3, fullName);
            cs.setInt(4, userID);
            cs.executeUpdate();
            cs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        deleteStudentData(userID);
        setStudentData(login, group, libCardNumber);
        return true;

    }

    @Override
    public StudentData getStudentData(int id) {
        PreparedStatement ps;
        ResultSet rs;
        StudentData studentData = null;
        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getStudentData"));
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()) {
                int groupID = rs.getInt(3);
                String libCard = rs.getString(4);
                GroupDAO groupDAO = DAOFactory.getInstance(DBType.ORACLE).getGroupDAO();
                studentData = new StudentData(groupDAO.getGroupInfo(groupID), libCard);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentData;
    }

    public void deleteStudentData(int userID) {
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.deleteStudentData"));
            ps.setInt(1, userID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete user
     *
     */
    public void deleteUser(int id) {
        CallableStatement cs;
        try {
            cs = connection.prepareCall(Resourcer.getString("sql.deleteUser"));
            cs.setInt(1, id);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change user
     */
    public void changeUser(int userID, String userFullname,
                           String userPassword) {

    }

    /**
     * Return user list
     *
     * @param startPos
     * @param endPos
     * @return user list
     */
    public List<User> getUsers(int startPos, int endPos, String searchData) {
        List<User> users = new ArrayList<User>();
        StringBuilder search = new StringBuilder();
        search.append("%").append(searchData.toUpperCase()).append("%");
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.searchUsers"));
            ps.setString(1, search.toString());
            ps.setString(2, search.toString());
            ps.setInt(3, endPos);
            ps.setString(4, search.toString());
            ps.setString(5, search.toString());
            ps.setInt(6, startPos);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String login = rs.getString(2);
                String password = rs.getString(3);
                String fullName = rs.getString(4).trim();
                DAOFactory df = DAOFactory.getInstance(DBType.ORACLE);
                Role role = Role.valueOf(rs.getString(5));
                Status status = Status.valueOf(rs.getString(6));
                users.add(new User(login, password, fullName, status, role, id));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }


    /**
     * Return user info by user id
     *
     * @return user
     */
    public User getUserInfo(String userLogin) {
        return null;
    }

    public void addAdministrator(String userFullName, String login,
                                 String password) {
        CallableStatement cs;
        try {
            cs = connection
                    .prepareCall(Resourcer.getString("sql.addAdministrator"));
            cs.setString(3, userFullName);
            cs.setString(1, login);
            cs.setString(2, password);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addUser(String userFullName, String login, String password,
                         Role role) {
        PreparedStatement ps;
        try {
            ps = connection
                    .prepareStatement(Resourcer.getString("sql.addUser"));
            ps.setString(1, userFullName);
            ps.setString(2, login);
            ps.setString(3, password);
            ps.setString(4, role.toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLibrarian(String userFullName, String login,
                             String password) {
        CallableStatement cs;
        try {
            cs = connection
                    .prepareCall(Resourcer.getString("sql.addLibrarian"));
            cs.setString(3, userFullName);
            cs.setString(1, login);
            cs.setString(2, password);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addStudent(String userFullName, String login, String password,
                           String group, String libCardNumber) {
        CallableStatement cs;
        try {
            cs = connection
                    .prepareCall(Resourcer.getString("sql.addStudent"));
            cs.setString(3, userFullName);
            cs.setString(1, login);
            cs.setString(2, password);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setStudentData(String login, int groupID, String libCardNumber) {
        int studentID = getUserByLogin(login).getId();
        DAOFactory df = DAOFactory.getInstance(DBType.ORACLE);
        GroupDAO groupDAO = df.getGroupDAO();
        CallableStatement cs;
        try {
            cs = connection
                    .prepareCall(Resourcer.getString("sql.addStudentInfo"));
            cs.setInt(1, studentID);
            cs.setInt(2, groupID);
            cs.setString(3, libCardNumber);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setUserGroup(String login, String group) {
        GroupDAO groupDAO = DAOFactory
                .getInstance(
                        DBType.getTypeByName(Resourcer.getString("dao.dbtype")))
                .getGroupDAO();
        groupDAO.addUserToGroup(login, group);
    }

    public void addTeacher(String userFullName, String login, String password, List<GroupDiscipline> groupDisciplineData) {
        CallableStatement cs;
        try {
            cs = connection
                    .prepareCall(Resourcer.getString("sql.addTeacher"));
            cs.setString(3, userFullName);
            cs.setString(1, login);
            cs.setString(2, password);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (GroupDiscipline groupDiscipline : groupDisciplineData) {
            addTeacherGroupDisciplineData(login, groupDiscipline.getGroup(), groupDiscipline.getDiscipline());
        }
    }

    private void addTeacherGroupDisciplineData(String login, String group, String discipline) {
        DAOFactory df = DAOFactory.getInstance(DBType.ORACLE);
        int groupID = df.getGroupDAO().getGroupIDByName(group);
        int disciplineID = df.getDisciplineDAO().getDisciplineIDByName(discipline);
        int userID = getUserIDByLogin(login);
        CallableStatement cs;
        try {
            cs = connection
                    .prepareCall(Resourcer.getString("sql.addTeacherData"));
            cs.setInt(1, userID);
            cs.setInt(2, groupID);
            cs.setInt(3, disciplineID);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private int getUserIDByLogin(String login) {
        PreparedStatement ps;
        ResultSet rs;
        int id = 0;
        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getUserIDByLogin"));
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void setTeacherDiscipline(String login, String discipline) {
        DisciplineDAO disciplineDAO = DAOFactory
                .getInstance(
                        DBType.getTypeByName(Resourcer.getString("dao.dbtype")))
                .getDisciplineDAO();
        disciplineDAO.setTeacherDiscipline(discipline, login);
    }

    public List<User> getOnlineUsers() {
        return null;
    }


    public List<User> getUsersInGroup(String groupName) {
        List<User> users = new ArrayList<User>();
        PreparedStatement ps;
        ResultSet rs;


        return users;
    }

}
