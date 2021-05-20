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
import javax.swing.JTextField;
import java.util.*;
// THIS is a DATA CONTAINER for all the stuff needed to display each individual 
// row in the builder screen, and to contain the 'dropped' file info

//This is a data carrier that is attached to each iteration on a 2-pane, 4-pane or 6-pane
//screen.  Ideally it will have pointers to all the parts, removing the necessity to search 
//thru the display objects for textfields, etc

public class VideoFilePointer {
    private     NormsJPanel            videoFilePane = null;
    private     int                    index         = 0;
    private     JFileChooser           jfc           = null;
    private     JFrame                 jfcFrame       = null;
    private     File                   videoFile     = null;
    protected   LinkedList<File>       fileQueue     = new LinkedList<File>();
    private     JLabel                 whatVideoFileLabel = new JLabel ("----------------------------------------------------------------what file?--------------------------------------------------------------------------------");
	private     BatGUI                 bg_ = null;
	private     JTextField             durationTextField = null;
	private     JButton                openButton = null;  //aka the "vfyl" or "videoFile" button. (I think) 
    
    public JButton getOpenButton() {
		return openButton;
	}
	public void setOpenButton(JButton openButton) {
		this.openButton = openButton;
	}
	public JTextField getDurationTextField() {
		return durationTextField;
	}
	public void setDurationTextField(JTextField durationTextField) {
		this.durationTextField = durationTextField;
	}
	public VideoFilePointer(BatGUI bg) {
		// TODO Auto-generated constructor stub
    	bg_  = bg;
	}
 	public JLabel getWhatVideoFileLabel() {
		return whatVideoFileLabel;
	}
	public void setWhatVideoFileLable(JLabel whatVideoFile) {
		String  vfText = whatVideoFile.getText();
		out ("in VideoFilePointer:setWhatVideoFile Setting video file for path "+vfText);
		this.whatVideoFileLabel = whatVideoFile;
		File newvf = new File(vfText);
		File lastDir = newvf.getParentFile();
		bg_.setLastDirectory(lastDir);
		this.fileQueue.add(newvf);
		out ("in VideoFilePointer:setWhatVideoFile file queue is "+this.fileQueue.size());
	}
	public NormsJPanel getVideoFilePane() {
		return videoFilePane;
	}
	public void setVideoFilePane(NormsJPanel videoFilePane) {
		this.videoFilePane = videoFilePane;
	}
	public JFileChooser getJfc() {
		return jfc;
	}
	public void setJfc(JFileChooser jfc) {
		this.jfc = jfc;
	}
	public JFrame getJfcFrame() {
		return jfcFrame;
	}
	public void setJfcFrame(JFrame jflvfc) {
		this.jfcFrame = jflvfc;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	//-----------------------get video file--------------------
	public File getVideoFile() {
		return new File(whatVideoFileLabel.getText());
	}
	private void out (String s) { System.out.println(s);}
	public void setVideoFile(File videoFile) {
		out ("in VideoFilePointer:setVideoFile Setting vide file for path "+videoFile.getPath());
		this.whatVideoFileLabel.setText(videoFile.getPath());
		this.fileQueue.add(videoFile);
		out ("in VideoFilePointer:setVideoFile file queue is "+this.fileQueue.size());
	}
	public LinkedList<File> getFileQueue() {
		return fileQueue;
	}
	public void setFileQueue(LinkedList<File> fileQueue) {
		this.fileQueue = fileQueue;
	}
}
