package skiHill;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		JRadioButton rdbtnLift0 = extractedRdbtnLift0(layeredPane);
		layeredPane.add(rdbtnLift0);
		
		JRadioButton rdbtnLift1 = extractedRdbtnLift1(layeredPane);
		layeredPane.add(rdbtnLift1);

	}

	/**
	 * Button for the lift
	 * @param layeredPane
	 * @return
	 */
	private JRadioButton extractedRdbtnLift0(JLayeredPane layeredPane) {
		JRadioButton rdbtnLift0 = new JRadioButton("");
		
		//this is so we can simply iterate through the buttons and un-select them 
		deselectButtons.enqueue(rdbtnLift0);
		
		//whenever a button is clicked
		rdbtnLift0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnLift0.isSelected()) {
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
		
		rdbtnLift0.setOpaque(false);
		layeredPane.setLayer(rdbtnLift0, 1);
		rdbtnLift0.setBounds(396, 424, 21, 23);
		return rdbtnLift0;
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
	
	private void extractedLblImage() {
		lblImage = new JLabel("");
		lblImage.setBorder(null);
		lblImage.setIcon(new ImageIcon(skiHillJPanel.class.getResource("/img/parkCityPhoto_25_cropped.jpg")));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		//for cropped image: 
		lblImage.setBounds(115, 0, 1196, 708);
		//for parkcity header: 
//		lblImage.setBounds(120, 0, 1196, 708);
	}
}
