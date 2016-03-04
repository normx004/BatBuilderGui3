package batboy;

import java.util.*;
import java.io.*;

public class CmdFile {
	   private   Vector<CmdLine> cmds = null;
	   private   String batFile = null;
	   private   String vlcPathString = null;
	   
	   // Max Iter is how many times we want to try to make a unique filename
	   final int MAXITER = 4;
	   
	    public int getVectorLength () {
	    	return cmds.size();
	    }
	    //---------------toString----------------------------
	    public String toString() {
	    	StringBuffer sb = new StringBuffer("cmdFile: ");
	    	
	    	if (cmds == null) {
	    	     	sb.append(" cmds is null\n");
	    	}  	else  	{
	    	        sb.append(" cmds has " + cmds.size() + " elements\n"); 	
	    	}
	    	
	    	sb.append("bat file name " + batFile + "\n");
	    	sb.append("vlcPath: " + vlcPathString + "\n");
	    	sb.append("--------------------------\n");
	        return sb.toString();
	    }
	    //------------------getBatFile-----------------------------
		public String getBatFile() {
			return batFile;
		}
		public void  setBatFile(String s) {
			//out("Setting batfile to \"" + s + "\" in CmdFile");
			batFile = s;
		}
	  
		public  void setVlcPathString(String s) {
			vlcPathString = s;
		}
		public String getVlcPathString() {
			return vlcPathString;
		}
	
	
	   public CmdFile(String tg) {
		   out("WARNING---string "+tg+" IGNORED on CmdFile contruction");
		   cmds = new Vector<CmdLine>();
	   }
	   public CmdFile () {
		   cmds = new Vector<CmdLine>();
	   }
       public CmdFile( Vector<CmdLine> cl) {
    	   cmds = cl;
       }
       
       public void addCmd(CmdLine c) {
    	   cmds.add(c);
       }
       
       
   public void test() {
	   test(Boolean.TRUE);
   }
       
    public void test(Boolean overwrite) {
    	// run the file and see how it looks
    	writeOutputFile(overwrite);
    	ExecWrapper e = new ExecWrapper(this.getBatFile());
    	e.setDebug(true);
    	e.doit(this.getBatFile());
    	
    }
    private void out(String s) {
		  System.out.println("CmdFile: " + s);
	  }
       
  
   	public void writeOutputFile(Boolean _overwrite) {
   		out("writing output file overwrite = "+_overwrite.toString());
   		boolean overwrite = _overwrite.booleanValue();
   		/*
   		final int MAXITER = 4;
		out("--------writing output file----------------");
		String bf = this.getBatFile();
		int last = bf.lastIndexOf('.');
		String last4 = bf.substring(last, last+4);
		out("last 4 of input was \"" + last4 + "\"");
		if ( last4.compareTo(".bat") != 0) {
			bf = bf + ".bat";
		}
		File  of = new File(bf);
		
		int count = 0;
		out("checking bat file "+bf);
		if (of.exists() && !overwrite ) {
			while (of.exists() && count < MAXITER) {
				out("............already exists....");
				bf = new String(bf+".bat");
				out ("checking " + bf);
				of = new File(bf);
				count +=1 ;
			}
		}
		if (count > MAXITER-2 && !overwrite) {
			System.err.println("too many bat file iterations already exist, go delete some");
			return;
		}
		*/
   		File of = createNewFile(overwrite, false);
		// should have a writeable file object ('of')  now
		int len = cmds.size();
		out("vector of cmd lines has " + len + " entries.");
		int idx = 0;
		// if debug probably
		while (idx < len) {
			CmdLine k = cmds.elementAt(idx);
			out ("------------------"+idx+"----------------------");
			String m = k.toString();
			String n = this.toString();
			out (m);
			out (n);
			out ("----------------------------------------");
			idx += 1;
		}
		
		FileOutputStream os = null;
		try {
			 os = new FileOutputStream(of);
		
		idx = 0;
		while (idx < len) {
			out("cmd line number "+ idx);
			CmdLine k = cmds.elementAt(idx);
			StringBuffer sb = new StringBuffer(" ");
            
			sb.append("\""          + this.getVlcPathString().trim() + "\" --no-embedded-video --video-x=");
			sb.append(k.getX()      + " --video-y=");
			sb.append(k.getY()      + " --volume=20 --start-time ");
			sb.append(k.getStart()  + " --stop-time ");
			sb.append(k.getEnd()    + " --play-and-exit --video-on-top ");
			sb.append("--rate="     + k.getSpeed());
			// --width 600 --height 400 
			sb.append(" --width="+k.getXsize()+" ");
			sb.append(" --height="+k.getYsize()+" ");
			
			if (k.getAspect().length() > 0 ) {
				sb.append(" --aspect-ratio="+k.getAspect()+" ");
			}
			
			sb. append("\""         + k.getTargetVideoFile().trim() + "\"\n");
			String st = new String(sb);
			byte b[]  = st.getBytes();
			out(st);
			os.write(b);
		    idx += 1;
		}
		os.close();
		} catch (IOException ioe) {
			System.err.println("io error dealing with bat file to write: " + ioe.getMessage());
		}
		
     }
   	
   	
	public void writeOutputDriverFile(Boolean _overwrite) {
   		out("writing output driver file overwrite = "+_overwrite.toString());
   		boolean overwrite = _overwrite.booleanValue();
   	
   		File of = createNewFile(overwrite, false);
		// should have a writeable file object ('of')  now
		
		FileOutputStream os = null;
		try {
			 os = new FileOutputStream(of);
		
			StringBuffer sb = new StringBuffer(" ");
			/*
			if ( this.getSerial()) {
				//out ("not parallel");
			}	else {
				sb.append(" start \"bozo\" ");
			}
			*/
			sb.append("\""          + this.getVlcPathString().trim() + "\" ");
			sb.append(" --no-video-title-show ");
			sb.append(" --qt-minimal-view ");
			
			// now add to-be-invoked XML file name
			StringBuffer bf = new StringBuffer(this.getBatFile());
			int last = bf.lastIndexOf(".");
			String last4 = bf.substring(last, last+4);
			out("last 4 of input was \"" + last4 + "\"");
			if ( last4.compareTo(".bat") != 0) {
					bf = bf.append( ".bat");
			}
			bf.append(".xspf");
						
			String st = new String(sb.toString() + " \"" + bf.toString()+"\"");
			byte b[]  = st.getBytes();
			out(st);
			os.write(b);
		   
		
		    os.close();
		} catch (IOException ioe) {
			System.err.println("io error dealing with bat file to write: " + ioe.getMessage());
		}
		
     }
   	
   	
   	private String preamble = new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?><playlist version=\"1\" xmlns=\"http://xspf.org/ns/0/\" xmlns:vlc=\"http://www.videolan.org/vlc/playlist/ns/0/\"><title>Playlist</title><location>\"");
   	
   	
   	private File createNewFile(Boolean ovw, boolean writeXML) {
   		
   		StringBuffer bf = new StringBuffer(this.getBatFile());
	
		int last = bf.lastIndexOf(".");
		String last4 = bf.substring(last, last+4);
		out("last 4 of input was \"" + last4 + "\"");
		if ( last4.compareTo(".bat") != 0) {
				bf = bf.append( ".bat");
		}
		if (writeXML) {
			bf.append(".xspf");
		}
		File  of = new File(bf.toString());
		out("creating "+bf.toString());
		
		int count = 0;
		out("checking bat file "+bf);
		if (of.exists() && !ovw ) {
			while (of.exists() && count < MAXITER) {
				out("............already exists....");
				bf = new StringBuffer(bf.append(".bat"));
				out ("checking " + bf);
				of = new File(bf.toString());
				count +=1 ;
			}
		}
		if (count > MAXITER-2 && !ovw) {
			System.err.println("too many bat file iterations already exist, go delete some");
			return null;
		}
		return of;
   	}
   	
   	
 	public void writeOutputFileXML(Boolean _overwrite) {
 		
 		writeOutputDriverFile(_overwrite);
 		
   		out("writing output xspf file overwrite = "+_overwrite.toString()+".xspf");
   		boolean overwrite = _overwrite.booleanValue();
   		
		out("--------writing output file----------------");
		/*
		StringBuffer bf = new StringBuffer(this.getBatFile());
		//int j = bf.
		int last = bf.lastIndexOf(".");
		String last4 = bf.substring(last, last+4);
		out("last 4 of input was \"" + last4 + "\"");
		if ( last4.compareTo(".bat") != 0) {
				bf = bf.append( ".bat");
		}
		bf.append(".xspf");
		File  of = new File(bf.toString());
		out("creating "+bf.toString());
		
		int count = 0;
		out("checking bat file "+bf);
		if (of.exists() && !overwrite ) {
			while (of.exists() && count < MAXITER) {
				out("............already exists....");
				bf = new StringBuffer(bf.append(".bat"));
				out ("checking " + bf);
				of = new File(bf.toString());
				count +=1 ;
			}
		}
		if (count > MAXITER-2 && !overwrite) {
			System.err.println("too many bat file iterations already exist, go delete some");
			return;
		}
		*/
		File of = createNewFile(_overwrite, true);
		// should have a writeable file object ('of')  now
		int len = cmds.size();
		out("vector of cmd lines has " + len + " entries.");
		int idx = 0;
		// if debug probably
		while (idx < len) {
			CmdLine k = cmds.elementAt(idx);
			idx += 1;
			out ("----------------------------------------");
			String m = k.toString();
			String n = this.toString();
			out (m);
			out (n);
			out ("----------------------------------------");
		}
		
		FileOutputStream os = null;
		try {
			 os = new FileOutputStream(of);
			 String hdr=new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?><playlist version=\"1\" xmlns=\"http://xspf.org/ns/0/\" xmlns:vlc=\"http://www.videolan.org/vlc/playlist/ns/0/\">   <title>Playlist</title>   <location>");
			 byte h[]= hdr.getBytes();
			 out (hdr);
			 // location = this file path
			 os.write(h);
			 os.write(of.getName().getBytes());
			 // end the 'location' tag? element?, start the envelope 'tracklist'
			 // Seems like the xspf file wants to have "file:///" in front of "w:\dir\otherdir\file.wmv"
			 //String loc=new String("<location>");
			 String loc=new String("<location>file:///");
			 String endLoc=new String("</location>");
			 
			 os.write(endLoc.getBytes());			 
			 
			 String tl = new String("<trackList>\n");
			 
			 os.write(tl.getBytes());
			 
			 String track=new String("<track><title>");
			 // modify for file:///
			 //String endTitle=new String("</title><location>");
			 String endTitle=new String("</title>");
			 
			 // now write an entry for each file to be played with its associated options
			 idx = 0;
			 while (idx < len) {
				 int jdx = idx +1;
				 out("cmd line number "+ idx);
				 CmdLine k = cmds.elementAt(idx);
				 // write track envelope, then title element
				 os.write(track.getBytes());
				 String title=new String("title" + idx);
				 os.write(title.getBytes());
				 os.write(endTitle.getBytes());
				 os.write(loc.getBytes());
				 // write location of the file to play
				 os.write(k.getTargetVideoFile().trim().getBytes());
				 os.write(endLoc.getBytes());
				 // extensions (cmd line args) go in extension block
				 String ex=new String("<extension application=\"http://www.videolan.org/vlc/playlist/0\">");
				 String endEx = new String("</extension></track>\n");
				 os.write(ex.getBytes());
				 //track id
				 String tid=new String("<vlc:id>"+jdx+"</vlc:id>");
				 os.write(tid.getBytes());
				 
				 // example option:  <vlc:option>start-time=42</vlc:option>
				 
				 os.write(makeOpt("video-x", k.getX()));
				 os.write(makeOpt("video-y", k.getY()));
				 os.write(makeOpt("volume","20"));
				 os.write(makeOpt("start-time",k.getStart()));
				 os.write(makeOpt("stop-time",k.getEnd()));
				 //os.write(makeOpt("play-and-exit",""));
				 os.write(makeOpt("video-on-top",""));
				 os.write(makeOpt("rate",k.getSpeed()));
				 if (k.getAspect() != null) {
					 if ( k.getAspect().length() > 0) {
						 os.write(makeOpt("aspect-ratio", k.getAspect()));
					 }
				 }
				// --width 600 --height 400 
				 out("x size is '" + k.getXsize() + "'");
				 if (k.getXsize().compareTo("0") != 0)
				 {
				  os.write(makeOpt("width", k.getXsize()));
				  os.write(makeOpt("height",k.getYsize()));
				 }
				 os.write(endEx.getBytes());
				 idx += 1;
			 }
			 String endit = new String("</trackList></playlist>");
			 os.write(endit.getBytes());
			 os.close();
		} catch (IOException ioe) {
			System.err.println("io error dealing with bat file to write: " + ioe.getMessage());
		}
		
     }
   	 private byte[] makeOpt(String opt, String val) {
   		 StringBuffer o = new StringBuffer("<vlc:option>");
   		 o.append(opt);
   		 if ( val.length() > 0) {
   		     o.append("=");
   		 }
   		 o.append(val);
   		 o.append("</vlc:option>");
   		 return o.toString().getBytes();
   	 }
   	
   }

