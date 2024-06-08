/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package activelearning;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class CourseServlet extends HttpServlet {
    private String mysqlUrl;
    private String mysqlUser;
    private String mysqlPassword;
    
    @Override
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
        mysqlUrl = getServletContext().getInitParameter("mysqlUrl");
        mysqlUser = getServletContext().getInitParameter("mysqlUser");
        mysqlPassword = getServletContext().getInitParameter("mysqlPassword");
        
        // You can store these parameters in servlet context attributes if needed
        getServletContext().setAttribute("mysqlUrl", mysqlUrl);
        getServletContext().setAttribute("mysqlUser", mysqlUser);
        getServletContext().setAttribute("mysqlPassword", mysqlPassword);
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // JDBC URL, username, and password of MySQL server
        

        // Load JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }

        // Initialize connection and statement
        try (Connection con = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
             Statement stmt = con.createStatement()) {

            // Fetch current student's name
            String studentName = (String) request.getSession().getAttribute("uname");

            // Fetch course details for the current student
            String query = "SELECT COURSE_NAME, INSTRUCTOR, TYPEOFCLASS, CLASSSECTION FROM COURSE_INFO WHERE STUDENTNAME = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentName);
            ResultSet rs = pstmt.executeQuery();

            // Store course details in a list
            List<Map<String, String>> courses = new ArrayList<>();
            while (rs.next()) {
                Map<String, String> courseDetails = new HashMap<>();
                courseDetails.put("courseName", rs.getString("COURSE_NAME"));
                courseDetails.put("instructor", rs.getString("INSTRUCTOR"));
                courseDetails.put("typeOfClass", rs.getString("TYPEOFCLASS"));
                courseDetails.put("classSection", rs.getString("CLASSSECTION"));
                courses.add(courseDetails);
            }

            // Set course list attribute
            request.setAttribute("studentCourses", courses);

            // Forward the request to the JSP page
            request.getRequestDispatcher("student.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // JDBC URL, username, and password of MySQL server
        

        // Load JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }

        // Initialize connection and statement
        try (Connection con = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
             Statement stmt = con.createStatement()) {

            // Fetch current student's name
            String studentName = (String) request.getSession().getAttribute("uname");

            // Fetch course details for the current student
            String query = "SELECT COURSE_NAME, INSTRUCTOR, TYPEOFCLASS, CLASSSECTION FROM COURSE_INFO WHERE STUDENTNAME = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentName);
            ResultSet rs = pstmt.executeQuery();

            // Store course details in a list
            List<Map<String, String>> courses = new ArrayList<>();
            while (rs.next()) {
                Map<String, String> courseDetails = new HashMap<>();
                courseDetails.put("courseName", rs.getString("COURSE_NAME"));
                courseDetails.put("instructor", rs.getString("INSTRUCTOR"));
                courseDetails.put("typeOfClass", rs.getString("TYPEOFCLASS"));
                courseDetails.put("classSection", rs.getString("CLASSSECTION"));
                courses.add(courseDetails);
            }

            // Set course list attribute
            request.setAttribute("studentCourses", courses);

            // Forward the request to the JSP page
            request.getRequestDispatcher("addCourse.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
}
}
