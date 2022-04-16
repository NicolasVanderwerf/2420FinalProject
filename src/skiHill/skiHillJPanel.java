package skiHill;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import java.awt.Color;

public class skiHillJPanel extends JPanel {
	private JLabel lblImage;
    private MainPanel mainPanel;
	
	//number of nodes (top/bottom of the lift)
	private static final int numButtons = 0;
	
	// allows us to search and remove Integers that represent the selected lifts
	private ArrayList<Integer> liftsSelected = new ArrayList<>();
	
	//creates all of the radio buttons for the lift
	private static JRadioButton[] allButtons = new JRadioButton[numButtons];
	
	//all of the locations of the buttons. Each index of the first array matches with the index of the JRadioButton above 
    /*
	private int[][] allButtonLocations = {{378, 544, 109, 23}, {401, 450, 109, 23}, 
			{362, 428, 109, 23}, {312, 518, 109, 23}, {464, 455, 109, 23}, 
			{337, 348, 109, 23}, {335, 345, 109, 23}, {424, 348, 109, 23}, 
			{536, 315, 109, 23}, {469, 331, 109, 23}, {350, 215, 109, 23}};
    */

    private int[][] allButtonLocations = {{378, 544, 109, 23}};
	
	//Gets the JLabel object at the bottom section of the skiHillApp so we can update the text 
	JLabel textOutput = skiHillApp.GetlblRouteOutput();
	
	/**
	 * Create the panel.
	 */
	public skiHillJPanel() {
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(null);
		
		add(layeredPane, BorderLayout.CENTER);
		
		//Size of image: 1196, 708
		extractedLblImage();
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
            
            mainPanel = new MainPanel(image,testST1);
            mainPanel.setBounds(50, 50, 1200, (int)(1200*0.536));
            mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            //this.add(mainPanel);
            mainPanel.setVisible(true);

        } catch (IOException ex) {
           // Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
		layeredPane.add(mainPanel);
		//layeredPane.add(lblImage);
		
		//creates all of the radio button objects
		for(int i = 0; i < numButtons; i++) {
			allButtons[i] = new JRadioButton();
		}
		
		//creates all of the buttons and adds them to the layeredPane
		for(int i = 0; i < allButtons.length; i++) {
		JRadioButton rdbtnLift = extractedRdbtnLift(layeredPane, i, allButtons[i]);
			layeredPane.add(rdbtnLift);
		}
	}

	/**
	 * responsible for creating all of the details of the buttons, and the on click the functionality. 
	 * @param layeredPane
	 * @return
	 */
	private JRadioButton extractedRdbtnLift(JLayeredPane layeredPane, int i, JRadioButton rdbtn) {
		//whenever a button is clicked
		rdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(liftsSelected.size() <= 2) {
					if(rdbtn.isSelected()) {
						liftsSelected.add(i);
						
						if(liftsSelected.size() == 2) {
							//disables all of the buttons since we only want a max of two selected
							for(JRadioButton el: allButtons) {
								el.setEnabled(false);
							}
							//enables the two that we have selected
							allButtons[liftsSelected.get(0)].setEnabled(true);
							allButtons[liftsSelected.get(1)].setEnabled(true);
							
							//updates the text at the bottom of the screen 
							textOutput.setText("Route: " +  liftsSelected.get(0) + "-->" + liftsSelected.get(1));
						}else {
							//enables all of the buttons 
							for(JRadioButton el: allButtons) {
								el.setEnabled(true);
							}
						}
					}
					else {//btn was deselected 
						liftsSelected.remove(Integer.valueOf(i));
						
						//enables all of the buttons since there are less than two selected 
						for(JRadioButton el: allButtons) {
							el.setEnabled(true);
						}
					}
				}
			}
		});
		rdbtn.setOpaque(false);
		layeredPane.setLayer(rdbtn, i+1);
		rdbtn.setBounds(allButtonLocations[i][0],allButtonLocations[i][1],allButtonLocations[i][2],allButtonLocations[i][3]);
		return rdbtn;
	}
	
	/**
	 * Creates the lbl that the buttons sit on top of 
	 */
	private void extractedLblImage() {
		lblImage = new JLabel("");
		lblImage.setBorder(null);
		//lblImage.setIcon(new ImageIcon(skiHillJPanel.class.getResource("/img/parkCityPhoto_25_cropped.jpg")));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		//for cropped image: 
		lblImage.setBounds(121, 31, 1196, 708);
		//for parkcity header: 
//		lblImage.setBounds(120, 0, 1196, 708);
	}

    public static void changeLocations(int x, int y){
        JRadioButton rdbtn = allButtons[0];
        rdbtn.setBounds(x,y,10,10);

    }
}
