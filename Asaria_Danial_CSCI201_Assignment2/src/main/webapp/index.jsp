
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%-- <%@ page import="okhttp3.*" %>
 --%>

<%@ page import="Util.*" %>

<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="301645777112-2rlc9gth0f5d4reimjcm9bf0kj7ahec0.apps.googleusercontent.com"
          name="google-signin-client_id">
    <title>Login / Register</title>
    <link href="https://fonts.googleapis.com" rel="preconnect">
    <link crossorigin href="https://fonts.gstatic.com" rel="preconnect">
    <link
            href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap"
            rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lobster">
    <script crossorigin="anonymous"
            src="https://kit.fontawesome.com/3204349982.js"></script>
    <script async defer src="https://apis.google.com/js/platform.js"></script>
    <link href="index.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Roboto"
          rel="stylesheet" type="text/css">
    <script src="https://apis.google.com/js/api:client.js"></script>
    <script src="https://accounts.google.com/gsi/client" async defer></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script>
    function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();
  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
  console.log('Name: ' + profile.getName());
  console.log('Image URL: ' + profile.getImageUrl());
  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
}
  var GoogleAuth;
  var SCOPE = 'https://www.googleapis.com/auth/drive.metadata.readonly';
  function handleClientLoad() {
    // Load the API's client and auth2 modules.
    // Call the initClient function after the modules load.
    gapi.load('client:auth2', initClient);
  }

  function initClient() {
    // In practice, your app can retrieve one or more discovery documents.
    var discoveryUrl = 'https://www.googleapis.com/discovery/v1/apis/drive/v3/rest';

    // Initialize the gapi.client object, which app uses to make API requests.
    // Get API key and client ID from API Console.
    // 'scope' field specifies space-delimited list of access scopes.
    gapi.client.init({
        'apiKey': 'AIzaSyDTf3wNzvp3cSBSLGvesViA9O0bdcbiCWk',
        'clientId': '1029435006638-as7pg0fpjprk7fmsvl1tefgjdu9pa8b6.apps.googleusercontent.com',
        'discoveryDocs': [discoveryUrl],
        'scope': SCOPE
    }).then(function () {
      GoogleAuth = gapi.auth2.getAuthInstance();

      // Listen for sign-in state changes.
      GoogleAuth.isSignedIn.listen(updateSigninStatus);

      // Handle initial sign-in state. (Determine if user is already signed in.)
      var user = GoogleAuth.currentUser.get();
      setSigninStatus();

      // Call handleAuthClick function when user clicks on
      //      "Sign In/Authorize" button.
      $('#sign-in-or-out-button').click(function() {
        handleAuthClick();
      });
      $('#revoke-access-button').click(function() {
        revokeAccess();
      });
    });
  }

  function handleAuthClick() {
    if (GoogleAuth.isSignedIn.get()) {
      // User is authorized and has clicked "Sign out" button.
      GoogleAuth.signOut();
    } else {
      // User is not signed in. Start Google auth flow.
      GoogleAuth.signIn();
    }
  }

  function revokeAccess() {
    GoogleAuth.disconnect();
  }

  function setSigninStatus() {
    var user = GoogleAuth.currentUser.get();
    var isAuthorized = user.hasGrantedScopes(SCOPE);
    if (isAuthorized) {
      $('#sign-in-or-out-button').html('Sign out');
      $('#revoke-access-button').css('display', 'inline-block');
      $('#auth-status').html('You are currently signed in and have granted ' +
          'access to this app.');
    } else {
      $('#sign-in-or-out-button').html('Sign In/Authorize');
      $('#revoke-access-button').css('display', 'none');
      $('#auth-status').html('You have not authorized this app or you are ' +
          'signed out.');
    }
  }

  function updateSigninStatus() {
    setSigninStatus();
  }
  /* function myFunction() {
  if (name!=NULL) {
	  document.getElementById("name").innerHTML = name;
	}
  } */
</script>
</head>
<body>
<!-- TODO -->
<% 
Cookie[] cookies = request.getCookies();
String userID = null;
String name = "";
if()
for(Cookie a: cookies)
{
	
	String cookieName = a.getName();
	/* if(cookieName.equals("UserID"))
	{
		userID = a.getValue();
	}
 */	if(cookieName.equals("name"))
	{
		name = URLDecoder.decode(a.getValue(), StandardCharsets.UTF_8);
	}
}
%>
  <div>
      <div id="SalEats">
        <a style="text-decoration:none; font-size: 20px; padding-top: 15px;" href="index.jsp"> <h3>SalEats!</h3></a>
			<% if (!(name.trim().isEmpty())) { %>
 				<a>Hi <%=name%>!</a>
 				<% System.out.println(name);%>
			<% } %> 
            <% if (name == "") { %>
          <a href="auth.jsp"> <h4>Login/Register</h4></a>
			<% } %> 
			<% if (!(name.trim().isEmpty())) {  %>
          <a href="LogoutDispatcher"> <h4>Logout</h4></a>
			<% } %> 
          <a href="index.jsp"> <h4>Home</h4></a>
          <hr style="width:100%", size="2", color=grey>
      </div>
    <div id="content">
        <div class="imgbox">
          <div class="banner">
              <img src="banner.jpeg" alt="Home Banner" class="rounded-corners" float = "left">
          </div>
        </div>
        <div>
        <form action="SearchDispatcher" method="POST">
          <div class="wrap">
          <div class="dropdown">
              <select name="category" id="category">
                  <option value="name">Name</option>
                  <option value="category">Category</option>
              </select>
              <br><br>
          </div>
          <div class="search">
            <input placeholder="Restaurant Name" name = "searched" id= "searched" style="width: 850px;">
            <div class="example">
            <button type="submit" style="color: white;background:#8b0000;"><i class="fa fa-search"></i></button>
          </div>
        </div>
            <div class = "checkbox-placing" style="display:inline">
              <input type="radio" class = "round" id="price" name="sortOption" value = "price" checked>
              <label for="price">price</label><br>
              <input type="radio" id="rating" name="sortOption" value = "rating">
              <label for="rating">rating</label><br>
          </div>
            <div class = "review-placing">
              <input type="radio" id="reviewCount" name="sortOption" value = "reviewCount">
              <label for="reviewCount">Review Count</label><br>
            </div>
          </div>
        </form>
    </div>
</div>
</div>
</body>
</html>