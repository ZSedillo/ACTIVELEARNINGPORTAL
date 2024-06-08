<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Homepage</title>
        <link rel="stylesheet" type="text/css" href="logins.css"> 
    </head>
    <body>

        <div class="body-content">    
             <a href="https://activelearning.ph/"target="_blank" ><img src="img/Active Learning Logo.png" alt="logo"> </a>
            <div class="form">
                
            <form action="LoginServlet" method="post">
                <label for="username">Username:</label> 
                <input type="text" id="username" name="username" placeholder="Email">
                <label for="password">Password:</label> 
                <input type="password" id="password" name="password" placeholder="Password">
                <p>No account?<a href="signUp.jsp"> Sign up here!</a></p>
                <input type="submit" value="Log In" id="login-button">
                <script>
                    // Display error message in alert
                    var errorMessage = "<%= session.getAttribute("errorMessage") %>";
                    if(errorMessage && errorMessage.trim().length > 0) {
                        if (errorMessage.trim() !== "null") { // Check if errorMessage is not "null"
                            alert(errorMessage);
                        }
                        errorMessage = null; // Clear the error message after displaying
                    }
                </script>
            </form>
                    </div>            
        
        </div>    
            
    </body>
</html>