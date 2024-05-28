package org.example;

import org.example.JDBC.AuthorDAO;
import org.example.JDBC.BookDAO;
import org.example.JDBC.GenreDAO;
public  class JDBCDAOFactory extends DAOFactory {

        @Override
        public BookDAO getBookDAO() { return new BookDAO();}

        @Override
        public AuthorDAO getAuthorDAO() {
            return new AuthorDAO();
        }

        @Override
        public GenreDAO getGenreDAO() {
            return new GenreDAO();
        }
}
