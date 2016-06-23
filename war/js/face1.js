
window.fbAsyncInit = function() {
            
                $(".fb_login").click(function() {
                         FB.init({
                appId      : '626764410807763',
                status     : false, 
                cookie     : true,
                xfbml      : true,
                oauth      : true
                }); 


                   FB.login(Facebook_login, {scope: 'public_profile,email,publish_actions'});
                }); 
            };


            (function(d){
            var js, id = 'facebook-jssdk'; if (d.getElementById(id)) {return;}
            js = d.createElement('script'); js.id = id; js.async = true;
            js.src = "//connect.facebook.net/en_US/all.js";
            d.getElementsByTagName('head')[0].appendChild(js);
            }(document));

          function Facebook_login () {
            FB.getLoginStatus(function(response) {
                if (response.status === 'connected') {
                    
                    FB.api(
                        '/me', {fields: 'link'},
                        function (response) {
                          if (response && !response.error) {
                            console.log(response);
                            user_link = response.link;
                
                                FB.api(
                                    '/me',
                                    function (response) {
                                        if (response && !response.error) {
                                            user_name = response.name;
                                            console.log( user_name, user_link);
                                            
                            
                                           getFromDataBase(user_name, user_link,null);
                                        }
                                    });
                            }
                                                 
                          });
                   // window.location = ("http://dino-runner.appspot.com?name=" +user_name +"&link=" + user_link);

                }
            });
          }                