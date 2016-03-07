package batboy;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PageBuilder  {
    VideoFilePointer vfp[] = null;
	public PageBuilder(VideoFilePointer[] vp) {
		// TODO Auto-generated constructor stub
		vfp = vp;
	}
	public String generatePage(){
		String status = new String("not enough files specified!");
		File filez[] = new File[vfp.length];
		int j = 0;
		int k = 0;
		while (j < vfp.length) {
			filez[j] = vfp[j].getVideoFile();
			if (filez[j] != null ) {
				k += 1;
			}
			j += 1;
		}
		if (k < vfp.length) {
			return status;
		}
		
		
		// all done, successful!
		status = new String("OK, generating page!");
		return status;
	}
}
