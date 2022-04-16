package skiHill;

import java.util.ArrayList;

import javax.swing.JLabel;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class backEndTest {
    private static int start;
    private static int end;
    private static String[] pointsOfInterest = { "Crescent", "Payday", "Kiing Con" };
    private static JLabel textOutput = skiHillApp.GetlblRouteOutput();
    private static ArrayList<Integer> liftsSelected = new ArrayList<>();

    public static void testOutPut(int i) {
        start = i;
        System.out.println("Back End Recieved: " + start);
        if (liftsSelected.size() < 2) {
            liftsSelected.add(i);
            /*
             * if(liftsSelected.size() == 2) {
             * //disables all of the buttons since we only want a max of two selected
             * for(JRadioButton el: allButtons) {
             * el.setEnabled(false);
             * }
             * //enables the two that we have selected
             * allButtons[liftsSelected.get(0)].setEnabled(true);
             * allButtons[liftsSelected.get(1)].setEnabled(true);
             * 
             * //updates the text at the bottom of the screen
             * textOutput.setText("Route: " + liftsSelected.get(0) + "-->" +
             * liftsSelected.get(1));
             * }
             */
        }
        if (liftsSelected.size() == 2) {
            String string1 = " ";
            String filename = "Pc Mountain Graph Raw Data";
            Digraph digraph = new Digraph(new In(filename));
            BreadthFirstDirectedPaths bfdp = new BreadthFirstDirectedPaths(digraph,liftsSelected.get(0));
            for(int el : bfdp.pathTo(liftsSelected.get(1))){
                string1 += " " + el;
            }
            textOutput.setText("Route: " + liftsSelected.get(0) + "-->" + liftsSelected.get(1) + " Path: " + string1);

        }

        // System.out.println("Lift: " + pointsOfInterest[start]);
        // Gets the JLabel object at the bottom section of the skiHillApp so we can
        // update the text

    }

}
