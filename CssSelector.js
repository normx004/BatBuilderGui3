function getCss(vCount) {
     var w     = window.innerWidth;
     var h     = window.innerHeight;
     var linky = document.createElement('link');
     var dimMsg="Entered CssSelector, for screen with " + vCount + " subwindows";
     console.log(dimMsg); 
     linky.setAttribute('rel', 'stylesheet');
	      
	if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|BB|PlayBook|IEMobile|Windows Phone|Kindle|Silk|Opera Mini/i.test(navigator.userAgent)) 
		{
		 console.log("Setting css to MOBILE");
		 console.log(navigator.userAgent);
		 linky.setAttribute('href', 'css/gui-mobile-2-4.css');
		 console.log("Have initially set css to mobile"); 
		}
	const hshow="----h is " + h;
	const showhsw="....window screen heightis " +  window.screen.height;
	const wshow="----w is " + w;
	const showwsw="....window screen width is " +  window.screen.width;
	const whoam="i am the getcss on the linux maxhine";
	
	console.log(whoam);
	console.log(hshow);
	console.log(showhsw);
	console.log(wshow);
	console.log(showwsw);
	
	//if ( w > 2000) {	
        // 	  console.log("Setting css to 3840"); 
	//	  linky.setAttribute('href', 'css/gui-3824-2-4.css');
	//   	  console.log("i have set css to 3840");
	//} else {
		if ( w > 1366 && w < 2000) { 
	    	  	console.log("Setting css to 1980"); 
		  	linky.setAttribute('href', 'css/gui-1920-2-4.css');
	   	  	console.log("i have set css to 1980");      
	  	} else {
			if ( w < 1300) {
		    		console.log("Setting css to tablet");
			  	linky.setAttribute('href', 'css/gui-tablet-2-4.css');
			   	console.log("i have set css to tablet");
				} else  
				  // so, big screen...       
				 if (vCount == 4) {
				     	console.log("Setting css to 3824 for FOUR");
				     	linky.setAttribute('href', 'css/gui-3824-2-4.css');
				     	console.log("i have set css to 3824 for 4");							
       		    		    } else 
					{
					  if (vCount == 6) {
					        console.log("Setting css to 3840 for SIX");
					     	linky.setAttribute('href', 'css/gui-3824-6.css');
					     	console.log("i have set css to 3824 for 6");		
					 }
					} 
				    }	 
		      		 
		  
	
	/*
	 console.log("Setting css to 3840"); 
		  linky.setAttribute('href', 'css/gui-3824-2-4.css');
	   	  console.log("i have set css to 3840");
	*/
	
   document.head.appendChild(linky); 									   
}
