package batboy;
import java.lang.*;
import java.util.*;
import java.io.*;

public class ExecRunner extends Thread {
   private ExecWrapper ex_ = null;
   public  Vector results_ = null;
   ExecRunner (ExecWrapper ex) {
       ex_ = ex;
   }
   public void run () {
        System.out.println("\n---Running thread---\n");
	    results_ = ex_.runit();
   }
}
