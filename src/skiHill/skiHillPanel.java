package skiHill;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import javax.swing.JPanel;

import edu.princeton.cs.algs4.Point2D;

/**
 * 
 * @author NicolasVanderWerf & HaydenBlackmer
 */
public class skiHillPanel extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {

    private final BufferedImage image;
    private final KdTreeST<Integer> poi;

    private double zoomFactor = 0.2;
    private double prevZoomFactor = 0.3;
    private boolean zoomer;
    private boolean dragger;
    private boolean released;
    private double xOffset = 0;
    private double yOffset = 0;
    private int xDiff;
    private int yDiff;
    private Point startPoint;
    private boolean leftClick = false;
    private boolean mouseMoving = false;
    
    @SuppressWarnings("unused")
    private int locationAddedCount;

    /**
     * Constructor for a new SkiHillPanel
     * 
     * @param image Image of Map
     * @param poi   KDTreeST of all locations
     */
    public skiHillPanel(BufferedImage image, KdTreeST<Integer> poi) {
        this.poi = poi;
        this.image = image;
        initComponent();
    }

    /**
     * Initializes Mouse Listeners.
     */
    private void initComponent() {
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    /**
     * Paint of Graphics2D object. Paints Map while also apply the transformations
     * for moving, scrolling, and zooming the photo. Paints with corresponding
     * colors depending
     * on how many are selected and which points are selected.
     * 
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;
        AffineTransform at = new AffineTransform();

        if (zoomer) {
            double xRel = (MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX());
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();
            double zoomDiv = zoomFactor / prevZoomFactor;

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;
            prevZoomFactor = zoomFactor;
            zoomer = false;
        }

        if (dragger) {
            if (released) {
                xOffset += xDiff;
                yOffset += yDiff;
                dragger = false;
            }
        }

        if (leftClick) {
            // Creates mouse location relative to transformations
            double mouseX = (((MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX())
                    - xOffset) / zoomFactor);
            double mouseY = (((MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY())
                    - yOffset) / zoomFactor);
            Point2D mouse = new Point2D(mouseX, mouseY);

            // createLiftLocations(mouseX, mouseY); //Only for creating lift locations when
            // creating a new resort or editing a previous.
            backEnd.backEndInput(poi.nearest(mouse), poi.get(poi.nearest(mouse)));
            leftClick = false;
        }
        if (mouseMoving) {
            mouseMoving = false;
        }
        // Apply Transformations to graphics object.
        if (!dragger) {
            if (xOffset > 0)
                xOffset = 0;
            if (yOffset > 0)
                yOffset = 0;
            if (xOffset < (-fileData.getMapSize()[0] * zoomFactor + 1280)) {
                xOffset = (-fileData.getMapSize()[0] * zoomFactor + 1280);
            }
            if (yOffset < (-fileData.getMapSize()[1] * zoomFactor + 720)) {
                yOffset = (-fileData.getMapSize()[1] * zoomFactor + 720);
            }
            
            at.translate(xOffset, yOffset);
            at.scale(zoomFactor, zoomFactor);
        } else {
            double xChange = xOffset + xDiff;
            double yChange = yOffset + yDiff;
            
            if (xChange > 0)
                xChange = 0;
            if (yChange > 0)
                yChange = 0;
            if (xChange < (-fileData.getMapSize()[0] * zoomFactor + 1280)) {
                xChange = (-fileData.getMapSize()[0] * zoomFactor + 1280);
            }
            if (yChange < (-fileData.getMapSize()[1] * zoomFactor + 720)) {
                yChange = (-fileData.getMapSize()[1] * zoomFactor + 720);
            }
            
            at.translate(xChange, yChange);
            at.scale(zoomFactor, zoomFactor);
        }

        // Transforms Graphics Object and Draws base Image.
        g2.transform(at);
        g2.drawImage(image, 0, 0, this);

        // Creates mouse location relative to transformations
        double mouseX = (((MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX()) - xOffset)
                / zoomFactor);
        double mouseY = (((MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY()) - yOffset)
                / zoomFactor);
        Point2D mouse = new Point2D(mouseX, mouseY);

        // Draws all dots, Draws in Grey if two points are selected.
        if (backEnd.twoPointsSelected == true)
            g2.setColor(new Color(211, 211, 211));
        else
            g2.setColor(new Color(0, 250, 0));
            
        for (Point2D el : poi.points()) {
            g2.fillOval((int) el.x() - 25, (int) el.y() - 25, 50, 50);
        }

        // Indicate nearest point to mouse in Blue
        Point2D nearest = poi.nearest(mouse);
        g2.setColor(new Color(0, 0, 250));
        g2.fillOval((int) nearest.x() - 15, (int) nearest.y() - 15, 30, 30);

        // Draw Selected Points Red
        g2.setColor(new Color(250, 0, 0));
        
        for (Point2D el : backEnd.pointsSelected) {
            g2.fillOval((int) el.x() - 25, (int) el.y() - 25, 50, 50);
        }

        // Debug Draw
        g2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 100));
        g2.drawString("X: " + xOffset, 500, 500);
        g2.drawString("Y: " + yOffset, 500, 700);
        g2.drawString("Zoom: " + zoomFactor, 500, 900);
    }

    /**
     * Applies zoom factor based on mouse wheel input.
     * 
     * @param e mouse event
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        zoomer = true;

        // Zoom in
        if (e.getWheelRotation() < 0) {
            zoomFactor *= 1.1;
            repaint();
        }
        
        // Zoom out
        if (e.getWheelRotation() > 0) {
            zoomFactor /= 1.1;
            if (zoomFactor < 0.2) {
                zoomFactor = 0.2;
            } else {
                repaint();
            }
        }
    }

    /**
     * Applies X and Y offset based on mouse input.
     * @param e mouse event
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            if (released == false) {
                if (startPoint != null) {
                    Point curPoint = e.getLocationOnScreen();
                    xDiff = curPoint.x - startPoint.x;
                    yDiff = curPoint.y - startPoint.y;
                    dragger = true;
                    repaint();
                }
            }
        }
    }

    /**
     * Refreshes map as mouse is moving.
     * @param e mouse event
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1 && e.getButton() != MouseEvent.BUTTON3) {
            mouseMoving = true;
            repaint();
        }
    }

    /**
     * Refreshes map with leftClick = true
     * @param e mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = true;
            repaint();
        }
    }

    /**
     * Refreshes map with released = false
     * @param e mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            released = false;
            startPoint = MouseInfo.getPointerInfo().getLocation();
            repaint();
        }
    }

    /**
     * Refreshes map with released = true
     * @param e mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            released = true;
            repaint();
        }
    }

    /**
     * Required MouseEvent
     * @param e mouse event
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Required MouseEvent
     * @param e mouse event
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Allows user to create points of interest on map when adding a new map.
     * Is not used when program is running. Writes clicked locations to a file.
     * locationAddedCount is the number of writen locations which is passed to
     * print location and next in backEnd.
     * @param mouseX mouse X coordinate relative to transformations
     * @param mouseY mouse Y coordinate relative to transformations
     */
    @SuppressWarnings("unused")
    private void createLiftLocations(double mouseX, double mouseY) {
        try {
            Writer output;
            output = new BufferedWriter(new FileWriter(fileData.getVertexPointsLocation(), true));
            output.append("\n" + mouseX + " " + mouseY);
            output.close();
            backEnd.printLocationAndNext(locationAddedCount);
            locationAddedCount++;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        
    }

}