function getCss() {
     var w = window.innerWidth;
     var linky = document.createElement('link');
     linky.setAttribute('rel', 'stylesheet');
	      
	if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|BB|PlayBook|IEMobile|Windows Phone|Kindle|Silk|Opera Mini/i.test(navigator.userAgent)) 
		{
		 console.log("Setting css to MOBILE");
		 console.log(navigator.userAgent);
		 linky.setAttribute('href', 'css/gui-mobile-2-4.css');
		 console.log("Have initially set css to mobile"); 
		}
		
	if ( w > 2000) {	
         console.log("Setting css to 3840"); 
		  linky.setAttribute('href', 'css/gui-3824-2-4.css');
	   	  console.log("hjave set css to 3840");
	} else {
		if ( w > 1366) { 
	    	  console.log("Setting css to 1980"); 
		  	linky.setAttribute('href', 'css/gui-1920-2-4.css');
	   	  	console.log("hjave set css to 1980");      
	  	} else {
			if ( w < 1300) {
		    	console.log("Setting css to tablet");
			  	linky.setAttribute('href', 'css/gui-tablet-2-4.css');
			   	console.log("have set css to tablet");
			} else  {          
			     	console.log("Setting css to 1366");
			     	linky.setAttribute('href', 'css/gui-1366-2-4.css');
			     	console.log("have set css to 1366");							
       		    	}
	}
   document.head.appendChild(linky); 									   
}
