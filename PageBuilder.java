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
    
    
    
    
    
	public PageBuilder(VideoFilePointer[] vp, BatGUI bg) {
		// TODO Auto-generated constructor stub
		vfp = vp;
		bg_ = bg;
	}
	public void out (String s) { System.out.println(s);}
	@SuppressWarnings("deprecation")
	//----------------GENERATE PAGE------------------------------
	public String generatePage(){
		StringBuffer page = new StringBuffer("");
		// head() gets the page header including the HTML type and css pointers 
		// 
		page.append(this.head());
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
			x = new Integer(System.getProperty("Dim2x"));
			y = new Integer(System.getProperty("Dim2y"));
		} else if (vfp.length == 4) {
			x = new Integer(System.getProperty("Dim4x"));
			y = new Integer(System.getProperty("Dim4y"));
		} else if (vfp.length == 6) {
			x = new Integer(System.getProperty("Dim6x"));
			y = new Integer(System.getProperty("Dim6y"));	
		} else 	if (vfp.length == 9) {
			x = new Integer(System.getProperty("Dim9x"));
			y = new Integer(System.getProperty("Dim9y"));	
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
					if ( multiFile) {
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
					if ( multiFile) {
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
	
	// Multi now used for all multis-not just MP4
	protected String makeMultiVideoEntry(LinkedList <File> q, int vidNum) {
		
		// OK, now we know if we need to use the "embed vlc" code, or can just go with
		// plain html5 "video" tags...
		StringBuffer v = new StringBuffer("");
		v.append( // do i need width/height here? YES!
				"<embed  type=\"application/x-vlc-plugin\"     pluginspage=\"http://www.videolan.org\" \n"+
				" width=\"" + this.getX().toString() + "\" height=\"" + this.getY().toString() + "\" \n" +
				"version=\"VideoLAN.VLCPlugin.2\" id=\"vlcplayer" + vidNum + 
				"\" autoPlay=\"true\" autoloop=\"true\" ></embed>\n"+ 	
			    "</div>\n"+
			 	"<script>\n");
		String func = new String(		
				"var vidsrc" + vidNum + " = new Array();\n");
		v.append(func);
		
		// OK, now we know if we need to use the "embed vlc" code, or can just go with
		// plain html5 "video" tags...
		int idx = 0;
		while (idx < q.size()) {
				v.append("vidsrc" + vidNum + "["+idx+"]='");
				String path   = new String(q.get(idx).getPath());		
				StringBuffer rtn = new StringBuffer("");
				// path comes from the "drop" with windows separators...
				// gotta change to unix-style for the browser
				path = path.replaceAll("\\\\", "/");
				v.append("file:///" + path);
				v.append("';\n");
				idx += 1;
		 }
		
		// here goes var H3 = new make the handler etc
		String vfnc = new String ("var H" + vidNum +" = new makeTheMultiHandlerObject('vlcplayer"+
		                           vidNum+"', vidsrc"+vidNum+", 0);\n");
		v.append(vfnc);
		// followed by document.getElement...
	    //v.append("videoPlay(\"myVideo" + vidNum +"\", vidsrc" +  vidNum +", 0);\n");
		//v.append("document.getElementById('myVideo"+vidNum+
		//		 "').addEventListener('ended', function() { H"+vidNum+
		//		 ".show();}, false);\n");
		v.append("</script>\n");
		return v.toString();
	}
	protected String makeVideoEntry(File fy) {
		String path   = new String(fy.getPath());
		int rindex    = path.lastIndexOf(".");
		String type   = path.substring(rindex, path.length());
		boolean isMp4 = false;
		if (type.compareToIgnoreCase(".mp4")==0) {
			out ("MP4!!! Path: " + path);
			isMp4 = true;
		}
		
		StringBuffer rtn = new StringBuffer("");
		String start = this.vidStart(isMp4,this.getX(), this.getY());
		rtn.append(start);
		// path comes from the "drop" with windows separators...
		// gotta change to unix-style for the browser
		path = path.replaceAll("\\\\", "/");
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
	
		String fileBase = System.getProperty("filebase");
		out ("htm file base is "+fileBase);
		Calendar now = Calendar.getInstance();
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
		String m = 	(fixNum(mo)+"."+fixNum(day) + "."+ fixNum(hr) + "."+ fixNum(min) + "."+fixNum(sec));
		out(m);
		String fn = new String(fileBase+"MultiVideo."+m+tag+".htm");
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
	
	public String head () {
		String s=null;
		s=new String("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \n     \" "+
		"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\"><html xmlns=\" "+
		"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">\n"+
		"<head> \n "+
		"<meta http-equiv=\"content-type\" content=\"text/html; "+
		"charset=iso-8859-15\" />  <meta http-equiv=\"content-language\" "+
		"content=\"en\" /> \n "+
		"<title>VLC Plugin Demo</title>");
				
		String css = getCss(new String (""), this.getImgCount());

		// add JavaScript to provide variable background...if a directory
		// for the background files has been specified
		StringBuffer scrpt = new StringBuffer("");
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
			 // now add the functions for multi-VLC entries
			 scrpt.append("// ------------------------------utility function--------------- \n" +
			 "function getVLC(str) { \n" +
			 "	var yy = document.getElementById(str); \n" +
			 "	return yy; \n" +
			 " } \n" +
			 "  \n" +
			 " //-------------------------MAKE THE HANDLER OBJECT-------------------- \n" +
			 " 	  function makeTheMultiHandlerObject ( el, vid, ix) { \n" +
			 " 	  //console.log(\"entered make the handler ovject for \" + el); \n" +
			 "  	this.elName = el; \n" +
			 "  	this.vidZ = vid; \n" +
			 "  	this.idx = ix; \n" +
			 "  	var iz = 0; \n" +
			 "  	var vlc = getVLC(el); \n" +
			 "  	if (vlc === 'undefined') { \n" +
			 "  		 console.log(\"OOOPS in makeTheHandlerObject, couldn't find VLC\"); \n" +
			 "  	} \n" +
			 "  	while (iz < vid.length) { \n" +
			 "  		//console.log(\"Adding to playlist: ( \" + el + \") \" + vid[iz]); \n" +
			 "  		vlc.playlist.add(vid[iz]); \n" +
			 "  		var plen = vlc.playlist.items.count; \n" +
			 "  		//console.log(\"vlc.playlist.items.count: ( \" + el + \") \" + \" is now \" + plen); \n" +
			 "  		iz += 1; \n" +
			 "  	} \n" + 
			 "  	// this plays the playlist once...\"autoloop\" in the embed tag makes it loop \n" +
			 "  	vlc.playlist.play(); \n" +
			 "  } \n" +
			 "</script> \n"
			);
		}
		
		String s1= new String( "\n</head>\n<body");
		
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
		if ( isMp4) {
		    rtn  = new String(" <Video  autoplay loop>	<source src=\"file:///");
		    return rtn;
		}
		// not mp4, so use vlc plugin
		String embed1 = new String (
			"<embed type=\"application/x-vlc-plugin\"  ");
		String embed2 = new String(
			" pluginspage=\"http://www.videolan.org\"  "+
			" version=\"VideoLAN.VLCPlugin.2\" id=\"vlcplayer\" "+
		    " loop=\"true\" "+
		    " autoPlay=\"true\" autoLoop=\"true\" "+
		    " target=\"file:///");
		String embed1a = new String(" width=\"" + x.toString() + "\" height=\"" + y.toString()+" ");
		return embed1+embed1a+embed2;
	}
	public String vidEnd(boolean isMp4) {
		String rtn= new String("");
		if (isMp4) {
		  rtn = new String("\"	type='video/mp4;codecs=\"avc1.42E01E, mp4a.40.2\"'>  "+
		  "\n             <h3> Your browser does not support html5 video! </h3> </Video>\n</div>");
		  return rtn;
		}
		// not mp4, so use vlc plugin
		rtn = new String("\"\n</embed>\n</div>\n");
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
		
		p.out("header: " + p.head());
		p.out("\nvidStart: "+p.vidStart(false,x,y));
		p.out("\nvidEnd  : "+p.vidEnd(false));
		p.out("\nPageEnd:  "+p.pageEnd());
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
}
