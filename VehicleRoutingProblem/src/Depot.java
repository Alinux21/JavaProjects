import java.util.ArrayList;
import java.util.List;

public class Depot {

    private String name;
    private List<Vehicle> vehicles;

    public Depot(String name) {
        this.name = name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }

    public void setVehicles(Vehicle... newVehicles) {

        vehicles= new ArrayList<>();
        for(Vehicle v : newVehicles){
            if(!vehicles.contains(v)){
                vehicles.add(v);
            }            
        }

        for (Vehicle v : vehicles) {
            v.setDepot(this);
            v.setAssigned(false);
        }
    }

    public List<Vehicle> getVehicles() {
        return this.vehicles;
    }

    public boolean isAssigned(Vehicle v){
        return v.getAssigned();
    }

    public Vehicle getAvailableVehicle() {
        for (Vehicle v : vehicles) {
            if (isAssigned(v)==false) {
                v.setAssigned(true);
                return v;
            }
        }
        return null;
    }

    public String printVehicles() {
        StringBuilder sb = new StringBuilder();
        for (Vehicle vehicle : vehicles) {
            sb.append(vehicle.getName() + " ");
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return "Depot: " + name + " Vehicles: " + printVehicles();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Depot)) {
            return false;
        }
        Depot other = (Depot) obj;
        return name.equals(other.name);
    }

}