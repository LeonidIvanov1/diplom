package servlet;


import resourcebundledemo.Resourcer;

import datalayer.DisciplineDAO;
import datalayer.GroupDAO;
import datalayer.LiteratureDAO;
import datalayer.SpecialtyDAO;
import datalayer.StandartDAO;
import datalayer.UserDAO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 * This class provides methods for obtaining various DAOs from contexts
 */
public class DAOServletManager {
    /**
     * Constructor of class DAOServletManager
     */
    private DAOServletManager() {

    }

    /**
     * Gets User DAO from ServletContext
     *
     * @param context ServletContext
     * @return User DAO
     */
    public static UserDAO getUserDAO(ServletContext context) {
        return (UserDAO) context.getAttribute(Resourcer.getString("dao.user"));
    }

    /**
     * Gets User DAO from HttpServletRequest
     *
     * @return User DAO
     */
    public static UserDAO getUserDAO(HttpServletRequest request) {
        return getUserDAO(request.getSession().getServletContext());
    }

    /**
     * Gets Group DAO from ServletContext
     *
     * @param context ServletContext
     * @return User DAO
     */
    public static GroupDAO getGroupDAO(ServletContext context) {
        return (GroupDAO) context
                .getAttribute(Resourcer.getString("dao.group"));
    }

    /**
     * Gets Group DAO from HttpServletRequest
     *
     * @return User DAO
     */
    public static GroupDAO getGroupDAO(HttpServletRequest request) {
        return getGroupDAO(request.getSession().getServletContext());
    }

    /**
     * Gets DisciplineDAO from ServletContext
     *
     * @param context ServletContext
     * @return User DAO
     */
    public static DisciplineDAO getDisciplineDAO(ServletContext context) {
        return (DisciplineDAO) context
                .getAttribute(Resourcer.getString("dao.discipline"));
    }

    /**
     * Gets DisciplineDAO from HttpServletRequest
     *
     * @return User DAO
     */
    public static DisciplineDAO getDisciplineDAO(HttpServletRequest request) {
        return getDisciplineDAO(request.getSession().getServletContext());
    }

    /**
     * Gets DisciplineDAO from ServletContext
     *
     * @param context ServletContext
     * @return User DAO
     */
    public static StandartDAO getStandartDAO(ServletContext context) {
        return (StandartDAO) context
                .getAttribute(Resourcer.getString("dao.standart"));
    }

    /**
     * Gets DisciplineDAO from HttpServletRequest
     *
     * @return User DAO
     */
    public static StandartDAO getStandartDAO(HttpServletRequest request) {
        return getStandartDAO(request.getSession().getServletContext());
    }

    /**
     * Gets DisciplineDAO from ServletContext
     *
     * @param context ServletContext
     * @return User DAO
     */
    public static SpecialtyDAO getSpecialtyDAO(ServletContext context) {
        return (SpecialtyDAO) context
                .getAttribute(Resourcer.getString("dao.specialty"));
    }

    /**
     * Gets DisciplineDAO from HttpServletRequest
     *
     * @return User DAO
     */
    public static SpecialtyDAO getSpecialtyDAO(HttpServletRequest request) {
        return getSpecialtyDAO(request.getSession().getServletContext());
    }

    /**
     * Gets DisciplineDAO from ServletContext
     *
     * @param context ServletContext
     * @return User DAO
     */
    public static LiteratureDAO getLiteratureDAO(ServletContext context) {
        return (LiteratureDAO) context
                .getAttribute(Resourcer.getString("dao.literature"));
    }

    /**
     * Gets DisciplineDAO from HttpServletRequest
     *
     * @return User DAO
     */
    public static LiteratureDAO getLiteratureDAO(HttpServletRequest request) {
        return getLiteratureDAO(request.getSession().getServletContext());
    }

}
