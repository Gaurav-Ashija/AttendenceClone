document.addEventListener('DOMContentLoaded', function() {
  var calendarEl = document.getElementById('calendar');

  var calendar = new FullCalendar.Calendar(calendarEl, {
 	   	      headerToolbar: {
	   	       right: 'false',
	   	       left: 'title',
	    	     },
  	    events: [
      {
        title: 'All Day Event',
        start: '2021-07-01'
      },
      {
        title: 'Long Event',
        start: '2021-07-07',
        end: '2021-07-10'
      },
      {
        groupId: '999',
        title: 'Repeating Event',
        start: '2021-07-09T16:00:00'
      },
      {
        groupId: '999',
        title: 'Repeating Event',
        start: '2021-07-16T16:00:00'
      },
      {
        title: 'Conference',
        start: '2021-07-11',
        end: '2021-07-13'
      },
      {
        title: 'Meeting',
        start: '2021-07-12T10:30:00',
        end: '2021-07-12T12:30:00'
      },
      {
        title: 'Lunch',
        start: '2021-07-12T12:00:00'
      },
      {
        title: 'Meeting',
        start: '2021-07-12T14:30:00'
      },
      {
        title: 'Birthday Party',
        start: '2021-07-13T07:00:00'
      },
      {
        title: 'Click for Google',
        url: 'http://google.com/',
        start: '2021-07-28'
      },
      {
      
       daysOfWeek: [0,6], //Sundays and saturdays
  	              textColor:"#FFFFFF" ,
 	             backgroundColor :"#000000",
 	            borderColor :"#000000",
	              title:"Weekly Off"
      }
      
    ]
  });

  calendar.render();
});