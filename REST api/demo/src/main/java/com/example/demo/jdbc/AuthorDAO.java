package com.example.demo.jdbc;

import java.sql.*;

public class AuthorDAO extends GenericDAO<Author> {


    public AuthorDAO() {
        super(Author.class);
    }

    public void create(String name) throws SQLException {

        try (PreparedStatement pstmt = con.prepareStatement(
                "insert into authors (name) values (?)")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }


}
