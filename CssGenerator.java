package batboy;

public class CssGenerator {
    String cssString = null;
    private int count_ = 0;
	public CssGenerator(int count) {
		count_ = count;
		// TODO Auto-generated constructor stub
		if (count == 4) {
			Integer width  = new Integer(System.getProperty("Dim4x"));
			Integer height = new Integer(System.getProperty("Dim4y"));
			gen4(width, height); 
		} else if (count == 6) {
			Integer width  = new Integer(System.getProperty("Dim6x"));
			Integer height = new Integer(System.getProperty("Dim6y"));
			gen4(width, height); 
			//gen6();
		} else if (count == 9) {
			Integer width  = new Integer(System.getProperty("Dim9x"));
			Integer height = new Integer(System.getProperty("Dim9y"));
			gen4(width, height); 
			//gen9();
		} else if (count == 2) {
			Integer width  = new Integer(System.getProperty("Dim2x"));
			Integer height = new Integer(System.getProperty("Dim2y"));
			gen4(width, height); 
			//gen2();
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
			String css = new String (
			"<style>\n"+
			"  article, aside, figure, footer, header, hgroup,\n"+ 
			"  menu, nav, section { display: block; }\n"+
			"  #aboutimages{\n"+
			"    text-align: center;\n"+
			"    margin: 0 auto;\n"+
			"    width: 100%;\n"+
			"    margin-top: 200px; " +
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
			
			  if ( count_ != 2) {
		        	css = css.replaceAll("margin-top: 200px;", "margin-top: 10px;");
		        }
			
			
			String head = new String("<h2>Very Hot Video</h2>");
			cssString = css+head;
		}
	
	public void out(String s) { System.out.println(s);}
	
	private String gen4 (Integer width, Integer height) {
		out("gen4 width "+width.toString()+", height "+height.toString());
		String css = new String (
				"<style>\n"+
				"  article, aside, figure, footer, header, hgroup,\n"+ 
				"  menu, nav, section { display: block; }\n"+
				"  #aboutimages{\n"+
				"    text-align: center;\n"+
				"    margin: 0 auto;\n"+
				"    width: 100%;\n"+
				"      margin-top: 200px; " +
				"    }\n"+
				"\n"+  
				"  #aboutimgleft {\n"+
				"   display:inline-block;\n"+
				"    float: left;\n"+
				"    margin-left: 30px;\n"+
				"  }\n"+
	            "\n"+
				"#aboutimgleft video{\n"+
				"   width:WWWpx;\n"+
				"    height:HHHpx;\n"+
				"}\n"+ 
	            "\n"+
				"#aboutimgcenter {\n"+
				"    display:inline-block;\n"+
				"}\n"+
				"\n"+ 
				"#aboutimgcenter video{\n"+
				"    width:WWWpx;\n"+
				"    height:HHHpx;\n"+
				"\n"+    
				"}\n"+
				"#aboutimgright {\n"+
				"    display:inline-block;\n"+
				"    float: right;\n"+
				"    margin-right: 30px;\n"+
				"}\n"+ 
				"#aboutimgright video{\n"+
				"     width:WWWpx;\n"+
				"    height:HHHpx;\n"+
				"}\n"+ 
				"</style>");
		
		        css= css.replaceAll("WWW", width.toString());
		        //out("First replace: "+css);
		        css= css.replaceAll("HHH", height.toString());
		        //out("Second replace: "+css);
		        if ( count_ != 2) {
		          if ( count_ == 4) {
		        	css = css.replaceAll("margin-top: 200px;", "margin-top: 10px;");
		          } else if (count_ == 6) {
		        	css = css.replaceAll("margin-top: 200px;", "margin-top: 60px;");
		          } else if (count_ == 9) {
		        	css = css.replaceAll("margin-top: 200px;", "margin-top: 60px;");
		          }
		        }
				
				String head = new String("<h2>Very Hot Video</h2>");
				cssString = css+head;
				return cssString;
	}
	public String getCss(String s){
		return s + cssString;
	}
    public static void main(String[] args) {
    	System.setProperty("Dim4x", "600");
    	System.setProperty("Dim4y", "425");
    	String junky = new String("abcdefghi");
    	
    	CssGenerator cGen = new CssGenerator(4);
    	cGen.out("replaced: " + junky.replaceAll("def", "XXX"));
    	
    	String g = cGen.getCss("");
    	cGen.out("Result: "+g);
    }
}
