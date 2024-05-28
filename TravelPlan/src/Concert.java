import java.time.LocalDate;
import java.util.Map;


public class Concert extends Attraction implements Visitable,Payable{
    private Map<LocalDate,TimeInterval> timetable;
    private double ticketprice;

    public Concert(String name,Map<LocalDate,TimeInterval> timetable,double price){
        super(name);
        this.timetable=timetable;
        this.ticketprice=price;
    }

    @Override
    public Map<LocalDate,TimeInterval> getTimetable(){
        return this.timetable;
    }

    public void setTimetable(Map<LocalDate, TimeInterval> timetable) {
        this.timetable = timetable;
    }

    @Override
    public double getTicketPrice(){
        return this.ticketprice;
    }

    public void setTicketPrice(double price){
        this.ticketprice=price;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Concert Timetable\n\nDay          Hours");
        timetable.forEach((k, v) -> sb.append(("\n" + k.getDayOfWeek() + "     " + v)));
        sb.append("\nTicket price:"+ticketprice+"$");
        return sb.toString();
    }


}
