package batboy;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

    // This action creates and shows a modal save-file dialog.
    public class SaveFileAction extends AbstractAction {
        JFileChooser chooser;
        JFrame frame;
        BatGUISlicer bg_ = null;
    
        SaveFileAction(JFrame frame, JFileChooser chooser, BatGUISlicer bg) {
            super("Save As...");
            bg_ = bg;
            this.chooser = chooser;
            this.frame = frame;
        }
        public void out(String s) {
        	System.out.println("s");
        }
        public void actionPerformed(ActionEvent evt) {
            // Show dialog; this method does not return until dialog is closed
        	System.out.println("In SaveFileAction.actionPerformed, bat file string is "+ bg_.getBatFileName());
        	chooser.setSelectedFile(bg_.getBatFileFile());
            chooser.showSaveDialog(frame);
    
            // Get the selected file
            File file = chooser.getSelectedFile();
            System.out.println( "In SaveFileAction...saving file to " + file.getPath());
            bg_.setBatFile(file.getPath());
            CmdFile f = bg_.getCmdFile();
            //f.setSpeed(bg_.getSpeed());
            f.setBatFile(file.getPath());
            //f.writeOutputFile(false);
            f.writeOutputFileXML(false);
            out("leaving SaveFileAction.actionPerformed");
        }
    };

