public abstract class Vehicle {
    protected String name;
    private Depot depot;
    private boolean assigned;

    public Vehicle(String name) {
        this.name = name;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public boolean getAssigned(){
        return this.assigned;
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }

    void setDepot(Depot depot) {
        this.depot = depot;
    }

    Depot getDepot() {
        return this.depot;
    }

    @Override
    public String toString() {
        return "Vehicle :" + name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Vehicle)) {
            return false;
        }
        Vehicle other = (Vehicle) obj;
        return name.equals(other.name);
    }

}
