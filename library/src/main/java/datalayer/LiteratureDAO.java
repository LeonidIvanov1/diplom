package datalayer;

import java.util.List;

import datalayer.data.User;
import datalayer.data.literature.*;

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
	 * @param search
	 * @param start
	 * @param end
	 */
	List<Literature> getLiteratureList(String search, int start, int end);

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
	 * @param search   -- book name
	 * @param start -- book author
	 * @param end   -- book year
	 * @return
	 */
	List<LiteratureCollection> getLiteratureCollections(String search, int start,
														int end);

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
     * @param s
     * @param name        -- book name
     * @param author      -- book author
     * @param year        -- book year
     * @param isbn        -- book ISBN
     * @param description -- book description
     * @param status
     * @param type
     */
	void addLiterature(String name, String author, String year, String isbn,
                       String description,String rental, int status, int type);

	/**
	 * Returns literature info
	 * 
	 * @param bookID -- book ISBN
	 * @return
	 */
	Literature getLiteratureInfo(int bookID);

	/**
	 * Removes literature
	 *
	 * @param literatureIsbn -- book ISBN
	 */
	void deleteLiterature(int literatureIsbn);

	/**
	 * Change literature
	 *  @param s
	 * @param name        -- book name
	 * @param author      -- book author
	 * @param year        -- book year
	 * @param isbn        -- book ISBN
	 * @param description -- book description
	 * @param status
	 * @param type
	 * @param holder
	 * @param bookID
	 */
	void changeLiteratureDAO( String name, String author, String year,
							 String isbn, String description,String rental, int status, int type, int holder, int bookID);

	/**
	 * Checks literature collection by FGOS
		 * @param name        -- book name
	 * @param author      -- book author
	 * @param year        -- book year
	 * @return true if all is normal, else false
	 */
	boolean checkLiterature(String name, String author, String year,
                            String discipline, String specialty);

    int getLiteratureCollectionCount(String search);

	int getLiteratureCount(String search);

    List<Literature> getUserLiterature(int id);

    List<LiteratureDate> getLiteratureDates(int id);

	void returnLiterature(int bookId);

	void extendLiterature(int bookId);

    LiteratureFundData getLibraryFundData();

    List<User> getHolders();

	void createReserveRequest(int groupID, int teacher, int discipline, List<LiteratureCollection> literatureCollectionList);

	List<ReserveRequest> getReserveRequestList(int userID);

	void deleteReserveRequest(int reserveID);
}
