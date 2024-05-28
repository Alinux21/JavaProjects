package bonus;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class BookDAO extends GenericDAO<Book> {


    public void create(Integer year, String name, String author, String genre) throws SQLException {

        Connection con = GenericDAO.connection;

        try (PreparedStatement pstmt = con.prepareStatement(
                "insert into books (year,name,author,genre) values (?,?,?,?)")) {
            pstmt.setInt(1, year);
            pstmt.setString(2, name);
            pstmt.setString(3, author);
            pstmt.setString(4, genre);
            pstmt.executeUpdate();
        }
    }

    public Optional<Book> findByName(String name) throws SQLException {

        Connection con = GenericDAO.connection;

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from books where name='" + name + "'")) {
            if (rs.next()) {
                return Optional.of(new Book((Integer) rs.getInt(1), (Integer) rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getString(5)));
            } else
                return Optional.empty();
        }
    }

    public Optional<Book> findById(Integer id) throws SQLException {

        Connection con = GenericDAO.connection;

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from books where id=" + id)) {
            if (rs.next()) {
                return Optional.of(new Book((Integer) rs.getInt(1), (Integer) rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getString(5)));
            } else
                return Optional.empty();
        }
    }

    public List<Book> findAll() throws SQLException {

        Connection con = GenericDAO.connection;

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from books")) {

            List<Book> books = new ArrayList<>();


            while (rs.next()) {
                books.add(new Book((Integer) rs.getInt(1), (Integer) rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getString(5)));
            }

            if (books.size() == 0) {
                return null;
            }

            return books;

        }

    }
   
    public List<Book> findAll(int maxRecordsCount) throws SQLException {

        Connection con = GenericDAO.connection;

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from books")) {

            List<Book> books = new ArrayList<>();


            while (rs.next() && books.size()<=maxRecordsCount) {
                books.add(new Book((Integer) rs.getInt(1), (Integer) rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getString(5)));
            }

            if (books.size() == 0) {
                return null;
            }

            return books;

        }

    }

}
