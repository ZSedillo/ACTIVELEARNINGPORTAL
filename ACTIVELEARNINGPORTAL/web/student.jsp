<%-- 
    Document   : student
    Created on : 04 26, 24, 11:32:28 AM
    Author     : ASUS
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Student</title>
        <link rel="stylesheet" type="text/css" href="student.css"> 
        <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">
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
            
             
            <section class="home-grid">
                <h1 class="heading">overview</h1>
                <div class ="box-container">
                
                    <div class="box">
                    <h3 class="title">Enroll now</h3>
                    <p class="tutor">What do you want to learn?</p>
                    <a class="inline-btn"><form action="CourseServlet" method="post">
                        <button type="submit">Add a Course</button>
                    </form></a>
                    </div>
                    
                    <div class="box">
                    <h3 class="title">Print Report</h3>
                    <p class="tutor">Print your record</p>
                    <form action="PrintOwnRecordServlet"><button type="submit"><a class="inline-btn"> Own Record</a></button></form>  
                    </div>
                    
                    <div class="box">
                    <h3 class="title">popular courses</h3>
                    <div class="flex">
                       <a href="https://activelearning.ph/learning-path/python-programming-training-philippines/" target="_blank"><i class="fab fa-python"></i><span>Python</span></a>
                       <a href="https://activelearning.ph/learning-path/web-development-training-philippines/" target="_blank"><i class="fas fa-globe"></i><span>Web Dev</span></a>
                       <a href="https://activelearning.ph/learning-path/java-programming-training-philippines/" target="_blank"><i class="fab fa-java"></i><span>Java Programming</span></a>
                    </div>
                    </div>

                    <div class="box">
                    <h3 class="title">Student testimonials</h3>
                    <p class="tutor">Check out reviews from students</p>
                    <a href="https://activelearning.ph/all-testimonials/" class="inline-btn" target="_blank">Learn more</a>  
                    </div>
            </section>   
             
            <section class="courses">
              <h1 class="heading">Current Courses</h1>
            <div class="box-container"> 
            
               <% 
                    List<Map<String, String>> courses = (List<Map<String, String>>) request.getAttribute("studentCourses");
                    if (courses != null && !courses.isEmpty()) {   
                        for (Map<String, String> course : courses) { %>
                            <div class="box">
                                <div class="tutor">
                                <img src="img/person.png" alt="">
                                <h2 class="title"><%= course.get("courseName") %></h2>
                                </div>
                                <div class="thumb">
                                <img src="https://th.bing.com/th/id/OIP.qreNHi-B1tlIXfShuxZMugHaEK?rs=1&pid=ImgDetMain" alt="">    
                                </div>
                                
                                <div class="info">
                                <h2><strong>Instructor:</strong> <%= course.get("instructor") %></h2>
                                <h2><strong>Type of Class:</strong> <%= course.get("typeOfClass") %></h2>
                                <h2><strong>Class Section:</strong> <%= course.get("classSection") %></h2>
                                </div>
                                <a class="inline-btn"><form action="ClassServlet" method="post" >
                                    <input type="hidden" name="courseName" value="<%= course.get("courseName") %>">
                                    <input type="hidden" name="typeOfClass" value="<%= course.get("typeOfClass") %>">
                                    <button type="submit">Start Learning</button>
                                </form></a>
                            </div>
                             
                    <%  }
                    } else { %>
                        <h1 class="dark">No courses enrolled.</h1>
                    <% } %> 
                </div>
          </section>  
        </main>
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
