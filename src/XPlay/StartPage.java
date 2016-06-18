package XPlay;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StartPage extends HttpServlet{

	
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");
		resp.getWriter().println("<html><title>Log in with Facebook</title><body>");
		resp.getWriter().println("<script>");
		
		addLogIn(resp);
		includeFB_SDK(resp);
		resp.getWriter().println("<div id=\"exit\"></div>");
		resp.getWriter().println("<div align = \"center\">");
		addLogInButton(resp);
		addLikeButton(resp);
		addComments(resp);
		
		resp.getWriter().println("<div id=\"post\"></div>");
		resp.getWriter().println("<div id=\"post_photo\"></div>");
		resp.getWriter().println("</div>");
		
		//resp.getWriter().println("<span id=\"fbLogout\" onclick=\"fbLogout()\"><a class=\"fb_button fb_button_medium\"><span class=\"fb_button_text\">Logout</span></a></span>");
		
		resp.getWriter().println("</body></html>");
	}
	
	private void addLikeButton(HttpServletResponse resp) throws IOException{
		resp.getWriter().println("<div class=\"fb-like\""  
				+ " data-share=\"true\""  
				+ " data-width=\"450\""
				+ " data-show-faces=\"true\">"
				+ " </div>");
	}
	
	private void includeFB_SDK(HttpServletResponse resp) throws IOException{
		resp.getWriter().println("window.fbAsyncInit = function() {"
				+ " FB.init({"
				+ " appId      : '1661902670732736',"
				+ " xfbml      : true,"
				+ " version    : 'v2.5'"
				+ " });"
				+ " };"
				+ " (function(d, s, id){"
				+ " var js, fjs = d.getElementsByTagName(s)[0];"
				+ " if (d.getElementById(id)) {return;}"
				+ " js = d.createElement(s); js.id = id;"
				+ " js.src = \"//connect.facebook.net/en_US/sdk.js\";"
				+ " fjs.parentNode.insertBefore(js, fjs);"
				+ " }(document, 'script', 'facebook-jssdk'));"
				+ " </script>");
	}
	private void addLogInButton(HttpServletResponse resp) throws IOException{
		resp.getWriter().println("<fb:login-button scope=\"public_profile,email,user_photos,user_posts,publish_actions,publish_pages\" onlogin=\"checkLoginState();\">"
				+ "</fb:login-button>"
				+ "<div id=\"status\">"
				+ "</div>"
				+ " <div id=\"image\"></div>"
				+ " <div id=\"posts\"></div>"
				);
	}
	
	private void addComments(HttpServletResponse resp) throws IOException{
		resp.getWriter().println("<div class=\"fb-comments\" data-href=\"http://xolmes-khr.appspot.com/StartPage\""
				+ "data-width=\"500\" data-numposts=\"10\"></div>");
	}
	private void addLogIn(HttpServletResponse resp) throws IOException{
		resp.getWriter().println(
				"var accessToken = ''; \n"
						+ 		"var id = ''; \n"
						+ "var j = 'http://img.thesun.co.uk/aidemitlum/archive/01419/Hidden-Leaf-tailed_1419300a.jpg'; \n"
			+ "function makeFacebookPhotoURL( id, accessToken ) { \n"
		+		"return 'https://graph.facebook.com/' + id + '/picture?access_token=' + accessToken; \n"
		+	"} \n"
		+"function exit(){ \n"
		+	"FB.logout(function(response) { \n"
		+ 	"}); \n"
		+ "} \n"
		
		+"function post_photo(url){ \n"
		+ "console.log(url); \n"
		+ "var params = {}; \n"
	//	+ "params['message'] = 'photo'; \n"
	//	+ "params['uploaded'] = true; \n"
		+ "params['url'] = url; \n"
		+ "params['access_token'] = accessToken; \n"
	
		+ 	"FB.api(\"/me/photos\",\"POST\",params,function (response) { \n"
		+ 			"if (response && !response.error) { \n"    
		+ 			"} \n"
		+ "else{console.log('Error' + ' ' + response.error);} \n"
		+ 		"} \n"
		+ 	"); \n"
		+ "} \n"
		
		+"function post_photo2(form){ \n"
		
		+ "var params = {}; \n"
	//	+ "params['message'] = 'photo'; \n"
		+ "params['source'] = '@'+form.file.value; \n"
		+ "params['access_token'] = accessToken; \n"
		+ "params['upload file'] = true; \n"
		
		
	
		+ 	"FB.api(\"/me/photos\",\"POST\",params,function (response) { \n"
		+ 			"if (response && !response.error) { \n"    
		+ 			"} \n"
		+ "else{console.log('Error' + ' ' + response); \n"
		+ 		"} \n"	
		+ "} \n"
		+ "); \n"
		+ "} \n"
		+"function postToFace(form){ \n"
		+	"var text = form.value; \n"
		+ 	"FB.api(\"/me/feed\",\"post\",{\"message\": ''+text+''},function (response) { \n"
        +			"console.log(response.success); \n"
        +			"if(response && !response.error) { \n"
   		+			"} \n"
   		+		"} \n"
   		+	"); \n"
		+ "} \n"
		+"function statusChangeCallback(response) { \n"
		+ 		"console.log('statusChangeCallback'); \n"
		+ "	    console.log(response); \n"			   
		+ "	    if (response.status === 'connected') { \n"			   
		+ "	      testAPI(); \n"
		+ "	    } else if (response.status === 'not_authorized') { \n"	   
		+ "	      document.getElementById('status').innerHTML = 'Please log ' +"
		+ "	        'into this app.'; \n"
		+ "	    } else {  \n"
		+ "	      document.getElementById('status').innerHTML = 'Please log ' +"
		+ "	        'into Facebook.'; \n"
		+ "	    } \n"
		+ "	  } \n"
		+ "	  function checkLoginState() { \n"
		+ "	    FB.getLoginStatus(function(response) { \n"
		+ "	      statusChangeCallback(response); \n"
		+ "	    }); \n"
		+ "	  } \n"
		+ "function testAPI() { \n"
		+ " console.log('Welcome!  Fetching your information.... '); \n"
		+ " FB.api('/me', function(response) { \n"
		+ " console.log('Successful login for: ' + response.name); \n"
		+ "   document.getElementById('status').innerHTML ="
		+ "    'Thanks for logging in, ' + response.name + '!'; \n"
		+ " }); \n"
	
		+ "FB.getLoginStatus(function(response) { \n"
		+ "	  if (response.status === 'connected') { \n"
		+ 			"console.log(response.authResponse.accessToken); \n"
		+ 			"accessToken = response.authResponse.accessToken; \n"
		+ 			"id = response.authResponse.id; "
		
		+ "			console.log(response.id);\n"
		
		//+ "post_photo(j); \n"
		
		+ "		  } \n"
		+ "		}); \n"
		
		
		+ "	FB.api(\"/me/photos\",function (response) { \n"
		+ 	"if (response && !response.error) { \n"
		+		"console.log(response.data.length); \n"
		+		"var images = ''; \n"
		+ 		"var nomer = 0; \n"			
		+ 		"while(response.data[nomer]!=null){ \n"
		+			"console.log(nomer); \n"
		+ 			"var link = makeFacebookPhotoURL(response.data[nomer].id, accessToken);	\n"
		+ 			"images  = images + '<img src =' + link + '><br><div class=\"fb-comments\" data-href=\"https://www.facebook.com/photo.php?fbid=712191392219574\""
		+           "data-width=\"500\" data-numposts=\"10\"></div> "
		+ 			"<div class=\"fb-like\" "  
		+ 			"data-share=\"true\" "  
		+ 			"data-width=\"450\" "
		+ 			"data-show-faces=\"true\">"
		+ 			"</div>'; \n"
		+ 			"nomer++; \n"
		+ "} \n"
		+ 	"document.getElementById('exit').innerHTML = '<form  onsubmit=exit();><input type=\"submit\" value=\"Exit\"></form>'; \n"
		+ 	"document.getElementById('post_photo').innerHTML = '<form  onsubmit=post_photo2(this)> "			
		+ 	"<input type=\"file\" name=\"file\" /> "
		+ 	"<input type=\"hidden\" name=\"source\" value=\"@myfile.jpg\"/> "
		+ 	"<input type=\"hidden\" name=\"message\" value=\"photo\"/> "
		+	"<input type=\"hidden\" name=\"access_token\" value=accessToken/> "
		+ 	"<input type=\"submit\" value=\"PostPhoto\"></form><br> "
		+ 	"<form onsubmit=post_photo(this.source.value)> "
		+ 	"<input  type=\"text\" name=\"source\" value=\"\" /> "
		//+ 	"<input type=\"hidden\" name=\"source\" value=\"photo\" /> "
		+ 	"<input type=\"submit\" value=\"PostPhoto\"></form>'; \n"
		+	"document.getElementById('image').innerHTML = images;\n"
		+	"document.getElementById('post').innerHTML = '<form  onsubmit=postToFace(this.text);> "
		+	"<input type=\"text\" name=\"text\" value=\"\"> "
		+	"<input type=\"submit\" value=\"Submit\"> "
		+	"</form>'; \n"
		+ 	"}\n"
		+ "}\n"
		+ ");\n"
		+ "FB.api(\"/me/feed\", function (response) { \n"
		+ "  if (response && !response.error) { \n"
		+ "var i = 0; \n"
		+ "var posts = ''; \n"
		+ "posts += '<div>'; \n"
		+		"while(response.data[i]!=null){ \n"
		+ "if(response.data[i].message!=undefined){ \n"
		+ "  posts += '<p>' + response.data[i].message + '</p><br>';} \n"
		+ "i++;} \n"
		+ "posts+='</div>'; \n"
		+ " document.getElementById('posts').innerHTML = posts; \n"
		+ "} \n"
		+ "}); \n"
		+"} \n"
	
		);
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.sendRedirect(getServletName());
	}
}
