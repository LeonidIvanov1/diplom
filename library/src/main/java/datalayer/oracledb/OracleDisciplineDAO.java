package datalayer.oracledb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import datalayer.data.Discipline;

import resourcebundledemo.Resourcer;

import datalayer.DisciplineDAO;
import datalayer.data.Group;

public class OracleDisciplineDAO implements DisciplineDAO {

    private Connection connection;

    public OracleDisciplineDAO(Connection connection) {
        this.connection = connection;
    }


    public void addDiscipline(String discipline) {
        try {
            CallableStatement cs = connection.prepareCall(Resourcer.getString("sql.addDiscipline"));
            cs.setString(1, discipline);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void removeDiscipline(int discipline) {
        try {
            CallableStatement cs = connection.prepareCall(Resourcer.getString("sql.deleteDiscipline"));
            cs.setInt(1, discipline);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void changeDiscipline(String discipline, int id) {
        try {
            CallableStatement cs = connection.prepareCall(Resourcer.getString("sql.changeDiscipline"));
            cs.setString(1, discipline);
            cs.setInt(2, id);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
                    .prepareStatement(Resourcer.getString("sql.getDisciplines"));
            rs = ps.executeQuery();
            while (rs.next()) {
                disciplines.add(rs.getString(2));
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


    public List<String> getSpecialtyDisciplines(int specialtyID) {
        List<String> disciplines = new ArrayList<String>();
        PreparedStatement ps;
        ResultSet rs = null;

        try {
            ps = connection
                    .prepareStatement(Resourcer.getString("sql.getSpecialtyDisciplines"));
            ps.setInt(1, specialtyID);
            rs = ps.executeQuery();
            while (rs.next()) {
                disciplines.add(rs.getString(2));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplines;
    }

    @Override
    public int getDisciplineIDByName(String discipline) {
        int disID = 0;
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getDisciplineIDByName"));
            ps.setString(1, discipline);
            rs = ps.executeQuery();
            if (rs.next()) {
                disID = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disID;
    }

    @Override
    public Discipline getDisciplineInfo(int id) {
        Discipline discipline = null;

        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getDisciplineInfo"));
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                discipline = new Discipline(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discipline;
    }

    @Override
    public List<Discipline> getDisciplinesID() {
        List<Discipline> disciplines = new ArrayList<>();

        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getDisciplinesID"));
            rs = ps.executeQuery();
            while (rs.next()) {
                disciplines.add(new Discipline(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  disciplines;
    }


}
