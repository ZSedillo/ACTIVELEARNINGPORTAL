<%-- 
    Document   : index
    Created on : 04 25, 24, 10:57:34 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Welcome to Active Learning</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="header-content">
                    <link rel="stylesheet" type="text/css" href="landingpage.css"> 
                </div>
        <div class="banner">
            <div class="navbar">
                 <img src="img\whitelogo.png" class="logo"> 
                 <ul>
                     <li><a href="https://activelearning.ph/"> Home </a></li>
                     <li><a href="https://activelearning.ph/about/"> About </a></li>
                     <li><a href="https://activelearning.ph/contact/"> Contact </a></li>    
                 </ul>
    </div>
           <div class="content"> 
               <h1> WELCOME TO ACTIVE LEARNING PORTAL!</h1>
                <p> Active Learning is a business education consulting firm based in Tokyo. We provide executive consulting services to a variety of businesses<br>
                    ranging from startups to multi-conglomerate enterprises. Active Learning seeks to impart our valued clients with guidance on employee <br>
                    recruitment and training as well as corporate strategy and restructuring. </p> 
                <div> 
<!--                    <button type="button"><span></span>GO TO SITE</button> -->
               <button type="button" onclick="window.location.href='loginPage.jsp'">
        <span></span> GET STARTED
    </button>
               </div>
           </div>
        </div>

        <script>
            // Display error message in alert
            var errorMessage = "<%= session.getAttribute("errorMessage") %>";
            if(errorMessage && errorMessage.trim().length > 0 && errorMessage.trim() !== "null") 
            {
                alert(errorMessage);
                errorMessage = null;
            }
        </script>
                    <footer>
                <div class="footer-content">
                </div>
            </footer>
    </body>
</html>

