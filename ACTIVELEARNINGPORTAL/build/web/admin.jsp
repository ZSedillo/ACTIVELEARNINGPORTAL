<%-- 
    Document   : Admin
    Created on : 05 1, 24, 3:07:19 AM
    Author     : LENOVO-Pc
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>Admin</title>
   <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">
     <link rel="stylesheet" href="admin.css">

</head>
<body>
<div id="maincontent">
        <main>        
             <div class="header">
             <section class="flex">
                 
                <a href="https://activelearning.ph/" class="logo" target="_blank">ActiveLearning</a> 


                <div class="icons">
                    <div id="menu-btn" class="fas fa-bars"></div>
                    <div id="toggle-btn" class="fas fa-sun"></div>
                 </div>  
               </section> 
            </div> 
      
         
         
            <div class="side-bar">
                
                <div class="profile">
                <img src="img/studentpfp.png" alt="Sample Image">
                <h3 class="name"><%= request.getSession().getAttribute("uname") %> </h3>
                <p class="role">Admin</p>
                </div>
                
                <nav class="navbar">
                <a href="admin.jsp"><i class="fas fa-home"></i><span>home</span></a>
                <a href="https://activelearning.ph/courses/" target="_blank"><i class="fas fa-graduation-cap"></i><span>courses</span></a>
                <a href="https://activelearning.ph/about/" target="_blank"><i class="fas fa-info-circle"></i><span>about</span></a>
                <a href="https://activelearning.ph/contact/" target="_blank"><i class="fas fa-envelope"></i><span>contact</span></a>
                <a><form action="LogOutServlet" method="post">
                    <button type="submit"><i class="fas fa-sign-out"></i><span>log out</span></button>
                </form></a>
                </nav>
                
            </div>

<section class="home-grid">

   <h1 class="heading">Overview</h1>

   <div class="box-container">

      
         <div class="box">
         <h3 class="title">Print Report</h3>
         <p class="tutor">Choose a record to print</p>
         <div class="reports">
         <form action="PrintOwnRecordServlet" ><button type="submit"><a class="inline-btn"><span>Own Record</span></a></button></form>
         <form action="PrintUsersDBServlet" ><button type="submit"><a class="inline-btn"><span>List of Users</span></a></button></form>
         <form action="PrintCourseListDBServlet" ><button type="submit"><a class="inline-btn"><span>Courses & Instructors</span></a></button></form>
         </div>
        </div>
       
        <div class="box">
            <h3 class="title">popular courses</h3>
                <div class="flex">
                    <a href="https://activelearning.ph/learning-path/python-programming-training-philippines/" target="_blank"><i class="fab fa-python"></i><span>Python</span></a>
                    <a href="https://activelearning.ph/learning-path/web-development-training-philippines/" target="_blank"><i class="fas fa-globe"></i><span>Web Dev</span></a>
                    <a href="https://activelearning.ph/learning-path/java-programming-training-philippines/" target="_blank"><i class="fab fa-java"></i><span>Java Programming</span></a>
                </div>
        </div>
       
   </div>

</section>


    <section class="courses">
    <h1 class="heading">Course List</h1>

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
         <h3 class="title">ITIL® 4 Foundation Certification Program</h3>
         <a href="https://activelearning.ph/course/itil-foundation-certification-training-philippines/" class="inline-btn" target="_blank">View Course</a>
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
         <h3 class="title">CompTIA Security+</h3>
         <a href="https://activelearning.ph/course/comptia-security-training-philippines/" class="inline-btn"target="_blank">View Course</a>
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
