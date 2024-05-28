public class Truck extends Vehicle {
    private int capacity;

    public Truck(String name) {
        super(name);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}