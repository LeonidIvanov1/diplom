package datalayer;

import java.util.List;

import datalayer.data.literature.Literature;
import datalayer.data.literature.LiteratureCollection;

public interface LiteratureDAO {
	/**
	 * Returns literature collections
	 * 
	 * @return
	 */
	List<LiteratureCollection> getLiteratureCollections();

	/**
	 * Returns literature collections after search
	 * 
	 * @param searchData -- search data
	 * @return
	 */
	List<LiteratureCollection> searchLiteratureCollections(String searchData);

	/**
	 * Return literature list
	 * 
	 * @return
	 */
	List<Literature> getLiteratureList();

	/**
	 * Return literature list after search
	 * 
	 * @param searchData -- search data
	 * @return
	 */
	List<Literature> searchLiterature(String searchData);

	/**
	 * Return literature collections
	 * 
	 * @param name   -- book name
	 * @param author -- book author
	 * @param year   -- book year
	 * @return
	 */
	LiteratureCollection getLiteratureCollection(String name, String author,
                                                 String year);

	/**
	 * Reserve literature
	 * 
	 * @param name     -- book name
	 * @param author   -- book author
	 * @param year     -- book year
	 * @param gropName -- group name
	 */
	void reserveLiterature(String name, String author, String year,
                           String gropName);

	/**
	 * Add new literature
	 * 
	 * @param name        -- book name
	 * @param author      -- book author
	 * @param year        -- book year
	 * @param isbn        -- book ISBN
	 * @param description -- book description
	 */
	void addLiterature(String name, String author, String year, String isbn,
                       String description);

	/**
	 * Returns literature info
	 * 
	 * @param literatureIsbn -- book ISBN
	 * @return
	 */
	Literature getLiteratureInfo(String literatureIsbn);

	/**
	 * Removes literature
	 * 
	 * @param literatureIsbn -- book ISBN
	 */
	void deleteLiterature(String literatureIsbn);

	/**
	 * Change literature
	 * 
	 * @param name        -- book name
	 * @param author      -- book author
	 * @param year        -- book year
	 * @param isbn        -- book ISBN
	 * @param description -- book description
	 */
	void changeLiteratureDAO(String name, String author, String year,
                             String isbn, String description);

	/**
	 * Checks literature collection by FGOS
		 * @param name        -- book name
	 * @param author      -- book author
	 * @param year        -- book year
	 * @return true if all is normal, else false
	 */
	boolean checkLiterature(String name, String author, String year,
                            String discipline, String specialty);
}
