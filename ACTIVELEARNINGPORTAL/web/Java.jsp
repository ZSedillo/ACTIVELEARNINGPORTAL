<%-- 
    Document   : WebDev
    Created on : 05 9, 24, 5:27:56 PM
    Author     : LENOVO-Pc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.IOException"%>
<%@page import="javax.servlet.ServletException"%>
<%@page import="javax.servlet.http.HttpServlet"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>

<%
    // Retrieve the employment type from the request attribute
    String employmentType = (String) request.getAttribute("employmentType");
%>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Python Class</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">
        <link rel="stylesheet" href="GoToClass.css">
        <link rel="shortcut icon"
              href="https://activelearning.ph/wp-content/uploads/2021/03/cropped-favicon-1-192x192.png" />
    </head>
    <body>    
        <header class="header">

            <section class="flex">

                <a href="home.html" class="logo">ActiveLearning</a>

                <div class="icons">
                    <div id="menu-btn" class="fas fa-bars"></div>
                    <div id="toggle-btn" class="fas fa-sun"></div>
                </div>
            </section>

            <div class="header-container">
                <h1>Java Programming</h1>
                <div class="instructor-container">

                </div>

            </div> 


            <div class="side-bar">

                <div id="close-btn">
                    <i class="fas fa-times"></i>
                </div>

                <div class="profile">
                    <img src="person.png" class="image" alt="">
                    <h3 class="name">Mr. To</h3>
                    <p class="role">Instructor</p>
                    <a href="profile.html" class="btn">view profile</a>
                </div>

                <nav class="navbar">
                    <a href="instructor.jsp"><i class="fas fa-home"></i><span>home</span></a>
               <a href="instructor2.jsp#courses-section"><i class="fas fa-graduation-cap"></i><span>courses</span></a>
                <a href="about.jsp"><i class="fa-solid fa-circle-info"></i><span>about</span></a>
                    <a href="login.jsp"><i class="fas fa-sign-out"></i><span>sign out</span></a>
                </nav>
            </div>
            <div class="button-container">
                <button class="button" onclick="scrollToSection('overview')">Overview</button>
                <button class="button" onclick="scrollToSection('course-outline')">Course Outline</button>
                <button class="button" onclick="scrollToSection('schedule')">Schedule</button>
            </div>

            <div class="container">
                <div class="container-item" id="overview">
                    <div class='overview'><h2>Overview </h2><label class="label">What to learn about?</label><button class="dropdown-btn" id="dropdown-btn"><i class="fas fa-angle-down"></i></button></div>
                    <div class="hidden-text">
                        <p>This course provides extensive experience with the Java language and its object-oriented features. This program will benefit professionals who wish to take the Oracle Certified Java Programmer certification and who want to pursue a career in Java or Android development.</p>
                    </div>
                </div>


                <div class="container-item" id="course-outline">
                    <h2>Course Outline</h2>
                    <div class="dropdown">
                        <div class='overview'><label class="label">The Java Platform </label><button class="dropdown-btn" id="dropdown-btn"><i class="fas fa-angle-down"></i></button>
                        </div>

                        <div class="dropdown-content" >
                            <ul>
                                <li>What is Java?</li>
                                <li>Java Virtual Machine</li>
                                <li>Java Development Process</li>
                                <li>Java SE, EE, and ME</li>
                            </ul>
                        </div>
                    </div>
                    <div class="dropdown">
                        <div class='overview'><label class="label">Java Development Overview</label><button class="dropdown-btn" id="dropdown-btn"><i class="fas fa-angle-down"></i></button>
                        </div>

                        <div class="dropdown-content">
                            <ul>
                                <li>Writing Your First Java Application</li>
                                <li>Compiling and Running Java Programs</li>
                                <li>The Eclipse IDE</li>
                            </ul>
                        </div>
                    </div>
                    <div class="dropdown" >
                        <div class='overview'><label class="label">Java Language Fundamentals </label><button class="dropdown-btn" id="dropdown-btn"><i class="fas fa-angle-down"></i></button>
                        </div>

                       
                              <div class="dropdown-content">
                            <ul>
                                <li>Elements of a Java Program</li>
                                <li>Identifiers</li>
                                <li>Literals and Data Types</li>
                                 <li>Operators</li>
                            </ul>
                        </div>
                        </div>
                    </div>
                
                              <div class="container-item" id="schedule">
    <h2>Schedule</h2>
    <p>This is the schedule section of the course.</p>
    <div class="schedule-container">
      <% if ("part-time".equals(employmentType)) { %>
            <!-- Show self-paced learning for Part-Time -->
            <div class="self-paced-container">
                <i class="fas fa-play-circle"></i>
                <h3>Self-Paced Learning</h3>
                <ul>
                    <li>Learn anytime, anywhere with our high-quality videos created by industry experts</li>
                    <li>Hands-on Exercises</li>
                </ul>
                <div class="gdrive-thumbnail-container">
                    <img src="phyton.png" alt="Google Drive" class="gdrive-thumbnail">
                    <a href="https://drive.google.com/drive/folders/1hZqmH6WJtTFC27jMpMbm5TB8Yuccj7YH?usp=sharing"><br>  Click this to access the Google Drive</a>
                </div>
            </div>
        <% } else if ("full-time".equals(employmentType)) { %>
            <!-- Show online classroom learning for Full-Time -->
            <div class="classroom-container">
                <i class="fas fa-chalkboard-teacher"></i>
                <h3>Classroom Learning</h3>
                <ul>
                    <li>Online classes with an expert instructor</li>
                    <li>Real-time remote assistance from instructor</li>
                </ul>
                <div class="meeting-link-container">
                    <img src="googlemeet.png" alt="Google Meet" class="meeting-thumbnail">
                    <a href="https://meet.google.com/qib-seau-fyu" class="meeting-link"> Click this to join the video meeting</a>
                </div>
            </div>
        <% } %>
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

                    const enableDarkMode = () => {
                        toggleBtn.classList.replace('fa-sun', 'fa-moon');
                        body.classList.add('dark');
                        localStorage.setItem('dark-mode', 'enabled');
                    };

                    const disableDarkMode = () => {
                        toggleBtn.classList.replace('fa-moon', 'fa-sun');
                        body.classList.remove('dark');
                        localStorage.setItem('dark-mode', 'disabled');
                    };

                    if (darkMode === 'enabled') {
                        enableDarkMode();
                    }

                    toggleBtn.onclick = (e) => {
                        darkMode = localStorage.getItem('dark-mode');
                        if (darkMode === 'disabled') {
                            enableDarkMode();
                        } else {
                            disableDarkMode();
                        }
                    };

                    let sideBar = document.querySelector('.side-bar');
                    let dropdownBtns = document.querySelectorAll('.dropdown-btn');

                    document.querySelector('#menu-btn').onclick = () => {
                        sideBar.classList.toggle('active');
                        body.classList.toggle('active');

                    };


                    let dropdownBtn = document.querySelectorAll('.dropdown-btn');
                    dropdownBtn.forEach(btn => {
                        btn.addEventListener('click', () => {
                            btn.classList.toggle('click');
                        });
                    });


                    window.onscroll = () => {
                        profile.classList.remove('active');
                        search.classList.remove('active');

                        if (window.innerWidth < 1200) {
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
