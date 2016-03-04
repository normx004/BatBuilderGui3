package batboy;

import java.awt.event.*;
import java.awt.event.ComponentEvent;

public class MyComponentListener implements ComponentListener {
	   private void out(String s) {
		   System.out.println(s);
	   }
	   public void componentHidden(ComponentEvent e) {
	         out(e.getComponent().getClass().getName() + " --- Hidden");
	     }

	     public void componentMoved(ComponentEvent e) {
	         out(e.getComponent().getClass().getName() + " --- Moved");
	     }

	     public void componentResized(ComponentEvent e) {
	         out(e.getComponent().getClass().getName() + " --- Resized ");   
	         out(e.toString());
	     }

	     public void componentShown(ComponentEvent e) {
	         out(e.getComponent().getClass().getName() + " --- Shown");

	     }
	     
	    	
	     
}
