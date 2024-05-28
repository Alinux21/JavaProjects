package org.example.JPA.JPAentities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genres")
@NamedQueries({
        @NamedQuery(name = "Genre.findById",
                query = "select e from Genre e where e.id=(:id)"),
        @NamedQuery(name = "Genre.findByName",
                query = "select e from Genre e where e.name=(:name)"),
        @NamedQuery(name = "Genre.findAll",
                query = "select e from Genre e"),
        @NamedQuery(name = "Genre.deleteAll",
                query = "delete from Genre e"),
})
public class Genre implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SuppressWarnings("unused") // Used by JPA
    private Integer id;

    @Column(name = "genre_name")
    private String name;

    @OneToMany(mappedBy = "bookGenre")
    @SuppressWarnings("unused") // Used by JPA
    private final List<Book> books = new ArrayList<>();

    @SuppressWarnings("unused") // Used by JPA
    public Genre() {
        // Default constructor required by JPA
    }

    public Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
