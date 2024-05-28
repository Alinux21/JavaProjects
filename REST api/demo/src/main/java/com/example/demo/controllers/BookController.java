package com.example.demo.controllers;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.jdbc.Book;
import com.example.demo.jdbc.BookDAO;

@RestController
@RequestMapping("/books")
public class BookController {

    private List<Book> books = new ArrayList<>();
    private final BookDAO bookDAO = new BookDAO();
    private  Map<Book,Book> suggestions = new HashMap<>();

    public BookController() {
        try {
            books = bookDAO.findAll();
            generateReadingSuggestions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    public List<Book> getAuthors() {
        return books;
    }

    @PostMapping
    public int createBook(@RequestParam int year, @RequestParam String name, @RequestParam String author, @RequestParam String genre) {
        int id = 1 + bookDAO.count();
        try{
            bookDAO.create(year, name, author, genre);
            books.add(bookDAO.findById(id).get());

        }catch (SQLException e) {
            System.out.println("Error creating book " + e.getMessage());
        }
        System.out.println("Added book succesfully");
        return id;
    }

    @PostMapping(value = "/obj", consumes="application/json")
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        books.add(book);
        System.out.println(book);
        return new ResponseEntity<>(
                "Book created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable int id, @RequestParam String name) {
        Book book = null;

        try{
            if(bookDAO.findById(id).isPresent()) {
                book = bookDAO.findById(id).get();
            }
        }catch (SQLException e) {
            System.out.println("Error finding book with id " + id + e.getMessage());
        }

        if (book == null) {
            return new ResponseEntity<>(
                    "Book not found", HttpStatus.NOT_FOUND); //or GONE
        }
        book.setName(name);
        bookDAO.setName(id, name);
        return new ResponseEntity<>(
                "Book updated successsfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        Book book = null;

        try{
            if(bookDAO.findById(id).isPresent()) {
                book = bookDAO.findById(id).get();
            }
        }catch (SQLException e) {
            System.out.println("Error finding book with id " + id + e.getMessage());
        }

        if (book == null) {
            return new ResponseEntity<>(
                    "Book not found", HttpStatus.NOT_FOUND); //or GONE
        }

        bookDAO.delete(id);
        books.remove(book);
        return new ResponseEntity<>("Book removed", HttpStatus.OK);
    }


    private void generateReadingSuggestions() {

        Random random = new Random();

        for(int i=0;i<books.size();i++){
            for(int j=i+1;j<books.size();j++){
                Book bookA = books.get(i);
                Book bookB = books.get(j);

                if(random.nextBoolean()){
                    suggestions.put(bookA,bookB);
                }else{
                    suggestions.put(bookB,bookA);
                }
            }
        }

    }

    @GetMapping("/longestsequence")
    public List<Book> getLongestSequence() {

        List<Book> longestSequence = new ArrayList<>();

        for (Book book : books) {
            List<Book> currentSequence = new ArrayList<>();
            currentSequence.add(book);

            while (suggestions.containsKey(book) && suggestions.get(book).getYear() > book.getYear()) {
                book = suggestions.get(book);
                currentSequence.add(book);
            }

            if (currentSequence.size() > longestSequence.size()) {
                longestSequence = currentSequence;
            }
        }

        int n = longestSequence.size()-1;
        AtomicInteger i= new AtomicInteger();
        longestSequence.forEach(book -> {
            if(i.get() <n) {
                i.getAndIncrement();
                System.out.println("\n"+book.getName() + " year:" + book.getYear() + "  -->  " + suggestions.get(book).getName() + " year:" + suggestions.get(book).getYear());
            }
        });



        return longestSequence;
    }






}