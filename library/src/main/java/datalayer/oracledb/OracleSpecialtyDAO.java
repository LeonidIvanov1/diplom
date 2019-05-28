package datalayer.oracledb;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import datalayer.data.Discipline;
import resourcebundledemo.Resourcer;

import datalayer.SpecialtyDAO;
import datalayer.data.Specialty;

public class OracleSpecialtyDAO implements SpecialtyDAO {

    private Connection connection;

    public OracleSpecialtyDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Specialty> getSpecialtyList() {
        List<Specialty> specialties = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = connection.prepareStatement(Resourcer.getString("sql.getSpecialties"));
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String code = rs.getString(3);
                float parameter = rs.getFloat(4);
                String desc = rs.getString(5);
                specialties.add(new Specialty(id, name, code, parameter, desc));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialties;

    }

    @Override
    public void addSpecialty(String name, String code, float parameter, String description) {
        try {
            CallableStatement cs = connection.prepareCall(Resourcer.getString("sql.addSpecialty"));
            cs.setString(1, name);
            cs.setString(2, code);
            cs.setString(4, description);
            cs.setFloat(3, parameter);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSpecialty(int id) {
        try {
            CallableStatement cs = connection.prepareCall(Resourcer.getString("sql.deleteSpecialty"));
            cs.setInt(1, id);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Specialty> searchSpecialties(String searchData) {
        return null;
    }

    @Override
    public Specialty getSpecialtyInfo(String name) {
        return null;
    }

    @Override
    public Specialty getSpecialty(int specialtyID) {
        Specialty specialty = null;
        PreparedStatement ps;
        ResultSet rs = null;
        try {

            ps = connection.prepareStatement(Resourcer.getString("sql.getSpecialtyByID"));
            ps.setInt(1, specialtyID);
            rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString(2);
                String specialtyCode = rs.getString(3);
                float standardParameter = rs.getFloat(4);
                String description = rs.getString(5);
                specialty = new Specialty(specialtyID, name, specialtyCode, standardParameter, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return specialty;
    }

    @Override
    public void changeSpecialty(String name, String code, float parameter, String description, int id) {
        try {
            CallableStatement cs = connection.prepareCall(Resourcer.getString("sql.changeSpecialty"));
            cs.setString(1, name);
            cs.setString(2, code);
            cs.setString(4, description);
            cs.setFloat(3, parameter);
            cs.setInt(5, id);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    List<Integer> getSpecialtyGroupsID (int specialtyID) {
        List<Integer> ids = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.getSpecialtyGroupsID"));
            ps.setInt(1, specialtyID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ids.add(rs.getInt(1));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  ids;
    }
    @Override
    public Set<Discipline> getSpecialtyDisciplines(int specialtyID) {
        Set<Discipline> disciplineSet = new HashSet<>();
        List<Integer> groups = getSpecialtyGroupsID(specialtyID);
        for (Integer groupID : groups) {
            try {
                PreparedStatement ps = connection.prepareStatement(Resourcer.getString("sql.getSpecialtyDisciplines"));
                ps.setInt(1, groupID);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    disciplineSet.add(new Discipline(rs.getInt(1), rs.getString(2)));
                }
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return disciplineSet;
    }
}
