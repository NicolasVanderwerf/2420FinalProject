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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import edu.princeton.cs.algs4.Point2D;

/**
 *
 * @author Thanasis1101
 * @version 1.0
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
    private BufferedImage button1;
    private int repaintTrack = 0;

    public skiHillPanel(BufferedImage image, KdTreeST<Integer> poi) {
        try {
            this.button1 = ImageIO.read(new File("red_button.jpg"));
        } catch (IOException e) {
            System.out.println("Fiked");
            e.printStackTrace();
        }
        this.poi = poi;
        this.image = image;
        initComponent();

    }

    private void initComponent() {
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void update(Graphics g) {

    }

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
            double mouseX = (((MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX())
                    - xOffset) / zoomFactor);
            double mouseY = (((MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY())
                    - yOffset) / zoomFactor);
            Point2D mouse = new Point2D(mouseX, mouseY);

            // createLiftLocations(mouseX, mouseY); //Only for creating lift locations when
            // creating a new resort or editing a previous.

            backEnd.testOutPut(poi.nearest(mouse), poi.get(poi.nearest(mouse)));

            leftClick = false;
        }

        if (mouseMoving) {
            mouseMoving = false;

        }

        if (!dragger) {
            if (xOffset > 0)
                xOffset = 0;
            if (yOffset > 0)
                yOffset = 0;

            if (xOffset < (-7193.0 * zoomFactor + 1280)) {
                xOffset = (-7193.0 * zoomFactor + 1280);
            }
            if (yOffset < (-3861.0 * zoomFactor + 720)) {
                yOffset = (-3861.0 * zoomFactor + 720);
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
            if (xChange < (-7193.0 * zoomFactor + 1280)) {
                xChange = (-7193.0 * zoomFactor + 1280);
            }
            if (yChange < (-3861.0 * zoomFactor + 720)) {
                yChange = (-3861.0 * zoomFactor + 720);
            }
            at.translate(xChange, yChange);
            at.scale(zoomFactor, zoomFactor);
        }

        //Transforms Graphics Object and Draws base Image.
        g2.transform(at);
        g2.drawImage(image, 0, 0, this);

        double mouseX = (((MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX()) - xOffset)
                / zoomFactor);
        double mouseY = (((MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY()) - yOffset)
                / zoomFactor);
        Point2D mouse = new Point2D(mouseX, mouseY);


        //Draws all dots, Draws in Grey if two points are selected.
        if (backEnd.twoPointsSelected == true)
            g2.setColor(new Color(211, 211, 211));
        else
            g2.setColor(new Color(0, 250, 0));
        for (Point2D el : poi.points()) {
            g2.fillOval((int) el.x() - 25, (int) el.y() - 25, 50, 50);
        }

        //Indicate nearest point to mouse in Blue
        Point2D nearest = poi.nearest(mouse);
        g2.setColor(new Color(0, 0, 250));
        g2.fillOval((int) nearest.x() - 15, (int) nearest.y() - 15, 30, 30);


        //Draw Selected Points Red
        g2.setColor(new Color(250, 0, 0));
        for (Point2D el : backEnd.pointsSelected) {
            g2.fillOval((int) el.x() - 25, (int) el.y() - 25, 50, 50);
        }

        //Debug Draw
        g2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 100));
        g2.drawString("X: " + xOffset, 500, 500);
        g2.drawString("Y: " + yOffset, 500, 700);
        g2.drawString("Zoom: " + zoomFactor, 500, 900);

    }

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

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            if (released == false) {
                Point curPoint = e.getLocationOnScreen();
                xDiff = curPoint.x - startPoint.x;
                yDiff = curPoint.y - startPoint.y;

                dragger = true;
                repaint();
            }
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1 && e.getButton() != MouseEvent.BUTTON3) {
            mouseMoving = true;
            //repaintTrack++;
            // System.out.println(repaintTrack);
            repaint();

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = true;
            repaint();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            released = false;
            startPoint = MouseInfo.getPointerInfo().getLocation();
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            released = true;
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void createLiftLocations(double mouseX, double mouseY) {

        try {
            Writer output;
            output = new BufferedWriter(new FileWriter("Edge Points.txt", true));
            output.append("\n" + mouseX + " " + mouseY);
            output.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}