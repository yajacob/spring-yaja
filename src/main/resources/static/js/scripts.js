$(document).ready(function() {
	$("#btn_answer_submit").click(addAnswer);
	$("a.link-delete-article").click(deleteAnswer);

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
	var template = answerTemplate.format(data.writer.userName, data.createDate,
			data.contents, data.question.id, data.id);
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

function deleteAnswer(e) {
	e.preventDefault();

	var deleteBtn = $(this);
	var url = deleteBtn.attr("href");
	console.log("url:" + url);

	$.ajax({
		type : 'delete',
		url : url,
		dataType : 'json',
		error : function(xhr, status) {
			console.log("Error");
		},
		success : function(data, status) {
			console.log("Success");
			if (!data.valid) {
				deleteBtn.closest("article").remove();
			} else {
				alert(data.errorMessage);
			}
		}
	});

}