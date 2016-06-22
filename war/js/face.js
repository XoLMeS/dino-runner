window.fbAsyncInit = function() {
    $(".fb_login").click(function() {
        FB.init({
            appId: '626764410807763',
            status: false,
            cookie: true,
            xfbml: true,
            oauth: true
        });
        FB.login(Facebook_login, {
            scope: 'public_profile,email,publish_actions'
        });
    });
};
(function(d) {
    var js, id = 'facebook-jssdk';
    if (d.getElementById(id)) {
        return;
    }
    js = d.createElement('script');
    js.id = id;
    js.async = true;
    js.src = "//connect.facebook.net/en_US/all.js";
    d.getElementsByTagName('head')[0].appendChild(js);
}(document));

function Facebook_login() {
    FB.getLoginStatus(function(response) {
        if (response.status === 'connected') {
            //window.location = "http://dino-runner.appspot.com/Dino_Runner_Servlet";
        }
    });
}