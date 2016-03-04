package batboy;

import javax.swing.*;
import java.awt.*;
//
//
//
// build "serial execution or parallel execution" panel - but do i really need this anymore? 
//
// maybe i'll make it the speed panel!
//
public class BuildSPPanel {
    private BatGUI batGui_ = null;
	public BuildSPPanel (BatGUI bg) {
		batGui_ = bg;
	}
	private JRadioButton spbut1 = new JRadioButton("slow"  , false);
	private JRadioButton spbut2 = new JRadioButton("medium"  , false);
	private JRadioButton spbut3 = new JRadioButton("regular"  , true);
	
    public JPanel	getPanel() {
    	 
    	
    	 
    	 spbut1.addActionListener(new java.awt.event.ActionListener() {
 	        public void actionPerformed(java.awt.event.ActionEvent evt) {
 	         spButtonPress(evt);
 	        }});
 	     spbut2.addActionListener(new java.awt.event.ActionListener() {
 	        public void actionPerformed(java.awt.event.ActionEvent evt) {
 	         spButtonPress(evt);
 	        }});
     	 spbut3.addActionListener(new java.awt.event.ActionListener() {
 	        public void actionPerformed(java.awt.event.ActionEvent evt) {
 	         spButtonPress(evt);
 	        }});
 	     
    	  
	     batGui_.setSpeedButt1( spbut1);
	     batGui_.setSpeedButt2( spbut2);
	     batGui_.setSpeedButt3( spbut3);
	     
	     ButtonGroup bgroupsp = new ButtonGroup();
	     bgroupsp.add(batGui_.getSpeedButt1());
	     bgroupsp.add(batGui_.getSpeedButt2());
	     bgroupsp.add(batGui_.getSpeedButt3());
	     
	     JPanel radioPanelsp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	     radioPanelsp.add(batGui_.getSpeedButt1());
	     radioPanelsp.add(batGui_.getSpeedButt2());
	     radioPanelsp.add(batGui_.getSpeedButt3());
	        
	     
	     radioPanelsp.setBorder(BorderFactory.createTitledBorder(
	                BorderFactory.createEtchedBorder(), "speed?"));
	     return radioPanelsp;

    }
    private static boolean DEBUG=true;
    private void out (String s) {
    	System.out.println(s);
    }
    private void spButtonPress(java.awt.event.ActionEvent vent) {
	        if (DEBUG) {
	       	  out("-------------------------spButtonpress-------------------------------------");
	       	  out("--------------------------------------------------------------");
	          out("speed radio Button: " +vent.toString());
	           out("Event value : " + vent.getActionCommand());
	           out("--------------------------------------------------------------");
	           out("--------------------------------------------------------------");
	        }
	     int qval = -1;
	     if (spbut1.isSelected()) {  qval = 0;}
         if (spbut2.isSelected()) {  qval = 1;}
         if (spbut3.isSelected()) {  qval = 2;}
         out("sp selected is " + qval);
         String[] sar = new String[3];
         sar[0] = "0.5";
         sar[1] = ".74";
         sar[2] = "1.0";
         batGui_.setSpeed(sar[qval]);
     }
    
    
	
}
