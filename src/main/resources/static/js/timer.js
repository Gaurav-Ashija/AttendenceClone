 
function startTime() {

  const today = new Date();
  let h = today.getHours();
  let m = today.getMinutes();
  let s = today.getSeconds();

  m = checkTime(m);
  s = checkTime(s);
  document.getElementById('txt').innerHTML ="Timing "+ ": " +h + ":" + m + ":" + s;
  setTimeout(startTime, 1000);
   
}	
 
 
function checkTime(i) {
  if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
  return i;
}

function punchTime()
{
   const today = new Date();
  let h = today.getHours();
  let m = today.getMinutes();
  let s = today.getSeconds();

  m = checkTime(m);
  s = checkTime(s);
  let punchTime=h + ":" + m ;
     
  let email = $('#employee-email').val();
  let location = $('#userLocation').text(); 
  
  if(location === "")
  {
      $('#location-error').show();
      $('#location-error').text("Please Select Your Location"); 
  }
  else
  {
       $.ajax({
        type:"post",
        data: {"punchTime":punchTime,
               "location":location
              },
        url:"/punchtime",
        async: false,
        dataType: "json",
        success: function(){
 			location.reload();
          }
     });
  }
   
}


 