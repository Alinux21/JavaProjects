package com.example.demo.jdbc;

import java.sql.*;

public class GenreDAO extends GenericDAO<Genre>{

    public GenreDAO() {
        super(Genre.class);
    }

    public void create(String name) throws SQLException {


        try (PreparedStatement pstmt = con.prepareStatement(
                "insert into genres (name) values (?)")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

}
