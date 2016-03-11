package batboy;

public class CssGenerator {
    String cssString = null;
	public CssGenerator(int count) {
		// TODO Auto-generated constructor stub
		if (count == 4) {
			gen4(); 
		} else if (count == 6) {
			gen6();
		} else if (count == 9) {
			gen9();
		} else if (count == 2) {
			gen2();
		}
	}
	private void gen2() {
	}
	private void gen6() {
	}
	private void gen9() {
	}
	private void gen4() {
			//cssString = new String("	<style> 	div.floatL {  float: left;  }\n  div.floatR {  float: right;  }  \n.columnRight {	width: 48%;	float: right;	padding: 10px;	margin-bottom: 20px;	}\n.columnLeft {	width: 48%;	float: left;	padding: 10px;	margin-bottom: 20px;}\n.columnMain {	width: 2%;	float: left;	padding: 10px;	margin-bottom: 20px;}\nh2 {  text-align: center;  color: pink;  background: grey;}  </style>\n");
			String s = new String (
			"<style>\n"+
			"  article, aside, figure, footer, header, hgroup,\n"+ 
			"  menu, nav, section { display: block; }\n"+
			"  #aboutimages{\n"+
			"    text-align: center;\n"+
			"    margin: 0 auto;\n"+
			"    width: 100%;\n"+
			"    }\n"+
			"\n"+  
			"  #aboutimgleft {\n"+
			"   display:inline-block;\n"+
			"    float: left;\n"+
			"    margin-left: 30px;\n"+
			"  }\n"+
            "\n"+
			"#aboutimgleft video{\n"+
			"   width:900px;\n"+
			"    height:500px;\n"+
			"}\n"+ 
            "\n"+
			"#aboutimgcenter {\n"+
			"    display:inline-block;\n"+
			"}\n"+
			"\n"+ 
			"#aboutimgcenter video{\n"+
			"    width:900px;\n"+
			"    height:500px;\n"+
			"\n"+    
			"}\n"+
			"#aboutimgright {\n"+
			"    display:inline-block;\n"+
			"    float: right;\n"+
			"    margin-right: 30px;\n"+
			"}\n"+ 
			"#aboutimgright video{\n"+
			"     width:900px;\n"+
			"    height:500px;\n"+
			"}\n"+ 
			"</style>");			
			
			
			String head = new String("<h2>Very Hot Video</h2>");
			cssString = s+head;
		}
	
	public String getCss(String s){
		return s + cssString;
	}

}
