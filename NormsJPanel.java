package batboy;
// This is an extension of a JPanel that contains a few additional data fields
// that I need to carry with each "getfilebutton - durationTextField - FilenameDropTarget" 
// item on the main page.
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class NormsJPanel extends JPanel {
    private     int              panelNumber = 0;
    private     int              totalVideoSeconds = 0;
    private     VideoFilePointer vidFilePtr = null;
    
    
    public VideoFilePointer getVidFilePtr() {
		return vidFilePtr;
	}
	public void setVidFilePtr(VideoFilePointer vidFilePtr) {
		this.vidFilePtr = vidFilePtr;
	}
	public int getTotalVideoSeconds() {
		return totalVideoSeconds;
	}
	public void setTotalVideoSeconds(int totalVideoSeconds) {
		this.totalVideoSeconds = totalVideoSeconds;
	}

	public NormsJPanel() {
		// TODO Auto-generated constructor stub
	}

	public NormsJPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public NormsJPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public NormsJPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public int getPanelNumber() {
		return panelNumber;
	}

	public void setPanelNumber(int panelNumber) {
		this.panelNumber = panelNumber;
	}

}
