package batboy;

public class MakeArbitraryPanel {
   private BatGUI  bG = null;
   public MakeArbitraryPanel () {
	   
   }
   public MakeArbitraryPanel (BatGUI b) {
	   bG = b;
   }
   private void out(String s) { System.out.println(s);}
   public void ackshun(java.awt.event.ActionEvent vent) {
	   BatGUIHtml bH = (BatGUIHtml)bG;
	   System.out.println("ackshun event!");
	   System.out.println("cmd: " + vent.getActionCommand());
	   System.out.println("toS: " + vent.toString());
	   int textWhere = vent.toString().indexOf("text=");
	   out ("Found text= at "+textWhere);
	   String ks = vent.toString().substring(textWhere+5,textWhere+6);
	   out ("Plucked '"+ks+"'");
	   Integer I = new Integer(ks);
	   
	   
	   if ( bG == null) {
		   return;
	   }
	   bH.setFileCount(I.intValue());
	   bH.buildMultifileActionPanel();
   }

}
