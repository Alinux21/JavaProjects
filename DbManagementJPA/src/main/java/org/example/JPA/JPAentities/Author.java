    package org.example.JPA.JPAentities;

    import jakarta.persistence.*;
    import  java.util.List;
    import  java.util.ArrayList;
    @Entity
    @Table(name = "authors")
    @NamedQueries({
            @NamedQuery(name = "Author.findById",
                    query = "select e from Author e where e.id=(:id)"),
            @NamedQuery(name = "Author.findByName",
                    query = "select e from Author e where e.name=(:name)"),
            @NamedQuery(name = "Author.findAll",
                    query = "select e from Author e"),
            @NamedQuery(name = "Author.deleteAll",
                    query = "delete from Author e"),
    })
    public class Author implements BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @SuppressWarnings("unused") // Used by JPA
        private Integer id;

        @Column(name = "author_name")
        private String name;

        @ManyToMany(mappedBy = "authors",cascade = CascadeType.REMOVE)
        @SuppressWarnings("unused") // Used by JPA
        private final List<Book> books = new ArrayList<>();

        @SuppressWarnings("unused") // Used by JPA
        public Author() {
            // Default constructor required by JPA
        }

        public Author(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Author{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
