import java.time.*;
import java.util.*;

public class Trip implements Comparator<Attraction>{
    private String cityName;
    private LocalDate start,end;
    private List<Attraction> attractions = new ArrayList<>();

    public Trip(String city,LocalDate start,LocalDate end){
        this.cityName=city;
        this.start=start;
        this.end=end;
    }

    public List<Attraction> visitableOnlyLocations(){
        List<Attraction> freeLocations = new ArrayList<>();

        for (Attraction attraction : attractions) {
            if(attraction instanceof Visitable && !(attraction instanceof Payable)){
                freeLocations.add(attraction);
            }
        }

       Collections.sort(freeLocations,this::compare);

        return freeLocations;
    }



    @Override
    public int compare(Attraction first,Attraction second){

        Iterator<Map.Entry<LocalDate,TimeInterval>> iteratorFirst = first.getTimetable().entrySet().iterator();
        Iterator<Map.Entry<LocalDate,TimeInterval>> iteratorSecond = second.getTimetable().entrySet().iterator();
        
        while (iteratorFirst.hasNext() && iteratorSecond.hasNext()) {
            
            Map.Entry<LocalDate,TimeInterval> firstEntry = iteratorFirst.next();
            Map.Entry<LocalDate,TimeInterval> secondEntry = iteratorSecond.next();

            if(firstEntry.getValue().getFirst().getHour()>secondEntry.getValue().getFirst().getHour()){
                return 1;
            }
            else if (firstEntry.getValue().getFirst().getHour()<secondEntry.getValue().getFirst().getHour()){
                return -1;
            }

        }

        if(iteratorFirst.hasNext()&&!iteratorSecond.hasNext()){
            return 1;
        }
        else if(!iteratorFirst.hasNext()&&iteratorSecond.hasNext()){
            return -1;
        }

        return 0;
    }


    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }


    public String getCityName() {
        return cityName;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

}




   

