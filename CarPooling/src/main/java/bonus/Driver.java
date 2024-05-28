package bonus;

import java.util.List;

import java.util.ArrayList;

public class Driver extends Person {

    private List<Destination> route = new ArrayList<>();

    public Driver(String name,int age,Destination start,Destination finalDestination){
        super(name,age,start,finalDestination);
    }

    public List<Destination> getRoute(){
        
        return this.route;
    }
    
    public void setPrevDestinations(List<Destination> prevDestinations){
                
        this.route=prevDestinations;
        this.route.add(0,start);
        this.route.add(route.size(),destination);
    
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.name+" "+this.age+" Route:"+this.start+" => ");
        route.stream().filter(dest -> route.indexOf(dest)!=0 && route.indexOf(dest)!=route.size()-1 )
        .forEach(dest -> sb.append(dest+" => "));
        sb.append(this.destination);

        return sb.toString();

    }

}
