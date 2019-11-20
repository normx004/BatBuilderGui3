package batboy;

public class BackgroundRandomizer {

	public BackgroundRandomizer() {
		// TODO Auto-generated constructor stub
	}
	public StringBuffer buildRandomizerLogic (BatGUI bg_) {
		StringBuffer s = null;
		if (bg_.isUseHttpServer()==false) {
			s = buildLocalPageLogic(bg_);
		} else {
			// using local http server!
			s = buildHttpServerLogic(bg_);
		}
		return s;
	}
	private void out(String s) {
		System.out.println(s);
	}
	StringBuffer buildHttpServerLogic(BatGUI bg_) {
	    StringBuffer scrpt = null;	
	
		if ( bg_.getBackGroundDirectory().length() > 0) {
			scrpt = new StringBuffer
					(
					"\n<script type=\"text/javascript\"> \n"+
					"  function backSet(){  \n"+
					"    setInterval(function() { \n"+
					"    	var bodx = document.getElementsByTagName('body')[0];\n"+
					"       var z = -1;\n" +
					"       while (z < 0 || z > filez.length) {\n" +
					"           z = Math.floor((Math.random() * filez.length) + 1);}\n"+
					"       console.log(\"httpRandom: \"+z);\n"+
					"    	var imgString = \"url(\" + filez[z] + \")\";\n");
			 
			 Integer bgImgInt = bg_.getBackgroundImageInterval();
			 scrpt.append("    	bodx.style.background=imgString; }, ");
			 scrpt.append(bgImgInt.toString());
			 scrpt.append(" ); } \n");
			 out("builgHttpserverLogic: backset script (start):\n                   "+scrpt);
	         }
		return scrpt;
	} // end local page logic

	StringBuffer buildLocalPageLogic(BatGUI bg_) {
	    StringBuffer scrpt = null;	
	
		if ( bg_.getBackGroundDirectory().length() > 0) {
			scrpt = new StringBuffer
					(
					"\n<script type=\"text/javascript\"> \n"+
					"		function getsec() {\n"+
					"	 var secx = new Date().getSeconds();\n"+
					"	 return secx;\n"+
					"	}\n"+
							
					"  function backSet(){  \n"+
					"  	var i =getsec(); \n"+
					"    setInterval(function() { \n"+
					"    	var bodx = document.getElementsByTagName('body')[0];\n"+
					"       var z = Math.floor((Math.random() * 100) + 1);\n"+
					"       console.log(\"Random: \"+z);\n"+
					"    	var imgString = \"url(file:///"); 
			 Integer bgImgInt = bg_.getBackgroundImageInterval();
			 scrpt.append(bg_.getBackGroundDirectory());
			 scrpt.append(
					"/img\" + z + \".jpg)\"; \n"+	
					"    	if (i > 100) { \n"+
					"    	   i = 1; \n"+
					"    	}\n"+
					"    	bodx.style.background=imgString; }, ");
			 scrpt.append(bgImgInt.toString());
			 scrpt.append(
					//"  }, 7000); \n"+
					" ); } \n");
	         }
		return scrpt;
	} // end local page logic
    
}
