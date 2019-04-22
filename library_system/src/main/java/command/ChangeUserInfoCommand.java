package command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.DisciplineDAO;
import datalayer.GroupDAO;
import datalayer.UserDAO;
import servlet.DAOServletManager;
import datalayer.data.Role;

public class ChangeUserInfoCommand implements ActionCommand {


	public CommandRedirection execute(HttpServletRequest request) {
		changeUserInfo(request);
		return new CommandRedirection(Resourcer.getString("path.page.main"),
				RedirectionType.FORWARD);
	}

	private void changeUserInfo(HttpServletRequest request) {
		UserDAO userDAO = DAOServletManager.getUserDAO(request);
		userDAO.changeUser(
				request.getParameter(Resourcer.getString("par.changeUser")),
				request.getParameter(Resourcer.getString("par.fullname")),
				request.getParameter(Resourcer.getString("par.password")));
		Role role = Role
				.valueOf(request.getParameter(Resourcer.getString("par.role")));
		switch (role) {
		case STUDENT:
			changeStudentGroup(request);
			break;
		case TEACHER:
			changeTeacherGroups(request);
			changeTeacherDisciplines(request);
		default:
			break;
		}

	}

	private void changeTeacherDisciplines(HttpServletRequest request) {
		DisciplineDAO disciplineDAO = DAOServletManager
				.getDisciplineDAO(request);
		List<String> disciplines = new ArrayList<String>();
		if (request.getParameterValues(
				Resourcer.getString("par.disciplines")) != null) {
			disciplines = Arrays.asList(request.getParameterValues(
					Resourcer.getString("par.disciplines")));
		}
		disciplineDAO.changeTeacherDisciplines(disciplines,
				request.getParameter(Resourcer.getString("par.login")));

	}

	private void changeTeacherGroups(HttpServletRequest request) {
		GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
		List<String> groups = new ArrayList<String>();
		if (request.getParameterValues(
				Resourcer.getString("par.groups")) != null) {
			groups = Arrays.asList(request.getParameterValues(
					Resourcer.getString("par.groups")));
		}
		groupDAO.changeTeacherGroups(
				request.getParameter(Resourcer.getString("par.login")), groups);

	}

	private void changeStudentGroup(HttpServletRequest request) {
		GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
		groupDAO.changeStudentGroup(
				request.getParameter(Resourcer.getString("par.login")),
				request.getParameter(Resourcer.getString("par.group")));
	}
}
