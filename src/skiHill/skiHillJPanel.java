package skiHill;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class skiHillJPanel extends JPanel {
	private JLabel lblImage;

	private Stack<Integer> liftsSelected = new Stack();
	private Queue<JRadioButton> deselectButtons = new Queue<>();
	
	//add all buttons to here 
//	private ArrayList<JRadioButton> x = new ArrayList<>();
	private JRadioButton[] allButtons = {new JRadioButton(""),new JRadioButton("")};
	private int[][] allButtonLocations = {{378, 544, 109, 23},{401, 450, 109, 23}};
	/**
	 * Create the panel.
	 */
	public skiHillJPanel() {
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(null);
		
		add(layeredPane, BorderLayout.CENTER);
		
		//1196, 708
		extractedLblImage();
		layeredPane.add(lblImage);
		
		for(int i = 0; i < allButtons.length-1; i++) {
		JRadioButton rdbtnLift = extractedRdbtnLift(layeredPane, i, allButtons[i]);
			layeredPane.add(rdbtnLift);
		}
	}

	/**
	 * Button for the lift
	 * @param layeredPane
	 * @return
	 */
	private JRadioButton extractedRdbtnLift(JLayeredPane layeredPane, int i, JRadioButton rdbtn) {
		//this is so we can simply iterate through the buttons and un-select them 
		deselectButtons.enqueue(rdbtn);
		
		//whenever a button is clicked
		rdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtn.isSelected()) {
					
					//pushed the number associated with this lift
					liftsSelected.push(0);
					
					if(liftsSelected.size() == 1) {
						rdbtn.setBounds(allButtonLocations[i+1][0],allButtonLocations[i+1][1],allButtonLocations[i+1][2],allButtonLocations[i+1][3]);
						//send int to wherever 
						// TODO 
						//run algorithm if two have been selected
						//unselect the two buttons.
					}
					
					if(liftsSelected.size() == 3) {
						deselectAllLifts();
					}
				
				}
				else {
					rdbtn.setBounds(allButtonLocations[i][0],allButtonLocations[i][1],allButtonLocations[i][2],allButtonLocations[i][3]);
					liftsSelected.pop();
					System.out.println("disabled" + liftsSelected);
				}
			}
		});
		rdbtn.setOpaque(false);
		layeredPane.setLayer(rdbtn, 1);
		rdbtn.setBounds(allButtonLocations[i][0],allButtonLocations[i][1],allButtonLocations[i][2],allButtonLocations[i][3]);
		return rdbtn;
	}
	
	/**
	 * Button for the lift
	 * @param layeredPane
	 * @return
	 */
	private JRadioButton extractedRdbtnLift1(JLayeredPane layeredPane) {
		JRadioButton rdbtnLift1 = new JRadioButton("");
		
		//this is so we can simply iterate through the buttons and un-select them 
		deselectButtons.enqueue(rdbtnLift1);
		
		//whenever a button is clicked
		rdbtnLift1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnLift1.isSelected()) {
					//pushed the number associated with this lift
					liftsSelected.push(0);
					
					if(liftsSelected.size() == 2) {
						// TODO 
						//run algorithm if two have been selected
						//unselect the two buttons.
						deselectAllLifts();
					}
				
				}
				else {
					liftsSelected.pop();
					System.out.println("disabled" + liftsSelected);
				}
			}
		});
		
		rdbtnLift1.setOpaque(false);
		layeredPane.setLayer(rdbtnLift1, 1);
		rdbtnLift1.setBounds(368, 515, 21, 23);
		return rdbtnLift1;
	}

	/**
	 * Deselects all of the lifts
	 */
	private void deselectAllLifts() {
		for(JRadioButton el: deselectButtons) {
			el.setSelected(false);
		}
		
		liftsSelected = new Stack();
	}
	
	private void extractedLblImage(){
		lblImage = new JLabel("");
		lblImage.setBorder(null);
		try {
			lblImage.setIcon(new ImageIcon(ImageIO.read(new File("panda test.jpg"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		//for cropped image: 
		lblImage.setBounds(121, 31, 1196, 708);
		//for parkcity header: 
//		lblImage.setBounds(120, 0, 1196, 708);
	}
}
