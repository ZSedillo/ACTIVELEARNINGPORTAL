<%-- 
    Document   : instructor
    Created on : 04 26, 24, 11:32:39 AM
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

        </head>
        <body>
        <header class="header">

           <section class="flex">

              <a href="home.html" class="logo">ActiveLearning</a>

             <div class="icons">
                 <div id="menu-btn" class="fas fa-bars"></div>
                 <div id="search-btn" class="fas fa-search"></div>
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
              <h3 class="name"><%= request.getSession().getAttribute("uname") %></h3>
              <p class="role">Instructor</p>
              <a href="profile.html" class="btn">view profile</a>
           </div>

           <nav class="navbar">
              <a href="instructor.jsp"><i class="fas fa-home"></i><span>home</span></a>
              <a href="#" onclick="scrollToSection('courses-section')"><i class="fas fa-graduation-cap"></i><span>courses</span></a>
              <a href="about.jsp"><i class="fa-solid fa-circle-info"></i><span>about</span></a>
              <a><form action="LogOutServlet" method="post">
                    <button type="submit"><i class="fas fa-sign-out"></i><span>log out</span></button>
                </form></a>
           </nav>

        </div>
              <script>
                  function scrollToSection(sectionId) {
                      const section = document.getElementById(sectionId);
                      if (section) {
                          const yOffset = -70; 
                          const y = section.getBoundingClientRect().top + window.pageYOffset + yOffset;
                          window.scrollTo({top: y, behavior: 'smooth'});
                      }
                  }
              </script>

        <section class="home-grid">

           <h1 class="heading">Overview</h1>

           <div class="box-container">


                 <div class="box">
                 <h3 class="title">Apply as Instructor</h3>
                 <p class="tutor">Apply now to become an instructor and start a rewarding journey of teaching</p>
                 <a href="applyJob.jsp" class="inline-btn">Get Started</a>
              </div>
           
               <div class="box">
                   <h3 class="title">Print Report</h3>
                   <p class="tutor">Click below to print all your records</p>
                   <form action="PrintOwnRecordServlet"><button class="inline-btn">Own Record</button></form>
                   <form action="PrintCourseListDBServlet"><button class="inline-btn">Print Course List</button></form>
               </div>
              <div class="box">
                    <h3 class="title">popular courses</h3>
                    <div class="flex">
                     <a href="WebDev.jsp" target="_blank"><i class="fas fa-globe"></i><span>Web Dev</span></a>
                       <a href="Java.jsp" target="_blank"><i class="fab fa-java"></i><span>Java Programming</span></a>
                    </div>
                    </div>
              <div class="box">
              <div class="box">
    <h3 class="title">Go to Class</h3>
    <p class="tutor">Click below to easily access and manage your course sections</p>
    <a href="https://meet.google.com/gtm-bojn-oax" class="inline-btn" target="_blank">Go To Class</a>  
</div> 

           </div>

        </section>


   <section id="courses-section" class="courses">

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
