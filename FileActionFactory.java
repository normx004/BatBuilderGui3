package batboy;
import javax.swing.*;

import java.lang.*;
import java.util.*;
import java.util.Arrays.*;

import java.io.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;

public class FileActionFactory {
	protected BatGUIHtml  batGuiH_ = null;
	protected BatGUISlicer batGuiS_ = null;
	
	private void out(String x) { System.out.println(x);}	
	
	FileActionFactory (BatGUIHtml bg) {
		batGuiH_ = bg;
	}
	FileActionFactory (BatGUISlicer bg) {
		batGuiS_ = bg;
	}
	public JPanel buildFileActionPane(String type) {
		JPanel bogus = null;
		
			if (type.compareTo("FILE")==0) {
				return buildFileActionPane();
			}
		return bogus;
	}
	
	private File getLastDirUsed() {
		String chk = new String("Q:\\temp\\lastGuiFile");
		String result = new String();
		File res = null;
		try {
		BufferedReader in  = new BufferedReader(new FileReader(chk));
		String ln  = in.readLine();
		
		if (ln == null || ln.length() == 0) {
			//out("NO INITIAL DIR NAME FOUND");
		} else {
			//out("got an initial directory: "+ln);
			result = ln;
			res = new File(result);
		}
		} catch (IOException ioe) {
			out("IO Error reading last directory name from file "+ioe.getMessage());
		}
		//File res = new File(result);
		return res;
	}
	// -----what video file shall we use------------------------
	// this routine used only if building slider xspf file for single-vlc display
	public JPanel buildFileActionPane() {
		 JPanel videoFilePane = new JPanel();
		 File lastDir         = getLastDirUsed();
		 JFileChooser jfc     = new JFileChooser(lastDir);
		 batGuiS_.setVideoFileChooser(jfc);
	     JFrame jf1vfc        = new JFrame();
	     Action openAction    = new OpenFileAction(jf1vfc, batGuiS_.getVideoFileChooser(), batGuiS_);
	     JButton openButton   = new JButton(openAction);
	     openButton.setText("videofile");
	     videoFilePane.add(openButton);
	     batGuiS_.setWhatVideoFile ( new JLabel ("----------------------------------------------------------------what file?--------------------------------------------------------------------------------"));
	     videoFilePane.add(batGuiS_.getWhatVideoFile());
	     
	    //-----------------watch for dropped files!=---------------------
	     videoFilePane.setDropTarget(new DropTarget() {
	         public synchronized void drop(DropTargetDropEvent evt) {
	        	 out("videoFilePane.setDropTarget line 69 DROP DROP DROP EVENT!!!! "+ evt.toString());
	             try {
	                 evt.acceptDrop(DnDConstants.ACTION_COPY);
	                 //List<File> droppedFiles = (List<File>)
	                Object droppedFiles = 
	                     evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
	                 for (File file : (java.util.List<File>)droppedFiles) {
	                     System.out.println("DROPPPED FILE ON THIS BUTTON(Name):"+file.getPath());
	                     batGuiS_.setVideoFile(file);
	                 }
	             } catch (Exception ex) {
	                 ex.printStackTrace();
	             }
	         }
	     });
	     //-=-------------EnD of Watch for dropped----------------------------------------- 
	     return videoFilePane;
	}
	  //-----------------------link checker------------------------
    public String seeLink(String path) throws IOException,  mslinks.ShellLinkException {
		out("Seelink invoked with path: "+path);
		File fyle = new File (path);
		if (fyle.exists()) {
			out("Well, at least the file exists (the shortcut that is)");
		}
		mslinks.ShellLink s1 = new mslinks.ShellLink(fyle);
		out("ShellLink object has been created");
		String thePath = s1.resolveTarget();
		out("link target is "+thePath);
		return thePath;
	}
	//--------------Build File Action Pane for HTML version-----------------------------
	public  void buildFileActionPane(VideoFilePointer vPtr) {
		 vPtr.setVideoFilePane(new NormsJPanel());
		 vPtr.getVideoFilePane().setPanelNumber(vPtr.getIndex());
		 out("in buildFAF, vPtr idx = "+vPtr.getIndex());
	
		 vPtr.setJfc(new JFileChooser(/*lastDir*/));
	     vPtr.setJflvfc( new JFrame());
	     // this adds an 'action' object that is invoked when the button is clicked
	     // jfl vfc is "jframe with visual file chooser"
	     Action openAction    = new OpenFileAction(vPtr.getJflvfc(), vPtr.getJfc(), vPtr);
	     JButton openButton   = new JButton(openAction);
	     
	     openButton.setText("videofile");
	     vPtr.getVideoFilePane().add(openButton);
	     // get WhatVideoFile returns a "JLabel" that is the drop target; text is "---what file?----"
	     // or the file path if a file has been chosen for that slot
	     vPtr.getVideoFilePane().add(vPtr.getWhatVideoFileLabel());
	    
	  
	    //-----------------watch for dropped files!=---------------------
	     // this seems to be where a video dragged to a multi-video-html-window
	     // constructor instance processes the drops...must check for link and 
	     // if link, substitute the link target...
	     vPtr.getVideoFilePane().setDropTarget(new DropTarget() {
	         public synchronized void drop(DropTargetDropEvent evt) {
	        	 out("vptr.getVideoFilePane line 109 DROP DROP DROP EVENT!!!! "+ evt.toString());
	        	 DropTargetContext ctx = evt.getDropTargetContext();
	        	 Component comp        = ctx.getComponent();
	        	 Class o               = comp.getClass();
	        	 out("Component is "+o.getCanonicalName());
	        	 NormsJPanel jp        = (NormsJPanel)comp;
	        	 int vidFileIdx        = jp.getPanelNumber();
	        	 out("FileActionFactory: dropTarget found vid index is "+vidFileIdx);
	        	 try {
	                 evt.acceptDrop(DnDConstants.ACTION_COPY);
	                 //List<File> droppedFiles = (List<File>)
	                 Object droppedFiles = 
	                     evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
	                 for (File file : (java.util.List<File>)droppedFiles) {
	                	 String thePath = file.getPath();
	                     System.out.println("DROPPPED FILE ON BUTTON(FileName):"+thePath);
	                     
	                     
	                     String finalPath = null;
	                     try {
	                    	 finalPath = seeLink(thePath);
	                     } catch (IOException ioe) {
	                    	 System.err.println("Uh oh, io exception looking at " + thePath);
	                     } catch (mslinks.ShellLinkException sle) {
	                    	 System.err.println("ok, looks like " + thePath + " is not a link");
	                     }
	                     
	                     if (finalPath != null ) {
	                    	 out("OK! it was a link, link target is "+finalPath);
	                    	 thePath = finalPath;
	                     }
	                     
	                     //got to replace "set video file" with access to the text field in the Jpanel
	                     Component[] components = jp.getComponents(); 
	                     Component   component  = null; 
	                     for (int ci = 0; ci < components.length; ci++) 
	                     { 
	                        component = components[ci]; 
	                        if (component instanceof JLabel) 
	                        {   
	                           //out("YES! its a JLabel");
	                           JLabel x =  ((JLabel)(component));
	                           x.setText(thePath);
	                           
	                           String  vfText = thePath;
	                   		   out ("in FileActionFactory:dropTarge Setting vid file " + vidFileIdx + " to path "+vfText);
	                           batGuiH_.getVidFiles()[vidFileIdx].fileQueue.add(new File(vfText));
	                   		   out ("in FileActionFactory:dropTarge file queue is "+batGuiH_.getVidFiles()[vidFileIdx].fileQueue.size());
	                           
	                           
	                           
	                           
	                           JFrame frame = batGuiH_.getFrame();
	                           frame.invalidate();
	                        }
	                     }
	                 }
	             } catch (Exception ex) {
	                 ex.printStackTrace();
	             }
	         }
	     });
	     //-=-------------EnD of Watch for dropped----------------------------------------- 
	     //return videoFilePane;
	}
	
}
