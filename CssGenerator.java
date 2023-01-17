package batboy;

public class CssGenerator {
    String cssString = null;
    BatGUI bg_ = null;
    private int count_ = 0;
	public CssGenerator(int count, BatGUI bg) {
		bg_ = bg;
		count_ = count;
		// TODO Auto-generated constructor stub 
		// 2, 4 or 6 are only currently supported counts
		if (count == 4 || count == 2 ) {
			Integer width  = new Integer(System.getProperty("Dim4x"));
			Integer height = new Integer(System.getProperty("Dim4y"));
			//gen4(width, height);
			gen4();
		} else if (count == 6) {
			Integer width  = new Integer(System.getProperty("Dim6x"));
			Integer height = new Integer(System.getProperty("Dim6y"));
			//gen6(width, height); 
			gen6();
		} else if (count == 9) {
			Integer width  = new Integer(System.getProperty("Dim9x"));
			Integer height = new Integer(System.getProperty("Dim9y"));
			//gen4(width, height); 
			gen9();
		}
		/*else if (count == 2) {
			Integer width  = new Integer(System.getProperty("Dim2x"));
			Integer height = new Integer(System.getProperty("Dim2y"));
			gen4(width, height); 
			//gen2();
		}
		*/
	}
	//private void gen2() {
	//}
	//private void gen6() {
	//}
	private void gen9() {
	}
	private String gen4() {
			//cssString = new String("	<style> 	div.floatL {  float: left;  }\n  div.floatR {  float: right;  }  \n.columnRight {	width: 48%;	float: right;	padding: 10px;	margin-bottom: 20px;	}\n.columnLeft {	width: 48%;	float: left;	padding: 10px;	margin-bottom: 20px;}\n.columnMain {	width: 2%;	float: left;	padding: 10px;	margin-bottom: 20px;}\nh2 {  text-align: center;  color: pink;  background: grey;}  </style>\n");
		    String bodyStyle = this.genInitialBackground();
		   /* 
			String css = new String (
			bodyStyle + 
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
			*/
		    String css = new String( bodyStyle + 
		   " var w = window.innerWidth;    "+
		   " var linky = document.createElement(\'link\');   "+
		   "     linky.setAttribute(\'rel\', \'stylesheet\');    "+
		       
		   "     if ( w > 1366) {    "+
		   "         console.log(\"Setting css to 1980\");    "+
		   "        linky.setAttribute(\'href\', \'css/gui-1920-2-4.css\');   "+
		   " 	console.log(\"Setting css to 1980\");     "+      
		   " } else { " +
		   "         console.log(\"Setting css to 1366\");    "+
		   "         linky.setAttribute(\'href\', \'css/gui-1366-2-4.css\');    "+
		   "      	console.log(\"Setting css to 1366\");    " +
		   "};" +
		   
            "if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|BB|PlayBook|IEMobile|Windows Phone|Kindle|Silk|Opera Mini/i.test(navigator.userAgent)) {  "+  
            "    console.log(\"Setting css to MOBILE\");     "+
            "    linky.setAttribute('href', 'css/gui-mobile-2-4.css');    	"+
            "    console.log(\"Setting css to mobile\"); } "+
		    " document.head.appendChild(linky);  </script>"); 
		    
		    
		    
			String head = new String("");
			if (count_ != 9 && count_ != 4) {
			    head = new String("<h2>Very Hot Video</h2>");
			}
			cssString = css+head;
			return cssString;
		}
	
	public void out(String s) { System.out.println(s);}
	
	private String gen6 () {
		//out("gen4 width "+width.toString()+", height "+height.toString()+"  count_ is "+count_);
		String marginTop = new String ("60px;");
		
		String bodyStyle = this.genInitialBackground();
		/*
		StringBuffer css = new StringBuffer (
				bodyStyle + 
				"<style>\n"+
				"  article, aside, figure, footer, header, hgroup,\n"+ 
				"  menu, nav, section { display: block; }\n"+
				"  #aboutimages{\n"+
				"    text-align: center;\n"+
				"    margin: 0 auto;\n"+
				"    width: 100%;\n" +
				"    margin-top: ");
		css.append( marginTop +
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
		
		        String cssS= css.toString().replaceAll("WWW", width.toString());
		        //out("First replace: "+css);
		        cssS= cssS.replaceAll("HHH", height.toString());
		        //out("Second replace: "+css);
		        if ( count_ != 2) {
		          if ( count_ == 4) {
		        	cssS = cssS.replaceAll("margin-top: 200px;", "margin-top: 10px;");
		          } else if (count_ == 6) {
		        	cssS = cssS.replaceAll("margin-top: 200px;", "margin-top: 60px;");
		          } else if (count_ == 9) {
		        	cssS = cssS.replaceAll("margin-top: 200px;", "margin-top: 10px;");
		          }
		        }
		        */
		        String css = new String( bodyStyle + 
				   " var w = window.innerWidth;    "+
				   " var linky = document.createElement(\'link\');   "+
				   "     linky.setAttribute(\'rel\', \'stylesheet\');    "+
				       
				   "     if ( w > 1366) {    "+
				   "         console.log(\"Setting css to 1980\");    "+
				   "        linky.setAttribute(\'href\', \'css/gui-1920-6.css\');   "+
				   " 	console.log(\"Setting css to 1980\");     "+      
				   " } else { "+
				   "         console.log(\"Setting css to 1366\");    "+
				   "         linky.setAttribute(\'href\', \'css/gui-1366-6.css\');    "+
				   "      	console.log(\"Setting css to 1366\");    " +
				   "};" +
			       " document.head.appendChild(linky); </script> ");
				    
		        String head = new String("");
				if (count_ != 9 && count_ != 4) {
					head = new String("<h2>Very Hot Video</h2>");	
				}
				cssString = css+head;
				return cssString;
	}
	public String genInitialBackground() {
		/* see what to put in the background, based on the props
		 either an image, or a color, or maybe nothing
		Prototype goal output:
			<style>
		 		body {
		 		        // NOTE: if runniing on server, leave this background image
		 		        // blank; we'll handle it with javascript later in the process
		 		        //
		 				background-image: url("file:///c:/temp/v28.jpg");
		 				background-attachment: fixed;
		 				background-repeat: no-repeat;
		 				background-position: center;
		 				background-color: black;
		 		}          
		 	</style>
		*/ 	
		 	
				String type  = System.getProperty("BrowserBackgroundType");
				String bgimg = System.getProperty("BrowserBackgroundImage");
				
			
				String t     = new String ("<style>\n  body { \n");
				String rtn   = new String("");
				if (type == null) {
					out("No BrowserBackgroundType");
					rtn = new String(t+"\nbackground-color: black;\n   }\n   ");
				
				} else {
					if (type.compareTo("color") == 0) {
						String c = System.getProperty("BrowserBackgroundColor");
						if( c == null) {
							out("No color specified by BrowswerBackgroundColor");
							c = new String("#000000");
						}
						rtn = new String(t+ "\n bgcolor=\"" + c + "\"\n   }\n");
						
					}	else {
					if (type.compareTo("image")==0 && bg_.getBackGroundDirectory().length()==0){			
						if ( bgimg == null) {
							out("No BrowserBackgroundImage specified, even for image type background");
							rtn = new String(t+"\nbackground-color: black;\n   }\n ");
							
						}
				     }
					}
				}	
				
				if (!bg_.isUseHttpServer()) {
					out("backgrou image will be "+bgimg);
					String prefix = new String("file:///");
					
					out("Prefix will be "+prefix);
					 rtn = new String(t+" background-image: url(\"" + prefix + bgimg+"\");\n");
					if (bg_.getBackGroundDirectory().length() > 0) {
						if ( bgimg != null) {
							rtn = new String(t+rtn);
						} else {
							// start black, then get image backgrounds...
							rtn = new String(t+" bgcolor=\"black\" >");
						}   
						out("background diretive will be "+rtn);
					}
				} else {
					rtn = t;
				}
			
				
				// note: <script> at end of this sets up for the 'select css based on browser window size
				//       and number of video windows to show
				String rtnPlus = new String(
						"\n    background-position: center;\n"+
			    		"     background-attachment: fixed;\n" +
			    		"     background-size: cover;\n" +
						"     background-repeat: no-repeat;\n" +
						"</style><script>"
				       );
				String r = rtn + rtnPlus;
				return r;
	}
	public String getCss(String s){
		return s + cssString;
	}
    public static void main(String[] args) {
    	System.setProperty("Dim4x", "600");
    	System.setProperty("Dim4y", "425");
    	String junky = new String("abcdefghi");
    	
    	CssGenerator cGen = new CssGenerator(4, null);
    	cGen.out("replaced: " + junky.replaceAll("def", "XXX"));
    	
    	String g = cGen.getCss("");
    	cGen.out("Result: "+g);
    }
}
