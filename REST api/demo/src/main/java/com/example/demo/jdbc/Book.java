package com.example.demo.jdbc;

public class Book extends BaseEntity {

    private Integer year;
    private String name;
    private String author;
    private String genre;

    public Book(Integer id, Integer year, String name, String author, String genre) {
        this.id = id;
        this.year = year;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book - id:" + id + " year:" + year + " name:" + name + " author :" + author + " genre:"+genre;
    }

}
