package datalayer.data.literature;

import datalayer.data.User;

public class Literature {
    private int id;
    private LiteratureStatus literatureStatus;
    private User holder;
    private String name;
    private String author;
    private String isbn;
    private String year;
    private String rental;
    private String description;
    private LiteratureType literatureType;

    public Literature(int id, LiteratureStatus literatureStatus, User holder,
                      String name, String author, String isbn,
                      String year, String rental, String description,
                      LiteratureType literatureType) {
        this.id = id;
        this.literatureStatus = literatureStatus;
        this.holder = holder;
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.rental = rental;
        this.description = description;
        this.literatureType = literatureType;
    }

    public LiteratureStatus getLiteratureStatus() {
        return literatureStatus;
    }

    public void setLiteratureStatus(LiteratureStatus literatureStatus) {
        this.literatureStatus = literatureStatus;
    }

    public User getHolder() {
        return holder;
    }

    public void setHolder(User holder) {
        this.holder = holder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LiteratureType getLiteratureType() {
        return literatureType;
    }

    public void setLiteratureType(LiteratureType literatureType) {
        this.literatureType = literatureType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
