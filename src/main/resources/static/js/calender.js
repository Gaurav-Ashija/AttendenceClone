document.addEventListener('DOMContentLoaded', function() {
  var calendarEl = document.getElementById('calendar');

  var calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: 'dayGridMonth',
    initialDate: '2021-07-07',
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay'
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
      }
    ]
  });

  calendar.render();
});