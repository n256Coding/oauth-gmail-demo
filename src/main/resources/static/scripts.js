var script = document.createElement('script');
script.src = 'https://code.jquery.com/jquery-3.3.1.min.js';
script.intgrity = 'sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=';
script.crossorigin = 'anonymous';

document.getElementsByName('head')[0].appendChild(script);

$(document).ready(function () {
   $("#showMails").click(request);
});

function request() {
    var AUTH_ENDPOINT = 'https://accounts.google.com/o/oauth2/v2/auth';
    var CLIENT_ID = 'xxxxxxxxxxxxx';
    var REDIRECT_URI = 'https://localhost:8443/callback';
    var SCOPE = 'https://www.googleapis.com/auth/gmail.readonly';
    var PROMPT = 'consent';
    var RESPONSE_TYPE = 'code';

    var requestUrl = AUTH_ENDPOINT + '?'+
            'client_id='+CLIENT_ID+'&'+
            'redirect_uri='+encodeURIComponent(REDIRECT_URI)+'&'+
            'scope='+encodeURIComponent(SCOPE)+'&'+
            'prompt='+encodeURIComponent(PROMPT)+'&'+
            'access_type=offline&'+
            'response_type='+encodeURIComponent(RESPONSE_TYPE);

    window.location.href = requestUrl;
}