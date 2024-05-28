import java.time.*;
import java.util.Map;

public interface Visitable {

    Map<LocalDate,TimeInterval> getTimetable();

    default LocalTime getOpeningHour(LocalDate date) {
        if(getTimetable().containsKey(date)){
            return getTimetable().get(date).getFirst();
        }
        else return LocalTime.of(0, 0);
    }

}
