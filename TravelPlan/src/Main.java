import java.time.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        TimeInterval openHoursWeek = new TimeInterval(LocalTime.of(8, 0), LocalTime.of(20, 0));
        TimeInterval openHoursWeekend = new TimeInterval(LocalTime.of(10, 0), LocalTime.of(15, 0));

        Map<LocalDate, TimeInterval> timetable = new TreeMap<LocalDate, TimeInterval>();

        for (int i = 12; i <=18; i++) {
            if (i == 17 || i == 18) {
                timetable.put(LocalDate.of(2024, 8, i), openHoursWeekend);
            } else
                timetable.put(LocalDate.of(2024, 8, i), openHoursWeek);
        }

        Museum museum = new Museum("History Museum",timetable,20);
        Museum museum2 = new Museum("Geography Museum",timetable,20);
        Museum museum3 = new Museum("Biology Museum",timetable,20);
        Museum museum4 = new Museum("Dracula Museum",timetable,20);

        Church church = new Church("Black Church",timetable);
        Church church2 = new Church("Catedrala Mitropolitana",timetable);

        Map<LocalDate, TimeInterval> concertTimetable = new TreeMap<LocalDate, TimeInterval>();
        concertTimetable.put(LocalDate.of(2024, 8, 17), new TimeInterval(LocalTime.of(20, 30), LocalTime.of(23, 0)));

        Concert concert = new Concert("BoneyM Concert",concertTimetable,30);


        Map<LocalDate, TimeInterval> statueTimetable = new TreeMap<LocalDate, TimeInterval>();
        statueTimetable.put(LocalDate.of(2024, 8, 15), new TimeInterval(LocalTime.of(00, 0), LocalTime.of(23, 59)));

        Statue statue = new Statue("Alexandru Ioan Cuza Statue",statueTimetable);

        Attraction[] arr = {museum,museum2,museum3,museum4,church,church2,concert,statue};

        Trip trip = new Trip("Arizona", LocalDate.of(2024, 8, 12), LocalDate.of(2024, 8, 18));
        trip.setAttractions(Arrays.asList(arr));

        TravelPlan travelPlan = new TravelPlan(trip);
        travelPlan.printAttractionGraph();
       
       // travelPlan.greedyStaticColoring();
        travelPlan.recursiveLargestFirstColouring();
        System.out.println(travelPlan);


    }   
}