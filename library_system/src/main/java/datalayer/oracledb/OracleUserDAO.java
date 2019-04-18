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
import datalayer.data.Role;
import datalayer.data.Status;
import datalayer.data.User;

/**
 * Class provides methods for Oracle DB User DAO
 *
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fullName;
	}

	/**
	 * Delete user
	 * 
	 * @param user -- application user
	 */
	public void deleteUser(String login) {
		PreparedStatement ps;
		try {
			ps = connection
					.prepareStatement(Resourcer.getString("sql.deleteuser"));
			ps.setString(1, login);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Change user
	 * 
	 * @param user -- application user
	 */
	public void changeUser(String userLogin, String userFullname,
			String userPassword) {
		PreparedStatement ps;
		try {
			ps = connection
					.prepareStatement(Resourcer.getString("sql.changeUser"));
			ps.setString(1, userFullname);
			ps.setString(2, userPassword);
			ps.setString(3, userLogin);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return user list
	 * 
	 * @return user list
	 */
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		PreparedStatement ps;
		ResultSet rs = null;

		try {
			ps = connection
					.prepareStatement(Resourcer.getString("sql.getusers"));
			rs = ps.executeQuery();
			while (rs.next()) {
				Status status = Status.valueOf(rs.getString(5));
				Role role = Role.valueOf(rs.getString(4));
				users.add(new User(rs.getString(2), rs.getString(3),
						rs.getString(1), status, role));
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * Return user after search
	 * 
	 * @return user
	 */
	public List<User> searchUsers(String searchData) {
		List<User> users = getUsers();
		List<User> searchUsers = new ArrayList<User>();
		for (User user : users) {
			if (user.getFullName().contains(searchData)
					|| user.getLogin().contains(searchData)) {
				searchUsers.add(user);
			}
		}
		return searchUsers;
	}

	/**
	 * Return user info by user id
	 * 
	 * @param userID -- index in user list
	 * @return user
	 */
	public User getUserInfo(String userLogin) {
		ArrayList<User> users = (ArrayList<User>) getUsers();
		for (User user : users) {
			if (user.getLogin().equals(userLogin)) {
				return user;
			}
		}
		return null;
	}

	public void addAdministrator(String userFullName, String login,
			String password) {
		addUser(userFullName, login, password, Role.ADMIN);
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
		addUser(userFullName, login, password, Role.LIBRARIAN);

	}

	public void addStudent(String userFullName, String login, String password,
			String group) {
		addUser(userFullName, login, password, Role.STUDENT);
		setUserGroup(login, group);
	}

	private void setUserGroup(String login, String group) {
		GroupDAO groupDAO = DAOFactory
				.getInstance(
						DBType.getTypeByName(Resourcer.getString("dao.dbtype")))
				.getGroupDAO();
		groupDAO.addUserToGroup(login, group);
	}

	public void addTeacher(String userFullName, String login, String password,
			List<String> groups, List<String> disciplines) {
		addUser(userFullName, login, password, Role.TEACHER);
		for (String group : groups) {
			setUserGroup(login, group);
		}
		for (String discipline : disciplines) {
			setTeacherDiscipline(login, discipline);
		}
	}

	private void setTeacherDiscipline(String login, String discipline) {
		DisciplineDAO disciplineDAO = DAOFactory
				.getInstance(
						DBType.getTypeByName(Resourcer.getString("dao.dbtype")))
				.getDisciplineDAO();
		disciplineDAO.setTeacherDiscipline(discipline, login);
	}

	public List<User> getOnlineUsers() {
		List<User> users = getUsers();
		List<User> onlineUsers = new ArrayList<User>();
		for (User user : users) {
			if (user.getStatus().equals(Status.ONLINE)) {
				onlineUsers.add(user);
			}
		}
		return onlineUsers;
	}


	public List<User> getUsersInGroup(String groupName) {
		List<User> users = new ArrayList<User>();
		PreparedStatement ps;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
					Resourcer.getString("sql.getUsersInGroup"));
			ps.setString(1, groupName);
			rs = ps.executeQuery();
			while (rs.next()) {
				Status status = Status.valueOf(rs.getString(5));
				Role role = Role.valueOf(rs.getString(4));
				users.add(new User(rs.getString(2), rs.getString(3),
						rs.getString(1), status, role));
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

}
