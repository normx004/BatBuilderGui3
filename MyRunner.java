package batboy;

import java.io.IOException;

public class MyRunner implements Runnable {
    private String cmd = null;
	public MyRunner() {
		// TODO Auto-generated constructor stub
	}
    private void out(String s) {
    	System.out.println("MyRunner: " +s);
    }
	@Override
	public void run() {
		out("Running in 'run' routine in MyRunner");
		try {
	    	Runtime r = Runtime.getRuntime();
	    	r.exec(cmd);
	    	out("no command errors");
		} catch (IOException ioe) {
			System.err.println("in MyRunner, runtime exception: " + ioe.getMessage());
			Thread.dumpStack();
		} catch (Exception e) {
			out("Exception: " + e.getMessage());
			Thread.dumpStack();
		}
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		out("in MyRunner, setting cmd to '"+cmd+"'");
		this.cmd = cmd;
	}

}
