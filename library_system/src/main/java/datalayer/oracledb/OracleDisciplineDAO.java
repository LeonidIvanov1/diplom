package datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import resourcebundledemo.Resourcer;

import datalayer.DisciplineDAO;
import datalayer.data.Group;
import datalayer.data.Role;
import datalayer.data.Status;
import datalayer.data.User;

public class OracleDisciplineDAO implements DisciplineDAO {

	private Connection connection;

	public OracleDisciplineDAO(Connection connection) {
		this.connection = connection;
	}

	
	public void addDiscipline(String discipline) {
		// TODO Auto-generated method stub

	}

	
	public void removeDiscipline(String discipline) {
		// TODO Auto-generated method stub

	}

	
	public void changeDiscipline(String discipline) {
		// TODO Auto-generated method stub

	}

	
	public void setTeacherDiscipline(String discipline, String login) {
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(
					Resourcer.getString("sql.addTeacherDiscipline"));
			ps.setString(1, login);
			ps.setString(2, discipline);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void setGroupDiscipline(String discipline, Group grop) {
	}

	
	public List<String> getDisciplinesList() {
		List<String> disciplines = new ArrayList<String>();
		PreparedStatement ps;
		ResultSet rs = null;

		try {
			ps = connection
					.prepareStatement(Resourcer.getString("sql.getdisciplines"));
			rs = ps.executeQuery();
			while (rs.next()) {
				disciplines.add(rs.getString(1));
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return disciplines;
	}

	
	public List<String> getTeacherDisciplines(String login) {
		List<String> disciplines = new ArrayList<String>();
		PreparedStatement ps;
		ResultSet rs = null;

		try {
			ps = connection
					.prepareStatement(Resourcer.getString("sql.getTeacherDisciplines"));
			ps.setString(1, login);
			rs = ps.executeQuery();
			while (rs.next()) {
				disciplines.add(rs.getString(1));
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return disciplines;
	}

	
	public void changeTeacherDisciplines(List<String> disciplines,
			String login) {
		deleteTeacherDisciplines(login);
		for (String discipline : disciplines) {
			setTeacherDiscipline(discipline, login);
		}
		
	}
	
	private void deleteTeacherDisciplines(String login) {
		PreparedStatement ps;
		try {
			ps = connection
					.prepareStatement(Resourcer.getString("sql.deleteTeacherDisciplines"));
			ps.setString(1, login);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public List<String> getGroupDisciplines(String groupName) {
		List<String> disciplines = new ArrayList<String>();
		PreparedStatement ps;
		ResultSet rs = null;

		try {
			ps = connection
					.prepareStatement(Resourcer.getString("sql.getGroupDisciplines"));
			ps.setString(1, groupName);
			rs = ps.executeQuery();
			while (rs.next()) {
				disciplines.add(rs.getString(1));
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return disciplines;
	}

	
}
