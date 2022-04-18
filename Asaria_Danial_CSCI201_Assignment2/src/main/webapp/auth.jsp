<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="1029435006638-cp1280lqdl3huv0mfva2ltmh65jiqf5f.apps.googleusercontent.com"
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
  console.log("Entered onsignin");
  var profile = googleUser.getBasicProfile();
  var email = profile.getEmail();
  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
  console.log('Name: ' + profile.getName());
  console.log('Image URL: ' + profile.getImageUrl());
  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
 var name = profile.getName();
 window.location.href = "GoogleDispatcher?name="
     + name;
 var auth2 = gapi.auth2.getAuthInstance();
 auth2.signOut().then(function () {
     console.log('User signed out.');
   });
 /*  + "&email="
 + email; */
}
    function signOut() {
      var auth2 = gapi.auth2.getAuthInstance();
      auth2.signOut().then(function () {
        console.log('User signed out.');
      });
    }
    
</script>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
<!-- TODO -->
<% String name = ""; %>
<% String Hi = "Hi"; %>

<div>
<div>
	<% String er = (String) request.getAttribute("error"); %>
	<% if (er != null)  {%>
    <div style= "background-color:#EF9A9A; height: 50px; text-align: center; padding-top: 30px;">
        <font color="grey"><% if (er != null) out.println(er);%></font>
    </div>     
   <% } %> 
	 </div>
<div>
    <div>
        <a style="text-decoration:none; font-size: 20px; padding-top: 15px;" href="index.jsp"> <h3>SalEats!</h3></a>
       <% if ((name != null && name.length()>1)) { %>
 				 <a><%=Hi%> <%=name%>!</a> 
 				<% System.out.println(name);%>
		<% } %> 
        <a href="auth.jsp"> <h4>Login/Register</h4></a>
        <a href="index.jsp"> <h4>Home</h4></a>
        <hr style="width:100%", size="2", color=grey>
    </div>
    <div id="content">
        <div id="login">
            <form action="LoginDispatcher" method="POST">
                <div>
                    <h1>Login</h1>
                </div>
                <div >
                    <div class = "login">
                        <label ><b>Email</b></label>
                        <input type="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" placeholder="" name="email" id= "email" required>

                        <label ><b>Password</b></label>
                        <input type="password" placeholder="" name="password" id= "password">
                        <button class = "authButton" type="submit"><i class='fa-solid fa-right-to-bracket' style='color: white'></i> Sign In</button>
                        <div class="g-signin2" data-onsuccess="onSignIn" style="width: 682px"></div> 
						 </div>
 
                </div>
            </form>
        </div>
        <div id="register">
<!--            action="action_page.php" method="post"-->
            <form action="RegisterDispatcher" method="POST">
                <div>
                    <h1>Register</h1>
                </div>
                    <div class = "login">
                        <label><b>Email</b></label>
                        <input type="text" placeholder="" id="email" name="email" required>
                        <label><b>Name</b></label>
                        <input type="text" placeholder="" id="name" name="name" required>
                        <label><b>Password</b></label>
                        <input type="password" placeholder="" id="password" name="password" required>
                        <label><b>Confirm Password</b></label>
                        <input type="password" placeholder="" id="confirm_password" name="confirm_password" required>
                        <input type="checkbox" placeholder="" id="terms" name="terms" required>
                        <label><b>I have read and agree to all terms and conditions of SalEats.</b></label>
                        <br>
                        <br>
                        <button class = "authButton" type="submit" class="pure-button pure-button-primary"><i class="fa-solid fa-user-plus" style='color: white'></i> Create Account</button>
                        <!--                        https://codepen.io/diegoleme/pen/qBpyvr-->
    <!--                     <script>
                            var password = document.getElementById("password")
                              , confirm_password = document.getElementById("confirm_password");

                            function validatePassword(){
                              if(password.value != confirm_password.value) {
                                confirm_password.setCustomValidity("Passwords Don't Match");
                              } else {
                                confirm_password.setCustomValidity('');
                              }
                            }

                            password.onchange = validatePassword;
                            confirm_password.onkeyup = validatePassword;
                          </script> -->
                    </div>
            </form>
        </div>
    </div>




</div>

</div>
</body>
</html>