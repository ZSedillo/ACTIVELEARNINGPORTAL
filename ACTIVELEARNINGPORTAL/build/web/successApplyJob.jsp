<%-- 
    Document   : successApplyJob
    Created on : 05 1, 24, 12:08:29 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Apply Job Success </title>
        <link rel="stylesheet" type="text/css" href="studentsuccess.css"> 
        <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">
    </head>
    <body>
    
        
        <div id="maincontent">
        <main>        
             <header class="header">
             <section class="flex">
                 
                <a href="https://activelearning.ph/" class="logo" target="_blank">ActiveLearning</a> 


                <div class="icons">
                    <div id="menu-btn" class="fas fa-bars"></div>
                    
                    
                    <div id="toggle-btn" class="fas fa-sun"></div>
                 </div>  
               </section> 
            </header> 
      
         
         
            <div class="side-bar">
                
                <div class="profile">
                <img src="img/studentpfp.png" alt="Sample Image">
                <h3 class="name"><%= request.getSession().getAttribute("uname") %> </h3>
                <p class="role">Instructor</p>
                </div>
                
                <nav class="navbar">
                <a href="CourseServlet"><i class="fas fa-home"></i><span>home</span></a>
                <a href="https://activelearning.ph/courses/" target="_blank"><i class="fas fa-graduation-cap"></i><span>courses</span></a>
                <a href="https://activelearning.ph/about/" target="_blank"><i class="fas fa-info-circle"></i><span>about</span></a>
                <a href="https://activelearning.ph/contact/" target="_blank"><i class="fas fa-envelope"></i><span>contact</span></a>
                <a><form action="LogOutServlet" method="post">
                    <button type="submit"><i class="fas fa-sign-out"></i><span>log out</span></button>
                </form></a>
                </nav>
                
            </div>  
        <div class="form">        
        <h1 class="heading">You are now applied.</h1>
        
        <div>
            <h3 class="title">Please take note of your details:</h3>
            <p><strong>Name:</strong> <%= request.getSession().getAttribute("uname") %></p>
            <p><strong>Course:</strong> ${course} </p>
            <p><strong>Type of Class:</strong> ${typeOfClass} </p>
        </div>
        <div class="button">
        <form action="instructor.jsp">
            <button type="submit">Exit</button>
            </input>
        </form>
         </div> 
        </div> 
        
             <script>
            let toggleBtn = document.getElementById('toggle-btn');
             let body = document.body;
             let darkMode = localStorage.getItem('dark-mode');

             const enableDarkMode = () =>{
                toggleBtn.classList.replace('fa-sun', 'fa-moon');
                body.classList.add('dark');
                localStorage.setItem('dark-mode', 'enabled');
             };

             const disableDarkMode = () =>{
                toggleBtn.classList.replace('fa-moon', 'fa-sun');
                body.classList.remove('dark');
                localStorage.setItem('dark-mode', 'disabled');
             };

             if(darkMode === 'enabled'){
                enableDarkMode();
             }

             toggleBtn.onclick = (e) =>{
                darkMode = localStorage.getItem('dark-mode');
                if(darkMode === 'disabled'){
                   enableDarkMode();
                }else{
                   disableDarkMode();
                }
             };

             let sideBar = document.querySelector('.side-bar');

             document.querySelector('#menu-btn').onclick = () =>{
                sideBar.classList.toggle('active');
                body.classList.toggle('active');
             };


             window.onscroll = () =>{
                profile.classList.remove('active');
                search.classList.remove('active');

                if(window.innerWidth < 1200){
                   sideBar.classList.remove('active');
                   body.classList.remove('active');
                }
             };
          </script>
    </body>
</html>