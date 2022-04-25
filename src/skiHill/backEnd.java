package skiHill;

import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;

/**
 * Back End class for app. Contains all graph functionality and interaction between different 
 * classes.
 * 
 * @author NicolasVanderWerf & HaydenBlackmer
 */
public class backEnd {
    private static String[] pointsOfInterest = createPointsOfInterestArray(fileData.getLiftNameLocation());
    private static JLabel textOutput = skiHillApp.getlblRouteOutput();
    private static ArrayList<Integer> liftsSelected = new ArrayList<>();
    private static StringBuilder sb = new StringBuilder();
    private static Queue<Queue<String>> allRoutes = new Queue<>();
    private static Queue<String> currentRoute = new Queue<>();

    public static Boolean twoPointsSelected = false;
    public static ArrayList<Point2D> pointsSelected = new ArrayList<>();

    /**
     * In charge of all of the back end functionality.
     * 
     * @param point the point that was selected by the user
     * @param i     the value of the index associated with the point
     */
    public static void backEndInput(Point2D point, int i) {
        if (liftsSelected.size() < 1) {
            liftsSelected.add(i);
            pointsSelected.add(point);
            textOutput
                    .setText("Starting Lift: " + pointsOfInterest[liftsSelected.get(0)] + ". Choose Your Destination.");
        } else if (liftsSelected.size() == 1) {
            liftsSelected.add(i);
            pointsSelected.add(point);
            twoPointsSelected = true;

            createGraphRoute();

            setRouteOutputFontSize();
            textOutput.setText(sb.toString());

            allRoutes.enqueue(currentRoute);
        } else if (liftsSelected.size() == 2) {
            resetTheRoute();
        }
    }

    /**
     * Reads in all of the ski hill names from a text file.
     * The names are added to an array.
     */
    private static String[] createPointsOfInterestArray(String filename) {
        In in = new In(filename);
        int length = in.readInt();
        in.readLine();
        String[] poiCreation = new String[length];

        for (int i = 0; !in.isEmpty(); i++) {
            String x = in.readLine();
            poiCreation[i] = x;
        }

        return poiCreation;
    }

    /**
     * Creates a file, and writes all of the routes
     * that the user has chosen.
     */
    public static void createOutputFile() {
        try {
            FileWriter myWriter = new FileWriter("routeOutput.txt");
            for (Queue<String> elQueue : allRoutes) {
                myWriter.write(elQueue.dequeue() + " \n");
                myWriter.write(elQueue.dequeue() + "\n\n");
                myWriter.write("Route: \n");
                myWriter.write("-------------------------------------------\n");
                for (String elCurrentLocation : elQueue) {
                    myWriter.write("\t" + elCurrentLocation + "\n");
                }
                myWriter.write("\n\n\n\n\n");
            }

            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method for testing.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // createPointsOfInterestArray("Lift Names Two Per Lift.txt");
        String filename = fileData.getGraphLocation();
        Digraph digraph = new Digraph(new In(filename));
        System.out.println(digraph.toString());

        System.out.println(pointsOfInterest[1]);

        System.out.println(29 - ((100 - 70) / 5));
        System.out.println(12 / 5);

    }

    /**
     * Resizes the font size, depending on the number of characters(of the route)
     * that need to
     * be displayed on the screen.
     */
    public static void setRouteOutputFontSize() {
        if (sb.length() <= 70) {
        } else if (sb.length() > 70 && sb.length() < 135) {
            int fontSize = 29 - ((100 - 70) / 5);
            textOutput.setFont(new Font("Monospaced", Font.BOLD, fontSize));
        } else {
            textOutput.setFont(new Font("Monospaced", Font.BOLD, 15));
        }
    }

    /**
     * Resets the app, preparing for another route to be selected.
     */
    public static void resetTheRoute() {
        liftsSelected.clear();
        pointsSelected.clear();
        twoPointsSelected = false;
        currentRoute = new Queue<>();
        sb = new StringBuilder();
        textOutput.setFont(new Font("Monospaced", Font.BOLD, 30));
        textOutput.setText("Lifts Cleared. Select Your Starting Lift: ");
    }

    /**
     * Creates the graph, and the quickest route between the two selected points.
     * Enqueues strings to our queue, to later be printed out to a text file.
     * Adds the route to the StringBuilder that will later be displayed on the
     * screen.
     */
    public static void createGraphRoute() {
        String filename = fileData.getGraphLocation();
        Digraph digraph = new Digraph(new In(filename));
        BreadthFirstDirectedPaths bfdp = new BreadthFirstDirectedPaths(digraph, liftsSelected.get(0));

        currentRoute.enqueue("Starting Location: " + pointsOfInterest[liftsSelected.get(0)]);
        currentRoute.enqueue("Ending Location  : " + pointsOfInterest[liftsSelected.get(1)]);

        for (int el : bfdp.pathTo(liftsSelected.get(1))) {
            if (liftsSelected.get(0) == liftsSelected.get(1)) {
                currentRoute.enqueue("You're Already There!");
                sb.append("You're Already There!");
            } else if (sb.length() == 0) {
                currentRoute.enqueue(pointsOfInterest[el]);
                sb.append("   Path: " + pointsOfInterest[el]);
            } else {
                sb.append(" -> " + pointsOfInterest[el] + " ");
                currentRoute.enqueue(pointsOfInterest[el]);
            }
        }
    }

    /**
     * Used in the creation of a new ski hill.
     * 
     * @param i index of lift added and the next lift to add.
     */
    public static void printLocationAndNext(int i) {
        System.out.println("Inputed: " + pointsOfInterest[i]);
        if (i + 1 >= pointsOfInterest.length) {
            System.out.println("Next Point: " + pointsOfInterest[i + 1]);
        }
    }
}