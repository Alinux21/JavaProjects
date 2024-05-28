package com.example.demo.jdbc;

import java.sql.*;

public class BookDAO extends GenericDAO<Book> {

    public BookDAO(){
        super(Book.class);
    }

    public void create(Integer year, String name, String author, String genre) throws SQLException {

        try (PreparedStatement pstmt = con.prepareStatement(
                "insert into books (year,name,author,genre) values (?,?,?,?)")) {
            pstmt.setInt(1, year);
            pstmt.setString(2, name);
            pstmt.setString(3, author);
            pstmt.setString(4, genre);
            pstmt.executeUpdate();
        }
    }

    public void setName(Integer id , String name){
        try (PreparedStatement pstmt = con.prepareStatement(
                "update books set name = ? where id = ?")) {
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating book name " + e.getMessage());
        }
    }

    public void delete(Integer id){
        try (PreparedStatement pstmt = con.prepareStatement(
                "delete from books where id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting book " + e.getMessage());
        }
    }

    public int count()  {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select count(*) from books")) {
            if (rs.next()) {
                return rs.getInt(1);
            } else
                return 0;
        }catch (SQLException e){
            System.out.println("Error counting books " + e.getMessage());
            return 0;
        }
    }

}
