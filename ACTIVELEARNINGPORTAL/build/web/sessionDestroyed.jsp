<%-- 
    Document   : SessionDestroyed
    Created on : 05 1, 24, 12:07:15 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Session Destroyed</title>
    </head>
    <body>
        <header>
            <div class="header-content">
                <%= request.getServletContext().getInitParameter("header") %>
                <link rel="stylesheet" type="text/css" href="index.css"> 
            </div>
        </header>
        <h1>Hello World!</h1>
        <footer>
            <div class="footer-content">
                <%= request.getServletContext().getInitParameter("footer") %>
            </div>
        </footer>
    </body>
</html>
