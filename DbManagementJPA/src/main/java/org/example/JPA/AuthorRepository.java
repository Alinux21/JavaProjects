package org.example.JPA;

import org.example.AuthorDAOInterface;
import org.example.JPA.JPAentities.Author;
import org.example.JPA.JPAentities.BaseEntity;

public class AuthorRepository extends AbstractRepository<Author> implements AuthorDAOInterface<Author> {

    public AuthorRepository() {
        super(Author.class);
    }

}
