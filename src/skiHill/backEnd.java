package skiHill;

import java.util.ArrayList;

import javax.swing.JLabel;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;

public class backEnd {
    private static int start;
    private static int end;
    private static String[] pointsOfInterest = createPointsOfInterestArray("Lift Names Two Per Lift.txt");
    private static JLabel textOutput = skiHillApp.GetlblRouteOutput();
    private static ArrayList<Integer> liftsSelected = new ArrayList<>();
    public static ArrayList<Point2D> pointsSelected = new ArrayList<>();
    public static Boolean twoPointsSelected = false;

    public static void testOutPut(Point2D point, int i) {
        System.out.println("Back End Recieved: " + i);
        if (liftsSelected.size() < 1) {
            liftsSelected.add(i);
            pointsSelected.add(point);
            textOutput.setText("Starting Lift: " + pointsOfInterest[liftsSelected.get(0)] + ". Choose Your Destination.");
            System.out.println(liftsSelected.size());
        } else if (liftsSelected.size() == 1) {
            liftsSelected.add(i);
            pointsSelected.add(point);
            twoPointsSelected = true;
            String string1 = " ";
            String filename = "Pc Mountain Graph Two per Lift";
            Digraph digraph = new Digraph(new In(filename));
            BreadthFirstDirectedPaths bfdp = new BreadthFirstDirectedPaths(digraph,liftsSelected.get(0));
            for(int el : bfdp.pathTo(liftsSelected.get(1))){
                string1 += " " + el;
            }
            textOutput.setText("Route: " + pointsOfInterest[liftsSelected.get(0)] + "-->" + pointsOfInterest[liftsSelected.get(1)] + " Path: " + string1);
        } else if(liftsSelected.size() == 2){
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
        String[] poiCreation = new String[length+1];
        for (int i = 0; !in.isEmpty(); i++) {
            String x = in.readLine();
            poiCreation[i] = x;
        }
        return poiCreation;
    }

    public static void main(String[] args) {
        //createPointsOfInterestArray("Lift Names Two Per Lift.txt");
        String filename = "Pc Mountain Graph Two per Lift";
        Digraph digraph = new Digraph(new In(filename));
        System.out.println(digraph.toString());
    }
}