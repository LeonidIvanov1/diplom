package datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import resourcebundledemo.Resourcer;

import datalayer.DAOFactory;
import datalayer.DBType;
import datalayer.DisciplineDAO;
import datalayer.GroupDAO;
import datalayer.UserDAO;
import datalayer.data.Group;
import datalayer.data.Specialty;
import datalayer.data.Standart;
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
			ps = connection
					.prepareStatement(Resourcer.getString("sql.getgroups"));
			rs = ps.executeQuery();
			while (rs.next()) {
				Standart standart = new Standart(rs.getString(2),
						rs.getFloat(1));
				Specialty specialty = new Specialty(rs.getString(3), standart);
				String groupName = rs.getString(4);
				DAOFactory df = DAOFactory.getInstance(DBType
						.getTypeByName(Resourcer.getString("dao.dbtype")));
				UserDAO userDAO = df.getUserDAO();
				DisciplineDAO disciplineDAO = df.getDisciplineDAO();
				List<User> students = userDAO.getUsersInGroup(groupName);
				List<String> disciplines = disciplineDAO
						.getGroupDisciplines(groupName);
				Group group = new Group(groupName, specialty, students,
						disciplines);
				groups.add(group);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groups;
	}

	
	public void deleteGroup(String name) {
		PreparedStatement ps;
		try {
			ps = connection
					.prepareStatement(Resourcer.getString("sql.delteGroup"));
			ps.setString(1, name);
			ps.executeUpdate();
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

	
	public Group getStudentGroup(String login) {
		Group group = null;
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = connection
					.prepareStatement(Resourcer.getString("sql.getUserGroup"));
			ps.setString(1, login);
			rs = ps.executeQuery();
			if (rs.next()) {
				Standart standart = new Standart(rs.getString(2),
						rs.getFloat(1));
				Specialty specialty = new Specialty(rs.getString(3), standart);

				String groupName = rs.getString(4);
				DAOFactory df = DAOFactory.getInstance(DBType
						.getTypeByName(Resourcer.getString("dao.dbtype")));
				UserDAO userDAO = df.getUserDAO();
				DisciplineDAO disciplineDAO = df.getDisciplineDAO();
				List<User> students = userDAO.getUsersInGroup(groupName);
				List<String> disciplines = disciplineDAO
						.getGroupDisciplines(groupName);
				group = new Group(groupName, specialty, students, disciplines);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group;
	}

	
	public List<Group> getTeacherGroups(String login) {
		List<Group> groups = new ArrayList<Group>();
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = connection
					.prepareStatement(Resourcer.getString("sql.getUserGroup"));
			ps.setString(1, login);
			rs = ps.executeQuery();
			while (rs.next()) {
				Standart standart = new Standart(rs.getString(2),
						rs.getFloat(1));
				Specialty specialty = new Specialty(rs.getString(3), standart);
				DAOFactory df = DAOFactory.getInstance(DBType
						.getTypeByName(Resourcer.getString("dao.dbtype")));
				String groupName = rs.getString(4);
				UserDAO userDAO = df.getUserDAO();
				DisciplineDAO disciplineDAO = df.getDisciplineDAO();
				List<User> students = userDAO.getUsersInGroup(groupName);
				List<String> disciplines = disciplineDAO
						.getGroupDisciplines(groupName);
				Group group = new Group(groupName, specialty, students,
						disciplines);
				groups.add(group);

			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groups;
	}

	
	public void changeStudentGroup(String login, String groupName) {
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(
					Resourcer.getString("sql.changeUserGroup"));
			ps.setString(1, groupName);
			ps.setString(2, login);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	
	public void changeTeacherGroups(String login, List<String> groupsName) {
		deleteTeacherGroups(login);
		for (String group : groupsName) {
			addUserToGroup(login, group);
		}

	}

	private void deleteTeacherGroups(String login) {
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(
					Resourcer.getString("sql.deleteTeacherGroups"));
			ps.setString(1, login);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void addGroup(String name, String specialty,
			List<String> disciplines) {
		PreparedStatement ps;
		try {
			ps = connection
					.prepareStatement(Resourcer.getString("sql.addGroup"));
			ps.setString(1, specialty);
			ps.setString(2, name);
			ps.executeUpdate();
			ps.close();
			for (String discipline : disciplines) {
				addGroupDiscipline(name, discipline);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	
	public void addGroupDiscipline(String groupName, String discipline) {
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(
					Resourcer.getString("sql.addGroupDiscipline"));
			ps.setString(1, groupName);
			ps.setString(2, discipline);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	
	public Group getGroupInfo(String groupName) {
		ArrayList<Group> groups = (ArrayList<Group>) getGroupList();
		for (Group group : groups) {
			if (group.getName().equals(groupName)) {
				return group;
			}
		}
		return null;
	}

	
	public void changeGroup(String name, String specialty,
			List<String> disciplines) {
		deleteGroup(name);
		addGroup(name, specialty, disciplines);
	}

	
	public List<Group> searchGroups(String searchData) {
		List<Group> groups = getGroupList();
		List<Group> searchGroups = new ArrayList<Group>();
		for (Group group : groups) {
			if (group.getName().contains(searchData)
					|| group.getSpecialty().getName().contains(searchData)) {
				searchGroups.add(group);
			}
		}
		return searchGroups;
	}

}
