package batboy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JFrame;

 
import java.awt.event.KeyEvent;
import java.io.File;

public class BatGUIHtml extends BatGUI implements ActionListener, FocusListener{
	protected JLabel j                    = null;
	protected VideoFilePointer[] vidFiles = null; 
	protected int fileCount               = 0;
	
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
    	String title = new String("HtmlFilms");
    	initWindowStart(title);
        String[] buts = new String[5];
        
        buts[0] = new String("0");
        buts[1] = new String("2");
        buts[2] = new String("4");
        buts[3] = new String("6");
        buts[4] = new String("9");
        
        //public JPanel getButtons(String borderTitle, String[] labels) {
        MakeArbitraryPanel mapa      = new MakeArbitraryPanel(this);
        GenericButtonPanelFactory gb = new GenericButtonPanelFactory(mapa);
        JPanel butPan = gb.getButtons(new String("How Many To Show in Grid"), buts);
        gb.addAction(butPan);
        JLabel howmany = new JLabel("How Many?");
        frame.add(howmany);
        frame.add(butPan);
        
        //FileActionFactory faf = new FileActionFactory(this);
        //JPanel filePanel = faf.buildFileActionPane("FILE");
        //frame.add(filePanel);
        // last thing before return!!!
        frame.setVisible(true);
    }
    
    public void  setBatFile(String path) {
		   batFile_    = new File(path);
    }
    
    protected void buildMultifileActionPanel () {
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
    	//-------------------TEST button----------------------------
    	Button  testButton = getTestButton();
	    frame.add(testButton);
    	
    	// Build the file input frames 
    	vidFiles              = new VideoFilePointer[howMany];
    	
    	while (k < howMany) {
    		
    		FileActionFactory faf = new FileActionFactory(this);
    		JPanel manyFiles      = new JPanel();
    		FlowLayout fl         = new FlowLayout();
    		manyFiles.setLayout(fl);
    		//int height            = 45 * howMany; // 45 pixels per row
    		int height            = 45 ; // 45 pixels per row
    		Dimension dim         = new Dimension(800,height);
    		manyFiles.setPreferredSize(dim);
    	
    		vidFiles[k] = new VideoFilePointer();
    		vidFiles[k].setIndex(k);
    		faf.buildFileActionPane(vidFiles[k]);
    		manyFiles.add(vidFiles[k].getVideoFilePane());
    		k+=1;
    		
    		frame.add(manyFiles);
    	}
    	
    	
    	
    	frame.setVisible(true);
    }
  
    public void actionPerformed(ActionEvent e) {
    	PageBuilder pb = new PageBuilder(vidFiles, this);
        if ("doit".equals(e.getActionCommand())) {
          String result = pb.generatePage();
          //show result of page generation
          if (j != null) {
        	  frame.getContentPane().remove(j);
        	  //j.setEnabled(false);
          }
          // ack file generation
          j = new JLabel(result);
          j.setOpaque(true);
          j.setBackground(Color.white);
          BevelBorder bord = new BevelBorder(1);
          j.setBorder(bord);
          frame.add(j);
          // show resulting file name
          JLabel k = new JLabel(this.getBatFileName());
          k.setOpaque(true);
          k.setBackground(Color.white);
          BevelBorder bord1 = new BevelBorder(1);
          k.setBorder(bord1);
          frame.add(k);
          
          frame.setVisible(true);
        } 
    }
    public void testButtonPress(java.awt.event.ActionEvent vent) {
        if (DEBUG) {
        	 out("-----------------------testButtonPress---------------------------------------");
        	 out("--------------------------------------------------------------");
        	 out("testButton:   " +vent.toString());
             out("Event value : " + vent.getActionCommand());
             out("--------------------------------------------------------------");
             out("--------------------------------------------------------------");
        }
        test();
    }
    public void test() {
    	// run the file and see how it looks
    	File fyle = super.getBatFile();
    	out("Test button 'test' starts with "+fyle.toString());
    	String nam = fyle.getName();
    	out("Test: base file name is "+nam);
    	String fileBase = System.getProperty("filebase");
    	out("Test: parent path is "+fileBase);
    	File foil = new File(fileBase + "\\"+nam);
    	out("Result is: "+foil.getPath());
    	out("Bat file (really, the html) is "+fyle.getPath());
    	String cmd = new String("C:\\PROGRA~2\\Opera\\350206~1.92\\opera.exe -newwindow \""+
    	                        fyle.getPath()+"\"");
    	out("Cmd is: "+cmd);
    	ExecWrapper e = new ExecWrapper(cmd,true);
    	//e.setDebug(true);
    	out("Calling 'doitHtml'");
    	e.doitHtml(cmd);
    }

}
