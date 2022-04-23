package skiHill;

import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JLabel;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;

public class backEnd {
    private static String[] pointsOfInterest = createPointsOfInterestArray("Lift Names Two Per Lift.txt");
    private static JLabel textOutput = skiHillApp.GetlblRouteOutput();
    private static ArrayList<Integer> liftsSelected = new ArrayList<>();
    public static ArrayList<Point2D> pointsSelected = new ArrayList<>();
    public static Boolean twoPointsSelected = false;
    private static StringBuilder sb = new StringBuilder();

    public static void backEndInput(Point2D point, int i) {
        textOutput.setFont(new Font("Monospaced", Font.BOLD, 30));
        System.out.println("Back End Recieved: " + i);
        if (liftsSelected.size() < 1) {
            liftsSelected.add(i);
            pointsSelected.add(point);
            textOutput
                    .setText("Starting Lift: " + pointsOfInterest[liftsSelected.get(0)] + ". Choose Your Destination.");
            System.out.println(liftsSelected.size());

        } else if (liftsSelected.size() == 1) {
            liftsSelected.add(i);
            pointsSelected.add(point);
            twoPointsSelected = true;
            String filename = "PC Mountain Graph Two per Lift";
            Digraph digraph = new Digraph(new In(filename));
            BreadthFirstDirectedPaths bfdp = new BreadthFirstDirectedPaths(digraph, liftsSelected.get(0));
            for (int el : bfdp.pathTo(liftsSelected.get(1))) {
                if (sb.length() == 0)
                    sb.append("   Path: " + pointsOfInterest[el]);
                else
                    sb.append("-> " + pointsOfInterest[el] + " ");
            }

            // sets the font size so the text wont overflow. Doesnt seem to be any way to do
            // it dynamically
            if (sb.length() <= 70) {
            } else if (sb.length() > 70 && sb.length() < 135) {
                int fontSize = 29 - ((100 - 70)/5);
                textOutput.setFont(new Font("Monospaced", Font.BOLD, fontSize));
            } else {
                textOutput.setFont(new Font("Monospaced", Font.BOLD, 15));
            }

            textOutput.setText(sb.toString());
            sb = new StringBuilder();

        } else if (liftsSelected.size() == 2) {
            liftsSelected.clear();
            pointsSelected.clear();
            twoPointsSelected = false;
            textOutput.setText("Lifts Cleared. Select Your Starting Lift: ");
            System.out.println("Lifts Cleared");
        }
    }

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

    public static void printLocationAndNext(int i) {
        System.out.println("Inputed: " + pointsOfInterest[i]);
        if (i + 1 <= pointsOfInterest.length) {
            System.out.println("Next Point: " + pointsOfInterest[i + 1]);
        }
    }

    public static void main(String[] args) {
        // createPointsOfInterestArray("Lift Names Two Per Lift.txt");
        String filename = "PC Mountain Graph Two per Lift";
        Digraph digraph = new Digraph(new In(filename));
        System.out.println(digraph.toString());

        System.out.println(pointsOfInterest[1]);

        System.out.println(29 - ((100 - 70)/5));
        System.out.println(12/5);

    }
}