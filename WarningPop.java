package batboy;

import javax.swing.*;

public class WarningPop {

	public WarningPop() {
		// TODO Auto-generated constructor stub
	}
	
	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

}
