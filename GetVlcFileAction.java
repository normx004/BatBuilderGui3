package batboy;

	

	import java.awt.event.ActionEvent;
	import java.io.File;

	import javax.swing.AbstractAction;
	import javax.swing.JFileChooser;
	import javax.swing.JFrame;



	    // This action creates and shows a modal open-file dialog.
	    public class GetVlcFileAction extends AbstractAction {
	        JFrame frame;
	        JFileChooser chooser;
	        BatGUISlicer bg_ = null;
	    
	        GetVlcFileAction(JFrame frame, JFileChooser chooser, BatGUISlicer bg) {
	            super("Open...");
	            bg_ = bg;
	            this.chooser = chooser;
	            this.frame = frame;
	        }
	    
	        public void actionPerformed(ActionEvent evt) {
	            // Show dialog; this method does not return until dialog is closed
	        	chooser.setCurrentDirectory(new File("C:\\Program Files (x86)\\VideoLAN"));
	        	String choosertitle = new String("What VLC Program Shall We Use?");
	            chooser.setDialogTitle(choosertitle);
	        	
	        	
	            chooser.showOpenDialog(frame);
	    
	            // Get the selected file
	            File file = chooser.getSelectedFile();
	            if (file == null) {
	            	System.out.println("No file selected for VLC file");
	            } else {
	                System.out.println("chosen file: "+ file.getName());
	                bg_.setVlcFile(file);
	            }
	        }
	    };



