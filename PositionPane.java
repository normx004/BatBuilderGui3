package batboy;

import javax.swing.*;
import java.awt.*;
//
// Defines 'x - and -y position' panel (couple of text boxes)
public class PositionPane {
	BatGUI batGui_ = null;
	
	public PositionPane ( BatGUI bg) {
		batGui_ = bg;
	}
	
	public JPanel getPanel() {
		
		 JPanel posPane = new JPanel();
	     posPane.setLayout(new BoxLayout(posPane, BoxLayout.PAGE_AXIS));
	     batGui_.setXvalTextField( new JTextField("005"));
	     batGui_.setYvalTextField( new JTextField("005"));
	     batGui_.getXvalTextField().setName("xval pos text field");
	     posPane.add(new JLabel("X position"));
	     System.out.println("adding xval text field to position pane: "+batGui_.getXvalTextField().toString());
	     posPane.add(batGui_.getXvalTextField());
	     System.out.println("value in xval text field is "+batGui_.getXvalTextField().getText());
	     posPane.add(new JLabel("Y position"));
	     posPane.add(batGui_.getYvalTextField());
	     
	     // combine radio upper/lower/left/right and position display into one panel
	     JPanel sposPane = new JPanel();
	     sposPane.setLayout(new BoxLayout(sposPane, BoxLayout.LINE_AXIS));
	     sposPane.add(batGui_.getRadioPanel());
	     sposPane.add(posPane);
	     sposPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	     return sposPane;
	}
	
	
}
