package datalayer;



import datalayer.data.Discipline;
import datalayer.data.Specialty;

import java.util.List;
import java.util.Set;

public interface SpecialtyDAO {

	/**
	 * Returns specialty List
	 * 
	 * @return
	 */
	List<Specialty> getSpecialtyList();

	/**
	 * Adds new specialty
	 *
     * @param name     -- specialty name
     * @param code
     * @param parameter
     * @param description
     */
	void addSpecialty(String name, String code, float parameter, String description);

	/**
	 * Removes specialty
	 *
	 * @param id -- specialty name
	 */
	void deleteSpecialty(int id);

	/**
	 * Returns specialty list contains search data
	 * 
	 * @param searchData -- search data
	 * @return
	 */
	List<Specialty> searchSpecialties(String searchData);

	/**
	 * Return specialty by name
	 * 
	 * @param name -- specialty name
	 * @return
	 */
	Specialty getSpecialtyInfo(String name);

    Specialty getSpecialty(int specialtyID);

	void changeSpecialty(String name, String code, float parameter, String description, int id);

    Set<Discipline> getSpecialtyDisciplines(int specialtyID);
}
