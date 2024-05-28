package org.example.JDBC;

import org.example.BookDAOInterface;
import org.example.JDBC.JDBCEntities.JDBCBook;
import org.example.JPA.JPAentities.BaseEntity;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class BookDAO implements BookDAOInterface {

    protected static Connection connection;

    public BookDAO(){
        connection = Database.getConnection();
    }

    public BaseEntity save(BaseEntity bookEntity) {

        JDBCBook entity = (JDBCBook) bookEntity;

        try (PreparedStatement pstmt = connection.prepareStatement(
                "insert into books (year,book_name,author_name,genre_name,publishing_house,publishing_date) values (?,?,?,?,?,?)")) {
            pstmt.setInt(1, entity.getYear());
            pstmt.setString(2, entity.getBook_name());
            pstmt.setString(3, entity.getAuthor_name());
            pstmt.setString(4, entity.getGenre_name());
            pstmt.setString(5, entity.getPublishing_house());
            pstmt.setDate(6, Date.valueOf(entity.getPublishing_date()));
            pstmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println("Book '"+ entity.getBook_name() +"' was saved.");

        return entity;
    }

    public Optional<JDBCBook> findByName(String name)  {

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from books where book_name='" + name + "'")) {
            if (rs.next()) {
                return Optional.of(new JDBCBook(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getDate(8).toLocalDate()));
            } else
                return Optional.empty();
        }catch (SQLException e){
            return Optional.empty();
        }
    }

    public Optional<JDBCBook> findById(Integer id) {


        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from books where id=" + id)) {
            if (rs.next()) {
                return Optional.of(new JDBCBook( rs.getInt(1),  rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getDate(8).toLocalDate()));
            } else
                return Optional.empty();
        }catch (SQLException e){
            System.out.println("Error in findById"+e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<JDBCBook> findAll() {

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from books")) {

            List<JDBCBook> JDBCBooks = new ArrayList<>();


            while (rs.next()) {
                JDBCBooks.add(new JDBCBook(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getDate(8).toLocalDate()));
            }

            if (JDBCBooks.isEmpty()) {
                return null;
            }

            return JDBCBooks;

        }catch (SQLException e){
            return null;
        }

    }

    public List<JDBCBook> findAll(int maxRecordsCount) {


        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from books")) {

            List<JDBCBook> JDBCBooks = new ArrayList<>();


            while (rs.next() && JDBCBooks.size()<=maxRecordsCount) {
                JDBCBooks.add(new JDBCBook(rs.getInt(1),rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getDate(8).toLocalDate()));
            }

            if (JDBCBooks.isEmpty()) {
                return null;
            }

            return JDBCBooks;

        }catch (SQLException e){
            System.err.println("Error in findAll(int maxRecordsCount)"+e.getMessage());
            return null;
        }

    }

}
