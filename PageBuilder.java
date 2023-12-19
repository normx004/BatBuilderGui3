package batboy;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class PageBuilder  {
    VideoFilePointer vfpArray[] = null;
    public int getImgCount() { return vfpArray.length;}
    BatGUI bg_ = null;
    Integer x_=null;
    Integer y_=null;
    
    int dur_[] = null;
    int low_ = 99999;
	int high_ = 0;
	
	int vfpLength_;
    
    boolean useHttpServer = false;
    
	public PageBuilder(VideoFilePointer[] vp, BatGUI bg) {
		// TODO Auto-generated constructor stub
		vfpArray = vp;
		vfpLength_ = vp.length;
		out("vfpLength is "+vfpLength_);
		bg_ = bg;
		useHttpServer = bg_.isUseHttpServer(); 
		out("-------xxxx-------useHttpServer is set to "+useHttpServer+" ---------xxxxxxxxxx--------------");
	}
	public void out (String s) 
	{
		String s1 = "PageBuilder:"+s;
		System.out.println(s1);
	}
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
		// break it up!!!
		int dur_[]     = getDurations(vfpArray);
		//int vfpLength_ = vfpArray.length; 
		
		
		out("================start page=============================");
		String header = this.head(vfpArray.length);
		
		out("+++++++++++++++++video grid+++++++++++++++++++++++++++");
		String theGrid = this.makeVideoGrid(vfpArray);
		
		out("---------------------file list and event listeners-------------------");
		int z = 0;
		StringBuffer theFilms = new StringBuffer("\n<script>\n");
		while (z < vfpArray.length) {
	    	theFilms.append( this.getFileListsAndEventListeners(vfpArray[z], z));
	    	z += 1;
		}
		//theFilms.append("\n</script>\n");
		
		out("~~~~~~~~~~~~~~~~~~~~~~~~handlers and text toggles~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		//String finalScripts = this.getHandlersAndTextToggles();
		
		out("************************page end**********************************************");
		String pageEnd = this.pageEnd();
		
		StringBuffer page = new StringBuffer(header + theGrid + theFilms +  pageEnd);
		
		// now create a file name, and write the new page to the file
	    String fn = setFileName();		
		File of = new File(fn);
		this.writeFile(page, of);
		// all done, successful!
		String status = new String("OK, generating page!");
		return status;
		
	}
	
	//-----------------------contstruct X blocks like:------------------------------
	/*
	 * 
	 *  var vidsrc0 = ['vids/A.mp4', 'vids/B.mp4'];
	 *  var currentVideo0 = 0;
	 *  videoPlay(0, vidsrc0, "myVideo0");
	 *  document.getElementById('myVideo0').addEventListener('ended', function() { myHandler0('myVideo0', vidsrc0) }, false);
	 *  document.getElementById('myVideo0').addEventListener("click", toggleTextVisibility0);
	 * 
	 * private     LinkedList<File>       getVideoFilePointerFileList() {
	 *	return fileQueue;
	 */
	String getFileListsAndEventListeners(VideoFilePointer iq, int vidNum) {
		
		//now make the scripts to reference the video-containers with filelists and event listeners
		//clean up the filepaths - use fwd slash, for one thing...
		
		StringBuffer v = new StringBuffer("");
		v.append("var vidsrc" + vidNum + " = [");
		int q = iq.getVideoFilePointerFileList().size();
		out("File list is " + q + " files long for video number "+vidNum);
		LinkedList<File> qq = iq.getVideoFilePointerFileList();
		int idx = 0;
		while (idx < q) {
			   
				v.append("'");
				String path   = new String(qq.get(idx).getPath());		
				StringBuffer rtn = new StringBuffer("");
				// path comes from the "drop" with windows separators...
				// gotta change to unix-style for the browser
				path = path.replaceAll("\\\\", "/");
				out("MakeMulti-vidoeentry: file path is "+path);
				int vidx = path.indexOf ("/vids");
				out("MakeMulti-videoentry: file index of 'vids' is "+vidx);
				if (vidx+1 > 1) {
				    path = path.substring(vidx+1);
				    out("MakeMulti-videoentryfile path after substring is "+path);
				}
				if (idx < qq.size()-1) {
					v.append(path + "',");	
				}
				else 
				{
					v.append(path + "'];\n");
				}
				idx += 1;
		
		}
	                       
		// followed by document.getElement...
		v.append("var currentVideo" + vidNum + "= -1;\n");
		v.append("videoPlay(currentVideo" + vidNum + ", vidsrc" +  vidNum + ", \"myVideo" + vidNum +"\");\n");
	    
		v.append("document.getElementById(\'myVideo" + vidNum +
		 "\').addEventListener('ended', function() { myHandler" + vidNum + "(\'" +
		 "myVideo" + vidNum + "\', vidsrc" + vidNum + ")}, false);\n");
		
		v.append("document.getElementById(\'myVideo" + vidNum +
		 "\').addEventListener('click', toggleTextVisibility" + vidNum + ");\n");
		
	
	    	
		
		return v.toString();
		
	}
		
		
	String startPage() {	
		StringBuffer page = new StringBuffer("");
		
		dur_  = getDurations(vfpArray);
		
		int ix = 0;
		while (ix < dur_.length) {
			if (dur_[ix] < low_) {
				low_ = dur_[ix];
			}
			if (dur_[ix] > high_) {
				high_ = dur_[ix];
			}
			ix += 1;
		}
	    out("low duration: "+low_+", high duration: "+high_);
		// head() gets the page header including the HTML type and css pointers 
		// 
		page.append(this.head(vfpArray.length));
		out ("page.append completed");
		// error msg just in case
		String status = new String("not enough files specified!");
		File filez[]  = new File[vfpArray.length];
		int j = 0;
		int k = 0;
		Integer x = null;
		Integer y = null;
		
		vfpLength_ = vfpArray.length;
		
		// NOTE: vfp.length is the indicator of how many windows are to be put
		// on the page...each window showing a video or a list of videos.
		out("vfpArray.length is "+vfpArray.length);
		if (vfpArray.length == 2) {
			x = Integer.valueOf(System.getProperty("Dim2x"));
			y =  Integer.valueOf(System.getProperty("Dim2y"));
		} else if (vfpArray.length == 4) {
			x =  Integer.valueOf(System.getProperty("Dim4x"));
			y =  Integer.valueOf(System.getProperty("Dim4y"));
		} else if (vfpArray.length == 6) {
			x =  Integer.valueOf(System.getProperty("Dim6x"));
			y =  Integer.valueOf(System.getProperty("Dim6y"));	
		} else 	if (vfpArray.length == 9) {
			x =  Integer.valueOf(System.getProperty("Dim9x"));
			y =  Integer.valueOf(System.getProperty("Dim9y"));	
		}
		this.setX(x);
		this.setY(y);
		out ("Array: " + vfpArray.length+".  Height will be "+ y.toString() + ", width will be "+x.toString());
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
		if (vfpArray.length==2 || vfpArray.length == 4 )  {
			while (j < vfpArray.length) {
				boolean multiFile = false;
				LinkedList q = vfpArray[j].getFileQueue();
				if (q.size() > 1) {
					out ("MULTI FILES ON THIS VFP - new embed logic needed!!!! 2, 4 or 6");
					multiFile = true;
				}
				filez[j] = vfpArray[j].getVideoFile();
				if (filez[j] != null ) {
					if ( j ==0 || j == 2 || j ==4 || j == 6) {
						if ( j == 2) {
							page.append("</div>");
						}
						//page.append("\n<div id=\"aboutimages\">\n<div id=\"aboutimgleft\">\n");
						//page.append("\n<div class=\"video-container\">\n");
					} else { 
						//page.append("<div id=\"aboutimgright\">\n" );
						//page.append("<div class=\"video-container\">\n");
					}
					// here's where we actually build the <video ... /video> entries
					String vEntry = null;
					out("checking multifile for lenght=2 or 4");
					if ( multiFile) {
						out("         ----yes, multifile A");
						vEntry = this.makeMultiVideoEntry(q, j);
					} else {
						vEntry = this.makeVideoEntry(filez[j]);
					}
					
					page.append(vEntry);
					//page.append("</div>\n");
					k += 1;
				}
				j += 1;
			}
			//page.append("</div>");
		}
		// NOTEN NOTE NOTE this code for 6 and 9 is probably bad, neeeds rework
		String vEntry = null;
		if (vfpArray.length==6 || vfpArray.length == 9 )  {
			while (j < vfpArray.length) {
				boolean multiFile = false;
				LinkedList<File> q = vfpArray[j].getFileQueue();
				if (q.size() > 1) {
					out ("MULTI FILES ON THIS VFP 6 or 9 - new embed logic needed!!!!");
					multiFile = true;
				}
				filez[j] = vfpArray[j].getVideoFile();
				if (filez[j] != null ) {
					if ( j ==0 || j == 3  || j == 6) {
						if ( j == 3 || j == 6 ) {
							page.append("</div>");
						}
						//page.append("\n<div id=\"aboutimages\">\n<div id=\"aboutimgleft\">\n");
					} else if (j==1 || j == 4 || j == 7){ 
						//page.append("<div id=\"aboutimgcenter\">\n" );
					} else if (j ==2 || j == 5 || j == 8) {
						//page.append("<div id=\"aboutimgright\">\n" );						
					}
					
					 vEntry = null;
					out("-----checking multifile for 6 or 9");
					if ( multiFile) {
						out("         yes, multifile B");
						// generate the scrips that reference the video-containers... with
						// the list of files to show and the ended/click event listeners
						vEntry = this.makeMultiVideoEntry(q, j);
					} else {
						vEntry = this.makeVideoEntry(filez[j]);
					}
										
					page.append(vEntry);
					k += 1;
				}
				j += 1;
			}
			//page.append("</div>");
		}
		
		return vEntry;
		//page.append(this.pageEnd());
		//if (k < vfp.length) {
		//	out("uh oh, returning status, k is "+k);
		//	return status;
		//}
		
	}
	
	// -------------------------Multi now used for all multis-not just MP4----------------------------------------
	protected String makeMultiVideoEntry(LinkedList <File> q, int vidNum) {
		out("^^^^^^^^^entered make multi video entry^^^^^^^^^^^^^^^");
		// the list of files to show and the ended/click event listeners
		
		StringBuffer v = new StringBuffer("");
		
		int jdx = 0;
		out ("Q.size is "+q.size());
		
		// first, output the video-containers
		while (jdx < q.size()) {
		
			v.append("       <div class=\"video-container\">\n");
	
			v.append( // do i need width/height here? No!
				"          <Video id=" +
				"\"myVideo" +  vidNum + "\"" + 
				" autoPlay autoloop muted controls></video>\n"	
			   
			 	);
		
			v.append( // add the target for the clickable pathname display
				"          <div   id=\"texxxt" + vidNum + "\" class=\"text\" "+
				//" style=\"display:none\""+
				">" + 
				"<h2>Some text to display</h2> </div></div>\n "				
				);
			v.append("</div>\n");
			jdx += 1;
		}
		
		
		
		
		
		//noew make the scripts to reference the video-containers 
		//clean up the filepaths - use fwd slash, for one thing...
		int idx = 0;
		v = new StringBuffer("");
		v.append("<script>\n");
		while (idx < q.size()) {
			   
				v.append("'");
				String path   = new String(q.get(idx).getPath());		
				StringBuffer rtn = new StringBuffer("");
				// path comes from the "drop" with windows separators...
				// gotta change to unix-style for the browser
				path = path.replaceAll("\\\\", "/");
				out("MakeMulti-vidoeentry: file path is "+path);
				int vidx = path.indexOf ("/vids");
				out("MakeMulti-videoentry: file index of 'vids' is "+vidx);
				if (vidx+1 > 1) {
				    path = path.substring(vidx+1);
				    out("MakeMulti-videoentryfile path after substring is "+path);
				}
				if (idx < q.size()-1) {
					v.append(path + "',");	
				}
				else 
				{
					v.append(path + "'];\n");
				}
				idx += 1;
		
		
	     
		                           
		// followed by document.getElement...
		v.append("var currentVideo" + vidNum + "= -1;\n");
	    v.append("videoPlay(currentVideo" + vidNum + ", vidsrc" +  vidNum + ", \"myVideo" + vidNum +"\");\n");
	    
		v.append("document.getElementById(\'myVideo" + vidNum +
				 "\').addEventListener('ended', function() { myHandler" + vidNum + "(\'" +
				 "myVideo" + vidNum + "\', vidsrc" + vidNum + ")}, false);\n");
		
		v.append("document.getElementById(\'myVideo" + vidNum +
				 "\').addEventListener('click', toggleTextVisibility" + vidNum + ");\n");
		
	
	    }	
		
		//v.append("</script>\n");
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
		if  (idx+1 > 0) {
			path = path.substring(idx+1);
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
    	
    }
	private String setFileName() {
		// we had enough files, so write the page to a file...
		String bar = new String("----------------------------------------------------");
	
		String fileBase = bg_.getFileBase() + "/";
		
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
	  		System.err.println("PageBuilder 376: io error dealing with bat file to write: " + ioe.getMessage());
	  		// must be windows?
	  		try {
	  		File tof = new File("c:\\temp\\MultiVideo.TEST.htm");
	  		FileOutputStream tos = new FileOutputStream(tof);
	  		String st = new String(sb);
	  		byte b[]  = st.getBytes();
	  		//out(st);
	  		tos.write(b);
	    	tos.close();
	  	      } catch (IOException ioex) {
	  		    System.err.println("PageBuilder 388: io error dealing with bat file to write: " + ioex.getMessage());
	  	      }
	  	  }
	  }
	
    /*PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \n     \" "+
		"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\"><html xmlns=\" "+
		"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"
		*/    
    
	public String head (int numSubWin) {

		StringBuffer s=null;
		s=new StringBuffer("<!DOCTYPE HTML >\n"+
		"<html>\n" +
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
		// the rest of the header   
		    +	"</title>\n "
		    +	"<script type=\"text/javascript\" src=\"js/CssSelector.js\"></script> \n "
		    +	"<script type=\"text/javascript\" src=\"js/MultiVideo.subs.js\"></script> \n "
		    +	"<script>document.addEventListener(\"DOMContentLoaded\", function () { getCss(");
		s.append(vfpLength_);
		s.append(")}); </script>\n");
		    
                                   
		s.append("</script>\n");
		String t = s.toString();
		out("header:"+t);
		return t;
		
	}
	
	
	public String makeVideoGridNot() { 
	
		// add JavaScript to provide variable background...if a directory
		// for the background files has been specified
		StringBuffer scrpt = new StringBuffer("");
		
		// add in the video container css code that allows for text overlay....
		
		
			 // now add the functions for multi-VLC entries
			
		    int vLoopIdx = 0;
		    scrpt.append("\n<script>\n");
		    
			 
		     scrpt.append( "\ngetCss();\n</script>\n"); 
			 
			 
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
			return scrpt.toString();
	}
	
	
	//-------------------------------MAKE VIDEO GRID (1st block after header)------------------------------
	public String makeVideoGrid(VideoFilePointer[] vfp) {
		
		StringBuffer pagex = new StringBuffer("");
		String s           = new String("");
				
		s= new String( "\n</head>\n<body style=\"background-color:black;\";>\n");
				
		pagex.append(s);
		pagex.append("\n <div class=\"video-grid\">\n");
		
		int j =0;
		
		
		while (j < vfp.length) {
				boolean multiFile = false;
				pagex.append("  <div class=\"video-container\">\n");
				StringBuffer vEntry = new StringBuffer("");
				pagex.append(
					"     <video id=\"myVideo" + j + "\"  autoplay  muted controls></video>\n"
				   +"     <div id=\"texxxt" + j +"\" class=\"text\">Some text to display</div>\n"
				   +"</div>\n");	
				
				pagex.append(vEntry);
				j += 1;	
		}
				
		
			pagex.append("</div>");
		
		 return pagex.toString();
		}
		


	public String vidStart (boolean isMp4, Integer x, Integer y) {
		String rtn = new String("");
	
		   
		    rtn  = new String(" <Video  autoplay loop muted controls>	<source src=\"");
		    return rtn;
	
	}
	public String vidEnd(boolean isMp4) {
		String rtn= new String("");
	
		  rtn = new String("\"	type='video/mp4;codecs=\"avc1.42E01E, mp4a.40.2\"'>  "+
		  "\n             <h3> Your browser does not support html5 video! </h3> </Video>\n</div>");
		  return rtn;	
	}
	public String pageEnd() {
		StringBuffer s = new StringBuffer("");
		// set up click-events for pathname toggle
		int idx = vfpLength_;
		out("...pageEnd, loop counter is "+vfpLength_);
		int k = 0;
		//s.append("<script>\n");
		while (k < idx) {
			
			s.append( 
	        "function myHandler" + k + "(elName, videoArray) {\n"
	        +"   	currentVideo" + k + " = (currentVideo" + k + " + 1) % videoArray.length;\n"
	    	+"	videoPlay(currentVideo" + k + ", videoArray, elName);\n"
	        +"}	\n\n"
			
	    	
	    	);
		 k = k+1;
		}
		k = 0;
        while (k < idx) {
			
			s.append(         
	    	 "function toggleTextVisibility" + k + "() {\n"
	    	+ "    var textElement = document.getElementById('texxxt" + k + "');\n"
	    	+ "    showVidName('myVideo" + k + "', textElement);\n"
	    	+"}\n"
	    	);
		 k = k+1;
		}
		
		s.append(" </script></body>\n</html>\n");
		return s.toString();
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
