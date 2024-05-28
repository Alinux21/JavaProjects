package bonus;


public class Destination {
    private String name;

    public Destination(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }



    @Override
    public String toString(){
        return this.name;  
    }


}
