package com.example.demo.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class GenericDAO<T extends BaseEntity> {

    protected static Connection con = Database.getConnection();
    private final Class<T> entity;

    public GenericDAO(Class<T> entity) {
        this.entity = entity;
    }

    public Optional<T> findByName(String name) throws SQLException {

        String tableName = this.getClass().getSimpleName().replace("DAO", "").toLowerCase() + "s";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select * from " + tableName + " where name='" + name + "'")) {
            if (rs.next()) {

                if (entity.getSimpleName().equals("Author"))
                    return Optional.of((T) new Author(rs.getInt(1), rs.getString(2)));
                else if (entity.getSimpleName().equals("Genre"))
                    return Optional.of((T) new Genre(rs.getInt(1), rs.getString(2)));
                else if (entity.getSimpleName().equals("Book")) {
                    return Optional.of((T) new Book(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
                            rs.getString(5)));
                }
                return Optional.empty();


            } else
                return Optional.empty();
        }
    }

    public Optional<T> findById(Integer id) throws SQLException {

        String tableName = this.getClass().getSimpleName().replace("DAO", "").toLowerCase() + "s";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select * from " + tableName + " where id='" + id + "'")) {
            if (rs.next()) {

                if (entity.getSimpleName().equals("Author"))
                    return Optional.of((T) new Author(rs.getInt(1), rs.getString(2)));
                else if (entity.getSimpleName().equals("Genre"))
                    return Optional.of((T) new Genre(rs.getInt(1), rs.getString(2)));
                else if (entity.getSimpleName().equals("Book")) {
                    return Optional.of((T) new Book(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
                            rs.getString(5)));
                }
                return Optional.empty();


            } else
                return Optional.empty();
        }
    }

    public List<T> findAll() throws SQLException {

        String tableName = this.getClass().getSimpleName().replace("DAO", "").toLowerCase() + "s";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select * from " + tableName)) {

            List<BaseEntity> resultList = new ArrayList<>();

            while (rs.next()) {
                if (entity.getSimpleName().equals("Author"))
                    resultList.add(new Author(rs.getInt(1), rs.getString(2)));
                else if (entity.getSimpleName().equals("Genre"))
                   resultList.add(new Genre(rs.getInt(1), rs.getString(2)));
                else if (entity.getSimpleName().equals("Book")) {
                    resultList.add(new Book(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
                            rs.getString(5)));
                }
            }
            if(resultList.size() > 0){
                return (List<T>)resultList;
            }
        }
        return null;
    }

    public List<T> findAll(Integer maxRecords) throws SQLException{

            String tableName = this.getClass().getSimpleName().replace("DAO", "").toLowerCase() + "s";

            try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from " + tableName + " limit " + maxRecords)) {

                List<BaseEntity> resultList = new ArrayList<>();

                while (rs.next()) {
                    if (entity.getSimpleName().equals("Author"))
                        resultList.add(new Author(rs.getInt(1), rs.getString(2)));
                    else if (entity.getSimpleName().equals("Genre"))
                        resultList.add(new Genre(rs.getInt(1), rs.getString(2)));
                    else if (entity.getSimpleName().equals("Book")) {
                        resultList.add(new Book(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
                                rs.getString(5)));
                    }
                }
                if(resultList.size() > 0){
                    return (List<T>)resultList;
                }
            }
            return null;
    }



}





