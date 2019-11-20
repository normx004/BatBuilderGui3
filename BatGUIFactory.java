package batboy;

public class BatGUIFactory {

	public BatGUIFactory() {
		// TODO Auto-generated constructor stub
	}
    public BatGUI getBatGUI(String[] args) {
    	// args:
    	//    1: props file
    	//    2: 'slicer' for cutting videos, or 'html' for building web pages to watch them
    	//if type is slicer, get an xspf file generator gui
    	//if type is html,   get a  page generator gui
    	String type = args[1];
    	if (type.compareTo("slicer")==0) {
    		System.out.println("Initializing SLICER");
    		BatGUI b = new BatGUISlicer(args);
    		b.setArgs(args);
    		return b;
    	}
    	if (type.compareTo("html")==0) {
    		System.out.println("Initializing HTML");
    		BatGUI b = new BatGUIHtml(args);
    		b.setArgs(args);
    		return b;
    	}
    	System.err.println("Wrong factory request, was " + type + ", but should be 'slicer' or 'html'");
    	if (args[0] != null ) {
        	System.err.println("Arg 0: "+ args[0]);   		
    	}
    	if (args[1] != null){
    		System.err.println("Arg 1: "+ args[1]);
    	}
    	
    	return null;
    }
}
