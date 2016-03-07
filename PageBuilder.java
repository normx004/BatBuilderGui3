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
		while (j < vfp.length) {
			filez[j] = vfp[j].getVideoFile();
			if (filez[j] != null ) {
				page.append(this.vidStart());
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
		Date dx = new Date();
		out (dx.getMonth()+" "+dx.getHours() + " " + dx.getMinutes()+ " "+ dx.getSeconds());
		String fn = new String(fileBase+"MultiVideo."+dx.getMonth()+"."+dx.getHours()+"."+dx.getMinutes()+"."
				+dx.getSeconds()+".htm");
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
		s=new String("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \n     \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"><head> \n <meta http-equiv=\"content-type\" content=\"text/html; charset=iso-8859-15\" />  <meta http-equiv=\"content-language\" content=\"en\" /> \n <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" /> \n <title>VLC Plugin Demo</title></head>\n<body>");
		return s;
	}
	public String vidStart () {
		String s= null;
		s = new String(" <Video  width=\"600\" height=\"400\" autoplay loop>	<source src=\"file:///");
		return s;
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
		
		p.out("header: " + p.head());
		p.out("\nvidStart: "+p.vidStart());
		p.out("\nvidEnd  : "+p.vidEnd());
		p.out("\nPageEnd:  "+p.pageEnd());
	}
}
