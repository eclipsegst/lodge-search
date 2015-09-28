$( document ).ready(function() {
    $("[rel='tooltip']").tooltip();    
 
    $('.thumbnail').hover(
        function(){
            //$(this).find('.caption').slideDown(250); //.fadeIn(250)
            $(this).find('.caption').fadeIn(200); //.fadeIn(250)
        },
        function(){
            //$(this).find('.caption').slideUp(250); //.fadeOut(205)
	    $(this).find('.caption').fadeOut(200);
        }
    ); 
    	$('#carouselHacked').carousel();

      var d = new Date();
      var n = d.getDate();
      $('.input-group.date').datepicker({
          todayHighlight: true,
          language: 'ja'
      });

});