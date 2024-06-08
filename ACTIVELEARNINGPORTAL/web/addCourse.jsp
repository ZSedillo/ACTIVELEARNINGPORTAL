<%-- 
    Document   : addCourse
    Created on : 04 28, 24, 1:02:50 PM
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
        <title>Add course</title>
        <link rel="stylesheet" type="text/css" href="addcourse.css"> 
        <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">
    </head>
    <body>
        
   
        <div id="maincontent">
        <main>        
             <header class="header">
             <section class="flex">
                 
                <a href="https://activelearning.ph/" class="logo">ActiveLearning</a> 


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
        <h1 class="heading">Overview</h1>
        <div class="box-container">
            <div class="box">
            <h3 class="title">Enroll Now</h3>
            <form action="AddCourseServlet" method="post" class="application-form">
            <p class="tutor1">
                <strong>Please take not when choosing Classroom Learning, it will have a fixed schedule date
                <br> Java: May 10,2024
                <br> Python: May 12,2024
                <br> WebDev: May 14,2024
                </strong>
            </p>    
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
            </div>
            <div>
                <p class="tutor"><label><strong>Starting Date:</strong></label></p>
                <select name="month">
                    <option value="01">January</option>
                    <option value="02">February</option>
                    <option value="03">March</option>>
                    <option value="04">April</option>
                    <option value="05">May</option>
                    <option value="06">June</option>
                    <option value="07">July</option>
                    <option value="08">August</option>
                    <option value="09">September</option>
                    <option value="10">October</option>
                    <option value="11">November</option>
                    <option value="12">December</option>
                </select>
                <select name="day">
                    <% for (int day = 1; day <= 31; day++) { %>
                        <option value="<%= String.format("%02d", day) %>"><%= String.format("%02d", day) %></option>
                    <% } %>
                </select>
                <select name="year">
                    <% for (int year = 2024; year >=2000 ; year--) { %>
                        <option value="<%= year %>"><%= year %></option>
                    <% } %>
                </select>
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
            
            <a class="inline-btn">
            <button type="submit">Add Course</button>
            </a>
        </form>
        </div>
           
           <div class="box">
                    <h3 class="title">Print Report</h3>
                    <p class="tutor1">Print your record</p>
                    <a href="" class="inline-btn" target="_blank"> Own Record</a>  
            </div>
                
                
           <div class="box">
                    <h3 class="title">popular courses</h3>
                    <div class="flex">
                       <a href="https://activelearning.ph/learning-path/python-programming-training-philippines/" target="_blank"><i class="fab fa-python"></i><span>Python</span></a>
                       <a href="https://activelearning.ph/learning-path/web-development-training-philippines/" target="_blank"><i class="fas fa-globe"></i><span>Web Dev</span></a>
                       <a href="https://activelearning.ph/learning-path/java-programming-training-philippines/" target="_blank"><i class="fab fa-java"></i><span>Java Programming</span></a>
                    </div>
                    </div>

                    <div class="box" id="box">
                    <h3 class="title">student testimonials</h3>
                    <p class="tutor1">Check out reviews from students</p>
                    <a href="https://activelearning.ph/all-testimonials/" class="inline-btn" target="_blank">Learn more</a>  
                    </div>
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
                                <a class="inline-btn"><form action="ClassServlet" method="post">
                                   <input type="hidden" name="courseName" value="<%= course.get("courseName") %>">
                                    <input type="hidden" name="typeOfClass" value="<%= course.get("typeOfClass") %>"> 
                                   <button type="submit">Start Learning</button>
                                </form></a>
                            </div>
                             
                    <%  }
                    } else { %>
                        <h1 class="dark">No courses available.</h1>
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