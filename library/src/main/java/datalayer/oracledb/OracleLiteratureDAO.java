package datalayer.oracledb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import datalayer.*;
import datalayer.data.Discipline;
import datalayer.data.literature.*;
import resourcebundledemo.Resourcer;

import datalayer.data.Group;
import datalayer.data.User;

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
        String s = "%" + search.toUpperCase() + "%";
        List<LiteratureCollection> collections = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getLiteratureCollections"));
            ps.setString(1, s);
            ps.setString(2, s);
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


    public void addLiterature(String name, String author, String year, String isbn,
                              String description, String rental, int status, int type) {
        CallableStatement cs;
        try {
            cs = connection
                    .prepareCall(Resourcer.getString("sql.addLiterature"));
            cs.setString(1, name);
            cs.setString(2, author);
            cs.setString(3, isbn);
            cs.setString(4, year);
            cs.setString(5, description);
            cs.setString(6, rental);
            cs.setInt(7, status);
            cs.setInt(8, type);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Literature getLiteratureInfo(int bookID) {
        Literature literature = null;
        try {
            PreparedStatement ps = connection.prepareCall(Resourcer.getString("sql.getBookInfo"));
            ps.setInt(1, bookID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
                literature = new Literature(id, literatureStatus, holder, name, author, isbn, year, rental, description, literatureType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return literature;
    }


    public void deleteLiterature(int literatureIsbn) {
        CallableStatement ps;
        try {
            ps = connection.prepareCall(
                    Resourcer.getString("sql.deleteLiterature"));
            ps.setInt(1, literatureIsbn);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void changeLiteratureDAO(String name, String author, String year,
                                    String isbn, String description, String rental, int status, int type, int holder, int bookID) {
        CallableStatement ps;
        try {
            ps = connection.prepareCall(
                    Resourcer.getString("sql.changeLiterature"));
            ps.setString(1, name);
            ps.setString(2, author);
            ps.setString(4, year);
            ps.setString(5, description);
            ps.setString(3, isbn);
            ps.setString(6, rental);
            ps.setInt(7, status);
            ps.setInt(8, type);
            ps.setInt(9, holder);
            ps.setInt(10, bookID);
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

    @Override
    public List<Literature> getUserLiterature(int id) {
        List<Literature> literature = new ArrayList<>();


        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.getUserLiterature"));
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int bookid = rs.getInt(1);
                String name = rs.getString(2);
                String author = rs.getString(3);
                String isbn = rs.getString(4);
                String year = rs.getString(5);
                String description = rs.getString(6);
                String rental = rs.getString(7);
                LiteratureStatus literatureStatus = getLiteratureStatus(rs.getInt(8));
                LiteratureType literatureType = getLiteratureType(rs.getInt(9));
                User holder = DAOFactory.getInstance(DBType.ORACLE).getUserDAO().getUserByID(id);
                literature.add(new Literature(bookid, literatureStatus, holder, name, author, isbn, year, rental, description, literatureType));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return literature;
    }

    @Override
    public List<LiteratureDate> getLiteratureDates(int id) {
        List<LiteratureDate> dates = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.getLiteratureDates"));
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dates.add(new LiteratureDate(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }

    @Override
    public void returnLiterature(int bookId) {
        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.returnLiterature"));
            ps.setInt(1, bookId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void extendLiterature(int bookId) {
        Literature lit = getLiteratureInfo(bookId);
        int rent = Integer.parseInt(lit.getRental()) + 2;
        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.extendLiterature"));

            ps.setInt(1, bookId);
            ps.setInt(2, rent);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LiteratureFundData getLibraryFundData() {
        int inStock = getStatusLiteratureCount(1);

        int outOfStock = getStatusLiteratureCount(4);
        int reserved = getStatusLiteratureCount(2);
        int writtenOff = getStatusLiteratureCount(3);
        int all = inStock + outOfStock + reserved + writtenOff;

        return new LiteratureFundData(inStock, all, outOfStock, reserved, writtenOff);
    }

    @Override
    public List<User> getHolders() {
        List<User> users = new ArrayList<>();
        UserDAO userDAO = DAOFactory.getInstance(DBType.ORACLE).getUserDAO();
        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.getHoldersID"));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(userDAO.getUserByID(rs.getInt(1)));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private int getLiteratureTypeID(LiteratureType type) {
        switch (type) {
            case BOOK:
                return 1;
            case TRAINING:
                return 2;
            default:
                return 0;
        }
    }


    @Override
    public void createReserveRequest(int groupID, int teacher, int discipline, List<LiteratureCollection> literatureCollectionList) {
        List<Integer> books = new ArrayList<>();
        for (LiteratureCollection literatureCollection : literatureCollectionList) {
            books.addAll(getBooksID(literatureCollection.getName(), literatureCollection.getAuthor(),
                    literatureCollection.getYear(), getLiteratureTypeID(literatureCollection.getLiteratureType())));
        }

        Group group = DAOFactory.getInstance(DBType.ORACLE).getGroupDAO().getGroupInfo(groupID);
        ArrayList<User> students = (ArrayList<User>) group.getStudents();
        for (int i = 0; i < students.size(); i++) {
            reserveBook(books.get(i), students.get(i).getId());
            try {
                CallableStatement cs = connection.prepareCall(Resourcer.getString("sql.addReserveRequest"));
                cs.setInt(1, (groupID + teacher * discipline));
                cs.setInt(2, teacher);
                cs.setInt(3, groupID);
                cs.setInt(4, discipline);
                cs.setInt(5, books.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<ReserveRequest> getReserveRequestList(int userID) {
        List<ReserveRequest> requests = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.getReserveRequestList"));
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User teacher = DAOFactory.getInstance(DBType.ORACLE).getUserDAO().getUserByID(userID);
                int resID = rs.getInt(1);
                Group group = DAOFactory.getInstance(DBType.ORACLE).getGroupDAO().getGroupInfo(rs.getInt(3));
                Discipline discipline = DAOFactory.getInstance(DBType.ORACLE).getDisciplineDAO().getDisciplineInfo(rs.getInt(4));
                List<Literature> list = getLiteratureListReserve(resID);
                requests.add(new ReserveRequest(resID, teacher, group, discipline, list));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    @Override
    public void deleteReserveRequest(int reserveID) {
        unreserveBooks(reserveID);
        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.deleteReserveRequest"));
            ps.setInt(1, reserveID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void unreserveBooks(int reserveID) {
        List<Literature> literature = getLiteratureListReserve(reserveID);
        for (Literature book : literature) {
            try {
                PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.unreserveBooks"));
                ps.setInt(1, book.getId());
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Literature> getLiteratureListReserve(int reserveID) {
        List<Literature> literature = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.getReserveBooks"));
            ps.setInt(1, reserveID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                literature.add(DAOFactory.getInstance(DBType.ORACLE).getLiteratureDAO().getLiteratureInfo(rs.getInt(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return literature;
    }

    private void reserveBook(int book_id, int user_id) {
        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.reserveBook"));
            ps.setInt(1, book_id);
            ps.setInt(2, book_id);
            ps.setInt(3, user_id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> getBooksID(String name, String author, String year, int type) {
        List<Integer> ids = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.getBooksIDS"));
            ps.setString(1, name);
            ps.setString(2, author);
            ps.setString(3, year);
            ps.setInt(4, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    private int getStatusLiteratureCount(int status) {
        int count = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.getLiterStatusCount"));
            ps.setInt(1, status);
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
