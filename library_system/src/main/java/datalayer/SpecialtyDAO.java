package datalayer;



import datalayer.data.Specialty;
import datalayer.data.Standart;

import java.util.List;

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
	 * @param standart -- specialty standart
	 */
	void addSpecialty(String name, Standart standart);

	/**
	 * Removes specialty
	 * 
	 * @param name -- specialty name
	 */
	void deleteSpecialty(String name);

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
}
