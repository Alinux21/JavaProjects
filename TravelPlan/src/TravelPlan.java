import java.time.*;
import java.util.*;

public class TravelPlan implements Comparator<Attraction> {
    private int[][] attractionGraph;
    private Trip trip;
    private EnumMap<Colors, LocalDate> daysColouring;

    public TravelPlan(Trip trip) {
        this.trip = trip;

        List<Attraction> attractions = trip.getAttractions();

        this.attractionGraph = new int[attractions.size()][attractions.size()]; // construiesc graful unde atractiile
                                                                                // reprezinta noduri

        for (int i = 0; i < attractions.size(); i++) {
            for (int j = i + 1; j < attractions.size(); j++) {
                if (attractions.get(i).getClass() == attractions.get(j).getClass()) { // daca doua atractii sunt de
                                                                                      // acelasi fel (doua muzee)
                    attractionGraph[i][j] = attractionGraph[j][i] = 1; // le conectez printr-o muchie in graf
                }
            }
        }

        daysColouring = new EnumMap<>(Colors.class);
        int no_of_days = trip.getEnd().getDayOfMonth() - trip.getStart().getDayOfMonth();

        for (int i = 0; i <= no_of_days; i++) {
            daysColouring.put(Colors.values()[i], trip.getStart().plusDays(i));
        }

    }

    public void printAttractionGraph() {
        for (int i = 0; i < attractionGraph.length; i++) {
            for (int j = 0; j < attractionGraph.length; j++) {
                System.out.print(attractionGraph[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void greedyStaticColoring() {
        List<Attraction> sortedAttractions = new ArrayList<>(largestDegreeFirst()); // sorting the elements descending
                                                                                    // by their degree

        for (Attraction attraction : sortedAttractions) {
            setAvailableColor(attraction);
            System.out.println(attraction.getName() + "->" + attraction.getColor());
        }

    }

    public List<Attraction> largestDegreeFirst() { // we sort the attractions descending by their degree in the graph
        List<Attraction> sortedAttractions = new ArrayList<>(trip.getAttractions());
        Collections.sort(sortedAttractions, this::compare);

        return sortedAttractions;
    }

    public void setAvailableColor(Attraction attr) {
        List<Colors> usedColors = new ArrayList<>();

        for (int i = 0; i < attractionGraph.length; i++) {
            if (attractionGraph[trip.getAttractions().indexOf(attr)][i] == 1) {
                if (trip.getAttractions().get(i).getColor() != null) {
                    usedColors.add(trip.getAttractions().get(i).getColor());
                }
            }
        }

        for (int i = 0; i < Colors.values().length; i++) {
            Colors currentColor = Colors.values()[i];
            LocalDate colourCorrespondingDate = daysColouring.get(currentColor);
            if (!usedColors.contains(currentColor) && attr.getTimetable().containsKey(colourCorrespondingDate)) { // if
                                                                                                                  // the
                                                                                                                  // colour
                                                                                                                  // wasn't
                                                                                                                  // used
                attr.setColor(currentColor); // and the attraction is open on the colour's day
                break;
            }
        }

    }

    public void recursiveLargestFirstColouring() {

        for (int j = 0; j < Colors.values().length; j++) {
            colors:
            for (int i = 0; i < attractionGraph.length; i++) {
                if(trip.getAttractions().get(i).getColor()==null){      //if the attraction isn't coloured
                    Colors currentColor = Colors.values()[j];           
                    LocalDate colourCorrespondingDate = daysColouring.get(currentColor);
                    if(trip.getAttractions().get(i).getTimetable().containsKey(colourCorrespondingDate)){ // and is open on the current colour's day
                        trip.getAttractions().get(i).setColor(Colors.values()[j]);
                        System.out.println(
                                trip.getAttractions().get(i).getName() + "->" + trip.getAttractions().get(i).getColor());
                        recursiveColouring(i, j);
                        break colors;
                    }
                }
        }
    }
}

    public void recursiveColouring(int vertexIndex, int colourIndex) {

        for (int i = vertexIndex; i < attractionGraph.length; i++) {
            Colors currentColor = Colors.values()[colourIndex];
            LocalDate colourCorrespondingDate = daysColouring.get(currentColor);
            if (vertexIndex != i && attractionGraph[vertexIndex][i] == 0
                    && trip.getAttractions().get(i).getColor() == null &&
                    trip.getAttractions().get(i).getTimetable().containsKey(colourCorrespondingDate)) {

                trip.getAttractions().get(i).setColor(Colors.values()[colourIndex]);
                System.out.println(
                       trip.getAttractions().get(i).getName() + "->" + trip.getAttractions().get(i).getColor());
                recursiveColouring(i, colourIndex);
                break;
            }
        }

    }

    @Override
    public int compare(Attraction atr1, Attraction atr2) {

        int atr1Degree = 0, atr2Degree = 0;
        for (int i = 0; i < attractionGraph.length; i++) {
            if (attractionGraph[trip.getAttractions().indexOf(atr1)][i] == 1) {
                atr1Degree++;
            }
            if (attractionGraph[trip.getAttractions().indexOf(atr2)][i] == 1) {
                atr2Degree++;
            }
        } // Integer.compare returns the ascending order
        return Integer.compare(atr1Degree, atr2Degree) * (-1); // by multiplying with (-1) we get the descending order
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=_=_=Travel Plan=_=_=\n");
        sb.append("\nCity:" + trip.getCityName() + "   Duration:" + trip.getStart() + " " + trip.getEnd() + "\n");

        for (Map.Entry<Colors, LocalDate> entry : daysColouring.entrySet()) {
            sb.append("\nDay:" + entry.getValue() + " Attractions:");
            for (Attraction attraction : trip.getAttractions()) {
                if (attraction.getColor() == entry.getKey())
                    sb.append(attraction.getName() + " | ");
            }
        }

        return sb.toString();
    }

}
