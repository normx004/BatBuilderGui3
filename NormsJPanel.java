package batboy;

import java.awt.LayoutManager;

import javax.swing.JPanel;

public class NormsJPanel extends JPanel {
    private     int panelNumber = 0;
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