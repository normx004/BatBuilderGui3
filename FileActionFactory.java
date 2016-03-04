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
	BatGUISlicer   batGui_ = null;
	
	FileActionFactory (BatGUISlicer bg) {
		batGui_ = bg;
	}
	public JPanel buildFileActionPane(String type) {
		JPanel bogus = null;
		if (type.compareTo("VLC")==0) {
			return buildVLCActionPane();
		} else {
			if (type.compareTo("FILE")==0) {
				return buildFileActionPane();
			}
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
			out("NO INITIAL DIR NAME FOUND");
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
		 File lastDir = getLastDirUsed();
		 JFileChooser jfc = new JFileChooser(lastDir);
		 batGui_.setVideoFileChooser(jfc);
	     JFrame jf1vfc = new JFrame();
	     Action openAction = new OpenFileAction(jf1vfc, batGui_.getVideoFileChooser(), batGui_);
	     JButton openButton = new JButton(openAction);
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
	private void out(String x) { System.out.println(x);}
	
	//-----------------Select alternate VLC program---------------------------   
	public JPanel buildVLCActionPane() {
	     JPanel vlcPane = new JPanel();
	     vlcPane.setLayout(new BoxLayout(vlcPane, BoxLayout.LINE_AXIS));
	     batGui_.setVlcFileChooser( new JFileChooser("select vlc file"));
	     
	     JFrame jf3 = new JFrame();
	     Action  vlcAction = new GetVlcFileAction(jf3, batGui_.getVlcFileChooser(), batGui_);
	     JButton vlcButton = new JButton(vlcAction);
	     vlcButton.setText("VLC File");
	     vlcPane.add(vlcButton);
	     
	     Component  vLabel = new Label("VLC File: ");
	     vLabel.setBackground(Color.YELLOW);	     
	     vlcPane.add(vLabel);
	     //final  String DEFAULTVLC = "C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe";
	     final  String DEFAULTVLC = batGui_.getVlcFileName();
	     out("got vlc file name from batgui, it is "+DEFAULTVLC);
	     batGui_.setVlcFileLabel( new JLabel(DEFAULTVLC + "                           "));
	     vlcPane.add(batGui_.getVlcFileLabel());
	     return vlcPane;
		
	}
	
	
}
