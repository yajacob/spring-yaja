$(document).ready(function() {
	$("#answer_submit").click(addAnswer);
});

function addAnswer(e) {
	e.preventDefault();
	console.log("clicked!!");

	var queryString = $(".answer-write").serialize();
	console.log("query:" + queryString);

	var url = $(".answer-write").attr("action");
	console.log('url:' + url);
	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		error : onError,
		success : onSuccess
	});
}

function onError() {

}

function onSuccess(data, status) {
	console.log(data);
	var answerTemplate = $("#answerTemplate").html();
	var template = answerTemplate.format(data.writer.userName, data.createDate, data.contents, data.id, data.id);
	console.log(template);
	$(".qna-comment-yaja-articles").append(template);
	$("#ans_contents").val("");
}

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};
