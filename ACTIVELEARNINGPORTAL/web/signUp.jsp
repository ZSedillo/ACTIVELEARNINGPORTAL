<%-- 
    Document   : signUp.jsp
    Created on : 05 2, 24, 4:01:30 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="signUp.css"> 

        <title>Homepage</title>
    </head>
    <body>
               <div class="body-content">
    <a href="https://activelearning.ph/"><img src="img/whitelogo.png" alt="logo"> <a/>

        <div class="form">

            <form action="SignUpServlet" method="post">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" placeholder="Email">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" placeholder="Password">
                <label for="password">Confirm Password:</label>
                <input type="password" id="confirm password" name="conpassword" placeholder="Confirm Password">
                <div class="dropdown-container">
                    <select id="role" name="role" class="dropdown-button">
                        <option value="" disabled selected>Select a role</option>
                        <option value="student">Student</option>
                        <option value="instructor">Instructor</option>
                    </select>
                </div>

                <input type="submit" value="Sign Up" id="signup-button">

                <script>
                    // Display error message in alert
                    var errorMessage = "<%= session.getAttribute("errorMessage") %>";
                    if(errorMessage && errorMessage.trim().length > 0 && errorMessage.trim() !== "null") 
                    {
                        alert(errorMessage);
                        errorMessage = null;
                    }
                </script>
            </form>
        </div>

</div>
                    
    </body>
</html>
