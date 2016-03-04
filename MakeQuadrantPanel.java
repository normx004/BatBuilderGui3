package batboy;

import javax.swing.JPanel;

import javax.swing.*;
import java.awt.*;

public class MakeQuadrantPanel extends MakeArbitraryPanel {
    
	private String[] buttLabels = new String[4];
	private String   borderTitle = new String("Screen Position");
	private BatGUI  bg = null;
	private GenericButtonPanelFactory grp = null;
	private JPanel p = null;
	
	public MakeQuadrantPanel(BatGUI b) {
	    grp = new GenericButtonPanelFactory(this);
		bg = b;
		buttLabels[0] = new String("upper  left");
		buttLabels[1] = new String("upper right");
		buttLabels[2] = new String("lower  left");
		buttLabels[3] = new String("lower right");
	}
	
	public JPanel getButtons() {
		p = grp.getButtons(borderTitle, buttLabels) ;
		grp.addAction(p);
		return p;
		
	}
	/*
	qaudrant1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
         quadrantButtonPress <use ackshun here> (evt);
       }});
       */
	 public void out(String s) { System.out.println(s);}
	 public void ackshun(java.awt.event.ActionEvent vent) {
	      //  if (DEBUG) {
	      //  	 out("-------------------------quadrantButtonpress-------------------------------------");
	      //  	 out("--------------------------------------------------------------");
	      //  	 out("quadrant radio Button: " +vent.toString());
	      //     out("Event value : " + vent.getActionCommand());
	      //     out("--------------------------------------------------------------");
	      //     out("--------------------------------------------------------------");
	      //  }
	    int qval = -1;
	    Object[] jbut = p.getComponents();
	    int i = 0;
	    while (i < jbut.length) {
	    		JRadioButton b = (JRadioButton)jbut[i]; 
	    		if (b.isSelected()) {
	    			break;
	    		}
	    		i += 1;
        }
	    qval = i+1;
        out("Quadrant selected is " + qval);
        int xlocs[] = new int[4];
        int ylocs[] = new int[4];
        xlocs[0]=6; xlocs[1]=bg.getXoffset();
        xlocs[2]=6;xlocs[3]=bg.getXoffset();
        ylocs[0]=6; ylocs[1]=6;
        ylocs[2]=bg.getYoffset();
        ylocs[3]=bg.getYoffset();
        int xloc = xlocs[qval-1];
        int yloc = ylocs[qval-1];
        bg.setVideoXval ( new Integer(xloc));
        bg.setVideoYval(  new Integer(yloc));
        bg.setXvalText(bg.getVideoXval().toString());
        bg.setYvalText(bg.getVideoYval().toString());
    }
}
