package batboy;

	import java.awt.*;
import java.awt.List;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.*;

import javax.imageio.*;

import java.awt.image.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.util.*;
import java.lang.*;
import java.util.logging.*;

//import org.apache.log4j.*;

	public class BatGUI implements ActionListener, FocusListener  {
		
	   private Logger logr = Logger.getLogger("BatGUILogger");
		
	   private static boolean DEBUG = false;
	   
	   public static void setDebug(boolean t) {
	   	DEBUG=t;
	   }
	   //  current working slice object
	   private CmdLine    currentCmd_ = null;
	   	   
	   //multi-cmd line object that knows what file to write and how to write it
	   private CmdFile    cmdFile_ = null;
	   
	   private int        xoffset=550;
	   public void        setXoffset(int a) { xoffset = a;}
	   public int         getXoffset() { return xoffset;}
	   private int        yoffset=550;
	   public void        setYoffset(int a) { yoffset = a;}
	   public int         getYoffset() { return yoffset;}
	   
	   private int        totalTimeAllSegments = 0;
	   private void       addToTotalTimeElapsed(int s) {
		   totalTimeAllSegments += s;
		   Integer iTot = new Integer(totalTimeAllSegments);
		   setTotalTimeText(iTot.toString());
	   }
	   
	   private JTextField totalTimeText = new JTextField("0",6);
	   private void setTotalTimeText(String s) {
		   totalTimeText.setText(s);
	   }
	   
	   private  String      speed_ = new String("1.0");
	   
	   public String getSpeed() {
		return speed_;
	   }
	   public void setSpeed(String speed) {
		speed_ = speed;
	  }
	private File       batFile_ = null;
	   //---------------------speed buttons (new!)-----------------------------
	   JRadioButton speedButt1     = null;
	   public void setSpeedButt1(JRadioButton serial) {
		this.speedButt1 = serial;
	   }
	   public JRadioButton getSpeedButt1() {
		return this.speedButt1;
	   }
	   JRadioButton speedButt2   = null;
	   public void setSpeedButt2(JRadioButton parallel) {
		this.speedButt2 = parallel;
	   }
	   public JRadioButton getSpeedButt2() {
		return this.speedButt2;
	   }
	   JRadioButton speedButt3   = null;
	   public void setSpeedButt3(JRadioButton parallel) {
		this.speedButt3 = parallel;
	   }
	   public JRadioButton getSpeedButt3() {
		return this.speedButt3;
	   }
	   //------------------------------------
	   private String aspect_ = new String("");
	   
	   public String getAspect_() {
		return aspect_;
	   }
	   public void setAspect_(String aspect) {
		aspect_ = aspect;
	   }
	//------------------------------------
	   private JTextField xSize_ = null;
	   private JTextField ySize_ = null;
	   public  JTextField getxSizeTextField() {
			return xSize_;
		}
		public void setxSizeTextField(JTextField xSize) {
			xSize_ = xSize;
		}
		public JTextField getySizeTextField() {
			return ySize_;
		}
		public void setySizeTextField(JTextField ySize) {
			ySize_ = ySize;
		}
	   //---------------------------------------------------
	   private JTextField startMin_ = null;

	   private JTextField startSec_ = null;
	    
	   private JTextField endMin_ = null;
	   private JTextField endSec_ = null;

	   private JTextField starttotsec_ = null;
	   private JTextField endtotsec_ = null;
	   
	   public void setStartMin(JTextField j)    { startMin_ = j;}
	   public void setStartSec(JTextField j)    { startSec_ = j;}
	   public void setEndMin(JTextField j)      { endMin_ = j;}
	   public void setEndSec(JTextField j)      { endSec_ = j;}
	   public void setStartTotSec(JTextField j) { starttotsec_ = j;}
	   public void setEndTotSec(JTextField j)   { endtotsec_ = j;}
	   
	   public JTextField getStartMin( )    { return startMin_;}
	   public JTextField getStartSec( )    { return startSec_;}
	   public JTextField getEndMin( )      { return endMin_;}
	   public JTextField getEndSec( )      { return endSec_;}
	   public JTextField getStartTotSec( ) { return starttotsec_;}
	   public JTextField getEndTotSec( )   { return endtotsec_;}
		
	   //------------------------------------------------------------
	   
	   private JFileChooser videoFileChooser_ = null;
	   private JFileChooser fc2_ = null;
	   private JFileChooser vlcFileChooser_ = null;
	   private JFileChooser batFileSaveChooser_ = null;
	   
	   public JFileChooser getVlcFileChooser() { return vlcFileChooser_; }
	   public void         setVlcFileChooser(JFileChooser j) { vlcFileChooser_ = j;}
	   public String       getVlcFileName() { return vlcFileName_;}
	   
	   public void         setVideoFileChooser(JFileChooser j) { videoFileChooser_ = j;}
	   public JFileChooser getVideoFileChooser() { return videoFileChooser_;}
	   
	   TableModel dataModel_ =  null; //new TableModel();
       JTable table_         = null; //new JTable(dataModel);
	     

	   private File videoFile_ = null;
	   private String VlcFile_ = null;
	   
	   private final static String NOEMBED  = "--no-embedded-video";
	   private final static String VOLUME   = "--volume=30";
	   private final static String PLAYEXIT = "--play-and-exit";
	   private final static String XPREFIX  = "--video-x=";
	   private final static String YPREFIX  = "--video-y=";
	   
	   JRadioButton qaudrant1   = null;
	   JRadioButton qaudrant2   = null;
	   JRadioButton qaudrant3   = null;
	   JRadioButton qaudrant4   = null;
	   int          qval = -1;
       //---video position
	   Integer videoXval = new Integer(5);
	   public  Integer getVideoXval() {return videoXval;}
	   public  void    setVideoXval(Integer a) { videoXval = a;}
	   Integer videoYval = new Integer(5);
	   public  Integer getVideoYval() {return videoYval;}
	   public  void    setVideoYval(Integer a) { videoYval = a;}
	   
	   private      JPanel radioPanel = null;
	   public       JPanel getRadioPanel() {
		return      radioPanel;
	   }
	   private      JPanel aspectPanel = null;
	   public       JPanel getAspectPanel() {
			return aspectPanel;
	   }
	   // setters and getters for x and y position textfields
	   private      JTextField xval_ = null;
	   public JTextField getXvalTextField() {
		return xval_;
	   }
	   public void setXvalTextField(JTextField xval) {
		xval_ = xval;
	   }
	   public  void setXvalText(String s) {
		   out("setting value " + s + " in xpos text string '" + xval_.getName()+"''");
		   xval_.setText(s);
	   }
	   private      JTextField yval_ = null;
	   public JTextField getYvalTextField() {
		return yval_;
 	   }
	   public void setYvalTextField(JTextField yval) {
		yval_ = yval;
	   }
	   public  void setYvalText(String s) { yval_.setText(s);}
	   //---------------------------------------------------------
	   //String vlcname = new String( "C:\\Program Files (x86)\\VideoLAN\\VLC1.0.5\\vlc.exe")
	   String vlcname = new String("C:\\Program Files (x86)\\videolan\\vlc\\vlc.exe");
	   
	   private JLabel   vlcFileLabel_ = new JLabel(vlcname);                             
	   private String   vlcFileName_  = new String(vlcname); 
	   //--------VIDEO file display
	   private JLabel whatVideoFile = null;
	   
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
		   setBatFile(path);
		   whatVideoFile.setText(path);
		   if (cmdFile_ == null) {
		       cmdFile_ = new CmdFile(path);
		   }
		   //out("in 'set video file' setting cmdfile batfile path to "+path);
		   cmdFile_.setBatFile(path);
	   }
       //-------------------------------------------------------------------\
      
       public void        setVlcFileLabel(JLabel j) { vlcFileLabel_ = j;}
	   public      JLabel getVlcFileLabel() { return vlcFileLabel_;}
	   
	   public CmdFile getCmdFile() {
		   return cmdFile_;
	   }
	   
	   public Boolean getSerial() {
		   Boolean yorn = new Boolean(false);
		   if ( speedButt1.isSelected()) {
			   out("in getSerial(batgui) serial is selected");
			   yorn = new Boolean(true);
		   } else {
			   out("in getSerial(batgui) serial is NOT selected");
		   }
		   return yorn;
	   }
	   
	   
	
	   public void setVlcFile (File f){
		   VlcFile_ = f.getPath();
		   out ("in batgui VLC file is "+f.getPath());
		   String fstr = new String(f.getPath());
		   vlcFileLabel_.setText(fstr);
	   }
	  
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
	
	   
	   //-----------------BUTTON PRESS--------------------------------
	   private void buttonPress(java.awt.event.ActionEvent vent) {
	        if (DEBUG) {
	        	 out("----------------------buttonPress----------------------------------------");
	        	 out("--------------------------------------------------------------");
	        	 out("button press: " + vent.toString());
	             out("Event value : " + vent.getActionCommand());
	             out("--------------------------------------------------------------");
	             out("--------------------------------------------------------------");
	        }
	   }
	   private void batFileSaveButtonPress(java.awt.event.ActionEvent vent) {
	        if (DEBUG) {
	        	 out("---------------------batFileSaveButtonPress-----------------------------------------");
	        	 out("--------------------------------------------------------------");
	        	 out("batFileSaveButtonPress: " +vent.toString());
	             out("Event value : " + vent.getActionCommand());
	             //cmdFile_.setSpeed(this.getSpeed());
	             out(cmdFile_.toString());
	             out("invokes SaveFileAction");
	             out("--------------------------------------------------------------");
	             out("--------------------------------------------------------------");
	        }
	        // has no effect: cmdFile_.setSerial(serial_);
	   }
	   private void addListButtonPress(java.awt.event.ActionEvent vent) {
	        if (DEBUG) {
	        	 out("-------------------addListButtonPress-------------------------------------------");
	        	 out("--------------------------------------------------------------");
	        	 out("addListButton: " +vent.toString());
	             out("Event value : " + vent.getActionCommand());
	             out("--------------------------------------------------------------");
	             out("--------------------------------------------------------------");
	        }
	        if (cmdFile_ == null) {
	            	 cmdFile_ = new CmdFile();
	        }
	      	 prepCmdFile(cmdFile_);
	         CmdLine c = makeCmdLine();
	         //cmdFile_.setSerial(serial_);
	         //-------GOTTA SET SPEED HERE------
	        // cmdFile_.setSpeed(speed_);
	         cmdFile_.addCmd(c);
	         // add new row to display table --
	         int offset = cmdFile_.getVectorLength() - 1;
	         
	         Boolean sval = this.getSerial();
	         //out("inserting into table, serial value is " + serial_.toString() + " but getSerial returns "+sval.toString());
	         table_.setValueAt(sval,offset, 0);
	         
	         table_.setValueAt(c.getX(),    offset, 1);
	         table_.setValueAt(c.getY(),    offset, 2);
	         table_.setValueAt(c.getStart(),offset, 3);
	         table_.setValueAt(c.getEnd(),  offset, 4);
	         String tvf = c.getTargetVideoFile();
	         int    last = tvf.lastIndexOf("\\");
	         //out ("inserting into table, last index of backslash is "+last);
	         String  vf = tvf.substring(last+1, tvf.length() );
	         //table_.setValueAt(c.getTargetVideoFile(), offset, 5);
	         table_.setValueAt(vf, offset, 7);
	         table_.setValueAt(c.getXsize(),offset,5);
	         table_.setValueAt(c.getYsize(),offset,6);
	         
	         // update on-screen total of elapsed time
	         Integer iEnd = new Integer(c.getEnd());
	         Integer iStart = new Integer(c.getStart());
	         int segmentSec = iEnd.intValue() - iStart.intValue();
	         out("total time this segment is "+segmentSec);
	         this.addToTotalTimeElapsed(segmentSec);
	   }
	  
	   // ------------------------CLEAR BUTTON------------------------------------
	   private void clearButtonPress(java.awt.event.ActionEvent vent) {
		   
		
		   
	        if (DEBUG) {
	        	 out("-----------------------clearButtonPress---------------------------------------");
	        	 out("--------------------------------------------------------------");
	        	 out("clearButton: " +vent.toString());
	             out("Event value : " + vent.getActionCommand());
	             out("--------------------------------------------------------------");
	             out("--------------------------------------------------------------");
	        }
	     
	   }
	// ------------------------IncStartTime BUTTON------------------------------------
	   private void incStartButtonPress(java.awt.event.ActionEvent vent) {	
		   
	        //if (DEBUG) {
	        	 out("-----------------------incStartButtonPress---------------------------------------");
	        	 out("--------------------------------------------------------------");
	        	 out("clearButton: " +vent.toString());
	             out("Event value : " + vent.getActionCommand());
	             out("--------------------------------------------------------------");
	             out("--------------------------------------------------------------");
	        //}
	     JTextField start = this.getStartSec();
	     Integer sec = new Integer(start.getText());
	     out("get start button value its " + sec.toString());
	     int k = sec.intValue();
	     k = k+1;
	     sec = Integer.valueOf(k);
	     start.setText(sec.toString());
	     this.setStartSec(start);
	     this.calcSec("startS");
	   }
	   // ------------------------TEST BUTTON------------------------------------
	   private void testButtonPress(java.awt.event.ActionEvent vent) {
		   
		
		   
	        if (DEBUG) {
	        	 out("-----------------------testButtonPress---------------------------------------");
	        	 out("--------------------------------------------------------------");
	        	 out("testButton: " +vent.toString());
	             out("Event value : " + vent.getActionCommand());
	             out("--------------------------------------------------------------");
	             out("--------------------------------------------------------------");
	        }
	             
	             
	             out("start sec "+ starttotsec_.getText());
	             out("end   sec "+ endtotsec_.getText());
	             out("target file name " + videoFile_.getPath());
	             out("bat filename  "+ batFile_.getPath());
	             out("x "+xval_.getText()+", y "+yval_.getText());
	             out("x size: "+ xSize_.getText());
	             out("y size: "+ ySize_.getText());
	             if (speedButt1.isSelected()){
	            	 speed_ = new String(".5");
	             	 out("slow is selected");
	             }	else {
	            	 if (speedButt2.isSelected()){
		            	 speed_ = new String(".75");
		             	 out("medium is selected");
		             }	else {
		             speed_ = new String("1.0");
	            	 out("speed NOT selected or 'normal' was chosen");
		             }
	             }
	             out("aspect: "+this.getAspect_());
	             out("speed? "+speed_.toString());
	             //out("but will RUN parallel for this test of one file");
	             
	             //prepCmdFile();
	             CmdLine c   = makeCmdLine();
	             CmdFile cfx = new CmdFile();
	             prepCmdFile(cfx, new String(speed_));
	             cfx.addCmd(c);
	             // make a file in the videofile's directory
	             String vf = c.getTargetVideoFile();
	             int endIndex = vf.lastIndexOf('\\');
	             out("Last slash in "+vf+" is at "+endIndex);
	             String base = vf.substring(0, endIndex);
	             out("Base is " + base);
	             String tBat = new String(base+"\\testum.bat");
	             out("temp bat file is "+tBat);
	             // tell the cmd file about it
	             cfx.setBatFile(tBat);
	             // use same cmd file name each time for the test
	             Boolean overwrite = new Boolean(Boolean.TRUE);
	             cfx.test(overwrite);
	             if ( batFileSaveChooser_ != null) {
	            	 batFileSaveChooser_.rescanCurrentDirectory();
	             }
	   }
	   
	   //
	   //
	   //
	   // Make a cmd line from current object 
	   private CmdLine makeCmdLine() {
		         CmdLine c = new CmdLine();
	             c.setStart(starttotsec_.getText());
	             c.setEnd(endtotsec_.getText());
	             c.setX(xval_.getText());
	             c.setY(yval_.getText());
	             c.setTargetVideoFile(this.getVideoFile());
	             c.setXsize(xSize_.getText());
	             c.setYsize(ySize_.getText());
	             c.setSpeed(this.getSpeed());
	             c.setAspect(this.getAspect_());
	             
	             return c;
	   }
	   // initialize command file object
	   private void prepCmdFile(CmdFile cmf) {
		   out ("prepping cmd file");
		   //cmf.setSpeed(speed_);
           if ( VlcFile_ == null) {
        	 // Label is initialized with default value - this is probably a little sleazy
          	 VlcFile_ = new String(vlcFileLabel_.getText());
           }
           cmf.setVlcPathString(VlcFile_);
       } // initialize command file object
	   private void prepCmdFile(CmdFile cmf, String tf) {
		   out ("prepping cmd file");
		   //cmf.setSpeed(tf);
           if ( VlcFile_ == null) {
        	 // Label is initialized with default value - this is probably a little sleazy
          	 VlcFile_ = new String(vlcFileLabel_.getText());
           }
           cmf.setVlcPathString(VlcFile_);
       }
	   //-----------------TEXT EVENT--------------------------------
	   private void textEvent(java.awt.event.ActionEvent vent) {
	        if (DEBUG) {
	        	 out("---------------------textEvent-----------------------------------------");
	        	 out("--------------------------------------------------------------");
	        	 out("embedded text event: " + vent.toString());
	             out("Event value : " + vent.getActionCommand());
	             out("--------------------------------------------------------------");
	             out("--------------------------------------------------------------");
	        }
	   }
	   
		  // ------------------------ACTION PERFORMED-------------------------
		  public void actionPerformed(ActionEvent evt) {
			    out("text event; action command is " + evt.getActionCommand());
			    out((evt.getSource()).toString());
			    out("param string" + evt.paramString());
		        String text = startMin_.getText();
		        text = text + '.';
		        startMin_.setText(text);
		        startMin_.selectAll();

		        //Make sure the new text is visible, even if there
		        //was a selection in the text area.
		        startMin_.setCaretPosition(startMin_.getDocument().getLength());
		    }
		  
		
		  public void focusGained(FocusEvent e) {
				//calcSec();
				out("(unused arbitrary method: ) Focus " + displayFocusMessage("gained" , e));
		  }	
		  
		  public void focusLost(FocusEvent e) {
		    	//calcSec();
		    	out("(unused arbitrary method: )Focus " + displayFocusMessage("lost" , e));
		    }
		
		  //
		  //
		  //  ---some utility routines-------
		 public void calcSec(String whichField) {
			    Integer smin = 0;
			    Integer ssec = 0;
			    Integer emin = 0;
			    Integer esec = 0;
			 
			 
			    out("Calculating time value for "+whichField);
			    if ( whichField != null && whichField.compareTo("startM")==0) {
			    	smin = parse(startMin_.getText()); 
			    	emin = parse(endMin_.getText()); 
			    	if (emin < smin) {
			    		endMin_.setText(startMin_.getText());
			    	}
				}   
			    if (startMin_.getText() == null) {
			    	startMin_.setText("0");
			    }
			    if (startSec_.getText() == null) {
			    	startSec_.setText("0");
			    }
			    if (endMin_.getText() == null) {
			    	endMin_.setText("0");
			    }
			    if (endMin_.getText() == null) {
			    	endMin_.setText("0");
			    }
				smin = parse(startMin_.getText()); 
				ssec = parse(startSec_.getText()); 
				emin = parse(endMin_.getText()); 
				esec = parse(endSec_.getText());
				
			    Integer starttotsec = new Integer(smin.intValue() * 60 + ssec.intValue());
				Integer endtotsec   = new Integer(emin.intValue() * 60 + esec.intValue());
				
				starttotsec_.setText(starttotsec.toString());
				endtotsec_.setText(endtotsec.toString());
				
				
				if (starttotsec > endtotsec) {
					out("start > end, start is "+starttotsec.toString()+", end is "+endtotsec.toString());
					Integer min  = starttotsec / 60;
					Integer sec = starttotsec - (min.intValue()*60);
					out("so....setting endmin to "+min.toString()+", endsec to "+sec.toString());
					endMin_.setText(min.toString());
					endSec_.setText(sec.toString());
				}
	      }
		  
		  Integer parse (String s) {
			  Integer i = new Integer(0);
			  try {
				   i = new Integer(s); 
			  } catch (NumberFormatException en) {
				  //System.err.println("Sorry, '" + s+ "' is not a good number");
			  }
			  if (i.intValue() < -1) {
				  i = new Integer(0);
			  }
			  if (i.intValue() > 60) {
				  return i; // but if this is seconds, it is wrong...
			  }
			  return i;
		  }

		 

		  public String displayFocusMessage(String prefix, FocusEvent e) {
			    StringBuffer stb = new StringBuffer();
				stb.append(prefix
			                       + (e.isTemporary() ? " (temporary):" : ":")
			                       +  e.getComponent().getClass().getName()
			                       + "; Opposite component: " 
			                       + (e.getOppositeComponent() != null ?
			                          e.getOppositeComponent().getClass().getName() : "null"));
					       //+ newline); 
				return new String(stb);
			    }

			    
			



	 //----------------CONSTRUCTOR---------------------------
	 public BatGUI (String[] args) {
		     logr.log(Level.INFO, "who hooooo");
		     // create frame
		     Boolean gotProps=new Boolean(false);
		     System.out.println("starting BatGUI constructor. if props file supplied, s/b first arg or -Dprops=file");
		     if ( args.length > 0 && args[0] != null) {
		    	 out("props expected in "+args[0]);
		    	 getProps(args[0]);
		    	 gotProps=new Boolean(true);
		     }
		     if ( args.length == 0 ) {
		    	 String prop = System.getProperties().getProperty("props");
		    	 if (prop != null ) {
		    		 getProps(prop);
		    		 gotProps=new Boolean(true);
		    	 }
		     }
		     if (gotProps.booleanValue()) {
		    	 String xinc=System.getProperty("xinc");
		    	 String yinc=System.getProperty("yinc");
		    	 String vlcfile=System.getProperty("vlc");
		    	 // Override xoffset and yoffset default values from input properties
		    	 try {
		    	       if ( xinc != null) {
		    	    	   setXoffset( Integer.parseInt(xinc));
		    	       }
		    	       if (yinc != null ) {
		    	    	   setYoffset(Integer.parseInt(yinc));
		    	       }
		    	 } catch (NumberFormatException ne) {
		    		 System.err.println("number format bat for xinc " + xinc + " or yinc " + yinc);
		    	 }
		    	 if ( vlcfile != null ) {
		    		 setVlcFileName_(vlcfile);
		    	 }
		    	 
		     }
		     initWindow();
	 }
	 
	 
	 private void getProps(String ps){
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
	 //------------------------------------------------------------
	  private void out(String s) {
		  System.out.println(s);
	  }


	//--------------------------INIT WINDOW-----------------------	 
	//--------------------------INIT WINDOW-----------------------	 
	//--------------------------INIT WINDOW-----------------------	 
	//--------------------------INIT WINDOW-----------------------	 
	//--------------------------INIT WINDOW-----------------------	 
	//--------------------------INIT WINDOW-----------------------	 
	  @SuppressWarnings("deprecation")
	 private void initWindow() {
	     String title = "Video Slicer";
	    
	     //JFrame frame = new JFrame(title);
	     ImageIcon ic = new ImageIcon("q:\\temp\\v24.jpg");
	     JFrame frame = new BackgroundImageJFrame(ic);
	     frame.setTitle(title);
	     
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
	     
	    
		   
	     //---------------------TABLE to display current FILE--------------------
	     dataModel_ = new TableModel();
	     table_ = new JTable(dataModel_);
	     dataModel_.initColumnSizes(table_);
	     JScrollPane tableScrollpane = new JScrollPane(table_);
	     
	     //-----------------------------start and end values----------------------------
	     TimePaneFactory tpf = new TimePaneFactory(this);
	     JPanel timePane = tpf.getTimePane();
	     
	     //--------------radio buttons for window location------------------------
	    /*
	     RadioPanelFactory rp = new RadioPanelFactory(this);
	     radioPanel = rp.getQuadrantButtons();
	     */
	     MakeQuadrantPanel mrp = new MakeQuadrantPanel(this);
	     radioPanel      = mrp.getButtons();
	     //--------------radio buttons for aspect ration------------------------
		 MakeAspectRatioPanel marp = new MakeAspectRatioPanel(this);
		 aspectPanel   =      marp.getButtons();
		     
	     // ----------------------corner location text fields
	     PositionPane pospan = new PositionPane(this);
	     JPanel       sposPane = pospan.getPanel();
	     
	  // ----------------------window size text fields
	     WindowSizePane wsizePan = new WindowSizePane(this);
	     JPanel         wsizePane = wsizePan.getPanel();
	             
	     //--------------radio buttons
	     BuildSPPanel   prb = new BuildSPPanel(this);
	     JPanel radioPanelsp = prb.getPanel();
	    
	     FileActionFactory faf = new FileActionFactory(this);
	     
	     //-----------------Select alternate VLC program---------------------------
	     JPanel vlcPane = new JPanel();
	     vlcPane = faf.buildFileActionPane("VLC");
	     
	     
	     // A container can also be created with a specific layout
	     
	   //------------------action buttons------------------------------
	     JPanel pane2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	     
	     Button incStartButton = new Button("IncSec");
	     pane2.add(incStartButton);
	     incStartButton.addActionListener(new java.awt.event.ActionListener() {
		        public void actionPerformed(java.awt.event.ActionEvent evt) {
		         incStartButtonPress(evt);
		        }
	     });
	     
	     //------------------action buttons------------------------------
	     
	     Button testButton = new Button("test");
	     pane2.add(testButton);
	     testButton.addActionListener(new java.awt.event.ActionListener() {
		        public void actionPerformed(java.awt.event.ActionEvent evt) {
		         testButtonPress(evt);
		        }
	     });
	     
	   //------------------action buttons------------------------------
	     Button addListButton = new Button("append2list");
	     pane2.add(addListButton);
	     addListButton.addActionListener(new java.awt.event.ActionListener() {
		        public void actionPerformed(java.awt.event.ActionEvent evt) {
		         addListButtonPress(evt);
		        }
	     });
	     
	     
	     //-------------Find video file button---------------------------------
	     JPanel pane2vlc = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	     pane2vlc = faf.buildFileActionPane("FILE");
	     /*
	     whatVideoFile = new JLabel ("----------------------------------------------------------------what file?--------------------------------------------------------------------------------");
	     pane2vlc.add(whatVideoFile);
	    */
	    //--------------------save bat file to disk-------------------------------
	     JFrame jf2 = new JFrame();
	     batFileSaveChooser_ = new JFileChooser("save bat file");
		 Action saveAction = new SaveFileAction(jf2, batFileSaveChooser_, this);
		 JButton saveButton = new JButton(saveAction);
		 saveButton.setText("saveBat");
		 
	     pane2.add(saveButton);
	     saveButton.addActionListener(new java.awt.event.ActionListener() {
		        public void actionPerformed(java.awt.event.ActionEvent evt) {
		         batFileSaveButtonPress(evt);
		        }
	     });
	     
	
	     
	    
	     // A container can also be created with a specific layout
	     JPanel pane3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	     pane3.setBackground(Color.orange);
	    
	     // Create frame with a text area in the center
	     Component label1 = new Label("Enter start/end in min/sec");
	     label1.setSize(300, 150);
	     label1.setBackground(Color.YELLOW);
	     frame.setLayout(new FlowLayout (FlowLayout.CENTER));
	     
	     frame.add(label1);
	     	     
	     //------------------final action button------------------------------
	     JPanel pane2clear = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	     JButton clearButton = new JButton("clear");
	     pane2clear.add(clearButton);
	     clearButton.addActionListener(new java.awt.event.ActionListener() {
		        public void actionPerformed(java.awt.event.ActionEvent evt) {
		         clearButtonPress(evt);
		        }
	     });
	    
	     //---------------small pane to show total elapsed time added to table------------
	     JPanel elapsedTimePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	     JLabel elapsedLabel = new JLabel("Total Elapsed: ");
	     elapsedTimePanel.add(elapsedLabel);
	     elapsedTimePanel.add(totalTimeText);	     
	     
	     //--------- Add the containers to the bottom of the frame---------------------
	     
	     //new
	     /*
	     frame.add(bkpanel);
	     bkpanel.add(pane2vlc);
	     bkpanel.add(vlcPane);
	     bkpanel.add(timePane);
	     
	     //frame.add(pane3x);
	     radioPanel.setBackground(Color.white);
	     //frame.add(radioPanel);
	     
	     bkpanel.add(wsizePane);
	     bkpanel.add(sposPane);
	     
	     bkpanel.add(aspectPanel);
	     
	     bkpanel.add(pane2);
	     bkpanel.add(pane3);
	     TitledBorder bord = (TitledBorder)(radioPanelsp.getBorder());
	     //out(bord.toString());
	     bord.setTitleColor(Color.white);
	     radioPanelsp.setForeground(Color.white);
	     radioPanelsp.setBackground(Color.blue);
	     bkpanel.add(radioPanelsp);
	     	     
	     bkpanel.add(tableScrollpane);
	     bkpanel.add(pane2clear);
	     bkpanel.add(elapsedTimePanel);
	     */ 
	     frame.add(pane2vlc);
	     frame.add(vlcPane);
	     frame.add(timePane);
	     
	     //frame.add(pane3x);
	     radioPanel.setBackground(Color.white);
	     //frame.add(radioPanel);
	     
	     frame.add(wsizePane);
	     frame.add(sposPane);
	     
	     frame.add(aspectPanel);
	     
	     frame.add(pane2);
	     frame.add(pane3);
	     TitledBorder bord = (TitledBorder)(radioPanelsp.getBorder());
	     //out(bord.toString());
	     bord.setTitleColor(Color.white);
	     radioPanelsp.setForeground(Color.white);
	     radioPanelsp.setBackground(Color.blue);
	     frame.add(radioPanelsp);
	     	     
	     frame.add(tableScrollpane);
	     frame.add(pane2clear);
	     frame.add(elapsedTimePanel);
	     
	     
	     frame.setBackground(Color.pink);
	     //----------------------------WINDOW SIZING-----------------------
	     // Show the frame
	     int width = 764;
	     int height = 817;//275;//225;//300;
	     
	     width = 864;
	     height = 835;
  	     // a good size for the main window seems to be 734x840
	     frame.setSize(width, height);
	     frame.setVisible(true);
	     tableScrollpane.setSize(800, 200); //this has no effect until after the 'set visible'
	     //table_.setSize(1299, 400);
		    	
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
	 public static void main(String[] args) {
	  // start'em up
	  BatGUI.setDebug(true);
	  BatGUI sc;
	  //String       fn = new String("");
	  //if ( args.length > 0) {
	  // fn = args[0];
	  //}
	  sc = new BatGUI(args);
	  sc.sleep(10000);

	 }
	public void setVlcFileName_(String vlcFileName_) {
		this.vlcFileName_ = vlcFileName_;
	}
	public String getVlcFileName_() {
		return vlcFileName_;
	}
}



