package batboy;
import java.io.*;

public class CmdLine {
  
	private String start = null;
	private String end = null;
	private String x = null;
	private String y = null;
	private String targetVideoFile = null;
	private String xsize = null;
	private String ysize = null;
	private String speed = null;
	private String aspect = null;
	
	public String getSpeed() {
		return speed;
	}
	public void setAspect(String s) {
		aspect = s;
	}
	public String getAspect() {
		return aspect;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getXsize() {
		return xsize;
	}
	public void setXsize(String xsize) {
		this.xsize = xsize;
	}
	public String getYsize() {
		return ysize;
	}
	public void setYsize(String ysize) {
		this.ysize = ysize;
	}
	public void setTargetVideoFile(String s) {
		targetVideoFile = s;
	}
	public void setTargetVideoFile(File s) {
		targetVideoFile = s.getPath();
	}
	public String getTargetVideoFile() {
		return targetVideoFile;
	}
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	
    public String  toString() {
    	return ("start " + start +  "\n" +
    	"end   " + end +  "\n" +
    	"x     " + x + "\n" +
    	"y     " + y +  "\n" +
    	"xsize " + xsize + "\n" +
    	"ysize " + ysize + "\n" +
    	"speed " + speed + "\n" +
    	"aspect" + aspect + "\n"+
    	"file  " + targetVideoFile);
    	
    }
    
    public CmdLine(String c) {
    	// parse line from bat command file, populate this new object
    }
	public CmdLine() {
		// TODO Auto-generated constructor stub
	}
	
}
