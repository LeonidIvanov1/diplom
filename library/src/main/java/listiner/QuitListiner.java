package listiner;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import resourcebundledemo.Resourcer;

import datalayer.UserDAO;
import servlet.DAOServletManager;

/**
 *
 */
public class QuitListiner implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent sessionEvent) {
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        logout(sessionEvent);
    }

    private void logout(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        String login = (String) session
                .getAttribute(Resourcer.getString("atr.login"));
        UserDAO userDAO = DAOServletManager
                .getUserDAO(session.getServletContext());
        userDAO.logout(login);
    }
}
