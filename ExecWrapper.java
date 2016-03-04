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
	  private String[] cmdsStart_ = null;
	  private Runtime rt_;
	  private String  os_;
	  private boolean debug_;
	  private boolean noStdOut_;
	  private Properties p_;
	  
	  public  Vector results_;

	  ExecWrapper (String[] t)
	    {
	     init();
	     System.out.println("Constructed from string array");
	     int sz = t.length + 3;
	     cmds_ = concatem(cmdsStart_, t);
	    }

	  ExecWrapper (String t)
	    {
	     System.out.println("Plain string constructor: "+t);
	     init();

	     String[] loc = new String[1];
	     loc[0] = t;
	     cmds_ = concatem(cmdsStart_,loc);
	       
	     if (debug_)
	        {
	           System.out.println("From-string constructor; Command: '" + t + "'");
	        }
	    }

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
	       System.out.println("Set 'no std out' to " + tf);
	      }
	  }

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
	 

	  public void init()
	  {
	     rt_          = Runtime.getRuntime();
	     Properties p = System.getProperties();
	     p_           = p;
	     os_          = p.getProperty("os.name");
	     String os_a  = p.getProperty("os.arch");
	     debug_       = getDebugFromProps();

	     System.out.println("os_: " + os_ + "  arch: " + os_a);

	     if (       os_.compareTo("Windows NT") == 0 
	    		 || os_.compareTo("Windows XP") == 0
	    	     || os_.compareTo("Windows Vista") == 0
	    	     || os_.compareTo("Windows 7") == 0
		    	 )
	        {
	    	 System.out.println("Using cmd /c format");
	         cmdsStart_ = new String[2];
	         cmdsStart_[0] = "cmd";
	         cmdsStart_[1] = "/c";
	        }
	     else
	        {
	         cmdsStart_ = new String[1];
	         cmdsStart_[0] = new String(" ");
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
	      System.out.println("-----------here goes--------------------");
	     }

	   try{
	         p = getRt().exec(getCmds());
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
	                   System.out.println("Bytecount: " + byteCount);
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
	                       System.out.println("Got an input, its '" + fragment + "'");
	                      }
	                   String result = new String(results,0,byteCount).trim();
	                   tempRtn.addElement(result);
	                  }
	               else
	                  {
	                   //if ( debug_)
	                      {
	                       System.out.println("No (more) output!");
	                      }
	                  }
	              }
	            }

	         p.waitFor();
	         int exitValue = p.exitValue();
	         if (debug_)
	            {
	             System.out.println("Normal termination; exit value: " + exitValue);
	            }
	        }
	     catch (IOException io)
	        {
	         System.out.println("IO Exception: " + io);
		 io.printStackTrace(System.err);
	        }
	     catch (InterruptedException io)
	        {
	         System.out.println("Interrupted Exception: " + io);
		 io.printStackTrace(System.err);
	        }
	     catch (IllegalThreadStateException io)
	        {
	         System.out.println("Subprocess not yet terminated exception: " + io);
		 io.printStackTrace(System.err);
	        }
	   Vector rtn = new Vector();
	   int rIdx = 0;
	   while (rIdx < tempRtn.size())
	    {
		 StringTokenizer tkn = new StringTokenizer((String)tempRtn.elementAt(rIdx));
	     int tks = tkn.countTokens();
	     System.out.println("token count "+tks);
	     while ( tkn.hasMoreTokens())
	     {
	      rtn.addElement( tkn.nextToken("\n"));
	     }
	     rIdx += 1;
	    }
	   return rtn;
	  }
	 
	  public static void main (String[] args)
	    {
	     
	     ExecWrapper e = new ExecWrapper(args[0]);
	     
	     e.doit(args[0]);
	    }
	  
	  
	   public void doit(String arg) {
	     
		   File f              = null;
		     BufferedReader brdr = null;
		     Vector cmds         = new Vector();
		     boolean ok          = true;
		     Vector              r = new Vector();
		     int                 idx = 0;
		     
	     System.out.println("Expecting argument to be filespec of file of cmds");
	     
	    
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
		
	     System.out.println("Read " + cmds.size() + " lines from " + arg + "\n");
	     
	     //r = new ExecRunner[cmds.size()];
	     int vs = cmds.size();
	     //int idx = 0;
	     String s = null;
	     while (idx < vs) {
	         //System.out.println("idx is " + idx);
	         s = (String) cmds.elementAt(idx);
	         System.out.println("Cmd : " + idx + ": " + s);
	         String delim = new String("|");
	         StringTokenizer tkn = new StringTokenizer(s, delim);
		     Vector  cvec = new Vector();
		     int rIdx = 0;
		     while ( tkn.hasMoreTokens())
	               {
	                cvec.add( tkn.nextToken());
		            System.out.println("TOKEN: " + cvec.elementAt(rIdx));
	                rIdx += 1;
	               }
	        
	         
		 System.out.println("got " + rIdx + " tokens");
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
			 System.out.println("fragment[" + vecidx + "] = " + frag );
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
	    	 System.out.println("Starting #"+k);
	    	 ((ExecRunner)r.elementAt(k)).start();
	    	 k+=1;
	     }
	     int j = 0;
	     while ( j < idx ) {
	    	 System.out.println("Join for #"+j+ " of " + idx);
	    	 ((ExecRunner)r.elementAt(j)).join();
	    	 ExecRunner er = (ExecRunner)r.elementAt(j);
	    	 if (er.results_ != null ){
	    		 System.out.println("vector returned has " + er.results_.size());
	    	 }
	    	 j+=1;
	     }
	  
	     System.out.println("Done!");
	    } catch (  	InterruptedException ie ) {
	    	System.err.println("Interrupted dammit " + ie.getMessage());
	    	ie.printStackTrace(System.err);
	    }
	  
	}
}
