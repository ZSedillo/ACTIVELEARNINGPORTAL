<%-- 
    Document   : captcha
    Created on : 04 28, 24, 10:17:04 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Captcha</title>
        <link rel="stylesheet" type="text/css" href="captcha.css"> 
    </head>
    <body>
        
        <%
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
        %>
        
        <%
             response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
            if(session.getAttribute("uname")==null)
                response.sendRedirect("error_session.jsp"); 
        %>
        
        <div class="body-content">   
        <h1>Check if you are human</h1>
        <div class="form">
            <h3>Generated Text : </h3>
            <h3><span style="user-select: none;">
                 <%= request.getSession().getAttribute("captcha") %>
            </span></h3>
            <form action="CaptchaServlet" method="post">
                <label for="userCaptcha">Enter Captcha code above:</label>
                <input type="text" id="userCaptcha" name="userCaptcha" placeholder="Captcha">
                <input type="submit">
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
