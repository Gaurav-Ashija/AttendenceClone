 
 		   function getLocation() {
 		   		   let x = document.getElementById("userLocation");
 		     if (navigator.geolocation) {
		       navigator.geolocation.getCurrentPosition(showPosition);
		     } else { 
		       x.innerHTML = "Geolocation is not supported by this browser.";
		     }
		   }

		   function showPosition(position) {
		     let x = document.getElementById("userLocation");
		     
			 let APIKey = "0df26150642240f1aafea4238d8e4631";  
		     let latitude = position.coords.latitude;
		     let longitude = position.coords.longitude; 
		     let location;
		     
		     let request = new XMLHttpRequest();
			     //request.open('GET','https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=' + latitude + '&longitude=' + longitude + '', true);
			     request.open('GET','https://api.opencagedata.com/geocode/v1/json?q=' + latitude + ',' + longitude + '&key=' + APIKey + '', true);
			     request.onload = function () {
			       // Begin accessing JSON data here
			       let data = JSON.parse(this.response);
			  
				      if (request.status == 200) {
				    	 //location = data.locality + "," + data.city + "," + data.postcode;
				    	 
				    	  location = data.results[0].formatted;
				    	  console.log(location);
				    	  x.innerHTML = location;
				    	  
					  } else {
					    console.log('error');
					  }
			       
			     }
	
			     request.send();
		   }
