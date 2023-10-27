//=========================VIDEO PLAY========================================
        function videoPlay(videoNum, videoSource, el) {
	    videoNum = (videoNum + 1) % videoSource.length;
	    document.getElementById(el).setAttribute("src",videoSource[videoNum]);
	    document.getElementById(el).load();
	    document.getElementById(el).play();
        }
	

	function showVidName(myVid, txtEl) {
		var  theName  = document.getElementById(myVid);
		var  vidName  = theName.getAttribute('src');
            	if (txtEl.style.display === "none" || ! txtEl.style.display  ) 
               	{ 
			txtEl.textContent   = vidName;
                	txtEl.style.display = "block"; 
			txtEl.style.display = "position: absolute";
	    		txtEl.style.border  = "3px solid #73ad21";
            		txtEl.style.top     = "50%";
            		txtEl.style.left    = "50%";
	    		txtEl.style.zIndex = "1";
            		txtEl.style.transform        = "translate(-50%, -50%)";
            		txtEl.style.textAlign        = "center";
           	 	txtEl.style.background       =  "rgba(255, 255, 255, 0.5)";
           	 	txtEl.style.padding          = "20px";		
               	}
                else 
               	{ 
		  txtEl.style.display = "none"; 
		}
	}
