<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>Python Class</title>
   <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">
     <link rel="stylesheet" href="class.css">

</head>
<body>    
    <div class="side-bar">
                
                <div class="profile">
                <img src="img/studentpfp.png" alt="Sample Image">
                <h3 class="name"><%= request.getSession().getAttribute("uname") %> </h3>
                <p class="role">Student</p>
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
<div class='maincontent'>                
<div class="header">
             <section class="flex">
                 
                <a href="https://activelearning.ph/" class="logo" target="_blank">ActiveLearning</a> 


                <div class="icons">
                    <div id="menu-btn" class="fas fa-bars"></div>
                    <div id="toggle-btn" class="fas fa-sun"></div>
                 </div>  
               </section> 
            </div> 

<div class="header-container">
    <h1>Welcome to Python Programming Class</h1>
   <div class="instructor-container">
   
   </div>
   
</div> 


            
    <div class="button-container">
   <button class="button" onclick="scrollToSection('overview')">Overview</button>
   <button class="button" onclick="scrollToSection('course-outline')">Course Outline</button>
   <button class="button" onclick="scrollToSection('schedule')">Course Material</button>
</div>

<div class="container">
 <div class="container-item" id="overview">
      <div class='overview'><h2>Overview </h2><button class="dropdown-btn" id="dropdown-btn"><i class="fas fa-angle-down"></i></button></div>
      <div class="hidden-text">
         <p>Python is a widely used, open-source programming language that is especially suited for a wide range of applications including web development, machine learning, and data science. This Python training course provides the foundations for you to start writing Python applications.</p>
      
      </div>
   </div>

<div class="container-item" id="course-outline">
   <h2>Course Outline</h2>
   <div class="dropdown">
      <div class='overview'><label class="label">Introduction to Python </label><button class="dropdown-btn" id="dropdown-btn"><i class="fas fa-angle-down"></i></button>
      </div>
      
       <div class="dropdown-content" >
         <p>This is the introduction to Python section of the course.</p>
      </div>
   </div>
   <div class="dropdown">
      <div class='overview'><label class="label">Basic Python Syntax </label><button class="dropdown-btn" id="dropdown-btn"><i class="fas fa-angle-down"></i></button>
      </div>
          
      <div class="dropdown-content">
         <p>This is the basic Python syntax section of the course.</p>
      </div>
   </div>
   <div class="dropdown" >
      <div class='overview'><label class="label">Control Flow Structures </label><button class="dropdown-btn" id="dropdown-btn"><i class="fas fa-angle-down"></i></button>
      </div>
          
      <div class="dropdown-content">
         <p>This is the control flow structures section of the course.</p>
      </div>
   </div>
</div>


   <div class="container-item" id="schedule">
    <h2>Course Material</h2>
    <p>This is the scheduled class of the course.</p>
    <% 
    String typeOfClass = request.getParameter("typeOfClass");
    if ("Self-Paced Learning".equals(typeOfClass)) {
%>
    <div class="schedule-container">
        <div class="self-paced-container">
            <i class="fas fa-play-circle"></i> <!-- Font Awesome icon for self-paced learning -->
            <h3>Self-Paced Learning</h3>
            <ul>
                <li>Learn anytime, anywhere with our high-quality videos created by industry experts</li>
                <li>Hands-on Exercises</li>
            </ul>
            <div class="b">
                  <a class="inline-btn"><form action="EnterClassServlet" method="post">           
                  <button type="submit">Enter Class</button>
                  </form></a>
            </div>
            </div>
        </div>
     <% } else if ("Classroom Learning".equals(typeOfClass)) { %>
        <div class="classroom-container">
            <i class="fas fa-chalkboard-teacher"></i> <!-- Font Awesome icon for classroom learning -->
            <h3>Classroom Learning</h3>
            <ul>
                <li>Online classes with an expert instructor</li>
                <li>Real-time remote assistance from instructor</li>
            </ul>
            <div class="b">
                  <a class="inline-btn"><form action="EnterClassServlet" method="post">           
                  <button type="submit">Enter Class</button>
                  </form></a>
            </div>
            </div>
        
        <% }  %>
        </div>
    </div>
</div>
                
                
<script>
   function scrollToSection(sectionId) {
      const section = document.getElementById(sectionId);
      const yOffset = -60; // Adjust this value according to your layout
      const y = section.getBoundingClientRect().top + window.pageYOffset + yOffset;
      window.scrollTo({top: y, behavior: 'smooth'});
   }
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

let sideBar = document.querySelector('.side-bar');
let dropdownBtns = document.querySelectorAll('.dropdown-btn');

document.querySelector('#menu-btn').onclick = () =>{
   sideBar.classList.toggle('active');
   body.classList.toggle('active');
 
};

let dropdownBtn = document.querySelectorAll('.dropdown-btn');
dropdownBtn.forEach(btn => {
    btn.addEventListener('click', () => {
        btn.classList.toggle('click');
    }); });


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
  document.addEventListener('DOMContentLoaded', function () {
    const overviewContainer = document.getElementById('overview');
    const arrowIcon = overviewContainer.querySelector('.dropdown-btn');

    arrowIcon.addEventListener('click', function () {
        overviewContainer.classList.toggle('active');
    });
});
document.addEventListener('DOMContentLoaded', function () {
   const dropdownButtons = document.querySelectorAll('.dropdown-btn');

   dropdownButtons.forEach(button => {
      button.addEventListener('click', function () {
         const dropdownContent = this.parentElement.nextElementSibling;
         dropdownContent.classList.toggle('active');
      });
   });
});
    </script>
    
   
</body>
</html>