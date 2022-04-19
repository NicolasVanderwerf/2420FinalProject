package skiHill;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static JLabel GetlblRouteOutput() {
        return lblRouteOutput;
    }
    /**
     * Create the frame.
     */
    public skiHillApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(25, 10, 1280, 720);
        contentPane = new JPanel();
        contentPane.setBorder(null);
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        

        JLabel lblRunRoute = extractedlblRouteOutput();
        contentPane.add(lblRunRoute, BorderLayout.SOUTH);
        
        JPanel centerPanel = extractedSkiHillJPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);
        

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        
    }

    private JPanel extractedSkiHillJPanel(){
        try {

            // Load the image that will be shown in the panel
            BufferedImage image = ImageIO.read(new File("4WitRozEG0phUysB.jpeg"));
        
            String filename = "Edge Points.txt";
            In in = new In(filename);
        
            KdTreeST<Integer> testST1 = new KdTreeST<Integer>();
        
             for (int i = 0; !in.isEmpty(); i++) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                //System.out.println(p + " " + i);
                testST1.put(p, i);
            }
            
            mainPanel = new skiHillPanel(image,testST1);
            mainPanel.setBounds(50, 50, 1000, (int)(1000*0.536));
            mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            //this.add(mainPanel);
            mainPanel.setVisible(true);
        
        } catch (IOException ex) {
           // Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mainPanel;
    }

   
    private JLabel extractedlblRouteOutput() {
        lblRouteOutput = new JLabel("Select your location, then your desired destination: ");
        lblRouteOutput.setBorder(new EmptyBorder(20, 0, 20, 0));
        lblRouteOutput.setFont(new Font("Monospaced", Font.BOLD, 30));
        lblRouteOutput.setHorizontalAlignment(SwingConstants.CENTER);
        return lblRouteOutput;
    }

}