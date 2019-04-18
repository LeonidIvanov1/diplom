package datalayer.oracledb;

import java.sql.Connection;

import datalayer.StandartDAO;

public class OracleStandartDAO implements StandartDAO {
	private Connection connection;
	
	public OracleStandartDAO(Connection connection) {
		this.connection = connection;
	}
}
