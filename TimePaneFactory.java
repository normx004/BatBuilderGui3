package batboy;

import javax.swing.*;
import  java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class TimePaneFactory implements FocusListener {
	
	private BatGUI batGui_ = null;
	
	public TimePaneFactory (BatGUI batGui) {
		batGui_ = batGui;
	}
	
	  public void focusGained(FocusEvent e) {
		    String cname = null;
		    JTextField jtf = (JTextField)e.getComponent();
		    if (jtf == null) {
		    	jtf = (JTextField)e.getOppositeComponent();
		    }
		    if ( jtf == null ) {
		    	cname=new String("Unknown");
		    } else {
		    	cname = jtf.getName();
		    	if ( cname == null) {
		    	cname=new String("Unknown");
		    	}
		    }
		    //out("gained focus compnent name: " + jtf.getName());
	    	batGui_.calcSec(jtf.getName());
			//out("Focus " + batGui_.displayFocusMessage("gained" , e));
			//out ("-----------------");
	  }	
	
	  public void focusLost(FocusEvent e) {
		    String cname = null;
		    JTextField jtf = (JTextField)e.getComponent();
		   		    
		    if (jtf == null) {
		    	jtf = (JTextField)e.getOppositeComponent();
		    }
		    if ( jtf == null ) {
		    	cname=new String("Unknown");
		    } else {
		    	cname = jtf.getName();
		    	if ( cname == null) {
		    	cname=new String("Unknown");
		    	}
		    }
		    
		   // out("lost focus compnent name: " + cname);
	    	batGui_.calcSec(cname);
	    	//out("Focus " + batGui_.displayFocusMessage("lost" , e));
	    	//out ("-----------------");
	    }
	
	 private void out(String s) { System.out.println(s);}
	
	   //-----------------TEXT EVENT--------------------------------
	 private void textEvent(java.awt.event.ActionEvent vent) {
	        //if (DEBUG) {
	        	 out("---------------------textEvent-----------------------------------------");
	        	 out("--------------------------------------------------------------");
	        	 out("embedded text event: " + vent.toString());
	             out("Event value : " + vent.getActionCommand());
	             out("--------------------------------------------------------------");
	             out("--------------------------------------------------------------");
	        //}
	   }
	//--------------------Create and return TIME PANE---------------------------------
	JPanel getTimePane() {
			 JTextField   startM = new JTextField (10);
			 JTextField   startS = new JTextField (10);
			 JTextField   endingM = new JTextField (10);
			 JTextField   endingS = new JTextField (10);
		     
			 startM.setName("startM");
			 startS.setName("startS");
			 endingM.setName("endM");
			 endingM.setName("endSM");
			 
			 
			 JPanel timePane1 = new JPanel();
		     timePane1.setLayout(new BoxLayout(timePane1, BoxLayout.LINE_AXIS));
		     JLabel  startMin = new JLabel("start min");
		     timePane1.add(startMin);
		     batGui_.setStartMin( startM);
		     batGui_.getStartMin().addFocusListener(this);

		     timePane1.add(batGui_.getStartMin());
		     //startMin_.addActionListener(this);
		     batGui_.getStartMin().addActionListener(new java.awt.event.ActionListener() {
		        public void actionPerformed(java.awt.event.ActionEvent evt) {
		         textEvent(evt);
		        }
		       });
		     
		     JLabel startSec = new JLabel("start sec");
		     timePane1.add(startSec);
		     batGui_.setStartSec( startS);
		     batGui_.getStartSec().addFocusListener(this);
		     timePane1.add(batGui_.getStartSec());
		     batGui_.getStartSec().addActionListener(new java.awt.event.ActionListener() {
		        public void actionPerformed(java.awt.event.ActionEvent evt) {
		         textEvent(evt);
		        }
		       });
		     
		JPanel timePane2 = new JPanel();
		timePane2.setLayout(new BoxLayout(timePane2, BoxLayout.LINE_AXIS));
		JLabel endMin = new JLabel("end min  ");
		timePane2.add(endMin);
		batGui_.setEndMin(endingM);
		batGui_.getEndMin().addFocusListener(this);
    
		timePane2.add(batGui_.getEndMin());
		batGui_.getEndMin().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				textEvent(evt);
			}
		});
    
    
		JLabel endSec = new JLabel("end sec  ");
		timePane2.add(endSec);
		batGui_.setEndSec(endingS);
		batGui_.getEndSec().addFocusListener(this);
		timePane2.add(batGui_.getEndSec());
		//endSec_.addActionListener(this);
		batGui_.getEndSec().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				textEvent(evt);
			}
		});
		timePane1.setBorder(BorderFactory.createMatteBorder(3,3,3,3,Color.PINK));
		timePane2.setBorder(BorderFactory.createMatteBorder(3,3,3,3,Color.PINK));
    //	pane1.add(timePane);
    // 	A container can also be created with a specific layout
    //	JPanel pane3x = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		timePane1.add(new JLabel("     Start Total Seconds  "));
		batGui_.setStartTotSec(new JTextField(5));
		batGui_.getStartTotSec().setEditable(false);
		timePane1.add(batGui_.getStartTotSec());
		timePane2.add(new JLabel("     End   Total Seconds  "));
		batGui_.setEndTotSec( new JTextField(5));
		batGui_.getEndTotSec().setEditable(false);
		timePane2.add(batGui_.getEndTotSec());
		
    //	now put the two time panes together
		JPanel timePane = new JPanel();
		timePane.setLayout(new BoxLayout(timePane, BoxLayout.PAGE_AXIS));
		timePane.add(timePane1);
		timePane.add(timePane2);
		return timePane;
	}
}
