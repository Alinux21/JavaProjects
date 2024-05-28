import java.util.ArrayList;
import java.util.List;

public class Problem {
    private final static int INF = 999999;

    public int cost[][];
    public int asps[][];
    private List<Depot> depots;
    private List<Client> clients;
    public boolean[] visitedClients;

    public Problem(int n, int m) {
        int size = n * m;
        this.cost = new int[size][size];
        this.asps = new int[size][size];
        int x = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                x = i * m + j;
                if (j > 0) {
                    cost[x - 1][x] = cost[x][x - 1] = (int) (Math.random()*10);
                }
                if (i > 0) {
                    cost[x - m][x] = cost[x][x - m] = (int) (Math.random()*10);
                }
            }
        }

    }

    public void computeShortestPaths() { // Floyd Warshall

        for (int i = 0; i < cost.length; i++) {
            for (int j = 0; j < cost.length; j++) {
                if (i == j) {
                    asps[i][j] = 0;
                } else if (cost[i][j] != 0) {
                    asps[i][j] = cost[i][j];
                } else {
                    asps[i][j] = INF;
                }
            }
        }

        for (int k = 0; k < asps.length; k++) {
            for (int i = 0; i < asps.length; i++) {
                for (int j = 0; j < asps.length; j++) {
                    if (asps[i][k] + asps[k][j] < asps[i][j])
                        asps[i][j] = asps[i][k] + asps[k][j];
                }
            }
        }

    }

    public void printCosts() {
        for (int i = 0; i < cost.length; i++) {
            for (int j = 0; j < cost.length; j++) {
                if (cost[i][j] == 0) {
                    System.out.print(". ");
                } else {
                    System.out.print(cost[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public void printShortestPaths() {
        for (int i = 0; i < asps.length; i++) {
            for (int j = 0; j < asps.length; j++) {
                if (asps[i][j] == 0) {
                    System.out.print(". ");
                } else {
                    System.out.print(asps[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public List<Depot> getDepots() {
        return depots;
    }

    public void setDepots(Depot[] newDepots) {
        this.depots = new ArrayList<>();

        for (Depot d : newDepots) {
            if (!this.depots.contains(d)) {
                this.depots.add(d);
            }
        }
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(Client[] newClients) {
        this.clients = new ArrayList<>();

        for (Client c : newClients) {
            if (!this.clients.contains(c)) {
                this.clients.add(c);
            }
        }
    }

    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();

        for (Depot d : depots) {
            for (Vehicle v : d.getVehicles()) {
                vehicles.add(v);
            }
        }
        return vehicles;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nProblem:\n\n");

        sb.append("Depots:");
        for (Depot d : depots) {
            sb.append("\n" + d.getName());
            sb.append(" Vehicles:" + d.printVehicles());
        }
        sb.append("\n\nClients:");
        for (Client c : clients) {
            sb.append("\n" + c);
        }

        return sb.toString();
    }
}
