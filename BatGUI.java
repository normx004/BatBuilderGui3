package batboy;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.*;
import java.util.*;
import java.util.logging.Level;
import java.lang.Thread.*;

import javax.swing.*;

public class BatGUI {
	
	 protected JFileChooser videoFileChooser_ = null;
	 public    void         setVideoFileChooser(JFileChooser j) { videoFileChooser_ = j;}
	 public    JFileChooser getVideoFileChooser() { return videoFileChooser_;}
	 protected String backGroundDirectory = null;
	 protected String  backGroundDirectoryAlias = null;
	 protected Integer backgroundImageInterval = 5000; // 5 sec default...
	 protected boolean useHttpServer = false;
	   
	 protected static boolean DEBUG = false;
	   
	   public static void setDebug(boolean t) {
	   	DEBUG=t;
	   }
	   
	   protected JTextField myTextField = null;

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
	         // background directory and alias
	         String backGrounds = System.getProperty("BrowserBackgroundDirectory");
	         if (backGrounds == null){
	        	 backGrounds = new String("");
	         }
	         out ("Found background directory in props: "+backGrounds);
	         setBackGroundDirectory(backGrounds);
	         String backGroundsAlias = System.getProperty("BrowserBackgroundDirectoryAlias");
	         if (backGroundsAlias == null){
	        	 backGroundsAlias = new String("");
	         }
	         out ("Found background alias directory in props: "+backGroundsAlias);
	         setBackGroundDirectoryAlias(backGroundsAlias);
	         
	         
	         
	         
	         String bgInt = System.getProperty("BackgroundImageInterval");
	         if (bgInt != null) {
	        	 Integer bgi = new Integer (bgInt);
	        	 this.setBackgroundImageInterval(bgi);
	         }
	         String uhs = System.getProperty("UseHttpServer");
	     	 if (uhs != null) {
	     		setUseHttpServer(true);
	     	 }
	     	
		 }
	//--------------variable file base------------------
	  private String fileBase = null;
	  public String getFileBase() {
			fileBase = System.getProperty("filebase");
			if (this.isUseHttpServer()) {
				fileBase = System.getProperty("filebaseBrowser");
			}
	    	out("Test: parent path is "+fileBase);
			return fileBase;
		}
	//--------------------sleeper------------------------------  
		public void sleep (int time) {
		  try {
		   Thread.sleep(time);
		  } catch (InterruptedException ie) {
		   System.out.println("ooops, interrupted sleep");
		  }
		 }
		//---------------------Focus Routines-----------------------------
		 public void focusGained(FocusEvent e) {
				//calcSec();
				out("(unused arbitrary method: ) Focus " + displayFocusMessage("gained" , e));
		  }	
		  
		  public void focusLost(FocusEvent e) {
		    	//calcSec();
		    	out("(unused arbitrary method: )Focus " + displayFocusMessage("lost" , e));
		    }
		  public String displayFocusMessage(String prefix, FocusEvent e) {
			StringBuffer stb = new StringBuffer();
			stb.append(prefix
			                  + (e.isTemporary() ? " (temporary):" : ":")
			                  +  e.getComponent().getClass().getName()
			                  + "; Opposite component: " 
			                  + (e.getOppositeComponent() != null ?
			                     e.getOppositeComponent().getClass().getName() : "null"));
 
			return new String(stb);
		  }
		  protected String[] myArgs = null;
		  protected void setArgs(String[] x) {
			  myArgs = x;
		  }
			protected File       batFile_ = null;
			protected File getBatFile() { return batFile_;}
			protected JFileChooser batFileSaveChooser_ = null;
			//------------------TEST BUTTON-----------------------------------------
			 public void testButtonPress(java.awt.event.ActionEvent e) { 
				 out("TestButtonPress in BatGUI");
				 //must be overridden!!! }
			 }
		     Button getTestButton() {
		    	 Button testButton = new Button("test");
			     //pane2.add(testButton);
		         testButton.addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
			         testButtonPress(evt);
			        }
		           });
		     return testButton;
			}
		     
		  //---------------BAT FILE HANDLING-------------------------------
		  public void  setBatFile(String path) {
			   String fstr = new String(path + ".xtestx.bat");
			   batFile_    = new File(fstr);
			   //out("in setBatFile in BatGUI, setting videoFileChooser 'set selected' file to "+fstr);
			   videoFileChooser_.setSelectedFile(batFile_);
			   //out("in batgui set bat file is " + fstr);
			   if ( batFileSaveChooser_ == null) {
				   batFileSaveChooser_ = new JFileChooser(batFile_);
				   //out("in batgui set bat file setting batFileSaveChooser file to "+fstr);
				   batFileSaveChooser_.setSelectedFile(batFile_);
			   }
		   }
		   public String getBatFileName() {
			   return batFile_.getPath();
		   }
		   public File getBatFileFile() {
			   return batFile_;
		   }
		
		  //-------------------------INIT-------------------------------------
		  Boolean gotProps=new Boolean(false);
		  public void init() {
			  //this method must be overridden!
			   
			     System.out.println("starting BatGUI constructor. if props file supplied, s/b first arg or -Dprops=file");
			     if ( myArgs.length > 0 && myArgs[0] != null) {
			    	 out("props expected in "+myArgs[0]);
			    	 getProps(myArgs[0]);
			    	 gotProps=new Boolean(true);
			     }
			     if ( myArgs.length == 0 ) {
			    	 String prop = System.getProperties().getProperty("props");
			    	 if (prop != null ) {
			    		 getProps(prop);
			    		 gotProps=new Boolean(true);
			    	 }
			     }
		  }
		  
		// ------------------------ACTION PERFORMED-------------------------
		  public void actionPerformed(ActionEvent evt) {
			  // right now this is a dummy; overridden in Slicer, at least....
			    out("text event; action command is " + evt.getActionCommand());
			    out((evt.getSource()).toString());
			    out("param string" + evt.paramString());
		        String text = myTextField.getText();
		        text = text + '.';
		        myTextField.setText(text);
		        myTextField.selectAll();

		        //Make sure the new text is visible, even if there
		        //was a selection in the text area.
		        myTextField.setCaretPosition(myTextField.getDocument().getLength());
		    }
		  
		  //--------VIDEO file display
		   protected JLabel whatVideoFile = null;
		   protected File   videoFile_    = null;
		   
		   public JLabel getWhatVideoFile() {
			return whatVideoFile;
		   }
	       public void setWhatVideoFile(JLabel whatVideoFile) {
			this.whatVideoFile = whatVideoFile;
		   }
	       public File getVideoFile() {
			   return videoFile_;
		   }
	       public  void setVideoFile(File f) {
			   videoFile_  = f;
			   String path = videoFile_.getPath();
			   out("in batgui video file is "+ path);
			   whatVideoFile.setText(path);
		   }
		 //-------------------init window startup---------------------------
         JFrame frame = null;
		 protected void initWindowStart(String title) {
			//JFrame frame = new JFrame(title);
		     ImageIcon ic = new ImageIcon("q:\\temp\\v24.jpg");
		     String bgdir=System.getProperty("bgimgDir");
		     String bgfil=System.getProperty("bgimgFile");
		    
		     String imgFileName = bgdir + "\\" + bgfil;
		     out("BatGUI screen imgFileName is "+imgFileName+", bgdir is "+bgdir+", bfil is "+bgfil);
		     File bgImage = new File(imgFileName);
		     
		     if (bgImage.exists()) {
		    	 out("BatGUI screen bg image file exists");
		    	 ic = new ImageIcon(imgFileName);
		     } else {
		    	 out("NO SUCH bgimg");
		     }
		     
		     frame = new BackgroundImageJFrame(ic);
		     frame.setTitle(title);
		     frame.setSize(900, 800);		     
		     frame.setBackground(Color.CYAN);
		    
		     // Add a listener for the close event
		     frame.addWindowListener(new WindowAdapter() {
		         public void windowClosing(WindowEvent evt) {
		             Frame frame = (Frame)evt.getSource();
		    
		             // Hide the frame
		             frame.setVisible(false);
		    
		             // If the frame is no longer needed, call dispose
		             frame.dispose();
		         }
		     });
		     // Add a listener for the close event
		     frame.addWindowListener(new WindowAdapter() {
		         public void windowClosing(WindowEvent evt) {
		             // Exit the application
		             System.exit(0);
		         }
		     });
		     
		  
		     // is there a resize event? i bet there is!
		     frame.addWindowListener(new WindowAdapter() {
		         public void windowResize(WindowEvent evt) {
		        	 int xsize = 0;
		        	 int ysize = 0; 
		        	 out("window resize evt: " + evt.toString());
		        	 out("window size is x=" + xsize + ", ysize="+ysize);
		         }
		     });
		     MyComponentListener lister = new MyComponentListener();
		     frame.addComponentListener(lister);
	         // ----try to put an image in the background--------
		     /*
		     BufferedImage duke = null;
		     try {
		         duke = ImageIO.read(new File("c:\\tmp\\v24.jpg"));
		     } catch (IOException e) {
		     }
		     
		     
		     BackgroundPanel bkpanel =
		    		    new BackgroundPanel(duke, BackgroundPanel.ACTUAL, 1.0f, 0.5f);
		    		GradientPaint paint =
		    		    new GradientPaint(0, 0, Color.BLUE, 600, 0, Color.RED);
		    		bkpanel.setPaint(paint);
		     */		
		     // -------------END of trying to put an image in the background------------------
		     // Create a container 
		     // with a flow layout, it arranges its children horizontally
		     
		     // A container can also be created with a other layouts
		     // a GridBag tries to arrange things in a 'gridlike' layout
		     // where components move around depending on how the window is sized
		     
		     JPanel pane1 = new JPanel();
		     //pane1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		     pane1 = new JPanel(new GridBagLayout());
		     
		    
		 }
		
		//--------------------MAIN------------------------------------- 
		//--------------------MAIN------------------------------------- 
		//--------------------MAIN------------------------------------- 
		//--------------------MAIN------------------------------------- 
		 public static void main(String[] args) {
		  // start'em up
		  BatGUISlicer.setDebug(true);
		  BatGUIHtml.setDebug(true);
		  
		  System.out.println("java -jar <the jar> <propsfilepath> <slicer|html>");
		
		  BatGUIFactory f = new BatGUIFactory();
		  BatGUI sc = f.getBatGUI(args);
		  sc.init();
		  sc.getProps(args[0]);
		  sc.sleep(10000);
	}
		public JFrame getFrame() {
			return frame;
		}
		public void setFrame(JFrame frame) {
			this.frame = frame;
		}
		public String getBackGroundDirectory() {
			return backGroundDirectory;
		}
		public void setBackGroundDirectory(String backGroundDirectory) {
			this.backGroundDirectory = backGroundDirectory;
		}
		public Integer getBackgroundImageInterval() {
			return backgroundImageInterval;
		}
		public void setBackgroundImageInterval(Integer backgroundImageInterval) {
			this.backgroundImageInterval = backgroundImageInterval;
		}
		public boolean isUseHttpServer() {
			return useHttpServer;
		}
		public void setUseHttpServer(boolean useHttpServer) {
			this.useHttpServer = useHttpServer;
		}
		public String getBackGroundDirectoryAlias() {
			return backGroundDirectoryAlias;
		}
		public void setBackGroundDirectoryAlias(String backGroundDirectoryAlias) {
			this.backGroundDirectoryAlias = backGroundDirectoryAlias;
		}
}

