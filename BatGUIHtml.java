package batboy;

import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder; 

import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.border.*;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.*;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JFrame;

 
import java.awt.event.KeyEvent;
import java.io.File;

import java.util.*;

//import EightPointThree.*;

public class BatGUIHtml extends BatGUI implements ActionListener, FocusListener{
	protected JLabel j                    = null;
	protected VideoFilePointer[] vidFiles = null; 
	protected int fileCount               = 0;
	//BatGUI bg_                            = null;
	//protected String OS = null;
	protected int lastVideoFilePointerIndex = -1;
	
	 //------------------------OUT------------------------------------
	  protected void out(String s) {
		  System.out.println("BatGUIHtml: " + s);
	  }
	
	
	public int getLastVideoFilePointerIndex() {
		return lastVideoFilePointerIndex;
	}

	public void setLastVideoFilePointerIndex(int lastVideoFilePointerIndex) {
		this.lastVideoFilePointerIndex = lastVideoFilePointerIndex;
	}
	ArrayList theScreenElements = new ArrayList();
	
	public void addAScreenElementToList(Component c) {
		theScreenElements.add((Object) c);
	}
	
	public int getFileCount() {
		return fileCount;
	}
	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
	
	public BatGUIHtml(String[] args) {
		// TODO Auto-generated constructor stub
	}
    public void init() {
    	super.init();
    	
    	
    	String vsn = new String(" 2021-05-26");
    	String title = new String("HtmlFilms");
    	title = new String(title + " "+vsn);
    	initWindowStart(title);
        String[] buts = new String[5];
        
        buts[0] = new String("0");
        buts[1] = new String("2");
        buts[2] = new String("4");
        buts[3] = new String("6");
        buts[4] = new String("9");
        
        //public JPanel getButtons(String borderTitle, String[] labels) {
        MakeArbitraryPanel        mapa   = new MakeArbitraryPanel(this);
        GenericButtonPanelFactory gb     = new GenericButtonPanelFactory(mapa);
        JPanel                    butPan = gb.getButtons(new String("How Many To Show in Grid"), buts);
        gb.addAction(butPan);
        JLabel howmany = new JLabel("How Many?");
        howmany.setBackground(Color.BLACK);
        howmany.setOpaque(true);
        howmany.setForeground(Color.CYAN);
        frame.add(howmany);
        frame.add(butPan);
        //position the window at upper right
        Point p = new Point(10,10);
        frame.setLocation(p);
        frame.setVisible(true);
    }
    
    public void  setBatFile(String path) {
		   batFile_    = new File(path);
    }
    
    protected void buildMultifileActionPanel () {
    	ArrayList scr = this.getTheScreenElements();
    	if (scr == null || scr.isEmpty()) {
    		// no previous screen to clear!
    	} else {
    		int scrCount = scr.size();
    		out("will be deleting "+ scrCount + " things from screen");
    		int k = 0;
    		while (k < scrCount) {
    			Component c = (Component) scr.get(k);
    			c.setVisible(false);
    			k += 1;
    		}
    	}
    	
    	// the problem here is with 2 or 4 or whatever drop targets in the same JPanel,
    	// it seems drag and drop doesn't distinguish between what button you are dropping
    	// onto...at least, I can't see how to do that. So I'll make 'n' separate panels...
    	int k = 0;
    	int howMany           = this.getFileCount();
    	out("build Multi File Action Panel. howMany is "+howMany);
    	//--------------------DOIT button------------------------
    	JButton doitButton = new JButton("Make Page");
    	doitButton.setActionCommand("doit");	
    	doitButton.addActionListener(this);
    	frame.add(doitButton);
    	addAScreenElementToList(doitButton);
    	//-------------------TEST button----------------------------
    	Button  testButton = getTestButton();
	    frame.add(testButton);
	    addAScreenElementToList(testButton);
    	
    	// Build the file input frames 
    	vidFiles              = new VideoFilePointer[howMany];
    	
    	while (k < howMany) {
    		// each new "manyFiles" is a JPanel that contains a file drop target. 
    		// the "vidFiles" array contains VideoFilePointer objects, each
    		// one with a pointer to the corresponding panel, and will
    		// contain the file objects dropped onto the JPanel
    		
    		// in "file action factory" the Panel is associated with a drop target
    		// function that will handle the drop for that particular panel by setting
    		// the JLabel text to the new file name.
    		
    		FileActionFactory faf            = new FileActionFactory(this);
    		NormsJPanel       manyFiles      = new NormsJPanel();
    		manyFiles.setPanelNumber(k);
    		Border            myBord         = BorderFactory.createRaisedBevelBorder();
    		manyFiles.setBorder(myBord);
    		FlowLayout fl                    = new FlowLayout();
    		manyFiles.setLayout(fl);
    		//int height            = 45 * howMany; // 45 pixels per row
    		int height            = 45 ; // 45 pixels per row
    		Dimension dim         = new Dimension(800,height);
    		manyFiles.setPreferredSize(dim);
    	
    		vidFiles[k] = new VideoFilePointer(this);
    		vidFiles[k].setIndex(k);
    		faf.buildFileActionPane(vidFiles[k]);
    		manyFiles.add(vidFiles[k].getVideoFilePane());
    		addAScreenElementToList(vidFiles[k].getVideoFilePane());
    		k+=1;
    		
    		frame.add(manyFiles);
    		addAScreenElementToList(manyFiles);
    	}
    	// define a new JFrame for this part and format it
    	// with some kind of table layout...it will hold 
    	// the 'pop a chooser' buttons, and the duration
    	// textfiled that displays total time for all the
    	// videos dropped so far on each of the 2, 4, 6 or 8
    	// drop targets
    	int j = 0;
    	GridLayout experimentLayout = new GridLayout(0,2);
    	JPanel butts = new JPanel(experimentLayout);
    	butts.setPreferredSize(new Dimension(600, 75));
    	butts.setMaximumSize(butts.getPreferredSize()); 
    	butts.setMinimumSize(butts.getPreferredSize());
    	//example
    	GridBagConstraints c = new GridBagConstraints();
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.gridheight = 2;
    	c.gridwidth = howMany;
    	c.gridx = 0;
    	c.gridy = 0;
    	
    	while (j < howMany) {
    		GridBagConstraints cx = new GridBagConstraints();
        	cx.fill = GridBagConstraints.HORIZONTAL;
        	cx.gridheight = 2;
        	cx.gridwidth = howMany;
        	
    		cx.gridy = 0;
    		cx.gridx = j;
    		cx.fill = GridBagConstraints.HORIZONTAL;
    		out("adding " + j + "th openbutton at grid x = " + cx.gridx + ", and y = "+cx.gridy);
    		JButton jbut = vidFiles[j].getOpenButton();
    		jbut.setName("vfylbutt");
    		butts.add(vidFiles[j].getOpenButton(),cx);
    		c.gridy += 1;
    		c.fill = GridBagConstraints.HORIZONTAL;
    		out("adding " + j + "th openbutton at grid x = " + cx.gridx + ", and y = "+cx.gridy);
    		butts.add(vidFiles[j].getDurationTextField(),cx);
    		j = j + 1;
    	}
    	
    	frame.add(butts);
    	
    	// try to establish an "undo" of the last added item
    	JButton undoitButton = new JButton("Undo!");
    	undoitButton.setBackground(Color.BLACK);
    	undoitButton.setForeground(Color.RED);
    	undoitButton.setActionCommand("undoit");	
    	undoitButton.addActionListener(this);
    	frame.add(undoitButton);
    	addAScreenElementToList(undoitButton);
    	
    	
    	frame.setVisible(true);
    }
    //---------------------------ACTION PERFORMED------------------------------
    //  (that is, "doit" or "undo" button was pressed)...
    public void actionPerformed(ActionEvent e) {
    	//  "DOIT" button - time to make the html file
    	PageBuilder pb = new PageBuilder(vidFiles, this);
        if ("doit".equals(e.getActionCommand())) {
          String result = pb.generatePage();
          //show result of page generation
          if (j != null) {
        	  frame.getContentPane().remove(j);
        	  //j.setEnabled(false);
          }
          // ack file generation - in a JLabel
          j = new JLabel(result);
          j.setOpaque(true);
          j.setBackground(Color.white);
          BevelBorder bord = new BevelBorder(1);
          j.setBorder(bord);
          frame.add(j);
          addAScreenElementToList(j);
          // show resulting file name
          JLabel k = new JLabel(this.getBatFileName());
          k.setOpaque(true);
          k.setBackground(Color.pink);
          BevelBorder bord1 = new BevelBorder(1);
          k.setBorder(bord1);
          frame.add(k);
          addAScreenElementToList(k);
          
          frame.setVisible(true);
        } 
        //  "UNDO" button -- undrop the last file name dropped on one of the screen segments
        if ("undoit".equals(e.getActionCommand())) {
            out("whoa - UNDOIT button pressed!!!");
            int ix = this.getLastVideoFilePointerIndex();
            out ("last video file pointer index was "+ ix);
            VideoFilePointer vpi = vidFiles[ix];
            LinkedList queue = vpi.getFileQueue();
            File lastFile = (File) (queue.getLast());
            out("undo found the last file in the list was "+lastFile.getPath());
            // must ALSO deduct the timing for this file from the total...
            FetchVideoDetails fvd = new FetchVideoDetails(lastFile.getPath());
            int  secs = fvd.getDuration();
            out ("that file had a duration of "+secs);
            String tDur = vpi.getDurationTextField().getText();
            Integer totalDuration = Integer.parseUnsignedInt(tDur);
            out ("old total duration was "+totalDuration);
            int newSecs = totalDuration - secs;
            out ("new total duration is " + newSecs);
            Integer t = new Integer(newSecs);
            vpi.getDurationTextField().setText(t.toString());
            // now clean up the linked list
            queue.removeLast();
           
            // and update the screen element
            lastFile = (File) (queue.getLast());
            String  vfText = lastFile.getPath();
    		out ("in VideoFilePointer:setWhatVideoFile Setting video file for path "+vfText);
    		vpi.getWhatVideoFileLabel().setText(vfText);
        } 
    }
    // test button gets hit when the page is built and can be thrown to a browser for a test
    public void testButtonPress(java.awt.event.ActionEvent vent) {
        if (DEBUG) {
        	 out("-----------------------testButtonPress---------------------------------------");
        	 out("--------------------------------------------------------------");
        	 out("testButton:   " +vent.toString());
             out("Event value : " + vent.getActionCommand());
             out("--------------------------------------------------------------");
             out("--------------------------------------------------------------");
        }
        // actually runit
        this.test();
    }
    // test the page by opening it with opera
    String fileBase = null;
    public void test() {  // test the page
    	// run the file and see how it looks
    	File fyle = super.getBatFile();
    	out("xxxTest button 'test' starts with "+fyle.toString());
    	String nam = fyle.getName();
    	out("Test: base file name is "+nam);
    	String fileBase = System.getProperty("filebase");
    	out("Test: parent path is "+fileBase);
    	File foil = new File(fileBase + "\\"+nam);
    	out("Result is: "+foil.getPath());
    	out("Bat file (really, the html) is "+fyle.getPath());
    	String shorty = fyle.getPath();
    	
    	if (! this.isUseHttpServer()) {
    		out("\nConstructing RunCmdFor83");
    		RunCmdFor83 rc83 = new RunCmdFor83();
    		out("Calllllling RunCmdFor83");
    		shorty = rc83.xlateTo83(fyle.getPath());
    		out("DONE with RunCmdFor83\n");
    		String shirty = shorty.replace("\\", "/");
    		out("===after replaceall: "+shirty);
    		shorty = shirty;
    	}
    	
        if ( this.isUseHttpServer()) {
    		shorty = fyle.getName();
    		StringBuffer s = new StringBuffer("http://localhost/");
    		String shirty = shorty.replace("\\", "/");
    		out("===after replaceall: "+shirty);
    		s.append(shirty);
    		shorty = s.toString();
        }
   
    	
    	StringBuffer cmd = new StringBuffer("");
    	// default test browser to opera, but allow an alternate from the properties
    	String browser = new String ("\"C:\\PROGRA~2\\Opera\\launcher.exe\" -newwindow ");
    	String altBrowse = System.getProperty("testbrowser");
    	
    	if (altBrowse != null) {
    		String brow = new String("\"" +altBrowse+"\" ");
    		out("Using browser: "+brow);
    		cmd.append(brow);
    	} else {
    		out("Using default browser: "+browser);
    		cmd.append(browser);
    	}
    	cmd.append(new String(" -new-window \""  + shorty + "\""));
    	out("Cmd is: "+cmd.toString());
    	ExecWrapper e = new ExecWrapper(cmd.toString(),true);
    	//e.setDebug(true);
    	out("Cxxxalling 'doitHtml'");
    	e.doitHtml(cmd.toString());
    }
	public VideoFilePointer[] getVidFiles() {
		return vidFiles;
	}
	public void setVidFiles(VideoFilePointer[] vidFiles) {
		this.vidFiles = vidFiles;
	}
	public ArrayList getTheScreenElements() {
		return theScreenElements;
	}
	public void setTheScreenElements(ArrayList theScreenElements) {
		this.theScreenElements = theScreenElements;
	}

	

	
	

}
