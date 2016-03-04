package batboy;

import javax.swing.*;

import java.awt.*;

public class WindowSizePane {
	BatGUI batGui_ = null;
	
	public WindowSizePane ( BatGUI bg) {
		batGui_ = bg;
	}
	
	public JPanel getPanel() {
		
		 JPanel sizePane = new JPanel();
	     sizePane.setLayout(new BoxLayout(sizePane, BoxLayout.PAGE_AXIS));
	     batGui_.setxSizeTextField( new JTextField("0"));
	     batGui_.setySizeTextField( new JTextField("0"));
	     sizePane.add(new JLabel("Xsize(width)"));
	     sizePane.add(batGui_.getxSizeTextField());
	     sizePane.add(new JLabel("Ysize(height)"));
	     sizePane.add(batGui_.getySizeTextField());
	     
	     /* combine radio upper/lower/left/right and position display into one panel
	      * 
	      *   public  JTextField getxSizeTextField_() {
			return xSize_;
		}
		public void setxSizeTextField_(JTextField xSize) {
			xSize_ = xSize;
		}
		public JTextField getySizeTextField_() {
			return ySize_;
		}
		public void setySizeTextField_(JTextField ySize) {
			ySize_ = ySize;
		}
	      * 
	     JPanel wszPane = new JPanel();
	     wszPane.setLayout(new BoxLayout(wszPane, BoxLayout.LINE_AXIS));
	     wszPane.add(batGui_.getRadioPanel());
	     wszPane.add(posPane);
	     wszPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	     return wszPane;
	     */
	     return sizePane;
	}
	
	
}
