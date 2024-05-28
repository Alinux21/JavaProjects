package org.example.JPA.JPAentities;

import jakarta.persistence.*;
import org.example.JPA.AuthorRepository;
import org.example.JPA.GenreRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "books")
@NamedQueries({
        @NamedQuery(name = "Book.findById",
                query = "select e from Book e where e.id=(:id)"),
        @NamedQuery(name = "Book.findByName",
                query = "select e from Book e where e.name=(:name)"),
        @NamedQuery(name = "Book.findAll",
                query = "select e from Book e"),
        @NamedQuery(name = "Book.deleteAll",
                query = "delete from Book e"),
})
public class Book implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SuppressWarnings("unused") // Used by JPA
    private Integer id;
    private Integer year;
    @Column(name="book_name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @Column(name="author")
    private final List<Author> authors = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre bookGenre;


    private String publishing_house;

    private LocalDate publishing_date;

    @SuppressWarnings("unused") // Used by JPA
    public Book(){
        // Default constructor required by JPA
    }

    public Book(Integer year,String name,List<Author> authors,Genre genre,String publishing_house,LocalDate publishing_date){
        this.year=year;
        this.name=name;

        AuthorRepository authorRepo = new AuthorRepository();
        for (Author authorName : authors) {
            Optional<Author> author;
           try {
               author = authorRepo.findByName(authorName.getName());
           }catch (Exception e){
               author = Optional.empty();
           }
            if (author.isEmpty()) {
                author = Optional.of(new Author(authorName.getName()));
                authorRepo.save(author.get());
            }
            this.authors.add(author.get());
        }

        GenreRepository genreRepo = new GenreRepository();
        Optional<Genre> newGenre;
        try{
             newGenre = genreRepo.findByName(genre.getName());
        }catch (Exception e){
             newGenre = Optional.empty();
        }

        if (newGenre.isEmpty()) {
            newGenre = Optional.of(new Genre(genre.getName()));
            genreRepo.save(newGenre.get());
        }
        this.bookGenre = newGenre.get();

        this.publishing_house=publishing_house;
        this.publishing_date=publishing_date;
    }

    public  void setId(Integer id){
        this.id=id;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", year=" + year +
                ", name='" + name + '\'' +
                ", author='" + authors + '\'' +
                ", genre='" + bookGenre + '\'' +
                ", publishing_house='" + publishing_house + '\'' +
                ", publishing_date='" + publishing_date + '\'' +
                '}';
    }
}
