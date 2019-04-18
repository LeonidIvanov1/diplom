package listiner;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import resourcebundledemo.Resourcer;

import datalayer.DAOFactory;
import datalayer.DBType;
import datalayer.oracledb.OracleDAOFactory;

/**
 * The class contains methods for initializing the date level when launching the
 * application and placing it in context.
 *
 */
public class DAOInitialiserListener implements ServletContextListener {

	/**
	 * Close connection on context destruction
	 */

	public void contextDestroyed(ServletContextEvent contextEvent) {

		switch (DBType.getTypeByName(Resourcer.getString("dao.dbtype"))) {
		case ORACLE:
			closeOracleDBConnection();
			break;
		default:
			break;
		}
	}

	/**
	 * Context initialization
	 */

	public void contextInitialized(ServletContextEvent contextEvent) {
		ServletContext context = contextEvent.getServletContext();
		initializationDAO(context);

	}

	/**
	 * Close Oracle DB connection
	 */
	private void closeOracleDBConnection() {
		OracleDAOFactory df;
		try {
			df = OracleDAOFactory.getInstance();
			df.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize DAO level and puts it into servlet context
	 * 
	 * @param context -- Servlet context
	 */
	private void initializationDAO(ServletContext context) {
		DAOFactory df = DAOFactory.getInstance(
				DBType.getTypeByName(Resourcer.getString("dao.dbtype")));
		context.setAttribute(Resourcer.getString("dao.user"), df.getUserDAO());
		context.setAttribute(Resourcer.getString("dao.group"),
				df.getGroupDAO());
		context.setAttribute(Resourcer.getString("dao.discipline"),
				df.getDisciplineDAO());
		
		context.setAttribute(Resourcer.getString("dao.standart"),
				df.getStandartDAO());
		
		context.setAttribute(Resourcer.getString("dao.specialty"),
				df.getSpecialtyDAO());
		
		context.setAttribute(Resourcer.getString("dao.literature"),
				df.getLiteratureDAO());
	}

}
