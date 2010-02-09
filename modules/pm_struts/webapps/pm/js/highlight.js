	function isHTML(text){
		var t = /(<\S[^><]*>)|(&.+;)/g;
		return text.match(t);
	}
	

	$('.cell').each(function(i) {
			var value = $(this).html();
			if(! isHTML(value)){
				//Second class is field id
				var field = $(this).attr('class').split(' ')[1];
				var td = $(this).parents().filter('td');
				var tr = $(this).parents().filter('tr');
				td.addClass(field+"_"+value)
				tr.addClass(field+"_"+value)
			}
	});
