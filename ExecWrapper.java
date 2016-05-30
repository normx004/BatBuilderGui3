package batboy;
import java.lang.*;
	import java.util.*;
	import java.io.*;
public class ExecWrapper {
	

	// Exec-script class runs script from java
	//
	//  single arg is string (or array of strings, one per word in command line)
	//  where string(s) define the name of a command file to execute...
	//
	//  might be nice if cmd  could be passed in directly...

	  private String[] cmds_ = null;
	  private String[] cmdsStart = null;
	  private Runtime rt_;
	  private String  os_;
	  private boolean debug_;
	  private boolean noStdOut_;
	  private Properties p_;
	  
	  private boolean isHtml_ =  false;
	  private String  cmd_ = null;
	  
	  public  Vector results_;
      //------------------construct with an ARRAY of strings----------------------
	  ExecWrapper (String[] t)
	    {
	     init();
	     out("Constructed from string array");
	     int sz = t.length + 3;
	     cmds_ = concatem(cmdsStart, t);
	    }
      //--------------construct with a string-----------------------
	  ExecWrapper (String t)
	    {
	     out("Plain string constructor: "+t);
	     init();

	     String[] loc = new String[1];
	     loc[0] = t;
	     cmds_ = concatem(cmdsStart,loc);
	       
	     if (debug_)
	        {
	           out("From-string constructor; Command: '" + t + "'");
	        }
	    }
	  //--------------construct for HTML style batGUI-----------------------
	  ExecWrapper (String cmd, boolean isHtml)
	    {
		 isHtml_ = true;
	     out("Command-based constructor for ExecWrapper: "+cmd);
	     cmd_ = cmd;
	     init();
	    }
      //----------------------COnCatEm-----------------------------------
	  public String[] concatem(String[] a, String[] b) {
	       int max = a.length + b.length;
	       String[] c = new String[max];
	       int idx  = 0;
	       int next = 0;
	       while (idx < a.length) {
	          c[idx] = a[idx];
	          idx += 1;
	          next += 1;	
	       }
	       next = 0;
	       while (idx < max) {
	       	  c[idx] = b[next];
	       	  idx += 1;
	       	  next += 1;
	       }    
	    return c;
	  }
	  
	  public void setDebug(boolean v)
	  {
	   debug_ = v;
	  }

	  public void setNoStdOut(boolean v)
	  {
	   noStdOut_ = v;
	   if ( debug_)
	      {
	       String tf = new String("true");
	       if (! v)
	          {
	           tf = new String("false");
	          }
	       out("Set 'no std out' to " + tf);
	      }
	  }
      private void out(String s) { System.out.println(s);}
	  public boolean getDebugFromProps()
	  {
	  boolean debug=false;
	  String de    = p_.getProperty("debug");
	     if ( de != null)
	     if (
	              de.compareTo("YES") == 0
	           || de.compareTo("yes") == 0
	           || de.compareTo("y") == 0
	           || de.compareTo("Y") == 0 
	        )
	         {
	          debug = true;
	         }
	      return debug;
	  }
	  
      //----------------------------------------INIT--------------------------------------
	  public void init()
	  {
		 rt_          = Runtime.getRuntime();
	     Properties p = System.getProperties();
	     p_           = p;
	     os_          = p.getProperty("os.name");
	     String os_a  = p.getProperty("os.arch");
	     debug_       = getDebugFromProps();

	     out("os_: " + os_ + "  arch: " + os_a);

	     if (       os_.compareTo("Windows NT") == 0 
	    		 || os_.compareTo("Windows XP") == 0
	    	     || os_.compareTo("Windows Vista") == 0
	    	     || os_.compareTo("Windows 7") == 0
	    	     || os_.compareTo("Windows 10") == 0
		    	 )
	        {
	    	 out("Using cmd /c format");
	         cmdsStart = new String[3];
	         cmdsStart[0] = "cmd";
	         cmdsStart[1] = "/c";
	        }
	     else
	        {
	         cmdsStart = new String[1];
	         cmdsStart[0] = new String(" ");
	        }

	   }


	  private Runtime getRt()
	  {
	   return rt_;
	  }
	  public String[] getCmds()
	  {
	   return cmds_;
	  }
	  //------------------------------------RUNIT---------------------------------------
	  public Vector runit()
	  {// returns vector of strings from stdout
	   Process         p;
	   DataInputStream inP;
	   Vector          tempRtn = new Vector();
	   
	   if ( debug_)
	     {
	      out("-----------here goes--------------------");
	     }

	   try{
		     String[] myWork = null;
		     myWork = getCmds();
             if (myWork == null) {
            	 myWork = getCmdsStart();
             }
             out("MyWork size: "+myWork.length);
             int x = 0;
             while (x < myWork.length) {
            	 out("mywork["+x+"]"+myWork[x]);
            	 x+=1;
             }
		     out("MyWork: "+myWork.toString());
		     x = 3;
		     int y = 0;
		     while (x < myWork.length) {
		    	 myWork[y] = myWork[x];
		    	 y +=1;
		    	 x +=1;
		     }
		     myWork[3] = null;
		     out("MyWork size: "+myWork.length);
             x = 0;
             while (x < myWork.length) {
            	 out("mywork["+x+"]"+myWork[x]);
            	 x+=1;
             }
	         p = getRt().exec(myWork);
	         if (!noStdOut_)
	            {
	             PrintWriter pOut = new PrintWriter(new BufferedOutputStream(p.getOutputStream()));
	             pOut.println(" ");
	             pOut.flush();
	             inP = new DataInputStream( new BufferedInputStream ( p.getInputStream()));
	             byte[] results = new byte[65535];
	             int byteCount = 1;
	             while (byteCount > 0)
	              {
	               byteCount = inP.read(results);
	               //if ( debug_)
	                  {
	                   out("Bytecount: " + byteCount);
	                  }
	               if ( byteCount != -1)
	                  {
	                   if ( debug_)
	                      {
	                       int k = 65534;
	                       boolean done = false;
	                       while (k > 0 && !done) {
	                       	   if( results[k] == 0) {
	                       	   	done=true;
	                       	   }
	                       	   k = k - 1;
	                       }
	                       String fragment = new String(results,0,k).trim();
	                       out("Got an input, its '" + fragment + "'");
	                      }
	                   String result = new String(results,0,byteCount).trim();
	                   tempRtn.addElement(result);
	                  }
	               else
	                  {
	                   //if ( debug_)
	                      {
	                       out("No (more) output!");
	                      }
	                  }
	              }
	            }

	         p.waitFor();
	         int exitValue = p.exitValue();
	         if (debug_)
	            {
	             out("Normal termination; exit value: " + exitValue);
	            }
	        }
	     catch (IOException io)
	        {
	         System.err.println("IO Exception: " + io);
		 io.printStackTrace(System.err);
	        }
	     catch (InterruptedException io)
	        {
	         System.err.println("Interrupted Exception: " + io);
		 io.printStackTrace(System.err);
	        }
	     catch (IllegalThreadStateException io)
	        {
	         System.err.println("Subprocess not yet terminated exception: " + io);
		 io.printStackTrace(System.err);
	        }
	   Vector rtn = new Vector();
	   int rIdx = 0;
	   while (rIdx < tempRtn.size())
	    {
		 StringTokenizer tkn = new StringTokenizer((String)tempRtn.elementAt(rIdx));
	     int tks = tkn.countTokens();
	     out("token count "+tks);
	     while ( tkn.hasMoreTokens())
	     {
	      rtn.addElement( tkn.nextToken("\n"));
	     }
	     rIdx += 1;
	    }
	   return rtn;
	  }
	  //----------------------doitHTML----------------------------------
	  public void doitHtml(String theCmd) {
		  out("In doitHTML....., the cmd is " + theCmd);
		  cmdsStart[2]  = cmd_;
		  // NOTE: at least with FireFox, the cmd /c screws up the system
		  // so that the filename of the html file is always said to be
		  // of improper format (or something like that)
		  
		  // on the other hand, it seems like the window gets kicked off but
		  // something blocks both the browser window and the java builder window
		  // so that the browser won't display its contents until the java builder is
		  // killed...weird.  didn't happen that way with cmd /c "brower" "html file"
		  String cmd = new String(/*"cmd /c " + */theCmd);
		  out("the command in doit: "+cmd);
		  //ExecWrapper e = new ExecWrapper(get);
		  String s=null;
		  try {
			  out ("In doitHtml try block");
			  Process p = Runtime.getRuntime().exec(cmd);
			  BufferedReader stdInput = new BufferedReader(new
	          InputStreamReader(p.getInputStream()));
	 
	          BufferedReader stdError = new BufferedReader(new
	                 InputStreamReader(p.getErrorStream()));
	 
	            // read the output from the command
	          out("Here is the standard output of the command:\n");
	          while (( s = stdInput.readLine()) != null) {
	                out(s);
	          }
	             
	          // read any errors from the attempted command
	          out("Here is the standard error of the command (if any):\n");
	          while ((s = stdError.readLine()) != null) {
	                out(s);
	          }
	             
	            //System.exit(0);
	        }
	        catch (IOException e) {
	            System.out.println("exception happened - here's what I know: ");
	            e.printStackTrace();
	            System.exit(-1);
	        }
		  
		   out("Doit Html Done!");
		  
	  }
	  //----------------------------DOIT---------------------------------------------
	   public void doit(String arg) {
		   
		 
		   File f              = null;
		     BufferedReader brdr = null;
		     Vector cmds         = new Vector();
		     boolean ok          = true;
		     Vector              r = new Vector();
		     int                 idx = 0;
		     
	     out("Expecting argument to be filespec of file of cmds");
	     
	    
	     try {
	     try {
	     	brdr = new BufferedReader(new FileReader(new File(arg)));
	        boolean more = true;
	    	while (more) {
	    					String s = brdr.readLine();
	    					if (s == null) {
	    						more = false;
	    					} else {
	    						cmds.add((Object)s);
	    					}
	    	}  // end while
	     } catch (IOException ioe) {
	     	System.err.println("Error in IO: " + ioe.getMessage());
	     	ioe.printStackTrace(System.err);
	     	ok = false;
	     	}	
	     if ( ! ok) {
	         System.exit(1);
	     }
		
	     out("Read " + cmds.size() + " lines from " + arg + "\n");
	     
	     //r = new ExecRunner[cmds.size()];
	     int vs = cmds.size();
	     //int idx = 0;
	     String s = null;
	     while (idx < vs) {
	         //System.out.println("idx is " + idx);
	         s = (String) cmds.elementAt(idx);
	         out("Cmd : " + idx + ": " + s);
	         String delim = new String("|");
	         StringTokenizer tkn = new StringTokenizer(s, delim);
		     Vector  cvec = new Vector();
		     int rIdx = 0;
		     while ( tkn.hasMoreTokens())
	               {
	                cvec.add( tkn.nextToken());
		            out("TOKEN: " + cvec.elementAt(rIdx));
	                rIdx += 1;
	               }
	        
	         
		 out("got " + rIdx + " tokens");
		 int  vecidx = 0;
		 String[] carray = new String[rIdx];
		 while ( vecidx < rIdx) {
			 String fragment = new String((String)cvec.elementAt(vecidx));
			 String frag = null;
			 //if ( fragment.indexOf(" ") > 0) {
			//	 frag = "\"" + fragment + "\"";
			 //} else {
				 frag = fragment;
			 //}
			 out("fragment[" + vecidx + "] = " + frag );
		     carray[vecidx] = frag;
		     vecidx += 1;
		 }
		     

		 ExecWrapper e = new ExecWrapper(carray);
		 r.add(new ExecRunner(e));
		 //r[rcount].start();
		 idx += 1;
	     }
	     int  k = 0;
	     while ( k < idx) {
	    	 out("Starting #"+k);
	    	 ((ExecRunner)r.elementAt(k)).start();
	    	 k+=1;
	     }
	     int j = 0;
	     while ( j < idx ) {
	    	 out("Join for #"+j+ " of " + idx);
	    	 ((ExecRunner)r.elementAt(j)).join();
	    	 ExecRunner er = (ExecRunner)r.elementAt(j);
	    	 if (er.results_ != null ){
	    		 out("vector returned has " + er.results_.size());
	    	 }
	    	 j+=1;
	     }
	  
	     out("Done!");
	    } catch (  	InterruptedException ie ) {
	    	System.err.println("Interrupted dammit " + ie.getMessage());
	    	ie.printStackTrace(System.err);
	    }
	  
	}
	 //-----------------------------------MAIN--------------------
		  public static void main (String[] args)
		    {
		     
		     ExecWrapper e = new ExecWrapper(args[0]);
		     
		     e.doit(args[0]);
		    }
		public String[] getCmdsStart() {
			return cmdsStart;
		}
		public void setCmdsStart(String[] cmdsStart) {
			this.cmdsStart = cmdsStart;
		}
		 
}
