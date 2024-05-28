package org.example;

import org.example.JPA.AuthorRepository;
import org.example.JPA.BookRepository;
import org.example.JPA.GenreRepository;

public class JPADAOFactory extends DAOFactory {


        @Override
        public BookDAOInterface getBookDAO() {
            return new BookRepository();
        }

        @Override
        public AuthorDAOInterface getAuthorDAO() {
            return new AuthorRepository();
        }

        @Override
        public GenreDAOInterface getGenreDAO() {
            return new GenreRepository();
        }
}
