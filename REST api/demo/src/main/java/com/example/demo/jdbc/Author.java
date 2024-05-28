package com.example.demo.jdbc;

import java.io.Serializable;

public class Author extends BaseEntity implements Serializable {

    private String name;

    public Author(Integer id,String name){
        this.id=id;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "Author : id:"+id+" name:"+name;
    }

}