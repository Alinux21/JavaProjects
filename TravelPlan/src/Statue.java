import java.time.LocalDate;
import java.util.Map;

public class Statue extends Attraction implements Visitable {

    private Map<LocalDate,TimeInterval> timetable;


    public Statue(String name,Map<LocalDate,TimeInterval> timetable){
        super(name);
        this.timetable=timetable;
    }

    @Override
    public Map<LocalDate,TimeInterval> getTimetable(){
        return this.timetable;
    }

    public void setTimetable(Map<LocalDate,TimeInterval> timetable){
        this.timetable=timetable;
    }



    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Statue Timetable\n\nDay               Hours");
        timetable.forEach((k, v) -> sb.append(("\nMonday-Sunday     "+ v)));
        return sb.toString();
    }
}
