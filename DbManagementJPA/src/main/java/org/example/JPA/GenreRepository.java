package org.example.JPA;

import org.example.GenreDAOInterface;
import org.example.JPA.JPAentities.Genre;

public class GenreRepository extends AbstractRepository<Genre> implements GenreDAOInterface<Genre>{

    public GenreRepository(){
        super(Genre.class);
    }

}
