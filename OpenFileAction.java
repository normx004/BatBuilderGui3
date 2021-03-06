package batboy;

import java.awt.event.ActionEvent;
import java.io.*;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;



    // This action creates and shows a modal open-file dialog.
    public class OpenFileAction extends AbstractAction {
        JFrame           frame = null;
        JFileChooser     chooser = null;
        BatGUI           bg_ = null;
        VideoFilePointer vfp = null;
    
        OpenFileAction(JFrame frame, JFileChooser chooser, BatGUI bg) {
            super("Open...");
            out("Constructing OpenFileAction with parameter bg = "+bg.toString());
            bg_ = bg;
            this.chooser = chooser;
            this.frame = frame;
        }
        OpenFileAction(JFrame frame, JFileChooser chooser, VideoFilePointer vp, BatGUI bg) {
            super("Open...");
            out("Constructing OpenFileAction with parameter vp = "+vp.toString());
            vfp = vp;
            bg_=bg;
            this.chooser = chooser;
            this.frame = frame;
        }
        private void out(String s) { System.out.println("OpenFileAction: " + s);}
        public void actionPerformed(ActionEvent evt) {
            // Show dialog; this method does not return until dialog is closed
        	//     chooser.setCurrentDirectory(new File("C:\\Program Files (x86)\\VideoLAN"));
        	String rep = null;
        	if (bg_ == null) { rep = "NULL";} else { rep = bg_.toString();}
        	out("bg_: " + rep);
        	if (vfp == null) { rep = "NULL";} else { rep = vfp.toString();}
        	out("vfp: " + rep);
        	String choosertitle = new String("What is the Target Video File?");
            chooser.setDialogTitle(choosertitle);
            out("OpenFileAction: setting chooser directory to "+bg_.getLastDirectory());
            chooser.setCurrentDirectory(bg_.getLastDirectory());
            chooser.showOpenDialog(frame);
    
            // Get the selected file
            File file = chooser.getSelectedFile();
            if (file == null) {
            	System.out.println("No Video file name yet");
            }
            else {
            	out("chosen file: "+ file.getName());
            	out("file path:   "+ file.getPath());
            	File fyle = file.getParentFile();
            	bg_.setLastDirectory(fyle);
                //bg_.setTable((Object)file.getName());
            	if ( bg_ != null ){
            		vfp.setVideoFile(file);
            		try {
                        PrintWriter outr
                        = new PrintWriter(new BufferedWriter(new FileWriter("/var/www/html/temp/lastGuiFile")));
                       outr.write(file.getParentFile().toString(),0,(int)file.getParentFile().toString().length());
                       outr.flush();
                       outr.close();
                   } catch (IOException ioe) {
                  	   System.out.println("Couldnt write 'last dir' file " + ioe.getMessage());
                   }
            	} else {
            	  if (vfp != null ) {
            		out ("openFileAction: vfp is NOT null at line 73");
            		vfp.setVideoFile(file);
            		File foyle = file.getParentFile();
                	bg_.setLastDirectory(foyle);
            		vfp.getWhatVideoFileLabel().setText(file.getPath());
            	  }
            	}
                
            }
            
        }
    };

