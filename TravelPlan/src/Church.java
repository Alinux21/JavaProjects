
import java.time.LocalDate;
import java.util.Map;

public class Church extends Attraction implements Visitable {
    private Map<LocalDate,TimeInterval> timetable;

    public Church(String name,Map<LocalDate,TimeInterval> timetable){
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
        sb.append("Church Timetable\n\nDay          Hours");
        timetable.forEach((k, v) -> sb.append(("\n" + k.getDayOfWeek() + "     " + v)));
        return sb.toString();
    }




}
