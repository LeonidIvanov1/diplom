package command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import resourcebundledemo.Resourcer;

import datalayer.DisciplineDAO;
import datalayer.GroupDAO;
import datalayer.UserDAO;
import servlet.DAOServletManager;
import datalayer.data.Group;
import datalayer.data.User;

/**
 * Class describe get user info command
 * 
 * @author Admin
 *
 */
public class GetUserInfoCommand implements ActionCommand {

	/**
	 * Redirection type
	 */
	private static final RedirectionType REDIRECTION_TYPE = RedirectionType.FORWARD;

	/**
	 * 
	 */

	public CommandRedirection execute(HttpServletRequest request) {
		setUserInfo(request);
		return new CommandRedirection(Resourcer.getString("path.page.user"),
				REDIRECTION_TYPE);
	}

	/**
	 * Set user info in request
	 * 
	 * @param request -- HttpServletRequest
	 */
	private void setUserInfo(HttpServletRequest request) {
		setGroupsData(request);
		setDisciplinesData(request);
		UserDAO userDAO = DAOServletManager.getUserDAO(request);
		User user = userDAO.getUserInfo(
				request.getParameter(Resourcer.getString("par.userid")));
		switch (user.getRole()) {
		case STUDENT:
			setStudentGroup(request, user);
			break;
		case TEACHER:
			setTeacherGroups(request, user);
			setTeacherDisciplines(request, user);
			break;
		default:
			break;
		}
		request.setAttribute(Resourcer.getString("atr.userinfo"), user);

	}

	private void setStudentGroup(HttpServletRequest request, User user) {
		GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
		Group group = groupDAO.getStudentGroup(user.getLogin());
		request.setAttribute(Resourcer.getString("atr.studgroup"), group);
	}

	private void setTeacherGroups(HttpServletRequest request, User user) {
		GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
		List<Group> groups = groupDAO.getTeacherGroups(user.getLogin());
		request.setAttribute(Resourcer.getString("atr.teachergroups"), groups);
		
	}
	
	private void setTeacherDisciplines(HttpServletRequest request, User user) {
		DisciplineDAO disciplineDAO = DAOServletManager.getDisciplineDAO(request);
		List<String> disciplines = disciplineDAO.getTeacherDisciplines(user.getLogin());
		request.setAttribute(Resourcer.getString("atr.teacherdisciplines"), disciplines);
	}

	private void setGroupsData(HttpServletRequest request) {
		GroupDAO groupDAO = DAOServletManager.getGroupDAO(request);
		request.setAttribute("groups", groupDAO.getGroupList());
	}

	private void setDisciplinesData(HttpServletRequest request) {
		DisciplineDAO disciplineDAO = DAOServletManager
				.getDisciplineDAO(request);
		request.setAttribute("disciplines", disciplineDAO.getDisciplinesList());
	}
}
