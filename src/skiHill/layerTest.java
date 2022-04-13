package skiHill;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class layerTest {
  public static void main(String[] args) {
      JFrame frame = createFrame();

      JLayeredPane layeredPane = new JLayeredPane();

      //adding a button to the JLayeredPane
      JButton button = new JButton("Show message");
      //need to do absolute positioning because by default LayeredPane has null layout,
      button.setBounds(100, 50, 150, 30);
      layeredPane.add(button, JLayeredPane.DEFAULT_LAYER);//depth 0

      //to make label visible
     

      //to hide the label
      JPanel centerPanel = new skiHillJPanel();
      centerPanel.setVisible(true);
      layeredPane.add(centerPanel,JLayeredPane.PALETTE_LAYER);
      layeredPane.setVisible(true);
      
      //frame.add(centerPanel);
      frame.add(layeredPane);
      
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
  }

  private static JFrame createFrame() {
      JFrame frame = new JFrame("JLayeredPane Basic Example");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(new Dimension(1200, 800));
      return frame;
  }
}
