import java.util.*;
import java.time.*;

public abstract class Attraction implements Comparable<Attraction>{
    private String name;
    private Map<LocalDate,TimeInterval> timetable;
    private Colors color;
    
    public Attraction(String name){
        this.name=name;
    }

    public Colors getColor(){
        return this.color;
    }

    public void setColor(Colors color){
        this.color=color;
    }

    @Override 
    public int compareTo(Attraction other){
        if(this.name==null||other.name==null){
            System.exit(-1);
        }
        return this.name.compareTo(other.name);
    }

    public Map<LocalDate,TimeInterval> getTimetable(){
        return this.timetable;
    }

    public String getName(){
        return this.name;
    }
}