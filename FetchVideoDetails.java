package batboy;

// this object now kinda poinntless

public class FetchVideoDetails {
// FFMpeg gives a line with the length of the video:
//		ffmpeg -i kg.mp4 2>&1 | /usr/bin/grep Duration
//		Duration: 00:00:50.02, start: 0.000000, bitrate: 3117 kb/s
//
// so can grep for that and decode
	String vidPath_ = null;
	StringBuffer bashPath = new StringBuffer("/mnt/");
	
	private void out(String s) {
		System.out.println("FetchVideoDetails: "+s);
	}
	public FetchVideoDetails (String vidPath) {
		vidPath_ = vidPath;
		
	}
	public int times() {
		out("looking for duration of "+vidPath_);
		
		GetVideoDuration gvd = new GetVideoDuration(vidPath_);
		return  gvd.getDuration();
		
	}
	
	
}
