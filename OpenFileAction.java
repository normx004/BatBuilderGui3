package batboy;

import java.awt.event.ActionEvent;
import java.io.*;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;



    // This action creates and shows a modal open-file dialog.
    public class OpenFileAction extends AbstractAction {
        JFrame frame;
        JFileChooser chooser;
        BatGUI bg_ = null;
    
        OpenFileAction(JFrame frame, JFileChooser chooser, BatGUI bg) {
            super("Open...");
            bg_ = bg;
            this.chooser = chooser;
            this.frame = frame;
        }
    
        public void actionPerformed(ActionEvent evt) {
            // Show dialog; this method does not return until dialog is closed
        	//chooser.setCurrentDirectory(new File("C:\\Program Files (x86)\\VideoLAN"));
        	String choosertitle = new String("What is the Target Video File?");
            chooser.setDialogTitle(choosertitle);
        	
        	
            chooser.showOpenDialog(frame);
    
            // Get the selected file
            File file = chooser.getSelectedFile();
            if (file == null) {
            	System.out.println("No Video file name yet");
            }
            else {
            	System.out.println("chosen file: "+ file.getName());
                //bg_.setTable((Object)file.getName());
                bg_.setVideoFile(file);
                try {
                PrintWriter outr
                = new PrintWriter(new BufferedWriter(new FileWriter("q:\\temp\\lastGuiFile")));
                outr.write(file.getParentFile().toString(),0,(int)file.getParentFile().toString().length());
                outr.flush();
                outr.close();
                } catch (IOException ioe) {
                	System.out.println("Couldnt write 'last dir' file " + ioe.getMessage());
                }
            }
            
        }
    };

