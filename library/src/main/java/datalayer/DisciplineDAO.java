package datalayer;

import java.util.List;

import datalayer.data.Discipline;
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
	void removeDiscipline(int discipline);

	/**
	 * Changes discipline name
	 * @param discipline
	 * @param id
	 */
	void changeDiscipline(String discipline, int id);

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
	 *
     * @param groupID@return
	 */
	List<String> getSpecialtyDisciplines(int groupID);

	int getDisciplineIDByName(String discipline);

    Discipline getDisciplineInfo(int id);

	List<Discipline> getDisciplinesID();

}
