package datalayer.data.literature;

import datalayer.data.User;

public class Literature {
	private LiteratureStatus literatureStatus;
	private User holder;
	private String name;
	private String author;
	private String isbn;
	private String year;
	private String description;

	public Literature(LiteratureStatus literatureStatus, User holder,
			String name, String author, String isbn, String year,
			String description) {
		super();
		this.literatureStatus = literatureStatus;
		this.holder = holder;
		this.name = name;
		this.author = author;
		this.isbn = isbn;
		this.year = year;
		this.description = description;
	}

	public Literature() {

	}

	/**
	 * @return the literatureStatus
	 */
	public LiteratureStatus getLiteratureStatus() {
		return literatureStatus;
	}

	/**
	 * @param literatureStatus the literatureStatus to set
	 */
	public void setLiteratureStatus(LiteratureStatus literatureStatus) {
		this.literatureStatus = literatureStatus;
	}

	/**
	 * @return the holder
	 */
	public User getHolder() {
		return holder;
	}

	/**
	 * @param holder the holder to set
	 */
	public void setHolder(User holder) {
		this.holder = holder;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
