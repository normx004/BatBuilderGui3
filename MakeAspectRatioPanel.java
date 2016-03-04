package batboy;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

public class MakeAspectRatioPanel extends MakeArbitraryPanel {
    
	private String[] buttLabels = new String[2];
	private String   borderTitle = new String("AspectRatio");
	private BatGUISlicer  bg = null;
	private GenericButtonPanelFactory grp = null;
	private JPanel p = null;
	
	public MakeAspectRatioPanel(BatGUISlicer b) {
	    grp = new GenericButtonPanelFactory(this);
		bg = b;
		buttLabels[0] = new String("auto");
		buttLabels[1] = new String("16:9");	
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
        out("Aspect ration selected is " + qval);
        if (qval == 2) {
        	bg.setAspect_("16:9");
        } else {
        	if (qval == 1) {
        		bg.setAspect_("");
        	}
        }
    }
}
