$( document ).ready(function() {
	$("#answer_submit").click(addAnswer);
});

function addAnswer(e) {
	alert('test1');
	e.preventDefault();
	console.log("clicked!!");
	
	var queryString = $(".answer-write").serialize();
	console.log("query:" + queryString);
	
	var url = $(".answer-write").attr("action");
	console.log('url:'+url);
	$.ajax({
			type:'post',
			url:url,
			data:queryString,
			dataType:'json',
			error:onError,
			success:onSuccess});
}

function onError() {
	
}

function onSuccess(data, status) {
	console.log(data);
}