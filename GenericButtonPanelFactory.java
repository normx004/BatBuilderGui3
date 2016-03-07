package batboy;

import javax.swing.*;
import java.awt.*;

public class GenericButtonPanelFactory {
	//
	///
	// defines "screen position" button panel, and handles button pushes
	//
	//
	//
	private   JRadioButton[] buttons   = null;
	
	//private   BatGUI batGui_;
	private MakeArbitraryPanel mapa = null;
	
	GenericButtonPanelFactory (MakeArbitraryPanel imap/*BatGUI batgui*/) {
		//batGui_ = batgui;
		mapa = imap;
	}
	//---------------getButtons-------(radio)--------------------
	public JPanel getButtons(String borderTitle, String[] labels) {
       int nButtons = labels.length;
	
	   buttons = new JRadioButton[nButtons];
	   int i=0;
	   while (i < nButtons) {
              buttons[i]   = new JRadioButton( labels[i] , true);
              i += 1;
	   }
 
	   ButtonGroup bgroup = new ButtonGroup();
       i = 0; 
       while (i < nButtons) {
        	bgroup.add(buttons[i]);
            i += 1;
       }
    
       JPanel radioPanel = new JPanel();
       radioPanel.setLayout(new GridLayout(1, nButtons));
       i = 0; 
       while (i < nButtons) {
         	radioPanel.add(buttons[i]);
            i += 1;
       }
    
    radioPanel.setBorder(BorderFactory.createTitledBorder(
               BorderFactory.createEtchedBorder(), borderTitle));
    
    return radioPanel;
   }
	
     //-----------------------------out--------------------------	
	 private void out(String s) {
		 System.out.println(s);
	 }
	 //---------------AddAction-------------------------------
	 public void addAction(JPanel p) {
		   int i = 0;
		   int nButtons = p.getComponentCount();
		   while (i < nButtons) {
		    	((JRadioButton)(p.getComponent(i))).addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
				         mapa.ackshun(evt);
				        } // end block of 'actionPerformed' code
			        } // end 'new java.awt.event.ActionListener()' 
		    	); // end   'addActionListener' argument def
		    	i += 1;
		   } // end 'while'
	 }
	 /******************************************************************************
	 private void quadrantButtonPress(java.awt.event.ActionEvent vent) {
	      //  if (DEBUG) {
	      //  	 out("-------------------------quadrantButtonpress-------------------------------------");
	      //  	 out("--------------------------------------------------------------");
	      //  	 out("quadrant radio Button: " +vent.toString());
	      //     out("Event value : " + vent.getActionCommand());
	      //     out("--------------------------------------------------------------");
	      //     out("--------------------------------------------------------------");
	      //  }
	     int qval = -1;
	     if (qaudrant1.isSelected()) {  qval = 1;}
         if (qaudrant2.isSelected()) {  qval = 2;}
         if (qaudrant3.isSelected()) {  qval = 3;}
         if (qaudrant4.isSelected()) {  qval = 4;}
         
         out("Quadrant selected is " + qval);
         int xlocs[] = new int[4];
         int ylocs[] = new int[4];
         xlocs[0]=6; xlocs[1]=batGui_.getXoffset();
         xlocs[2]=6;xlocs[3]=batGui_.getXoffset();
         ylocs[0]=6; ylocs[1]=6;
         ylocs[2]=batGui_.getYoffset();
         ylocs[3]=batGui_.getYoffset();
         int xloc = xlocs[qval-1];
         int yloc = ylocs[qval-1];
         batGui_.setVideoXval ( new Integer(xloc));
         batGui_.setVideoYval(  new Integer(yloc));
         batGui_.setXvalText(batGui_.getVideoXval().toString());
         batGui_.setYvalText(batGui_.getVideoYval().toString());
	   }
	   ****************************************************************************/
}
