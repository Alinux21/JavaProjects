package org.example;

import org.example.JDBC.BookDAO;
import org.example.JPA.BookRepository;
import org.example.JPA.JPAentities.BaseEntity;

public abstract class DAOFactory{

    public static final int JDBC = 1;
    public static final int JPA = 2;

    public abstract BookDAOInterface<BaseEntity> getBookDAO();
    public abstract GenreDAOInterface<BaseEntity> getGenreDAO();
    public abstract AuthorDAOInterface<BaseEntity> getAuthorDAO();

        public static DAOFactory getDAOFactory(int whichFactory) {

            switch (whichFactory) {
                case JDBC:
                    return new JDBCDAOFactory();
                case JPA:
                    return new JPADAOFactory();
                default:
                    return null;
            }
        }
    }

