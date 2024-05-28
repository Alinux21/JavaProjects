import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        int numDepots = 2; 
        
        Depot[] depots = new Depot[numDepots];

    
        for(int i = 0; i < numDepots; i++) {
            String depotName = "Depot " + i;
            Depot d = new Depot(depotName);
            d.setVehicles(new Truck("Truck"), new Truck("Ford"), new Drone("Drone"));
            depots[i] = d;
        }


        List<Client> clients = new ArrayList<>();
        int numClientsPerDepot = 4; 
        for (int i = 0; i < numDepots * numClientsPerDepot; i++) {
            String name = "Client" + i;
            LocalTime startTime = LocalTime.of(8, 0);
            LocalTime endTime = LocalTime.of(22, 0);
            Client client = new Client(name, startTime, endTime);
            clients.add(client);
        }

        Problem p = new Problem(numDepots, numClientsPerDepot);
        p.setClients(clients.toArray(new Client[0]));
        p.setDepots(depots);

        System.out.println(p);

        p.printCosts();

        p.computeShortestPaths();

        System.out.println();

        p.printShortestPaths();

        Solution s = new Solution(p);

        s.solveAsps();

    }
}
