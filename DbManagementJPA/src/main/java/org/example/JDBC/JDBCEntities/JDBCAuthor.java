package org.example.JDBC.JDBCEntities;

import org.example.JPA.JPAentities.BaseEntity;

public class JDBCAuthor extends JDBCBaseEntity {

    private String author_name;

    public JDBCAuthor(Integer id, String name){
        this.id=id;
        this.author_name =name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    @Override
    public String toString(){
        return "Author : id:"+id+" author_name:"+ author_name;
    }

}
