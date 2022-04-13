package skiHill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class skiHillApp extends JFrame {

    private JPanel contentPane;

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

    /**
     * Create the frame.
     */
    public skiHillApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(25, 10, 1440, 900);
        contentPane = new JPanel();
        contentPane.setBorder(null);
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
//        JLabel lblMapImg = extractedlblMapImg();
//        contentPane.add(lblMapImg, BorderLayout.CENTER);
        
        
        JLabel lblRunRoute = extractedlblRouteOutput();
        contentPane.add(lblRunRoute, BorderLayout.SOUTH);
        
        JPanel centerPanel = extractedSkiHillJPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);
        

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        
    }

    private JPanel extractedSkiHillJPanel() {
        JPanel centerPanel = new skiHillJPanel();
        return centerPanel;
    }

    private JLabel extractedlblRouteOutput() {
        JLabel lblRouteOutput = new JLabel("This is where the ski run output will go!");
        lblRouteOutput.setBorder(new EmptyBorder(20, 0, 20, 0));
        lblRouteOutput.setFont(new Font("Monospaced", Font.BOLD, 30));
        lblRouteOutput.setHorizontalAlignment(SwingConstants.CENTER);
        return lblRouteOutput;
    }
//
//    //contains the photo of the skihill
//    private JLabel extractedlblMapImg() {
//        JLabel lblMapImg = new JLabel("");
//        lblMapImg.setOpaque(true);
//        lblMapImg.setBackground(Color.WHITE);
//        
////        ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/img/parkCityPhoto.jpg").getImage().getScaledInstance(1196, 708, Image.SCALE_DEFAULT));
//        
//
//        lblMapImg.setIcon(new ImageIcon("src/img/parkCityPhoto_25.jpg"));
//        lblMapImg.setHorizontalAlignment(SwingConstants.CENTER);
//        
//        return lblMapImg;
//    }
}