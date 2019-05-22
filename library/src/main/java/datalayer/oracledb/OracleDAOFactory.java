package datalayer.oracledb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

import resourcebundledemo.Resourcer;

import datalayer.DAOFactory;
import datalayer.DisciplineDAO;
import datalayer.GroupDAO;
import datalayer.LiteratureDAO;
import datalayer.SpecialtyDAO;
import datalayer.StandartDAO;
import datalayer.UserDAO;

public class OracleDAOFactory extends DAOFactory {
	private static volatile OracleDAOFactory instance;
	private Connection connection;

	private OracleDAOFactory() {
	}

	public static OracleDAOFactory getInstance()
			throws ClassNotFoundException, SQLException {
		OracleDAOFactory factory = instance;
		if (instance == null) {
			synchronized (OracleDAOFactory.class) {
				instance = factory = new OracleDAOFactory();
				factory.connected();
			}
		}
		return factory;
	}

	private void connected() throws ClassNotFoundException, SQLException {
		Locale.setDefault(Locale.ENGLISH);
		Class.forName(Resourcer.getString("oracle.db.driver"));
		connection = DriverManager.getConnection(
				Resourcer.getString("oracle.db.url"),
				Resourcer.getString("oracle.db.user"),
				Resourcer.getString("oracle.db.password"));
	}

	public void closeConnection() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}


	public UserDAO getUserDAO() {
		return new OracleUserDAO(connection);
	}


	public GroupDAO getGroupDAO() {

		return new OracleGroupDAO(connection);
	}


	public DisciplineDAO getDisciplineDAO() {
		return new OracleDisciplineDAO(connection);
	}


	public SpecialtyDAO getSpecialtyDAO() {
		return new OracleSpecialtyDAO(connection);
	}


	public StandartDAO getStandartDAO() {
		return new OracleStandartDAO(connection);
	}


	public LiteratureDAO getLiteratureDAO() {
		// TODO Auto-generated method stub
		return new OracleLiteratureDAO(connection);
	}
}
