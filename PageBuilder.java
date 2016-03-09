package batboy;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class PageBuilder  {
    VideoFilePointer vfp[] = null;
	public PageBuilder(VideoFilePointer[] vp) {
		// TODO Auto-generated constructor stub
		vfp = vp;
	}
	public void out (String s) { System.out.println(s);}
	@SuppressWarnings("deprecation")
	public String generatePage(){
		StringBuffer page = new StringBuffer("");
		page.append(this.head());
		String status = new String("not enough files specified!");
		File filez[] = new File[vfp.length];
		int j = 0;
		int k = 0;
		Integer x = null;
		Integer y = null;
		if (vfp.length == 2) {
			x = new Integer(System.getProperty("Dim2x"));
			y = new Integer(System.getProperty("Dim2y"));
		} else if (vfp.length == 4) {
			x = new Integer(System.getProperty("Dim4x"));
			y = new Integer(System.getProperty("Dim4y"));
		} else if (vfp.length == 6) {
			x = new Integer(System.getProperty("Dim6x"));
			y = new Integer(System.getProperty("Dim6y"));	
		}
		out ("Height will be "+ y.toString() + ", width will be "+x.toString());
		while (j < vfp.length) {
			filez[j] = vfp[j].getVideoFile();
			if (filez[j] != null ) {
				page.append(this.vidStart(x,y));
			    String path = new String(filez[j].getPath());
			    //out("Path before: "+path);
			    path = path.replaceAll("\\\\", "/");
			    //out("Path after:  "+path);
				page.append(path);
				page.append(this.vidEnd());
				page.append("\n");
				k += 1;
			}
			j += 1;
		}
		page.append(this.pageEnd());
		if (k < vfp.length) {
			return status;
		}
		// we had enough files, so write the page to a file...
		String bar = new String("----------------------------------------------------");
		out ("\n\n\n"+bar+"\n"+page+"\n"+bar);
		String fileBase = System.getProperty("filebase");
		out ("htm file base is "+fileBase);
		Calendar now = Calendar.getInstance();
		int day = now.get(Calendar.DAY_OF_MONTH);
		int mo = now.get(Calendar.MONTH)+1;
		int hr = now.get(Calendar.HOUR);
		int min = now.get(Calendar.MINUTE);
		int sec = now.get(Calendar.SECOND);
		
		out (mo+" "+day + " " + hr + " "+ min + " "+sec);
		
		String fn = new String(fileBase+"MultiVideo."+mo+"."+day+"."+hr+"."
				+min+"."+sec+".htm");
		out ("new filename: "+fn);
		File of = new File(fn);
		this.writeFile(page, of);
		// all done, successful!
		status = new String("OK, generating page!");
		return status;
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
		s=new String("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \n     \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"><head> \n <meta http-equiv=\"content-type\" content=\"text/html; charset=iso-8859-15\" />  <meta http-equiv=\"content-language\" content=\"en\" /> \n <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" /> \n <title>VLC Plugin Demo</title></head>\n<body");
		String type = System.getProperty("BrowserBackgroundType");
		if (type == null) {
			out("No BrowserBackgroundType");
			String t = new String(s+">");
			return t;
		}
	
		if (type.compareTo("color") == 0) {
			String c = System.getProperty("BrowserBackgroundColor");
			if( c == null) {
				out("No color specified by BrowswerBackgroundColor");
				c = new String("#000000");
			}
			String t = new String(s+ " bgcolor=\"" + c + "\">");
			return t;
		}
		if (type.compareTo("image")==0) {
			String i = new String(System.getProperty("BrowserBackgroundImage"));
			if ( i == null) {
				out("No BrowserBackgroundImage specified, even for image type background");
				String t = new String(s+">");
				return t;
			}
			String t = new String(s+" background=\""+i+"\">");
			return t;
		}
		return s;
	}
	public String vidStart (Integer x, Integer y) {
		String s   = new String(" <Video  width=\"");
		String s1  = new String("\" height=\"");
		String s2  = new String("\" autoplay loop>	<source src=\"file:///");
		String rtn = new String(
				s + x.toString() + s1 + y.toString() + s2);
		return rtn;
	}
	public String vidEnd() {
		String s = null;
		s = new String("\"	type='video/mp4;codecs=\"avc1.42E01E, mp4a.40.2\"'>  <h3> Your browser does not support html5 video! </h3> </Video>");
		return s;
	}
	public String pageEnd() {
		String s = null;
		s = new String(" </body></html>");
		return s;
	}
	public static void main(String[] args) {
		VideoFilePointer vp[] = new VideoFilePointer[1];
		PageBuilder p = new PageBuilder(vp);
		String path = new String("C:\\Users\\norm\\Videos\\Misckellaneous\\FamousDiana\\W\\GNMiddayNews-Apr-18-2014WGNDT-11AM.mpg");
		p.out("Path before: "+path);
	    path = path.replaceAll("\\\\", "/");
	    p.out("Path after:  "+path);
	    Integer x = 500;
	    Integer y = 375;
		
		p.out("header: " + p.head());
		p.out("\nvidStart: "+p.vidStart(x,y));
		p.out("\nvidEnd  : "+p.vidEnd());
		p.out("\nPageEnd:  "+p.pageEnd());
	}
}
