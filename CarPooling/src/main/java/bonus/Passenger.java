package bonus;

public class Passenger extends Person{

    private boolean assigned;

    public Passenger(String name,int age,Destination start, Destination destination){
        super(name,age,start,destination);
    }

    public boolean isAssigned(){
        return this.assigned;
    }

    public void setAssigned(){
        this.assigned=true;
    }

    @Override
    public String toString(){
        return this.name+" "+this.age+" Route:"+this.start+"=>"+this.destination;
    }

}
