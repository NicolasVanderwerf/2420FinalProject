package skiHill;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

 
/**
 * Class KdTreeST<Value>. Creates a version of a binary search tree that contains points in the format 
 * of the class Point2D. Optimized data structure for returning the nearest points to a query point
 * and return all points contained in a query rectangle.
 * 
 * @author Hayden Blackmer and Nic Van der Werf
 *
 * @param <Value>
 */
public class KdTreeST<Value> {
    private double xmin; // minimum x-coordinate of rectangle
    private double ymin; // minimum y-coordinate of rectangle
    private double xmax; // maximum x-coordinate of rectangle
    private double ymax; // maximum y-coordinate of rectangle
    private Node head;
    private int size;
    
    /**
     * Creates class Node with the following parameters.
     */
    private class Node {
        private Point2D point;
        private RectHV rectangle;
        private Node left;
        private Node notLeft;
        private Value val; 
 
        /**
         * Takes the parameters and instantiates their respective variables. 
         * @param point
         * @param rectangle
         * @param left
         * @param notLeft
         * @param val
         */
        public Node(Point2D point, RectHV rectangle, Node left, Node notLeft, Value val) {
            this.point = point;
            this.rectangle = rectangle;
            this.left = left;
            this.notLeft = notLeft;
            this.val = val;
        }
    }

    public KdTreeST() {
    } 
 
    /**
     * Checks if the symbol table is empty.
     * @return 
     */
    public boolean isEmpty() {
        return size == 0;
    } 
 
    /**
     * Returns the number of points. 
     * @return
     */
    public int size() {
        return size;
    }
 
    /**
     * Compares points to see which is greater. Depending the Boolean vertical will compare Xs or Ys.
     * 
     * @param node
     * @param point
     * @param vertical
     * @return
     */
    private double comparePoints(Node node, Point2D point, Boolean vertical) {
        if (vertical) {
            return point.x() - node.point.x();
        } else {
            return point.y() - node.point.y();
        }
    }
 
    /**
     * Sets the boundaries for the min and max point values. 
     * Calls the private put method recursively. 
     * 
     * @param point
     * @param val
     */
    public void put(Point2D point, Value val) {
        if (point == null || val == null)
            throw new NullPointerException("Point Cannot be Null");
        
        xmin = Double.NEGATIVE_INFINITY;
        ymin = Double.NEGATIVE_INFINITY;
        xmax = Double.POSITIVE_INFINITY;
        ymax = Double.POSITIVE_INFINITY;
        
        head = put(head, point, val, true);
 
    }
 
    /**
     * Puts a point in the structure according to the rules of KDTreeST.
     * Creates the restricted rectangle for each point.
     * 
     * @param node
     * @param point
     * @param val
     * @param vertical
     * @return Node
     */
    private Node put(Node node, Point2D point, Value val, Boolean vertical) {
        if (node == null) {
            size++;
            RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
            return new Node(point, rect, null, null, val);
        }
 
        double comparePoints = comparePoints(node, point, vertical);
        if (node.point.equals(point))
            node.val = val;
        else if (comparePoints < 0) {
            if (vertical)
                xmax = node.point.x();
            else
                ymax = node.point.y();
            node.left = put(node.left, point, val, !vertical);
        }else if (comparePoints >= 0) {
            if (vertical)
                xmin = node.point.x();
            else
                ymin = node.point.y();
            
            node.notLeft = put(node.notLeft, point, val, !vertical);
        }
 
        return node;
    }
 
    /**
     * Returns the value associated with a point. 
     * @param p
     * @return
     */
    public Value get(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        
        return get(head, p, true);
    } 
    
    /**
     * Searches for a point in the structure and returns its value. If the point is 
     * not found will return null.
     * @param node
     * @param point
     * @param vertical
     * @return Value of Node
     */
    private Value get(Node node, Point2D point, Boolean vertical) {
        if (node == null) 
        	return null;
        
        if (node.point.equals(point)) 
        	return node.val;
        
        double compare = comparePoints(node, point, vertical);
        
        if (compare < 0) 
        	return get(node.left, point, !vertical);
        else if (compare >= 0) 
        	return get(node.notLeft, point, !vertical);
        
        return node.val;
    }
 
    /**
     * Checks if the symbol table contains point p. 
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        return get(p) != null;
    } 
 
    /**
     * Creates a Queue<Point2D> containing all points in level order.
     * @return queue of points. 
     */
    public Iterable<Point2D> points() {
        if (isEmpty())
            return new Queue<Point2D>();
        
        Queue<Point2D> pointQueue = new Queue<Point2D>();
        Queue<Node> nodeQueue = new Queue<Node>();
    
        nodeQueue.enqueue(head);
        
        while (!nodeQueue.isEmpty()) {
            Node node = nodeQueue.dequeue();
            
            if (node == null)
                continue;
            
            pointQueue.enqueue(node.point);
            nodeQueue.enqueue(node.left);
            nodeQueue.enqueue(node.notLeft);
        }
        return pointQueue;
    }
 
    /** 
     * Creates A Queue<Point2D> of points contained within the query rectangle.
     * Calls the recursive private method range().
     * @param rect
     * @return Iterable of points.
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        
        Queue<Point2D> returnQueue = new Queue<Point2D>();
        range(rect, returnQueue, head);
        return returnQueue;
    }
 
    /**
     * Recursively searches for points with the query rectangle. If teh rectangle constrained by a point 
     * does not interest the query rectangle that remaining branch will not be searched.
     * @param rect
     * @param returnQueue
     * @param node
     */
    private void range(RectHV rect, Queue<Point2D> returnQueue, KdTreeST<Value>.Node node) {
        if (node == null)
            return;
        
        if (!rect.intersects(node.rectangle))
            return;
        
        if (rect.contains(node.point))
            returnQueue.enqueue(node.point);
        
        range(rect, returnQueue, node.left);
        range(rect, returnQueue, node.notLeft);
    }
 
    /**
     * Returns the nearest node to the point passed in the parameter. 
     * Throws NullPointerException if point is null. 
     * Calls the private nearestNodeFinder method. 
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException("Point value cannot be null");
 
        Node nearestNode = head;
        return nearestNodeFinder(head, p, nearestNode, true).point;
    }
 
    /**
     * Recursively searches for the nearest Node. If the rectangle constrained by a point 
     * is further away then the previously found closest point that branch will not be searched.
     * @param node
     * @param point
     * @param currentMin
     * @param vertical
     * @return
     */
    private Node nearestNodeFinder(Node node, Point2D point, Node currentMin, Boolean vertical) {
        Node nearestNode = currentMin;
        if (node == null)
            return nearestNode;
 
        if (node.point.distanceSquaredTo(point) < currentMin.point.distanceSquaredTo(point)) {
            nearestNode = node;
        }
        if (node.rectangle.distanceSquaredTo(point) < currentMin.point.distanceSquaredTo(point)) {
            double compare = comparePoints(node, point, vertical);
            if (compare < 0) {
                nearestNode = nearestNodeFinder(node.left, point, nearestNode, !vertical);
                nearestNode = nearestNodeFinder(node.notLeft, point, nearestNode, !vertical);
            } else if (compare >= 0) {
                nearestNode = nearestNodeFinder(node.notLeft, point, nearestNode, vertical);
                nearestNode = nearestNodeFinder(node.left, point, nearestNode, vertical);
            }
        }
        return nearestNode;
    }
 
    /**
     * Main method for testing. 
     * @param args
     */
    public static void main(String[] args) {
        String filename = fileData.getVertexPointsLocation();
        In in = new In(filename);
 
        KdTreeST<Integer[]> testST1 = new KdTreeST<Integer[]>();
 
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            System.out.println(p + " " + i);
            Integer[] testArray = {i,0};
            testST1.put(p, testArray);
        }
 
        for (Point2D el : testST1.points()) {
            System.out.print(el + " ");
        }
        System.out.println();
        for (Point2D el : testST1.points()) {
            System.out.print(testST1.get(el)[0] + " ");
        }
 
        System.out.println();
 
        System.out.println(testST1.get(new Point2D(0.33, 0.1)));
        System.out.println(testST1.contains(new Point2D(0.5, 0.5)));
        System.out.println(testST1.contains(new Point2D(0.33, 0.1)));
 
        System.out.println("Nearest to (0.8 0.8): " + testST1.nearest(new Point2D(0.8, 0.8)));




        
    }
}
 
 

