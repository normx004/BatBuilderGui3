package batboy;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class PageBuilder  {
    VideoFilePointer vfp[] = null;
    public int getImgCount() { return vfp.length;}
    BatGUI bg_ = null;
    Integer x_=null;
    Integer y_=null;
    
    int dur[] = null;
    int low_ = 99999;
	int high_ = 0;
    
    boolean useHttpServer = false;
    
	public PageBuilder(VideoFilePointer[] vp, BatGUI bg) {
		// TODO Auto-generated constructor stub
		vfp = vp;
		bg_ = bg;
		useHttpServer = bg_.isUseHttpServer(); 
		out("-------xxxx-------useHttpServer is set to "+useHttpServer+" ---------xxxxxxxxxx--------------");
	}
	public void out (String s) { System.out.println(s);}
	//@SuppressWarnings("deprecation")
	public int[] getDurations(VideoFilePointer vp[]) {
		int[] dur = new int[vp.length];
		int k = 0;
		while (k < vp.length) {
			String  theNum = vp[k].getDurationTextField().getText();
			out("got the text field for this vfp: "+theNum);
			dur[k] = Integer.parseInt(theNum);
			k += 1;
		}	
		return dur;
	}
	//----------------GENERATE PAGE------------------------------
	public String generatePage(){
		out("PageBuilder: generatePage()");
		StringBuffer page = new StringBuffer("");
		int dur[]  = getDurations(vfp);
		
		int ix = 0;
		while (ix < dur.length) {
			if (dur[ix] < low_) {
				low_ = dur[ix];
			}
			if (dur[ix] > high_) {
				high_ = dur[ix];
			}
			ix += 1;
		}
	    out("low duration: "+low_+", high duration: "+high_);
		// head() gets the page header including the HTML type and css pointers 
		// 
		page.append(this.head(vfp.length));
		// error msg just in case
		String status = new String("not enough files specified!");
		File filez[] = new File[vfp.length];
		int j = 0;
		int k = 0;
		Integer x = null;
		Integer y = null;
		
		// NOTE: vfp.length is the indicator of how many windows are to be put
		// on the page...each window showing a video or a list of videos.
		out("vfp.length is "+vfp.length);
		if (vfp.length == 2) {
			x = Integer.valueOf(System.getProperty("Dim2x"));
			y =  Integer.valueOf(System.getProperty("Dim2y"));
		} else if (vfp.length == 4) {
			x =  Integer.valueOf(System.getProperty("Dim4x"));
			y =  Integer.valueOf(System.getProperty("Dim4y"));
		} else if (vfp.length == 6) {
			x =  Integer.valueOf(System.getProperty("Dim6x"));
			y =  Integer.valueOf(System.getProperty("Dim6y"));	
		} else 	if (vfp.length == 9) {
			x =  Integer.valueOf(System.getProperty("Dim9x"));
			y =  Integer.valueOf(System.getProperty("Dim9y"));	
		}
		this.setX(x);
		this.setY(y);
		out ("Array: " + vfp.length+".  Height will be "+ y.toString() + ", width will be "+x.toString());
		// NOTE: need an extra "</div>" after each row, either 2 or 3 column...
		// could possibly clear these out (on a page refresh) with a variant of this JS:
		/* 
		 * in this loop:

			var div = document.getElementById('xyz');
			while (div) {
    			div.parentNode.removeChild(div);
    			div = document.getElementById('xyz');
			}
		 */
		if (vfp.length==2 || vfp.length == 4 )  {
			while (j < vfp.length) {
				boolean multiFile = false;
				LinkedList q = vfp[j].getFileQueue();
				if (q.size() > 1) {
					out ("MULTI FILES ON THIS VFP - new embed logic needed!!!!");
					multiFile = true;
				}
				filez[j] = vfp[j].getVideoFile();
				if (filez[j] != null ) {
					if ( j ==0 || j == 2 || j ==4 || j == 6) {
						if ( j == 2) {
							page.append("</div>");
						}
						page.append("\n<div id=\"aboutimages\">\n<div id=\"aboutimgleft\">\n");
					} else { 
						page.append("<div id=\"aboutimgright\">\n" );
					}
					// here's where we actually build the <video ... /video> entries
					String vEntry = null;
					out("checking multifile for lenght=2 or 4");
					if ( multiFile) {
						out("         ----yes, multifile");
						vEntry = this.makeMultiVideoEntry(q, j);
					} else {
						vEntry = this.makeVideoEntry(filez[j]);
					}
					
					page.append(vEntry);
					page.append("</div>\n");
					k += 1;
				}
				j += 1;
			}
			page.append("</div>");
		}
		if (vfp.length==6 || vfp.length == 9 )  {
			while (j < vfp.length) {
				boolean multiFile = false;
				LinkedList q = vfp[j].getFileQueue();
				if (q.size() > 1) {
					out ("MULTI FILES ON THIS VFP - new embed logic needed!!!!");
					multiFile = true;
				}
				filez[j] = vfp[j].getVideoFile();
				if (filez[j] != null ) {
					if ( j ==0 || j == 3  || j == 6) {
						if ( j == 3 || j == 6 ) {
							page.append("</div>");
						}
						page.append("\n<div id=\"aboutimages\">\n<div id=\"aboutimgleft\">\n");
					} else if (j==1 || j == 4 || j == 7){ 
						page.append("<div id=\"aboutimgcenter\">\n" );
					} else if (j ==2 || j == 5 || j == 8) {
						page.append("<div id=\"aboutimgright\">\n" );						
					}
					
					String vEntry = null;
					out("-----checking multifile for 6 or 9");
					if ( multiFile) {
						out("         yes, multifile");
						vEntry = this.makeMultiVideoEntry(q, j);
					} else {
						vEntry = this.makeVideoEntry(filez[j]);
					}
										
					page.append(vEntry);
					k += 1;
				}
				j += 1;
			}
			page.append("</div>");
		}
		
		
		page.append(this.pageEnd());
		if (k < vfp.length) {
			return status;
		}
		// now create a file name, and write the new page to the file
	    String fn = setFileName();		
		File of = new File(fn);
		this.writeFile(page, of);
		// all done, successful!
		status = new String("OK, generating page!");
		return status;
	}
	
	// -------------------------Multi now used for all multis-not just MP4----------------------------------------
	protected String makeMultiVideoEntry(LinkedList <File> q, int vidNum) {
		out("^^^^^^^^^entered make multi video entry^^^^^^^^^^^^^^^");
		
		StringBuffer v = new StringBuffer("");
	
		v.append( // do i need width/height here? YES!
				"<Video id=" +
				"\"myVideo" +  vidNum + "\"" + 
				" width=\"" + this.getX().toString() + "\" height=\"" + this.getY().toString() + "\" \n" +
				" autoPlay autoloop muted>\n" + 	
			    "</Video>\n</div>\n" 
			 	);
		
		/* like this:
		 *  <Video id="myVideo1" width="900" height="500" 
 		*	autoPlay autoloop >
		*	</Video>
		*	</div>
		 */
		
		String func = new String(		
				"<script>\n var vidsrc" + vidNum + " = new Array();\n");
		v.append(func);
		
		//clean up the filepaths - use fwd slash, for one thing...
		int idx = 0;
		while (idx < q.size()) {
				v.append("vidsrc" + vidNum + "["+idx+"]='");
				String path   = new String(q.get(idx).getPath());		
				StringBuffer rtn = new StringBuffer("");
				// path comes from the "drop" with windows separators...
				// gotta change to unix-style for the browser
				path = path.replaceAll("\\\\", "/");
				out("MakeMulti-vidoeentryfile path is "+path);
				int vidx = path.indexOf ("/vids");
				out("MakeMulti-videoentryfile index of 'vids' is "+vidx);
				if (vidx > 0) {
				    path = path.substring(vidx);
				    out("MakeMulti-videoentryfile path after substring is "+path);
				}
				
				v.append(path + "';\n");
				idx += 1;
		 }
		
		// here goes var H3 = new make the handler etc
		///String vfnc = new String ("var H" + vidNum +" = new makeTheMultiHandlerObject('vlcplayer"+
		//                           vidNum+"', vidsrc"+vidNum+", 0);\n");
		//v.append(vfnc);
		                           
		                           
		// followed by document.getElement...
	    v.append("videoPlay(0, vidsrc" +  vidNum + ", \"myVideo" + vidNum +"\");\n");
	    
		v.append("document.getElementById(\'myVideo" + vidNum +
				 "\').addEventListener('ended', function() { myHandler" + vidNum + "(\'" +
				 "myVideo" + vidNum + "\', vidsrc" + vidNum + ")}, false);\n");
		
		v.append("</script>\n");
		out("\n---------------make multi video entry returned:");
		out (v.toString());
		out("\n\n");
		return v.toString();
	}
	protected String makeVideoEntry(File fy) {
		String path   = new String(fy.getPath());
		int rindex    = path.lastIndexOf(".");
		String type   = path.substring(rindex, path.length());
		
		
		// want to go with straight html5 which no longer allows video plugins...so...
		// everything must be mp4 or webM
		/*
		boolean isMp4 = false;
		if (type.compareToIgnoreCase(".mp4")==0) {
			out ("MP4!!! Path: " + path);
			isMp4 = true;
		}
		
		// seems like plain mp4 is giving firefox hiccups, so...
		if (bg_.isUseHttpServer()) {
			isMp4 = false;
		}
		*/
		boolean    isMp4 = true;
		StringBuffer rtn = new StringBuffer("");
		String     start = this.vidStart(isMp4,this.getX(), this.getY());
		rtn.append(start);
		// path comes from the "drop" with windows separators...
		// gotta change to unix-style for the browser
		//
		//Path will look like Q:\Apache24\htdocs\vids\someDirectory\Somevideo.mp4
		//  --- needs to look like /vids/someDirectory/Somevideo.mp4
		
		path = path.replaceAll("\\\\", "/");
		out("Makevidoeentryfile path is "+path);
		int idx = path.indexOf ("/vids");
		out("Makevideoentryfile index of 'vids' is "+idx);
		if  (idx > -1) {
			path = path.substring(idx);
		}
		out("Makevideoentryfile path after substring is "+path);
		
		
		
		rtn.append(path);
		rtn.append(this.vidEnd(isMp4));
		rtn.append("\n");
		return rtn.toString();
	}
	
	
	
    public String getCss(String original, int count) {
    	String s = original;
    	CssGenerator c = new CssGenerator(count, bg_);
    	return(c.getCss(s));
    	/*
    	if ( count == 2) {
    		s = c.getCss(s);
    	} else if (count ==4) {
    		s = c.getCss(s);
    	} else if (count == 6) {
    		s = c.getCss(s);
    	} else if (count ==9 ) {
    		s = c.getCss(s);
    	}
    	return s;
    	*/
    }
	private String setFileName() {
		// we had enough files, so write the page to a file...
		String bar = new String("----------------------------------------------------");
	
		String fileBase = bg_.getFileBase();
		
		out ("htm file base is "+fileBase);
		Calendar now = Calendar.getInstance();
		int yr = now.get(Calendar.YEAR);
		int day = now.get(Calendar.DAY_OF_MONTH);
		int mo = now.get(Calendar.MONTH)+1;
		int hr = now.get(Calendar.HOUR);
		int min = now.get(Calendar.MINUTE);
		int sec = now.get(Calendar.SECOND);
		
		
		// add "how many files" to filename string
		String tag = new String("");
		if (this.getImgCount() == 2 ) {
			tag = new String("-two");
		} else {
			if (this.getImgCount() == 4 ) {
				tag = new String("-four");
			} else {
				if (this.getImgCount() == 6) {
					tag = new String ("-six");
				} else {
					if (this.getImgCount() == 9 ) {
						tag = new String("-nine");
					}
				}
			}
		}
		String m = 	(fixNum(yr) + "." + fixNum(mo)+"."+fixNum(day) + "."+ fixNum(hr) + "."+ fixNum(min) + "."+fixNum(sec));
		out(m);
		// if we're interacting with the http server, instead of just a local web
		// page, the file needs to be of type "php" so the code will function 
		// correctly.
		String suffix = new String(".htm");
		if ( bg_.isUseHttpServer()) {
			suffix = new String(".php");
		}
		String fn = new String(fileBase+"MultiVideo."+m+tag+suffix);
		out ("new filename: "+fn);
		bg_.setBatFile(fn);
	    return fn;
	}
	private String fixNum (int num) {
		Integer i = new Integer(num);
		String  o = i.toString();
		if (o.length() < 2) {
			o = "0" + o;
		}
		return o;
	}
    protected void writeFile(StringBuffer sb, File of) {
	  	  FileOutputStream os = null;
	  	  out ("writing htm file "+of.getPath());
	  	  try {
	  		os = new FileOutputStream(of);
	  		String st = new String(sb);
	  		byte b[]  = st.getBytes();
	  		//out(st);
	  		os.write(b);
	    	    os.close();
	  	  } catch (IOException ioe) {
	  		System.err.println("io error dealing with bat file to write: " + ioe.getMessage());
	  	  }
	  }
	
    /*PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \n     \" "+
		"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\"><html xmlns=\" "+
		"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"
		*/    
    
	public String head (int numSubWin) {

		out ("##################pagebuilder head##############");
		String s=null;
		s=new String("<!DOCTYPE HTML >\n"+
		"<head> \n "+
		"<meta http-equiv=\"content-type\" content=\"text/html; "+
		"charset=iso-8859-15\" />  <meta http-equiv=\"content-language\" "+
		"content=\"en\" /> \n "+
		
		"<title>Multi-Video " 
		// here's where we should add the min and max durations of the segments
		// into the title for subsequent screen captures...(such as Multivideo 3m10s to 4m43s)
		// old: "Demo</title>");
		    + " lo: " + low_ 
		    + " hi: " + high_
		    +	"</title>");
		String css = getCss(new String (""), this.getImgCount());

		// add JavaScript to provide variable background...if a directory
		// for the background files has been specified
		StringBuffer scrpt = new StringBuffer("");
		if ( bg_.getBackGroundDirectory().length() > 0) {
			out("Setting up BackGrounDirectory randomizer");
			BackgroundRandomizer bgr = new BackgroundRandomizer();
			scrpt = bgr.buildRandomizerLogic(bg_);
			/*scrpt = new StringBuffer
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
			 */
			}
			 
			 // now add the functions for multi-VLC entries
			// scrpt.append("<script>\n// ------------------------------utility function--------------- \n" +	
		    int vLoopIdx = 0;
		    
		    while (vLoopIdx < numSubWin) {
			scrpt.append("\n// ------------------------------utility function--------------- \n" +	
			 "function myHandler" + vLoopIdx + "(elName, videoArray) {\n" +
			 "if (typeof i"+vLoopIdx+" === 'undefined'){                                         \n" + 
			 "		i"+vLoopIdx+" = 1;                                                           \n" +
			 "	} else {                                                             \n " +
			 "		i"+vLoopIdx+" += 1;                                                         \n " +
			 "	}                                                                    \n " +
			 "	if ( i"+vLoopIdx+" === videoArray.length) {                                      \n " +
			 "		i"+vLoopIdx+" = 0;                                                          \n " +
			 "	}                                                                     \n" +
			 "	videoPlay(i"+vLoopIdx+", videoArray, elName);                                    \n " +
			 "	}                                                                    \n " +
			 " \n") ;//+
		    
			// "</script> \n" 
			
			 scrpt.append("	  function videoPlay ( videoNum, videoSource, el) { \n");
			 scrpt.append(
			 "	//-- create a play function that will get a page segment, set its 'videoPlay' target to the first array element-->:\n" +
			 "	//-- Note: the setAttribute should be in a function maybe with arguments of page segment and video-array-first-element-->\n" +
			 "	document.getElementById(el).setAttribute(\"src\",videoSource[videoNum]);\n" +
			 "	document.getElementById(el).load();\n" +
			 "	document.getElementById(el).play();\n" +
		     "}");
			 vLoopIdx += 1;
		    } // end of "vloop" to make individual handlers for each subwindow
			
			 
		     scrpt.append( "\n</script>\n"); 
			 
			 
			 // I THINK THIS SECTION HAS TO BE REPEATED FOR EACH ARRAY, with the APPROPRIATE NAMING 
			 // no-should go right after setting up the array of video file names
			 //scrpt.append(
			 //" <!-- add an event listenter that, at the end of a video in the array, will bump the array -->\n" +
			 //" <!-- counter, and play the video that the array counter points to (back to the first if at end -->\n" +
			 //"document.getElementById('<the tag for this array>').addEventListener('ended', function() { \n" +
			 //"       myHandler('<the tag for this array>', <arrayJustDefined>)}, false);\n"
			 //);
			 
			 // if we're going to run thru a local web server, can use php to make a random selection
			 // from any image-containing directory, not just one that has "img1.jpg", "img2.jpg", etc...
			 if (isUseHttpServer()) {
				 out("......IsUseHTTPServer for backgrounds.........");
				 MakeBackgroundSelectPhp mbsp = new MakeBackgroundSelectPhp();
				 String php = mbsp.getThePhpCode(bg_.getBackGroundDirectory(), bg_.getBackGroundDirectoryAlias());
				 scrpt.append(php);
				 
				 String theInitialBackground = new String(
						 "<script type=\"text/javascript\"> \n"+
						 " function setBack(){  \n"+
						 "  	var bodxx = document.getElementsByTagName('body')[0];\n"+
						 "      var imgString = \"url(" +
						 
							System.getProperty("BrowserBackgroundImageAlias")+
						 
						 
						 ")\";\n"+
						 "   	 console.log(\"script version \" + imgString);\n"+
						 "   	bodxx.style.background=imgString; \n"+
						 "   	}\n"+
						 "</script>\n"+
						 
						"<script>\n"+
						"function start() {\n"+
						"  setBack();\n"+
						"  backSet();\n"+
						"}\n"+
						"</script>\n"
						 );
				 
				 scrpt.append(theInitialBackground);
			 }
			
		
		String s1 = null;
		if (bg_.isUseHttpServer()) {
		    s1= new String( "\n</script></head>\n<body onload=\"start();\" ");
		} else {
			s1= new String( "\n</script></head>\n<body style=\"background-color:black;\" ");
		}
		
		String temp = s+css+scrpt+s1;
		s = temp;
		
		
		String t = new String(s+">");
		return t;
		}
		
	//public String vidStart (Integer x, Integer y) {
	//	String s   = new String(" <Video  width=\"");
	//	String s1  = new String("\" height=\"");
	//	String s2  = new String("\" autoplay loop>	<source src=\"file:///");
	//	String rtn = new String(
	//			s + x.toString() + s1 + y.toString() + s2);
	
	//String rtn  = new String(" <Video  autoplay loop>	<source src=\"file:///");
	//	return rtn;
	//}\
	

	public String vidStart (boolean isMp4, Integer x, Integer y) {
		String rtn = new String("");
	
		    //rtn  = new String(" <Video  autoplay autoloop>	<source src=\"file:///");
		    rtn  = new String(" <Video  autoplay loop muted>	<source src=\"");
		    return rtn;
	
	}
	public String vidEnd(boolean isMp4) {
		String rtn= new String("");
	
		  rtn = new String("\"	type='video/mp4;codecs=\"avc1.42E01E, mp4a.40.2\"'>  "+
		  "\n             <h3> Your browser does not support html5 video! </h3> </Video>\n</div>");
		  return rtn;
		
		
	}
	public String pageEnd() {
		String s = null;
		s = new String(" </body></html>");
		return s;
	}
	
	public static void main(String[] args) {
		BatGUI bg = new BatGUI();
		VideoFilePointer vp[] = new VideoFilePointer[1];
		
		PageBuilder p = new PageBuilder(vp,bg);
		
		String path = new String("C:\\Users\\norm\\Videos\\Misckellaneous\\FamousDiana\\W\\GNMiddayNews-Apr-18-2014WGNDT-11AM.mpg");
		p.out("Path before: "+path);
	    path = path.replaceAll("\\\\", "/");
	    p.out("Path after:  "+path);
	    Integer x = 500;
	    Integer y = 375;
		
		p.out("header: "     + p.head(4));
		p.out("\nvidStart: " + p.vidStart(false,x,y));
		p.out("\nvidEnd  : " + p.vidEnd(false));
		p.out("\nPageEnd:  " + p.pageEnd());
	}
	public Integer getX() {
		return x_;
	}
	public void setX(Integer x_) {
		this.x_ = x_;
	}
	public Integer getY() {
		return y_;
	}
	public void setY(Integer y_) {
		this.y_ = y_;
	}
	public boolean isUseHttpServer() {
		return useHttpServer;
	}
	
}
