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
	function postToDataBase(method, userName, userLink, misc) {
	    console.log("Post request");
	    $.ajax({
	        url: "http://dino-runner.appspot.com/Service",
	        type: "POST",
	        data: {
	            method: method,
	            user_name: userName,
	            user_link: userLink,
	            shadow: misc.shadow,
	            coins: misc.coins,
	            score: misc.score
	        },
	        dataType: "json",
	        success: function(result) {
	            switch (result) {
	                case true:
	                    console.log(result);
	                    break;
	                default:
	                    console.log(result);
	            }
	        },
	        error: function(xhr, ajaxOptions, thrownError) {}
	    });
	};
	
	function getFromDataBase(userName, userLink, cap) {
	    console.log('get request');
	    $.ajax({
	        url: "http://dino-runner.appspot.com/Service",
	        type: "GET",
	        data: {
	            user_name: userName,
	            user_link: userLink,
	            capacity: cap
	        },
	        dataType: "json",
	        success: function(result) {
	            if (cap != null) {
	                var list = result;
	                console.log(list[0].propertyMap);
	                if (list){
	                	var html = "";
	                	for (var i = 0; i < list.length; i++){
	                            html += "<tr><td>" + (i+1) + "</td><td><a href=\""+list[i].propertyMap.Link+ "\">"+ list[i].propertyMap.Name + "</a></td>"+
	                            "<td>"+list[i].propertyMap.Highscore+"</td></tr>";
	                        
	                	}
	                    $("#scores").html(html);
	                }
	            }
	        },
	        error: function(xhr, ajaxOptions, thrownError) {
	        	console.log("xhr", xhr);
	        	console.log("ajaxOptions", ajaxOptions);
	        	console.log("error", thrownError);
	        	
	        }
	    });
	};


    function getFromGlobal() {
    	//var list = getFromDataBase(null,null, 10);
       //$("#scores").html("No scores yet.");
    }

$("#local").click(function(){
	$("#global").removeClass("active");
	$("#local").addClass("active");
	getFromLocal();
});

$("#global").click(function(){
	$("#local").removeClass("active");
	$("#global").addClass("active");
	$("#scores").html("<img src=\"images/loading.svg\"/>");
	getFromDataBase(null,null, 10);
	getFromGlobal();
});

getFromLocal();
});