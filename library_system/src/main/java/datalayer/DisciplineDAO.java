package datalayer;

import java.util.List;

import datalayer.data.Group;

public interface DisciplineDAO {
	/**
	 * Returns list of disciplines
	 * @return
	 */
	List<String> getDisciplinesList();

	/**
	 * Adds new discipline
	 * @param discipline
	 */
	void addDiscipline(String discipline);

	/**
	 * Removes discipline
	 * @param discipline 
	 */
	void removeDiscipline(String discipline);

	/**
	 * Changes discipline name
	 * @param discipline
	 */
	void changeDiscipline(String discipline);

	/**
	 * Sets teacher discipline
	 * @param discipline
	 * @param login
	 */
	void setTeacherDiscipline(String discipline, String login);

	/**
	 * Sets group discipline
	 * @param discipline
	 * @param grop
	 */
	void setGroupDiscipline(String discipline, Group grop);
	/**
	 * Change teacher disciplines
	 * @param disciplines
	 * @param login
	 */
	void changeTeacherDisciplines(List<String> disciplines, String login);
	/**
	 * Return teacher disciplines
	 * @param login
	 * @return
	 */
	List<String> getTeacherDisciplines(String login);
	/**
	 * Returns group disciplines
	 * @param groupName
	 * @return
	 */
	List<String> getGroupDisciplines(String groupName);
}
