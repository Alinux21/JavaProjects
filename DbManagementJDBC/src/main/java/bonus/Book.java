package bonus;

public class Book extends BaseEntity {

    private Integer year;
    private String name;
    private String author;
    private String genre;

    public Book(Integer id, Integer year, String name, String author, String genre) {
        this.id = id;
        this.year = year;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "Book - id:" + id + " year:" + year + " name:" + name + " author :" + author + " genre:"+genre;
    }

}
