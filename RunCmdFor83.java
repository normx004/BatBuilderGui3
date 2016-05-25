package batboy;

import java.io.*;

public class RunCmdFor83 {
	
private void out(String s) {
	System.out.println(s);
}
	
public RunCmdFor83() {
	out("RunCmdFor83: constructed!");
}

public String xlateTo83 (String fullWindowsName) {
	String shortName = new String("");
	out("RunCmdFor83: input string is "+fullWindowsName);
	 try {
	      String line;
	      String exLine = new String("cmd /c for %A in (\"" + fullWindowsName + "\") do @echo %~sA");
	      //Process p = Runtime.getRuntime().exec("cmd /c for %A in (\"C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe\") do @echo %~sA");
	      Process p = Runtime.getRuntime().exec(exLine);
	      BufferedReader bri = new BufferedReader
	        (new InputStreamReader(p.getInputStream()));
	      BufferedReader bre = new BufferedReader
	        (new InputStreamReader(p.getErrorStream()));
	      int k = 0;
	      while ((line = bri.readLine()) != null) {
	        out("RunCmdFor83-inputStream: " + line + "....k = "+k);
	      
	        // this doesn't work!!! why not?????
	        if ( line.matches("^[a-zA-Z]:")) {
	        	out("GOT IT! via match");
	        	shortName = line;
	        }
	        if ( k == 0)
	        {  
	        	out("Got it - via count!");
	        	shortName = line;
	        	break;
	        }
	        k += 1;
	      }
	      bri.close();
	      while ((line = bre.readLine()) != null) {
	        out("RunCmdFor83-errorStream: " + line);
	      }
	      bre.close();
	      p.waitFor();
	      out("RunCmdFor83: Done.");
	    }
	    catch (Exception err) {
	      err.printStackTrace();
	    }
	out("Shortname in RunCmdFor83 is " + shortName);
	return shortName; 
}

  public static void main(String args[]) {
        RunCmdFor83 rc = new RunCmdFor83();
        rc.out("input string is " + args[0]);
        String shortx = rc.xlateTo83(args[0]);
        rc.out(shortx);
  }
}