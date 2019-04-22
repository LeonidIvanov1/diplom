package servlet;

import command.ActionCommand;
import command.ActionFactory;
import command.CommandRedirection;
import resourcebundledemo.Resourcer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Processing request GET
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Processing request POST
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Processing request
     */
    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {
        CommandRedirection comandRedirection = null;
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);
        comandRedirection = command.execute(request);
        if (comandRedirection != null) {
            switch (comandRedirection.getRedirectionType()) {
                case FORWARD:
                    RequestDispatcher dispatcher = getServletContext()
                            .getRequestDispatcher(comandRedirection.getPage());
                    dispatcher.forward(request, response);
                    break;
                case SENDREDIRECT:
                    response.sendRedirect(
                            request.getContextPath() + comandRedirection.getPage());
                default:
                    break;
            }
        } else {
            request.getSession().setAttribute("nullPage",
                    Resourcer.getString("message.nullpage"));
            response.sendRedirect(request.getContextPath()
                    + Resourcer.getString("path.page.error"));
        }
    }
}