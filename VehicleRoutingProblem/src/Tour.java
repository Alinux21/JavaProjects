import java.util.ArrayList;
import java.util.List;

public class Tour {
    private Depot depot;
    private Vehicle vehicle;
    private List<Client> clients;

    public Tour(){
        this.clients = new ArrayList<>();
    }
    
    public void setDepot(Depot d) {
        this.depot = d;
    }

    public Depot getDepot() {
        return this.depot;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void addClient(Client c){
        this.clients.add(c);
    }

    @Override
    public String toString() {
        return "Tour \n" + vehicle + " \nclients=" + clients + "";
    }
}
