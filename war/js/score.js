$(function(){
	function getFromLocal() {
    var ar = localStorage.getItem('local_scores');
    var html = "";//$("#scores").html();
    if (ar) {
        ar = ar.split(',');
        ar.forEach(function (item, i, ar) {
            console.log('local ', item);
            if (item) {
                html += "<tr><td>" + (i+1) + "</td><td>"+ "Local user" + "</td>"+
                "<td>"+item+"</td></tr>";
            }
        });
       $("#scores").html(html);
    }
}

    function getFromGlobal() {
    /*var ar = localStorage.getItem('local_scores');
    var html = "";//$("#scores").html();
    if (ar) {
        ar = ar.split(',');
        ar.forEach(function (item, i, ar) {
            console.log('local ', item);
            if (item) {
                html += "<tr><td>" + (i+1) + "</td><td>"+ "Local user" + "</td>"+
                "<td>"+item+"</td></tr>";
            }
        });*/
       $("#scores").html("No scores yet.");
    }

$("#local").click(function(){
	$("#global").removeClass("active");
	$("#local").addClass("active");
	getFromLocal();
});

$("#global").click(function(){
	$("#local").removeClass("active");
	$("#global").addClass("active");
	getFromGlobal();
});

getFromLocal();
});