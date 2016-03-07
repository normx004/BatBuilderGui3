package batboy;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VideoFilePointer {
    public     JPanel                 videoFilePane = null;
    public     int                    index = 0;
    public     JFileChooser           jfc = null;
    public     JFrame                 jflvfc = null;
    public     File                   videoFile = null;
    public     JLabel                 whatVideoFile = new JLabel ("----------------------------------------------------------------what file?--------------------------------------------------------------------------------");
	public VideoFilePointer() {
		// TODO Auto-generated constructor stub
	}
    /*
     * public JPanel buildFileActionPane() {
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
     */
	public JLabel getWhatVideoFile() {
		return whatVideoFile;
	}
	public void setWhatVideoFile(JLabel whatVideoFile) {
		this.whatVideoFile = whatVideoFile;
	}
	public JPanel getVideoFilePane() {
		return videoFilePane;
	}
	public void setVideoFilePane(JPanel videoFilePane) {
		this.videoFilePane = videoFilePane;
	}
	public JFileChooser getJfc() {
		return jfc;
	}
	public void setJfc(JFileChooser jfc) {
		this.jfc = jfc;
	}
	public JFrame getJflvfc() {
		return jflvfc;
	}
	public void setJflvfc(JFrame jflvfc) {
		this.jflvfc = jflvfc;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public File getVideoFile() {
		return videoFile;
	}
	public void setVideoFile(File videoFile) {
		this.videoFile = videoFile;
	}
}
