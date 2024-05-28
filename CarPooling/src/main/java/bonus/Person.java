package bonus;

public class Person implements Comparable<Person> {
    
    protected String name;
    protected int age;
    protected Destination start;
    protected Destination destination;


    public Person(String name,int age,Destination start,Destination destination){
        this.name=name;
        this.age=age;
        this.start=start;
        this.destination=destination;
    }

    public String getName(){
        return this.name;
    }

    public Destination getDestination(){
        return this.destination;
    }

    public Destination getStart(){
        return this.start;
    }

    public int getAge(){
        return this.age;
    }

   @Override
    public int compareTo(Person other){
        return this.name.compareTo(other.name);
   }

}
