import java.time.LocalTime;


public class TimeInterval extends Pair<LocalTime,LocalTime> {
    
     public TimeInterval(LocalTime first,LocalTime second){
       super(first ,second );
    }

    @Override
    public String toString(){
        return getFirst()+"-"+getSecond();
    }

}
