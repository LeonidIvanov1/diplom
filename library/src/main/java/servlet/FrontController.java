package servlet;

import command.ActionCommand;
import command.ActionFactory;
import command.ValueObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


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

        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);
        ValueObject valueObject = command.execute(request);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter outs = response.getWriter();
        outs.print(valueObject.generateJSON());
        outs.flush();
    }
}