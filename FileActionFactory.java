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
	protected BatGUIHtml  batGui_ = null;
	
	private void out(String x) { System.out.println(x);}	
	
	FileActionFactory (BatGUIHtml bg) {
		batGui_ = bg;
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
	
	public JPanel buildFileActionPane() {
		 JPanel videoFilePane = new JPanel();
		 File lastDir         = getLastDirUsed();
		 JFileChooser jfc     = new JFileChooser(lastDir);
		 batGui_.setVideoFileChooser(jfc);
	     JFrame jf1vfc        = new JFrame();
	     Action openAction    = new OpenFileAction(jf1vfc, batGui_.getVideoFileChooser(), batGui_);
	     JButton openButton   = new JButton(openAction);
	     openButton.setText("videofile");
	     videoFilePane.add(openButton);
	     batGui_.setWhatVideoFile ( new JLabel ("----------------------------------------------------------------what file?--------------------------------------------------------------------------------"));
	     videoFilePane.add(batGui_.getWhatVideoFile());
	     
	    //-----------------watch for dropped files!=---------------------
	     videoFilePane.setDropTarget(new DropTarget() {
	         public synchronized void drop(DropTargetDropEvent evt) {
	        	 out("DROP DROP DROP EVENT!!!! "+ evt.toString());
	             try {
	                 evt.acceptDrop(DnDConstants.ACTION_COPY);
	                 //List<File> droppedFiles = (List<File>)
	                Object droppedFiles = 
	                     evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
	                 for (File file : (java.util.List<File>)droppedFiles) {
	                     System.out.println("DROPPPED FILE ON THIS BUTTON(Name):"+file.getPath());
	                     batGui_.setVideoFile(file);
	                 }
	             } catch (Exception ex) {
	                 ex.printStackTrace();
	             }
	         }
	     });
	     //-=-------------EnD of Watch for dropped----------------------------------------- 
	     return videoFilePane;
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
	     vPtr.getVideoFilePane().setDropTarget(new DropTarget() {
	         public synchronized void drop(DropTargetDropEvent evt) {
	        	 out("DROP DROP DROP EVENT!!!! "+ evt.toString());
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
	                     System.out.println("DROPPPED FILE ON BUTTON(FileName):"+file.getPath());
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
	                           x.setText(file.getPath());
	                           
	                           String  vfText = file.getPath();
	                   		   out ("in FileActionFactory:dropTarge Setting vid file " + vidFileIdx + " to path "+vfText);
	                           batGui_.getVidFiles()[vidFileIdx].fileQueue.add(new File(vfText));
	                   		   out ("in FileActionFactory:dropTarge file queue is "+batGui_.getVidFiles()[vidFileIdx].fileQueue.size());
	                           
	                           
	                           
	                           
	                           JFrame frame = batGui_.getFrame();
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
