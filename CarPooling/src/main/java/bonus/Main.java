package bonus;

import java.util.*;
import java.util.stream.*;
import com.github.javafaker.Faker;

public class Main {
    public static int prev_random=0;
    public static int random_calls=0;
    public static void main(String[] args) {

        System.out.println();


        List<Person> listOfPersons = new ArrayList<>();
        List<Person> listOfDrivers = new LinkedList<Person>();
        Set<Person> listOfPassengers = new TreeSet<Person>();

        listOfPersons = setupPersons();         // <-- here we change the number of persons that are generated
        listOfDrivers = setupDrivers(listOfPersons);
        listOfPassengers = setupPassengers(listOfPersons);

        
        Problem p = new Problem(listOfDrivers,listOfPassengers);
        //System.out.println("\n"+p);     

        int greedyMatches = p.greedyMatch();
        int hocroftKarpMatches = p.hopcroftKarp();  //sometimes the method getMatching() from JGraphT throws an exception for large instances
                                                    //the cause is probably the random generation logic used in my program
                                                    //run the program again if it happens since it does not happen very often


        System.out.println("\n\nComparison:");
        System.out.println("Greedy Matchings:"+greedyMatches+" HopcroftKarpMatchings:"+hocroftKarpMatches);

        System.out.println("\n\nMax Card Set:\n"); //the number of persons that HAVE NO DESTINATION IN COMMON
        p.getMaxCardSet();                         //this means that besides the start point they don't have any common destination
 
    }

    public static List<Person> setupPersons(){

        Faker faker = new Faker();

        int no_of_generated_persons = 10_000;           //modify this value for changing the number of generated persons
                                                        //use 10_000 for having aproximately 5_000 drivers and 5_000 passengers

        int[] ages = new int[50];
        for(int i=0;i<50;i++){
            ages[i]=faker.number().numberBetween(18, 70);
        }

        String[] names = new String[no_of_generated_persons+1];
        for (int i=0;i<names.length;i++) {
            names[i]=faker.name().firstName();
        }


        Destination[] destinations = new Destination[10];

        for(int i=0;i<destinations.length;i++){
            destinations[i]=new Destination(faker.address().city());
        }

        var persons = IntStream.rangeClosed(0, no_of_generated_persons)
                .mapToObj(i -> randomPersonGenerator()==1 ? 
                new Driver(names[i], ages[i%50],destinations[randomDest()],destinations[randomDest()]) : 
                new Passenger(names[i], ages[i%50],destinations[randomDest()],destinations[randomDest()]))
                .toArray(Person[]::new);

        List<Person> listOfPersons = new ArrayList<>();
        listOfPersons.addAll(Arrays.asList(persons));
        return listOfPersons;

    }

    public static List<Person> setupDrivers(List<Person> listOfPersons){

        Faker faker = new Faker();
        List<Person> listOfDrivers = new LinkedList<>();

        Destination[] destinations = new Destination[5];

        for(int i=0;i<destinations.length;i++){
            destinations[i]=new Destination(faker.address().city());
        }

        listOfDrivers = listOfPersons.stream()
                .filter(d -> d instanceof Driver)
                .collect(Collectors.toList());

        
        for (Person driver : listOfDrivers) {
                Driver driverObj = (Driver) driver;

                List<Destination> passingDestinations = new ArrayList<>();
                int x = random()%destinations.length-2;
                for(int i=0;i<x;i++){
                    if(driverObj.getDestination()!=destinations[i]&& driverObj.getStart()!=destinations[i] && !passingDestinations.contains(destinations[i]))
                    passingDestinations.add(destinations[i]);
                }

                driverObj.setPrevDestinations(passingDestinations);

        }

        return listOfDrivers;
    }

    public static Set<Person> setupPassengers(List<Person> listOfPersons){

        Set<Person> listOfPassengers = new TreeSet<>();
        listOfPassengers = listOfPersons.stream()
        .filter(p -> p instanceof Passenger)
        .collect(Collectors.toSet());

        return listOfPassengers;

    }

    public static int random(){        
        int x=(int)(Math.random() * 1_000_000 + 1);

        return x;
    }

    public static int randomDest(){        
        random_calls++;
        int x=(int)(Math.random() * 1_000_000 + 1)%10;

        if(random_calls%2==0&&prev_random==x){
            while(x==prev_random){
                x=(int)(Math.random() * 1_000_000 + 1)%10;
            }
        }


        prev_random=x;

        return x;
    }

    public static int randomPersonGenerator(){
        int x = (int)(Math.random()*1_000_000);
        if(x%2==0){
          return 1;
        }
        else return 0;

}

}