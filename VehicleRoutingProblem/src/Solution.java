

public class Solution {
    private Problem problem;


    public Solution(Problem problem) {
        this.problem = problem;
        this.problem.visitedClients = new boolean[problem.getClients().size()];

    }

    public void setProblem(Problem p) {
        this.problem = p;
    }

    public void solveAsps() {

        // 1st step : we choose the nearest unvisited client from the starting depot
        for (int d = 0; d < problem.getDepots().size(); d++) 
    {
            boolean noMoreVechilesLeft = false;
        findVehicles:
            while(!noMoreVechilesLeft)
        {

            Tour t = new Tour();
            Depot depot = problem.getDepots().get(d);
            t.setDepot(depot);

            Vehicle v = depot.getAvailableVehicle();
            if(v==null){
                break findVehicles;
            }
            // t.getClients().clear();
            t.setVehicle(v);

            StringBuilder tourDetails = new StringBuilder();
           tourDetails.append("Vehicle:" + v.getName() + " leaves depot:" + depot.getName() + " at 8 and visits");

            int nearestToDepotClientIndex = getNearestToDepotClient(8,d);
            if(nearestToDepotClientIndex==-1){
                break;
            }
            t.addClient(problem.getClients().get(nearestToDepotClientIndex)); // we add him to the assignedClients list

            int currentTime = 8 + problem.asps[d][nearestToDepotClientIndex + problem.getDepots().size()];
            int prevIndex = nearestToDepotClientIndex;

           tourDetails.append(" client:" + problem.getClients().get(nearestToDepotClientIndex).getName() + " at " + currentTime);

            // 2nd step : we continue to add as many clients as we can to this tour

            int nearestNeighbourIndex = getNearestNeighbour(currentTime, nearestToDepotClientIndex);

            while (nearestNeighbourIndex != -1) {

                currentTime += problem.asps[prevIndex + problem.getDepots().size()][nearestNeighbourIndex
                        + problem.getDepots().size()];
                t.addClient(problem.getClients().get(nearestNeighbourIndex)); // we add him to the assignedClients list

               tourDetails.append(
                       " client:" + problem.getClients().get(nearestNeighbourIndex).getName() + " at " + currentTime);

                prevIndex = nearestNeighbourIndex;
                nearestNeighbourIndex = getNearestNeighbour(currentTime, nearestNeighbourIndex);

            }
           tourDetails.append(" and returns to depot.");
           System.out.println(tourDetails);

        }
    }

}



    public int getNearestToDepotClient(int currentTime, int depotIndex) {

        int min = Integer.MAX_VALUE;
        int clientIndex = -1;

        int depotsSize = problem.getDepots().size();

        for (int i = depotsSize; i < problem.asps.length; i++) {
            if (problem.asps[depotIndex][i] < min && problem.asps[depotIndex][i] != 0
                    && problem.visitedClients[i - depotsSize] == false) {

                int currentClientHour = problem.getClients().get(i - problem.getDepots().size()).getMaxTime().getHour();

                if (currentTime + problem.asps[depotIndex][i] <= currentClientHour) {
                    min = problem.asps[depotIndex][i];
                    clientIndex = i;
                }
            }
        }
        if (clientIndex != -1) {
            clientIndex -= problem.getDepots().size();
            problem.visitedClients[clientIndex] = true;
        } 

        return clientIndex;

    }

    public int getNearestNeighbour(int currentTime, int assignedClientIndex) {

        int min = Integer.MAX_VALUE;
        int clientIndex = -1;
        assignedClientIndex += problem.getDepots().size();

        for (int i = problem.getDepots().size(); i < problem.asps.length; i++) {

            if (problem.visitedClients[i - problem.getDepots().size()] == false) { // first check if the client has not
                                                                                   // been already visited

                if (problem.asps[assignedClientIndex][i] < min && problem.asps[assignedClientIndex][i] != 0) { // check
                                                                                                               // if
                                                                                                               // it's a
                                                                                                               // local
                                                                                                               // minimum

                    int currentClientHour = problem.getClients().get(i - problem.getDepots().size()).getMaxTime()
                            .getHour();

                    if (currentTime + problem.asps[assignedClientIndex][i] <= currentClientHour) { // lastly check if
                                                                                                   // the vehicle
                                                                                                   // arrives
                        min = problem.asps[assignedClientIndex][i]; // in the client's visiting hours interval
                        clientIndex = i;
                    }
                }
            }
        }

        if (clientIndex != -1) {
            clientIndex -= problem.getDepots().size();
            problem.visitedClients[clientIndex] = true;
        }

        return clientIndex;

    }

}
