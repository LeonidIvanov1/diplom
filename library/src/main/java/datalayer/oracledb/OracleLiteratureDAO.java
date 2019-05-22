package datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import datalayer.data.literature.LiteratureStatus;
import datalayer.data.literature.LiteratureType;
import resourcebundledemo.Resourcer;

import datalayer.DAOFactory;
import datalayer.DBType;
import datalayer.LiteratureDAO;
import datalayer.UserDAO;
import datalayer.data.Group;
import datalayer.data.User;
import datalayer.data.literature.Literature;
import datalayer.data.literature.LiteratureCollection;

public class OracleLiteratureDAO implements LiteratureDAO {

    private Connection connection;

    public OracleLiteratureDAO(Connection connection) {
        this.connection = connection;
    }


    public List<LiteratureCollection> getLiteratureCollections() {

        return null;
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


    public List<Literature> getLiteratureList(String search, int start, int end) {
        List<Literature> literature = new ArrayList<>();
        String s = "%" + search.toUpperCase() + "%";

        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.getLiteratureList"));

            ps.setString(1, s);
            ps.setString(2, s);
            ps.setString(3, s);
            ps.setString(4, s);
            ps.setInt(5, end);
            ps.setString(6, s);
            ps.setString(7, s);
            ps.setString(8, s);
            ps.setString(9, s);
            ps.setInt(10, start);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String author = rs.getString(3);
                String isbn = rs.getString(4);
                String year = rs.getString(5);
                String description = rs.getString(6);
                String rental = rs.getString(7);
                LiteratureStatus literatureStatus = getLiteratureStatus(rs.getInt(8));
                LiteratureType literatureType = getLiteratureType(rs.getInt(9));
                User holder = DAOFactory.getInstance(DBType.ORACLE).getUserDAO().getUserByID(rs.getInt(10));
                literature.add(new Literature(id, literatureStatus, holder, name, author, isbn, year, rental, description, literatureType));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return literature;
    }

    private LiteratureStatus getLiteratureStatus(int anInt) {
        LiteratureStatus literatureStatus = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getLiteratureStatus"));
            ps.setInt(1, anInt);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                literatureStatus = LiteratureStatus.valueOf(rs.getString(2));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return literatureStatus;

    }

    private User getBookHolder(String login) {
        DAOFactory df = DAOFactory.getInstance(
                DBType.getTypeByName(Resourcer.getString("dao.dbtype")));
        UserDAO userDAO = df.getUserDAO();
        return userDAO.getUserInfo(login);
    }


    public List<Literature> searchLiterature(String searchData) {

        return null;
    }


    public List<LiteratureCollection> getLiteratureCollections(String search,
                                                               int start, int end) {

        List<LiteratureCollection> collections = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getLiteratureCollections"));
            rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String author = rs.getString(3);
                String year = rs.getString(2);
                int literatureTypeID = rs.getInt(4);
                int count = getLiteratureCollectionCount(name, author, year, literatureTypeID);
                LiteratureType literatureType = getLiteratureType(literatureTypeID);
                collections.add(new LiteratureCollection(name, author, year, count, literatureType));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return collections;

    }

    private int getLiteratureCollectionCount(String name, String author, String year, int bookTypeID) {
        int count = 0;
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getLiteratureCollectionFreeCount"));
            ps.setString(1, name);
            ps.setString(2, author);
            ps.setString(3, year);
            ps.setInt(4, bookTypeID);
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

    private LiteratureType getLiteratureType(int id) {
        PreparedStatement ps;
        ResultSet rs;
        LiteratureType literatureType = null;
        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getLiteratureType"));
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                literatureType = LiteratureType.valueOf(rs.getString(2).toUpperCase());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return literatureType;
    }


    public void reserveLiterature(String name, String author, String year,
                                  String groupName) {


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
        return null;
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

        return false;

    }

    @Override
    public int getLiteratureCollectionCount(String search) {
        PreparedStatement ps;
        ResultSet rs;
        int count = 0;

        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getLiteratureCollectionCount"));
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
    public int getLiteratureCount(String search) {
        String s = "%" + search.toUpperCase() + "%";
        int count = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.getLiteratureCount"));
            ps.setString(1, s);
            ps.setString(2, s);
            ps.setString(3, s);
            ps.setString(4, s);
            ResultSet rs = ps.executeQuery();

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

    private int getStudentsInGroups(String discipline, String specialty) {
        DAOFactory df = DAOFactory.getInstance(
                DBType.getTypeByName(Resourcer.getString("dao.dbtype")));
        List<Group> groups = df.getGroupDAO().getGroupList();
        int studentCount = 0;

        return studentCount;
    }

    private float getStandartParameter(String specialty) {
        DAOFactory df = DAOFactory.getInstance(
                DBType.getTypeByName(Resourcer.getString("dao.dbtype")));
        return 0;
    }

}
