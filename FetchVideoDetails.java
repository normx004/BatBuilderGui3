package batboy;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.InputStreamReader;
//    this routine calculates the full path of the requested video,
//    and shells out via java 'exec' to run
//             <temp/tmp directory path>/checkVid.bash
//    (see the "getDuration" method below for text of the script)

//    The script returns a line like this:
//              Duration: 00:00:22.02, start: 0.000000, bitrate: 3111 kb/s
//    which is broken down to just the seconds of video in the file, in
//    this case 22.
// 
public class FetchVideoDetails {
 private String vidPath = null;
 private BatGUI bg= null;
 
 public       FetchVideoDetails( String vid, BatGUI _bg) {
	 out("contructor got video path "+vid);
     vidPath = vid;
     bg = _bg;
     
 }
 private void out(String s){
     System.out.println("FetchVideoDetails: " + s);
 }
 private String convertPath(String p) {
	 String result = null;
	 Boolean stay = false;
	 String  os = System.getProperty("OS");
	 if (os == null) {
		 // must be linux
		 return  p;
	 }
	 if ( os.compareTo("Windows") == 0) {
		 stay = true;
	 }
	 // must be linux
	 if (!stay) {
		 return p;
	 }
	 // else, windows...
	    String volum = vidPath.substring(0,1);
	    String volume = volum.toLowerCase();
	    StringBuffer bashPath = new StringBuffer("/mnt/" + volume + "/");
		String restOfIt = vidPath.substring(3);
		out("rest of path is "+restOfIt);
		String slash = restOfIt.replace('\\', '/');
	    result = bashPath + slash;
	 return result;
 }
 public int getDuration() {
	 out("OK, just entered method to get the video file's duration");
	 out("....path is "+vidPath);
	 //String localPath = convertPath(vidPath);
	 String localPath = vidPath;
	 out("...after convertPath (not necessary?) the path is "+localPath);
     Process p;
     int resulto = 0;
     try {
         /*   Note: the /tmp/checkVid.bash script looks like this:
		     #!/usr/bin/bash
		     cd /tmp
		     ffmpeg  -i  $1 2>/tmp/ffmpeg.out
 	  	   /usr/bin/grep  Duration /tmp/ffmpeg.out
		     rm -rf /tmp/ffmpeg.out
     	   */
    	
    	out("Derived path is "+localPath);
    	 // Test: Print system properties
        out("--------------------------------------SYSTEMPROPS------------------------------------");
        System.getProperties().list(System.out);
        out("--------------------------------------END of SYSTEMPROPS------------------------------------");
        
    	out("now looking at checkVid path; bg is "+bg);
    	//String cvid = bg.getCheckVid();
    	
    	String cVid = System.getProperty("checkVid");
    	out("Got pointer to check video details:"+cVid);
    	String[] cmd = { "bash", cVid,localPath};
    	String os = null;
    	os = System.getProperty("os.name");
    	if (os == null) {
    		out("OS came up null, so i'm gonna assume 'WINDOWS'");
    		os = "Windows";
    	} else {
    		out("OS system prop found, it is "+os);
    	}
    	int comp = os.compareTo("Linux");
    	out("os compare result is "+comp);
    	String drive = "C:\\";
    	String temp = "temp";
    	
    	if ( os == null || os.compareTo("Linux")==0) {
    		
    		// assume linux
    		drive = "";
    		temp = "tmp";
    		//String cVid = bg.getCheckVid();
    		String[] xcmd = { "/usr/bin/bash", cVid, vidPath};
    		cmd = xcmd;
    	}
    	int k = 0;
    	while (k < cmd.length) {
    		out("---cmd[" + k + "] is '" + cmd[k]);
    		k += 1;
    	}
    	out(".... about to do the exec....");
        
     p = Runtime.getRuntime().exec(cmd); 
     p.waitFor();
     out("Returned from thread....");
     /*
     BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
     String line; 
     while((line = reader.readLine()) != null) { 
     */
    
     String fileSep = System.getProperty("file.separator");
     File fyle = new File( drive + fileSep + temp + fileSep + "ffmpeg.out");
     out("FYLE: "+fyle. getPath());
     Scanner scanner = new Scanner(fyle);
     while (scanner.hasNextLine()) {
     	String lion = scanner.nextLine();
	    out("from thread: '" +lion + "'");
	    if (lion.contains("Duration")) {
	    	//'  Duration: 00:00:50.02, start: 0.000000, bitrate: 3117 kb/s'
	    	// 0123456789012345678901234
 	    	String dword = lion.substring(2,10);
	    	out("substring from thread: '" +dword + "'");
	    	String  hh = lion.substring(12,14);
	    	String  mm = lion.substring(15,17);
	    	String  ss = lion.substring(18,20);
	    	out("time from thread: " + hh +  ":" + mm + ":" + ss);
	    	// There MUST be an easier way to do this...
	    	Integer ihh = Integer.parseUnsignedInt(hh);
	    	Integer imm = Integer.parseUnsignedInt(mm);
	    	Integer iss = Integer.parseUnsignedInt(ss);
	       
		    int x = ihh.intValue();
	    	int y = imm.intValue();
	    	int z = iss.intValue();
	       
	    	int num = 3600*x + 60*y + z;
	    	resulto =  num;
	    }
	   } 
    } catch (IOException e) {
       // TODO Auto-generated catch block
          e.printStackTrace();
    } catch (InterruptedException e) {
       // TODO Auto-generated catch block
         e.printStackTrace();
    }
    return resulto;
    }
 //parse
 
 public static void main(String[] args) {
	 BatGUI x = null;
     FetchVideoDetails r = new FetchVideoDetails(new String("/tmp/kg.mp4"), x);
     System.out.println(r.getDuration());    
  }
}
