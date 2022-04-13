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

import javax.swing.JPanel;

import edu.princeton.cs.algs4.Point2D;

/**
 *
 * @author Thanasis1101
 * @version 1.0
 */
public class MainPanel extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {

    private final BufferedImage image;
    private final KdTreeST<Integer> poi;

    private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private boolean zoomer;
    private boolean dragger;
    private boolean released;
    private double xOffset = 0;
    private double yOffset = 0;
    private int xDiff;
    private int yDiff;
    private Point startPoint;
    private boolean leftClick = false;

    public MainPanel(BufferedImage image, KdTreeST<Integer> poi) {
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

        if (zoomer) {
            AffineTransform at = new AffineTransform();

            double xRel = (MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX());
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = zoomFactor / prevZoomFactor;

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

            at.translate(xOffset, yOffset);
            at.scale(zoomFactor, zoomFactor);
            prevZoomFactor = zoomFactor;
            g2.transform(at);
            zoomer = false;
        }

        if (dragger) {
            AffineTransform at = new AffineTransform();
            at.translate(xOffset + xDiff, yOffset + yDiff);
            at.scale(zoomFactor, zoomFactor);
            g2.transform(at);

            if (released) {
                xOffset += xDiff;
                yOffset += yDiff;
                dragger = false;
            }

        }

        if (leftClick) {
            AffineTransform at = new AffineTransform();
            at.translate(xOffset, yOffset);
            at.scale(zoomFactor, zoomFactor);
            g2.transform(at);
            double mouseX = (((MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX())
                    - xOffset) / zoomFactor);
            double mouseY = (((MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY())
                    - yOffset) / zoomFactor);
            Point2D mouse = new Point2D(mouseX, mouseY);
            backEndTest.testOutPut(poi.get(poi.nearest(mouse)));

            leftClick = false;
        }

        // All drawings go here

        double mouseX = (((MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX()) - xOffset)
                / zoomFactor);
        double mouseY = (((MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY()) - yOffset)
                / zoomFactor);
        Point2D mouse = new Point2D(mouseX, mouseY);

        g2.drawImage(image, 0, 0, this);

        g2.setFont(new Font("Microsoft YaHei", Font.PLAIN, (int) (50)));
        g2.setColor(new Color(250, 0, 0));
        for (Point2D el : poi.points()) {
            g2.fillOval((int) el.x(), (int) el.y(), 50, 50);
        }
        Point2D nearest = poi.nearest(mouse);
        g2.setColor(new Color(0, 0, 250));
        g2.fillOval((int) nearest.x(), (int) nearest.y(), 50, 50);

        g2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 100));

        g2.drawString("X: " + mouseX, 500, 500);

        g2.drawString("Y: " + mouseY, 500, 700);
        System.out.println("Xmouse: " +
                MouseInfo.getPointerInfo().getLocation().getX() + " Ymouse: "
                + MouseInfo.getPointerInfo().getLocation().getY());

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
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (released == false) {
            Point curPoint = e.getLocationOnScreen();
            xDiff = curPoint.x - startPoint.x;
            yDiff = curPoint.y - startPoint.y;

            dragger = true;
            repaint();
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
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

}