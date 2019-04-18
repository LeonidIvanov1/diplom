package datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import resourcebundledemo.Resourcer;

import datalayer.SpecialtyDAO;
import datalayer.data.Group;
import datalayer.data.Specialty;
import datalayer.data.Standart;

public class OracleSpecialtyDAO implements SpecialtyDAO {

	private Connection connection;

	public OracleSpecialtyDAO(Connection connection) {
		this.connection = connection;
	}

	
	public List<Specialty> getSpecialtyList() {
		List<Specialty> specialties = new ArrayList<Specialty>();
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = connection.prepareStatement(
					Resourcer.getString("sql.getSpecialties"));
			rs = ps.executeQuery();
			while (rs.next()) {
				Standart standart = new Standart(rs.getString(3),
						rs.getFloat(2));
				Specialty specialty = new Specialty(rs.getString(1), standart);
				specialties.add(specialty);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return specialties;
	}

	
	public void addSpecialty(String name, Standart standart) {
		PreparedStatement ps;
		try {
			addStandart(standart);
			ps = connection
					.prepareStatement(Resourcer.getString("sql.addSpecialty"));
			ps.setString(1, standart.getDescription());
			ps.setString(2, name);
			ps.executeQuery();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void addStandart(Standart standart) {
		PreparedStatement ps;
		try {
			ps = connection
					.prepareStatement(Resourcer.getString("sql.addStandart"));
			ps.setFloat(1, standart.getParameter());
			ps.setString(2, standart.getDescription());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void deleteSpecialty(String name) {
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(
					Resourcer.getString("sql.removeSpecialty"));
			ps.setString(1, name);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public List<Specialty> searchSpecialties(String searchData) {
		ArrayList<Specialty> specialties = (ArrayList<Specialty>) getSpecialtyList();
		List<Specialty> searchSpecialties = new ArrayList<Specialty>();
		for (Specialty specialty : specialties) {
			if (specialty.getName().contains(searchData) || specialty
					.getStandart().getDescription().contains(searchData)) {
				searchSpecialties.add(specialty);
			}
		}
		return searchSpecialties;
	}

	
	public Specialty getSpecialtyInfo(String name) {
		List<Specialty> specialties = getSpecialtyList();
		for (Specialty specialty : specialties) {
			if (specialty.getName().equals(name)) {
				return specialty;
			}
		}

		return null;
	}

}
