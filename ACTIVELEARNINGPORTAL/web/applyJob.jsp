<%-- 
    Document   : applyJob
    Created on : 05 1, 24, 12:07:57 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>Instructor</title>
   <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">
     <link rel="stylesheet" href="instructor.css">
     <style>
    .home-grid .box-container .box button{
           background-color: transparent;
           font-size: 1.8rem;
           margin:0;
       }
       .home-grid .box-container .tutor{
          padding: 0;
          font-size: 1rem;
          color:var(--black);
          line-height: 2;  
       }

    .home-grid .box-container .box button{
    background-color: transparent;
    font-size: 1.8rem;
    margin:0;
    }

    .home-grid .box-container button{
       color:var(--white);
    }
    .home-grid .box-container button:hover {
       color: var(--white);
    }
    </style>

</head>
    <body>
    <header class="header">

       <section class="flex">

          <a href="home.html" class="logo">ActiveLearning</a>

          <form action="search.html" method="post" class="search-form">
             <input type="text" name="search_box" required placeholder="search courses..." maxlength="100">
             <button type="submit" class="fas fa-search"></button>
          </form>

          <div class="icons">
             <div id="menu-btn" class="fas fa-bars"></div>
             <div id="search-btn" class="fas fa-search"></div>
             <div id="user-btn" class="fas fa-user"></div>
             <div id="toggle-btn" class="fas fa-sun"></div>
          </div>
       </section>

    </header>   

    <div class="side-bar">

       <div id="close-btn">
          <i class="fas fa-times"></i>
       </div>

       <div class="profile">
         <img src="img/person.png" class="image" alt="">

          <h3 class="name">Mr. To</h3>
            <p class="role">Instructor</p>
          <a href="profile.html" class="btn">view profile</a>
       </div>

       <nav class="navbar">
          <a href="instructor.jsp"><i class="fas fa-home"></i><span>home</span></a>
          <a href="courses.jsp"><i class="fas fa-graduation-cap"></i><span>courses</span></a>
          <a href="teachers.jsp"><i class="fas fa-chalkboard-user"></i><span>teachers</span></a>
          <a><form action="LogOutServlet" method="post">
                    <button type="submit"><i class="fas fa-sign-out"></i><span>log out</span></button>
                </form></a>
       </nav>

    </div>

    <section class="home-grid">

       <h1 class="heading">Applying as Instructor</h1>

       <div class="box-container">
       <!-- Personal Information Section -->
       <div class="box-container">
            <div class="box">
            <h3 class="title">Apply Now</h3>
            <form action="ApplyJobServlet" method="post" class="application-form"> 
            <p class="tutor"><label for="username"><strong>Name:</strong> <%= request.getSession().getAttribute("uname") %></label></p>
            <div>
                <p class="tutor"><label for="course"><strong>Course:</strong> </label></p>
                <select name="course">
                    <option value="not_selected">Select a course</option>
                    <option value="Java">Java</option>
                    <option value="Python">Python</option>
                    <option value="WebDev">Web Development</option>
                </select>
            </div>
            <div> <p class="tutor"><label><strong>Mode of Learning:</strong></label></p>   
                <p class="tutor"><label>
                    <input type="radio" name="typeofclass" value="Self-Paced" > Self-Paced Learning 
                </label></p>
                <p class="tutor"><label>
                    <input type="radio" name="typeofclass" value="Classroom Learning" > Classroom Learning
                </label></p>
                <button type="submit" class="inline-button">Submit</button>
                
            </div>
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

    </div>

       </section>
        <section class="courses">
        <h1 class="heading">our courses</h1>

       <div class="box-container">

          <div class="box">
             <div class="tutor">
                <img src="img/tutor1.png" alt="">
                <div class="info">
                   <h3>Anderson Clark</h3>
                   <span>24-10-2024</span>
                </div>
             </div>
             <div class="thumb">
                <img src="img/appdev.jpg" alt="">           
             </div>
             <h3 class="title">Applications Development and Emerging Technologies</h3>
             <a href="  " class="inline-btn">View Course</a>
          </div>

          <div class="box">
             <div class="tutor">
                <img src="img/tutor2.png" alt="">
                <div class="info">
                   <h3>Amy Smith</h3>
                   <span>15-10-2024</span>
                </div>
             </div>
             <div class="thumb">
                <img src="img/comparchi.jpg" alt="">
                <span>5 videos</span>
             </div>
             <h3 class="title">Computer Architecture and Organization</h3>
             <a href="course1.jsp" class="inline-btn">View Course</a>
          </div>
       </div> 

    </section> 
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

    let profile = document.querySelector('.header .flex .profile');

    document.querySelector('#user-btn').onclick = () =>{
       profile.classList.toggle('active');
       search.classList.remove('active');
    };

    let search = document.querySelector('.header .flex .search-form');

    document.querySelector('#search-btn').onclick = () =>{
       search.classList.toggle('active');
       profile.classList.remove('active');
    };

    let sideBar = document.querySelector('.side-bar');

    document.querySelector('#menu-btn').onclick = () =>{
       sideBar.classList.toggle('active');
       body.classList.toggle('active');
    };

    document.querySelector('#close-btn').onclick = () =>{
       sideBar.classList.remove('active');
       body.classList.remove('active');
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
