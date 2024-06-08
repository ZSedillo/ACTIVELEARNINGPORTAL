/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package activelearning;

import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

/**
 *
 * @author ASUS
 */
public class CaptchaServlet extends HttpServlet 
{
    static {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        throw new RuntimeException("MySQL JDBC driver not found", e);
    }
    }
    private String mysqlUrl = "jdbc:mysql://localhost:3306/CourseSQL?useSSL=false";

    private String mysqlUser = "root";
    private String mysqlPassword = "1234";
    
    private String courseName;
    private String instructor;
    private String student;
    private String typeOfClass;
    private String startingDate;
    
    private String mysqlQuery = "SELECT COURSE_NAME, INSTRUCTOR, STUDENTNAME,TYPEOFCLASS, STARTINGDATE FROM course_info";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CaptchaServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CaptchaServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    private int length;
    private String generatedCaptcha;
    @Override
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
        String lengthParam = config.getInitParameter("length");
        length = Integer.parseInt(lengthParam);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        generatedCaptcha = generateCaptcha(length);
        HttpSession session = request.getSession();
        session.setAttribute("captcha", generatedCaptcha);
        request.getRequestDispatcher("captcha.jsp").forward(request, response);
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        String enteredCaptcha = request.getParameter("userCaptcha");
       
        HttpSession session = request.getSession();  
        String role = (String) session.getAttribute("role");
        if (session == null) 
        {  
            response.sendRedirect("error_session.jsp"); 
        }
        else if(checkCaptcha(generatedCaptcha, enteredCaptcha) == true)
        {
//            try (Connection mysqlCon = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
//            Statement mysqlStmt = mysqlCon.createStatement();
//            ResultSet rsMySQL = mysqlStmt.executeQuery(mysqlQuery)) 
//            {
//
//                    System.out.println("Setting up records for student");
//                    while (rsMySQL.next()) 
//                    {
//                        courseName = rsMySQL.getString("COURSE_NAME");
//                        instructor = rsMySQL.getString("INSTRUCTOR");
//                        typeOfClass = rsMySQL.getString("TYPEOFCLASS");
//                        student = rsMySQL.getString("STUDENTNAME");
//                        startingDate = rsMySQL.getString("STARTINGDATE");
//                    }
//                    request.getSession().setAttribute("course_name", courseName);
//                    request.getSession().setAttribute("instructor", instructor);
//                    request.getSession().setAttribute("class", typeOfClass);
//                    request.getSession().setAttribute("student", student);
//                    request.getSession().setAttribute("course_duration", startingDate);                     
//                    System.out.println("Setting up records compleated");
//            } 
//            catch (SQLException e) 
//            {
//                    e.printStackTrace();
//            }   
            if(role.equals("instructor"))
                response.sendRedirect("instructor.jsp");
            else if(role.equals("student"))             
                response.sendRedirect("CourseServlet");
            else if(role.equals("admin"))             
                response.sendRedirect("admin.jsp");

        }
        else
        {
            String errorMessage = "Incorrect Captcha. Please enter the Captcha properly.";
            request.getSession().setAttribute("errorMessage", errorMessage);
            generatedCaptcha = generateCaptcha(length);
            session.setAttribute("captcha", generatedCaptcha);
            request.getRequestDispatcher("captcha.jsp").forward(request, response);
        }
    
  
        
    }

    static boolean checkCaptcha(String captcha, String userCaptcha) 
    {
        return captcha.equals(userCaptcha);
    }
    
    static String generateCaptcha(int n) 
    {
        //to generate random integers in the range [0-61]
        Random rand = new Random(62); 
        
        // Characters to be included
        String chrs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
        // Generate n characters from above set and
        // add these characters to captcha.
        String captcha = "";
        while (n-- > 0)
        {
            int index = (int)(Math.random()*62);
            captcha+=chrs.charAt(index);
        }
        System.out.println(captcha);
        return captcha;
    }
    
    @Override
    public String getServletInfo() 
    {
        return "Short description";
    }// </editor-fold>
    
    

}