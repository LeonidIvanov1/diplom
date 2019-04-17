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
import datalayer.LiteratureDAO;
import datalayer.UserDAO;
import datalayer.data.Group;
import datalayer.data.Specialty;
import datalayer.data.User;
import datalayer.data.literature.Literature;
import datalayer.data.literature.LiteratureCollection;
import datalayer.data.literature.LiteratureStatus;

public class OracleLiteratureDAO implements LiteratureDAO {

	private Connection connection;

	public OracleLiteratureDAO(Connection connection) {
		this.connection = connection;
	}

	
	public List<LiteratureCollection> getLiteratureCollections() {
		PreparedStatement ps;
		ResultSet rs;
		List<LiteratureCollection> literatureCollections = new ArrayList<LiteratureCollection>();
		try {
			ps = connection.prepareStatement(
					Resourcer.getString("sql.getLiteratureCollections"));
			rs = ps.executeQuery();
			while (rs.next()) {
				String name = rs.getString(1);
				String author = rs.getString(2);
				String year = rs.getString(3);
				int inStockCount = rs.getInt(4);
				LiteratureCollection literatureCollection = new LiteratureCollection(
						name, author, year, inStockCount);
				literatureCollections.add(literatureCollection);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return literatureCollections;
	}

	
	public List<LiteratureCollection> searchLiteratureCollections(
			String searchData) {
		List<LiteratureCollection> literatureCollections = getLiteratureCollections();
		List<LiteratureCollection> searchLiteratureCollections = new ArrayList<LiteratureCollection>();

		for (LiteratureCollection collection : literatureCollections) {
			if (collection.getName().contains(searchData)
					|| collection.getAuthor().contains(searchData)) {
				searchLiteratureCollections.add(collection);
			}
		}
		return searchLiteratureCollections;
	}

	
	public List<Literature> getLiteratureList() {
		PreparedStatement ps;
		ResultSet rs;
		List<Literature> literature = new ArrayList<Literature>();
		try {
			ps = connection
					.prepareStatement(Resourcer.getString("sql.getLiterature"));
			rs = ps.executeQuery();
			while (rs.next()) {
				String name = rs.getString(1);
				String author = rs.getString(2);
				String year = rs.getString(4);
				String isbn = rs.getString(3);
				String description = rs.getString(5);
				LiteratureStatus status = LiteratureStatus
						.valueOf(rs.getString(7));
				User bookHolder = getBookHolder(rs.getString(6));

				Literature book = new Literature(status, bookHolder, name,
						author, isbn, year, description);
				literature.add(book);

			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return literature;
	}

	private User getBookHolder(String login) {
		DAOFactory df = DAOFactory.getInstance(
				DBType.getTypeByName(Resourcer.getString("dao.dbtype")));
		UserDAO userDAO = df.getUserDAO();
		return userDAO.getUserInfo(login);
	}

	
	public List<Literature> searchLiterature(String searchData) {
		List<Literature> literature = getLiteratureList();
		List<Literature> searchLiterature = new ArrayList<Literature>();

		for (Literature collection : literature) {
			if (collection.getName().contains(searchData)
					|| collection.getAuthor().contains(searchData)) {
				searchLiterature.add(collection);
			}
		}
		return searchLiterature;
	}

	
	public LiteratureCollection getLiteratureCollection(String name,
			String author, String year) {
		LiteratureCollection literatureCollection = null;
		List<LiteratureCollection> collections = getLiteratureCollections();
		for (LiteratureCollection collection : collections) {
			if (collection.getAuthor().equals(author)
					&& collection.getName().equals(name)
					&& collection.getYear().equals(year)) {
				literatureCollection = collection;
			}
		}
		return literatureCollection;
	}

	
	public void reserveLiterature(String name, String author, String year,
			String groupName) {
		DAOFactory df = DAOFactory.getInstance(
				DBType.getTypeByName(Resourcer.getString("dao.dbtype")));
		Group group = df.getGroupDAO().getGroupInfo(groupName);
		for (User user : group.getStudents()) {
			reserveBook(name, author, year, user.getLogin());
		}

	}

	private void reserveBook(String name, String author, String year,
			String login) {
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(
					Resourcer.getString("sql.reserveLiterature"));
			ps.setString(1, login);
			ps.setString(2, name);
			ps.setString(3, author);
			ps.setString(4, year);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void addLiterature(String name, String author, String year,
			String isbn, String description) {
		PreparedStatement ps;
		try {
			ps = connection
					.prepareStatement(Resourcer.getString("sql.addLiterature"));
			ps.setString(1, name);
			ps.setString(2, author);
			ps.setString(3, isbn);
			ps.setString(4, year);
			ps.setString(5, description);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public Literature getLiteratureInfo(String literatureIsbn) {
		Literature literature = null;
		for (Literature book : getLiteratureList()) {
			if (book.getIsbn().equals(literatureIsbn)) {
				literature = book;
			}
		}
		return literature;
	}

	
	public void deleteLiterature(String literatureIsbn) {
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(
					Resourcer.getString("sql.deleteLiterature"));
			ps.setString(1, literatureIsbn);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void changeLiteratureDAO(String name, String author, String year,
			String isbn, String description) {
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(
					Resourcer.getString("sql.changeLiterature"));
			ps.setString(1, name);
			ps.setString(2, author);
			ps.setString(3, year);
			ps.setString(4, description);
			ps.setString(5, isbn);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	
	public boolean checkLiterature(String name, String author, String year,
			String discipline, String specialty) {
		LiteratureCollection literatureCollection = getLiteratureCollection(
				name, author, year);
		boolean result = false;
		try {
			result = literatureCollection.getInStockCount()
					/ getStudentsInGroups(discipline,
							specialty) >= getStandartParameter(specialty);
		} catch (Exception ex) {
			result = false;
		}
		return result;

	}

	private int getStudentsInGroups(String discipline, String specialty) {
		DAOFactory df = DAOFactory.getInstance(
				DBType.getTypeByName(Resourcer.getString("dao.dbtype")));
		List<Group> groups = df.getGroupDAO().getGroupList();
		int studentCount = 0;
		for (Group group : groups) {
			if (group.getDisciplines().contains(discipline)
					&& group.getSpecialty().getName().equals(specialty)) {
				studentCount += group.getStudents().size();
			}
		}
		return studentCount;
	}

	private float getStandartParameter(String specialty) {
		DAOFactory df = DAOFactory.getInstance(
				DBType.getTypeByName(Resourcer.getString("dao.dbtype")));
		return df.getSpecialtyDAO().getSpecialtyInfo(specialty).getStandart()
				.getParameter();

	}

}
