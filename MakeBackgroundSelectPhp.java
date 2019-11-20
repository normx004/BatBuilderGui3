package batboy;

public class MakeBackgroundSelectPhp {

	public MakeBackgroundSelectPhp() {
		// TODO Auto-generated constructor stub
	}
	private void out(String s){
		System.out.println(s);
	}
    public String getThePhpCode(String dir, String dirAlias) {
    	// note: dir is fed to the "opendir" function in php
    	//       dirAlias is the directory pointer taken from the
    	//           httpd.conf file.  this is because "q:/autocoll/ShapeColl"
    	//           can't be used by the web server as it is used by
    	//           php; thus it needs something like "/q/autocoll/ShapeColl/"
    	//           (note trailing slash on the alias!)
    	StringBuffer sb = new StringBuffer("");
    	out ("in getthephpcode, dirAlias: "+dirAlias);
    	sb.append(
    			"<?php  \n"+
    			"		$dir = \"" + dir + "\";\n"+
    			"		if ( ! $dh  = opendir($dir)) { print \"<h1>CANT OPEN $dir</h1>\"; } else {\n"+
    			"		while (false !== ($filename = readdir($dh))) {\n"+
    			"           console.log(\"file: \"+$filename); " +
    			"		    $files[] = $filename; "+
    			"		}\n"+
                "      }\n"+
    			"		$howmany = count($files);\n"+
    			"		$k = 0; \n"+
    			"		$i = 0;\n"+
                "\n"+
    			"		while ($k < $howmany && $k < 50) {\n"+
    			"			if ( preg_match(\"/.jpg|.jpeg|.png/\", $files[$k]  ))    { \n"+
    			"			    $goodFiles[$i++] =   \"" + dirAlias + "\" . $files[$k];\n"+
    			"			    if ( $i < 0) {  // wired out for now\n"+
    			"			    	 $j = $i-1;\n"+
    			"			    	 print \"Found File: $goodFiles[$j]<br>\";\n"+
    			"			     }\n"+
    			"			}\n"+
    			"		  $k += 1;\n"+
    			"		}\n"+
                "\n"+
    			"		$x = $i;\n"+
    			"		$i = 0;\n"+
                "\n"+
    			"		print \"<script>var filez = [\";\n" +
    			"		while ($i < $x) {\n"+
    			"			 print \"\\\" $goodFiles[$i]\\\"\";\n"+
    			"			 $i += 1;\n"+
    			"			 if ($i < $x) {\n"+
    			"			 	print \",\";\n"+
    			"			}\n"+
    			"		}\n"+
    			"		print \"]</script>\";\n"+
    			"?>\n"
    		
    			);
    	out("in MakeBackgroundSelect-getThePHPCode\n"+sb.toString()+"\n\n");
    	return sb.toString();
    }
    int fCount_ = 0;
    private String trimFileName(String fylename) {
    	fCount_ += 1;
    	if (fCount_ < 10) {
    		out("Trimming "+ fylename);
    	}
    	int k = fylename.indexOf("/images");
    	
    	String fyle = fylename.substring(k);
    	if (fCount_ < 10) {
    		out("Trimmed: " + fyle);
    	}
    	return fyle;
    }

    public static void main(String[] args){
    	MakeBackgroundSelectPhp mbs = new MakeBackgroundSelectPhp();
    	String s=mbs.getThePhpCode("q:/Autocollage/ShapeCollage", "/q/Autocollage/ShapeCollage/");
    	System.out.println(s);
    }
}
