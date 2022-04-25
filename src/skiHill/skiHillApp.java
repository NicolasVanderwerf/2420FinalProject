package skiHill;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author NicolasVanderWerf & HaydenBlackmer
 */
@SuppressWarnings("serial")
public class skiHillApp extends JFrame {

    private skiHillPanel mainPanel;
    private JPanel contentPane;
    private static JLabel lblRouteOutput;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
                    skiHillApp frame = new skiHillApp();

                    // Add window listener by implementing WindowAdapter class to
                    // the frame instance.
                    frame.addWindowListener(new WindowAdapter() {
                        // To handle the close event we just need
                        // to implement the windowClosing() method.
                        @Override
                        public void windowClosing(WindowEvent e) {
                            backEnd.createOutputFile();
                            System.exit(0);
                        }
                    });
                    frame.setVisible(true);
                    frame.setResizable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Getter for the JLabel.
     * 
     * @return JLabel
     */
    public static JLabel getlblRouteOutput() {
        return lblRouteOutput;
    }

    /**
     * Create the frame.
     * Adds all of the components to the frame.
     */
    public skiHillApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(25, 10, 1280, 855);
        contentPane = new JPanel();
        contentPane.setBorder(null);
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel centerPanel = extractedSkiHillJPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);

        JLabel lblRunRoute = extractedlblRouteOutput();
        contentPane.add(new JScrollPane(lblRunRoute), BorderLayout.SOUTH);

    }

    /**
     * Creates a JLabel that takes up the bottom section of the JFrame.
     * This label is in charge of displaying the desired text to the user.
     * 
     * @return JLabel
     */
    private JLabel extractedlblRouteOutput() {
        lblRouteOutput = new JLabel("Select your location, then your desired destination: ");
        lblRouteOutput.setBorder(new EmptyBorder(20, 0, 20, 0));
        lblRouteOutput.setFont(new Font("Monospaced", Font.BOLD, 30));
        lblRouteOutput.setHorizontalAlignment(SwingConstants.CENTER);
        return lblRouteOutput;
    }

    /**
     * Sets up the center panel in the JFrame.
     * 
     * @return JPanel
     */
    private JPanel extractedSkiHillJPanel() {
        try {
            // Load the image that will be shown in the panel
            BufferedImage image = ImageIO.read(new File(fileData.getMapLocation()));

            String filename = fileData.getVertexPointsLocation();
            In in = new In(filename);

            KdTreeST<Integer> testST1 = new KdTreeST<Integer>();

            for (int i = 0; !in.isEmpty(); i++) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                testST1.put(p, i);
            }

            mainPanel = new skiHillPanel(image, testST1);
            mainPanel.setBounds(50, 50, 1000, (int) (1000 * 0.536));
            mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            mainPanel.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mainPanel;
    }
}