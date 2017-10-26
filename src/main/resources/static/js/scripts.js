String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};


$(".submit-answer input[type=submit]").click(addAnswer);

function addAnswer(e) {
	e.preventDefault();
	console.log("clicked!!");
	var queryString = $(".answer-write").serialize();
	console.log("query:" + queryString);
}