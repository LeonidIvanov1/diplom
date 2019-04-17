package datalayer;

public abstract class DAOFactory {
	public static DAOFactory getInstance(DBType dbType) {
		DAOFactory result = dbType.getDAOFactory();
		return result;
	}

	public abstract UserDAO getUserDAO();
	public abstract GroupDAO getGroupDAO();
	public abstract DisciplineDAO getDisciplineDAO();
	public abstract SpecialtyDAO getSpecialtyDAO();
	public abstract StandartDAO getStandartDAO();
	public abstract LiteratureDAO getLiteratureDAO();
}
