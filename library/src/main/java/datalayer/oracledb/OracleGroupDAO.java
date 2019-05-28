package datalayer.oracledb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import datalayer.DisciplineDAO;
import oracle.jdbc.proxy.annotation.Pre;
import resourcebundledemo.Resourcer;

import datalayer.DAOFactory;
import datalayer.DBType;
import datalayer.GroupDAO;
import datalayer.data.Group;
import datalayer.data.Specialty;
import datalayer.data.User;

public class OracleGroupDAO implements GroupDAO {


    private Connection connection;

    public OracleGroupDAO(Connection connection) {
        this.connection = connection;
    }


    public List<Group> getGroupList() {
        List<Group> groups = new ArrayList<Group>();
        PreparedStatement ps;
        ResultSet rs = null;
        try {

            ps = connection.prepareStatement(Resourcer.getString("sql.getGroups"));
            rs = ps.executeQuery();
            while (rs.next()) {
                DAOFactory df = DAOFactory.getInstance(DBType.ORACLE);
                int groupID = rs.getInt(1);
                String name = rs.getString(2);
                Specialty specialty = df.getSpecialtyDAO().
                        getSpecialty(rs.getInt(3));
                List<String> disciplines = df.getDisciplineDAO().getSpecialtyDisciplines(groupID);
                List<User> users = df.getUserDAO().getStudentsInGroup(groupID);
                groups.add(new Group(groupID, name, specialty, users, disciplines));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }


    public void deleteGroup(int groupID) {
        try {
            CallableStatement cs = connection.prepareCall(Resourcer.getString("sql.deleteGroup"));
            cs.setInt(1, groupID);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void addUserToGroup(String login, String group) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(
                    Resourcer.getString("sql.addUserToStudentGroup"));
            ps.setString(1, login);
            ps.setString(2, group);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addGroup(String name, String specialty,
                         List<String> disciplines) {
    }


    public void addGroupDiscipline(int groupID, String discipline) {
        DisciplineDAO disciplineDAO = DAOFactory.getInstance(DBType.ORACLE).getDisciplineDAO();
        int disciplineID = disciplineDAO.getDisciplineIDByName(discipline);

        try {
            CallableStatement cs = connection.prepareCall(Resourcer.getString("sql.addGroupDiscipline"));
            cs.setInt(1, groupID);
            cs.setInt(2, disciplineID);
            cs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public int getGroupIDByName(String groupName) {
        PreparedStatement ps;
        ResultSet rs;
        int id = 0;
        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getGroupByName"));
            ps.setString(1, groupName);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    public void changeGroup(String name, int specialtyID,
                            List<String> disciplines, int groupID) {
        CallableStatement cs;
        try {
            cs = connection.prepareCall(Resourcer.getString("sql.changeGroup"));
            cs.setString(1, name);
            cs.setInt(2, specialtyID);
            cs.setInt(3, groupID);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        deleteGroupDisciplines(groupID);

        for (String dis : disciplines) {
            addGroupDiscipline(groupID, dis);
        }


    }

    @Override
    public List<Group> getGroupsWithDisciplines(int specialtyID, int disciplineID) {
        List<Group> groups = new ArrayList<Group>();
        PreparedStatement ps;
        ResultSet rs = null;
        try {

            ps = connection.prepareStatement(Resourcer.getString("sql.getGroupsWithDisciplines"));
            ps.setInt(2, specialtyID);
            ps.setInt(1, disciplineID);
            rs = ps.executeQuery();
            while (rs.next()) {
                DAOFactory df = DAOFactory.getInstance(DBType.ORACLE);
                int groupID = rs.getInt(1);
                String name = rs.getString(2);
                Specialty specialty = df.getSpecialtyDAO().
                        getSpecialty(rs.getInt(3));
                List<String> disciplines = df.getDisciplineDAO().getSpecialtyDisciplines(groupID);
                List<User> users = df.getUserDAO().getStudentsInGroup(groupID);
                groups.add(new Group(groupID, name, specialty, users, disciplines));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    @Override
    public List<Group> getTeacherDisciplines(int userID) {
        List<Group> group = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.getTeacherGroups"));
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int groupID = rs.getInt(1);
                DAOFactory df = DAOFactory.getInstance(DBType.ORACLE);

                String name = rs.getString(2);
                Specialty specialty = df.getSpecialtyDAO().
                        getSpecialty(rs.getInt(3));
                List<String> disciplines = getGroupDisciplines(groupID);
                List<User> users = df.getUserDAO().getStudentsInGroup(groupID);
                group.add(new Group(groupID, name, specialty, users, disciplines));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;
    }


    public List<Group> searchGroups(String searchData) {
        List<Group> groups = getGroupList();
        List<Group> searchGroups = new ArrayList<Group>();
        for (Group group : groups) {
            if (group.getName().toUpperCase().contains(searchData.toUpperCase())) {
                searchGroups.add(group);
            }
        }

        return searchGroups;
    }

    @Override
    public int getGroupsCount() {
        int count = 0;
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getGroupsCount"));
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
    public Group getGroupInfo(int groupID) {
        Group group = null;
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getGroupInfo"));
            ps.setInt(1, groupID);
            rs = ps.executeQuery();
            if (rs.next()) {
                DAOFactory df = DAOFactory.getInstance(DBType.ORACLE);

                String name = rs.getString(2);
                Specialty specialty = df.getSpecialtyDAO().
                        getSpecialty(rs.getInt(3));
                List<String> disciplines = getGroupDisciplines(groupID);
                List<User> users = df.getUserDAO().getStudentsInGroup(groupID);
                group = new Group(groupID, name, specialty, users, disciplines);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;
    }

    private List<String> getGroupDisciplines(int groupID) {
        PreparedStatement ps;
        List<String> disciplines = new ArrayList<>();
        ResultSet rs;

        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getGroupDisciplines"));
            ps.setInt(1, groupID);
            rs = ps.executeQuery();
            while (rs.next()) {
                disciplines.add(rs.getString(1));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplines;
    }

    private void deleteGroupDisciplines(int groupID) {
        PreparedStatement ps;
        List<String> disciplines = new ArrayList<>();
        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.deleteGroupDisciplines"));
            ps.setInt(1, groupID);
            ps.executeQuery();

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addGroup(String name, List<String> list, int specialtyID) {
        CallableStatement cs;
        try {
            cs = connection.prepareCall(Resourcer.getString("sql.addGroup"));
            cs.setString(1, name);
            cs.setInt(2, specialtyID);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int groupID = getGroupIDByName(name);
        for (String dis : list) {
            addGroupDiscipline(groupID, dis);
        }
    }


}
