package datalayer.data.literature;

public class LiteratureCollection {

    private String name;
    private String author;
    private String year;
    private int inStockCount;
    private LiteratureType literatureType;

    public LiteratureCollection(String name, String author, String year,
                                int inStockCount, LiteratureType literatureType) {
        super();

        this.name = name;
        this.author = author;
        this.year = year;
        this.inStockCount = inStockCount;
        this.literatureType = literatureType;
    }

    public LiteratureCollection() {
        super();
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

    public int getInStockCount() {
        return inStockCount;
    }

    public void setInStockCount(int inStockCount) {
        this.inStockCount = inStockCount;
    }

    public LiteratureType getLiteratureType() {
        return literatureType;
    }

    public void setLiteratureType(LiteratureType literatureType) {
        this.literatureType = literatureType;
    }
}
