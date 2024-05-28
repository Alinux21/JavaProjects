package org.example.JDBC;

import org.example.GenreDAOInterface;
import org.example.JDBC.JDBCEntities.JDBCGenre;
import org.example.JPA.JPAentities.BaseEntity;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class GenreDAO implements GenreDAOInterface {

    protected static Connection connection;

    public GenreDAO(){
        connection = Database.getConnection();
    }

    public BaseEntity save(BaseEntity genre) {

        JDBCGenre entity = (JDBCGenre) genre;

        try (PreparedStatement pstmt = connection.prepareStatement(
                "insert into genres (genre_name) values (?)")) {
            pstmt.setString(1,entity.getGenre_name());
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println("Genre '"+ entity.getGenre_name() +"' was saved.");

        return entity;
    }

    public Optional<JDBCGenre> findByName(String name)  {

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from genres where genre_name='" + name + "'")) {
            if (rs.next()) {
                return Optional.of(new JDBCGenre(rs.getInt(1), rs.getString(2)));
            } else
                return Optional.empty();
        }catch (SQLException e){
            return Optional.empty();
        }
    }

    public Optional<JDBCGenre> findById(Integer id) {


        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from genres where id=" + id)) {
            if (rs.next()) {
                return  Optional.of(new JDBCGenre(rs.getInt(1), rs.getString(2)));
            } else
                return Optional.empty();
        }catch (SQLException e){
            return Optional.empty();
        }
    }

    public List<JDBCGenre> findAll(){


        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from genres")) {

            List<JDBCGenre> genres = new ArrayList<>();

            while (rs.next()) {
                genres.add(new JDBCGenre(rs.getInt(1), rs.getString(2)));
            }

            if (genres.isEmpty()) {
                return null;
            }

            return genres;
        }catch (SQLException e){
            return null;
        }

    }

}
