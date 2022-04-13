package skiHill;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

public class GraphTest {
    public static void main(String[] args) {
        String filename = "Pc Mountain Graph Raw Data";
        Digraph digraph = new Digraph(new In(filename));
        Topological topological = new Topological(digraph);

        System.out.println();
        System.out.println("Topological Order: ");
        if (topological.hasOrder()) {
            for (Integer el : topological.order()) {
                System.out.print(el + " ");
            }
        }
        System.out.println();
        System.out.println("Edges: " + digraph.E());
        System.out.println(digraph.V());
        BreadthFirstDirectedPaths bfdp = new BreadthFirstDirectedPaths(digraph,2);
        System.out.println(bfdp.hasPathTo(39));
        System.out.println("Path from 2 to 39: ");
        for(int el : bfdp.pathTo(39)){
            System.out.print(el + " ");
        }
       
        
    }
    
}
