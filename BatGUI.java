package batboy;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.*;
import java.util.*;
import java.lang.Thread.*;


public class BatGUI {
	 protected static boolean DEBUG = false;
	   
	   public static void setDebug(boolean t) {
	   	DEBUG=t;
	   }

	public BatGUI() {
		// TODO Auto-generated constructor stub
	}
	 //------------------------OUT------------------------------------
	  protected void out(String s) {
		  System.out.println(s);
	  }
	  //--------------------GetPROPS------------------------------------
	  protected void getProps(String ps){
			 Properties p = new Properties();
	    	 try {
	    	      p.load(new FileInputStream(ps));
	    	     } catch (IOException e) {
	    	    	 System.err.println("Failed to read properties file " + ps + ", must quit!");
	    	         System.exit(1);
	    	     }
	         out("Putting all props to system props");
	         Properties sysProps = System.getProperties();
	         sysProps.putAll(p);
		 }
		
	//--------------------sleeper------------------------------  
		public void sleep (int time) {
		  try {
		   Thread.sleep(time);
		  } catch (InterruptedException ie) {
		   System.out.println("ooops, interrupted sleep");
		  }
		 }
		
		
		
		//--------------------MAIN------------------------------------- 
		//--------------------MAIN------------------------------------- 
		//--------------------MAIN------------------------------------- 
		//--------------------MAIN------------------------------------- 
		 public static void main(String[] args) {
		  // start'em up
		  BatGUISlicer.setDebug(true);
		  BatGUIHtml.setDebug(true);
		
		  //String       fn = new String("");
		  //if ( args.length > 0) {
		  // fn = args[0];
		  //}
		  BatGUIFactory f = new BatGUIFactory();
		  BatGUI sc = f.getBatGUI(args);
		  sc.getProps(args[0]);
		  sc.sleep(10000);
	}
}

