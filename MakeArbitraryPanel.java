package batboy;

public class MakeArbitraryPanel {
   private BatGUI  bG = null;
   public MakeArbitraryPanel () {
	   
   }
   public MakeArbitraryPanel (BatGUI b) {
	   bG = b;
   }
   public void ackshun(java.awt.event.ActionEvent vent) {
	   BatGUIHtml bH = (BatGUIHtml)bG;
	   System.out.println("ackshun event!");
	   System.out.println("cmd: " + vent.getActionCommand());
	   System.out.println("toS: " + vent.toString());
	   if ( bG == null) {
		   return;
	   }
	   Integer I = new Integer(vent.getActionCommand());
	   bH.setFileCount(I.intValue());
	   bH.buildMultifileActionPanel();
   }

}
