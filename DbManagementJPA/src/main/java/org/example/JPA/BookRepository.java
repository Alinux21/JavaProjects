package org.example.JPA;

import jakarta.persistence.Query;
import org.example.BookDAOInterface;
import org.example.JPA.JPAentities.Book;

import java.util.List;

public class BookRepository extends AbstractRepository<Book> implements BookDAOInterface<Book>{

    public BookRepository(){
        super(Book.class);
    }


}
