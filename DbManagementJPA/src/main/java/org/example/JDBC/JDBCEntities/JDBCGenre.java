package org.example.JDBC.JDBCEntities;

public class JDBCGenre extends JDBCBaseEntity {
    
    private String genre_name;

    public JDBCGenre(Integer id, String genre_name){
        this.id=id;
        this.genre_name = genre_name;
    }

    public String getGenre_name() {
        return genre_name;
    }

    @Override
    public String toString(){
        return "Genre - id:"+id+" genre_name:"+ genre_name;
    }

}
