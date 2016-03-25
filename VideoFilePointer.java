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
// THIS is a DATA CONTAINER for all the stuff needed to display each individual 
// row in the builder screen, and to contain the 'dropped' file info
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
	//-----------------------get video file--------------------
	public File getVideoFile() {
		return new File(whatVideoFile.getText());
	}
	public void setVideoFile(File videoFile) {
		this.whatVideoFile.setText(videoFile.getPath());
	}
}
