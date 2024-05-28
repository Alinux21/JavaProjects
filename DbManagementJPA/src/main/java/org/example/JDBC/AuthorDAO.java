package org.example.JDBC;

import org.example.AuthorDAOInterface;
import org.example.JDBC.JDBCEntities.JDBCAuthor;
import org.example.JPA.JPAentities.BaseEntity;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class AuthorDAO implements AuthorDAOInterface {

    protected static Connection connection;

    public AuthorDAO(){
        connection = Database.getConnection();
    }

    public BaseEntity save(BaseEntity author){

        JDBCAuthor entity = (JDBCAuthor) author;

        try (PreparedStatement pstmt = connection.prepareStatement(
                "insert into authors (author_name) values (?)")) {
            pstmt.setString(1, entity.getAuthor_name());
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println("Author '"+ entity.getAuthor_name() +"' was saved."  );

        return  entity;
    }

    public Optional<JDBCAuthor> findByName(String name) {


        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from authors where author_name='" + name + "'")) {
            if (rs.next()) {
                return Optional.of(new JDBCAuthor(rs.getInt(1), rs.getString(2)));
            } else
                return Optional.empty();
        }catch (SQLException e){
            return Optional.empty();
        }
    }

    public Optional<JDBCAuthor> findById(Integer id){


        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from authors where id=" + id)) {
            if (rs.next()) {
                return  Optional.of(new JDBCAuthor(rs.getInt(1), rs.getString(2)));
            } else
                return Optional.empty();
        }catch (SQLException e){
            return Optional.empty();
        }
    }

    public List<JDBCAuthor> findAll() {


        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from authors")) {

            List<JDBCAuthor> authors = new ArrayList<>();

            while (rs.next()) {
                authors.add(new JDBCAuthor(rs.getInt(1), rs.getString(2)));
            }

            if (authors.isEmpty()) {
                return null;
            }

            return authors;

        }catch (SQLException e){
            return null;
        }

    }

}
