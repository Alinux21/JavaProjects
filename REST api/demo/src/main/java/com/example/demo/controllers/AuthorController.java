package com.example.demo.controllers;

import com.example.demo.jdbc.Author;
import com.example.demo.jdbc.AuthorDAO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private List<Author> authors= new ArrayList<>();
    private final AuthorDAO authorDAO = new AuthorDAO();

    public AuthorController(){
        try{
            authors = authorDAO.findAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @GetMapping
    public List<Author> getAuthors() {
        return authors;
    }


}
