package com.example.demo.jdbc;

public class Genre extends BaseEntity {
    
    private String name;

    public Genre(Integer id,String name){
        this.id=id;
        this.name=name;
    }

    @Override
    public String toString(){
        return "Genre - id:"+id+" name:"+name;
    }

}
