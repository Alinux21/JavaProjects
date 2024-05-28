public class Drone extends Vehicle {

    private int flight_duration;

    public Drone(String name) {
        super(name);
    }

    public int getFlight_duration() {
        return flight_duration;
    }

    public void setFlight_duration(int flight_duration) {
        this.flight_duration = flight_duration;
    }

}