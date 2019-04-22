package command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.UserDAO;
import servlet.DAOServletManager;
import datalayer.data.Role;

public class AddUserCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		addUser(request);
		return new CommandRedirection(Resourcer.getString("path.page.main"),
				RedirectionType.FORWARD);
	}

	private void addUser(HttpServletRequest request) {
		Role userRole = Role
				.valueOf(request.getParameter("select-role").toUpperCase());
		switch (userRole) {
		case ADMIN:
			addAdministrator(request);
			break;
		case LIBRARIAN:
			addLibrarian(request);
			break;
		case STUDENT:
			addStudent(request);
			break;
		case TEACHER:
			addTeacher(request);
		default:
			break;

		}
	}

	private void addAdministrator(HttpServletRequest request) {
		UserDAO userDAO = DAOServletManager.getUserDAO(request);
		userDAO.addAdministrator(request.getParameter("fullname"),
				request.getParameter("login"),
				request.getParameter("password"));
	}

	private void addLibrarian(HttpServletRequest request) {
		UserDAO userDAO = DAOServletManager.getUserDAO(request);
		userDAO.addLibrarian(request.getParameter("fullname"),
				request.getParameter("login"),
				request.getParameter("password"));
	}

	private void addStudent(HttpServletRequest request) {
		UserDAO userDAO = DAOServletManager.getUserDAO(request);
		userDAO.addStudent(request.getParameter("fullname"),
				request.getParameter("login"), request.getParameter("password"),
				request.getParameter("group"));
	}

	private void addTeacher(HttpServletRequest request) {
		UserDAO userDAO = DAOServletManager.getUserDAO(request);
		List<String> groups;
		List<String> disciplines;
		if (request.getParameterValues("group[]") != null) {
			groups = Arrays.asList(request.getParameterValues("group[]"));
		} else {
			groups = new ArrayList<String>();
		}
		if (request.getParameterValues("discipline[]") != null) {
			disciplines = Arrays.asList(request.getParameterValues("discipline[]"));
		} else {
			disciplines = new ArrayList<String>();
		}
		userDAO.addTeacher(request.getParameter("fullname"),
				request.getParameter("login"), request.getParameter("password"),
				groups, disciplines);
	}

}
