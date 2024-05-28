package org.example.JDBC.JDBCEntities;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class JDBCBook extends JDBCBaseEntity {

    private Integer year;
    private String book_name;
    private String author_name;
    private String genre_name;
    private String publishing_house;
    private LocalDate publishing_date;

    public JDBCBook(Integer id, Integer year, String name, String author, String genre, String publishing_house,LocalDate publishing_date) {
        this.id = id;
        this.year = year;
        this.book_name = name;
        this.author_name = author;
        this.genre_name = genre;
        this.publishing_house=publishing_house;
        this.publishing_date = publishing_date;
    }

    public Integer getYear() {
        return year;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public String getPublishing_house() {
        return publishing_house;
    }

    public LocalDate getPublishing_date() {
        return publishing_date;
    }

    @Override
    public String toString() {
        return "Book - id:" + id + " year:" + year + " book_name:" + book_name + " author_name :" + author_name + " genre_name:"+ genre_name +" publishing_house:"+publishing_house+" publishing_date:"+publishing_date;
    }

}
