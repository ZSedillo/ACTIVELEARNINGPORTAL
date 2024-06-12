/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package activelearning;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
public class AddCourseServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddCourseServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddCourseServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private String mysqlUrl;
    private String mysqlUser;
    private String mysqlPassword;
    
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        String name = (String) request.getSession().getAttribute("uname");
        String course = request.getParameter("course");
        String typeOfClass = request.getParameter("typeofclass");
        String startingMonth = request.getParameter("month");
        String startingDay = request.getParameter("day");
        String startingYear = request.getParameter("year");
        String StartingDate = startingYear+"-"+startingMonth+"-"+startingDay;
        String instructor = "to be assigned";
        String section = createSection(course, typeOfClass);
        if(sectionHasInstructor(section))
        {
            instructor=setInstructor(section);
            System.out.println("Has set instructor");
        }
        else
        {
            instructor = "to be assigned";
        }
        
        instructor = "to be assigned";
        System.out.println("Name: "+ name);
        System.out.println("Course: " + course);
        System.out.println("Type of Class: "+ typeOfClass);
        System.out.println("Section: "+ section);
        System.out.println("Instructor:" + instructor);
        System.out.println("Hii");
        System.out.println("Date: " + StartingDate);
        
        if(studentHasSameCourse(name,course) == true)
        {
            String errorMessage = "Student already enrolled in this course. You cannot add this class";
            request.getSession().setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("CourseServlet").forward(request, response);
        }
        else
        {
        
            if(!course.equals("not_selected"))
            {
                if(typeOfClass.equals("Self-Paced"))
                {
                    if(isValidDate(startingYear,startingMonth,startingDay) && !isPastDate(startingYear,startingMonth,startingDay) )
                    {
//                        sectionHasInstructor();
                        String sql = "INSERT INTO coursesql.course_info (STUDENTNAME, COURSE_NAME, TYPEOFCLASS, CLASSSECTION, INSTRUCTOR, STARTINGDATE) VALUES (?, ?, ?, ?, ?, ?)";

                        try (Connection connection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
                             PreparedStatement statement = connection.prepareStatement(sql)) 
                        {
                            statement.setString(1, name);
                            statement.setString(2, course);
                            statement.setString(3, typeOfClass);
                            statement.setString(4, section);
                            statement.setString(5, "to be assigned");
                            statement.setDate(6, java.sql.Date.valueOf(StartingDate));

                            int rowsInserted = statement.executeUpdate();
                            if (rowsInserted > 0) {
                                System.out.println("A new row has been inserted.");
                                // Successful insertion logic here
                            }


                            request.setAttribute("name", name);
                            request.setAttribute("course", course);
                            request.setAttribute("typeOfClass", typeOfClass);
                            request.setAttribute("StartingDate", StartingDate);
                            request.setAttribute("section", section);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("successStudentDB.jsp");
                            dispatcher.forward(request, response);


                        }
                        catch (SQLException e) {
                            e.printStackTrace();
                            String errorMessage = "SQL Error: " + e.getMessage();
                            request.getSession().setAttribute("errorMessage", errorMessage);
                            request.getRequestDispatcher("addCourse.jsp").forward(request, response);
                        }
    
                        request.setAttribute("name", name);
                        request.setAttribute("course", course);
                        request.setAttribute("typeOfClass", typeOfClass);
                        request.setAttribute("StartingDate", StartingDate);
                        request.setAttribute("section", section);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("successStudentDB.jsp");
                        dispatcher.forward(request, response);
                            

                    }
                    else if(!isValidDate(startingYear,startingMonth,startingDay))
                    {
                        String errorMessage = "Invalid Date. Please try again.";
                        request.getSession().setAttribute("errorMessage", errorMessage);
                        request.getRequestDispatcher("addCourse.jsp").forward(request, response);
                    }
                    else if(isPastDate(startingYear,startingMonth,startingDay))
                    {
                        String errorMessage = "The Date entered is in the past. Please try again.";
                        request.getSession().setAttribute("errorMessage", errorMessage);
                        request.getRequestDispatcher("addCourse.jsp").forward(request, response);
                    }
                }
                else if(typeOfClass.equals("Classroom Learning"))
                {
                    switch (course) 
                    {
                        case "Java":
                            StartingDate = "2024-06-14";
                            break;
                        case "Python":
                            StartingDate = "2024-06-14";
                            break;
                        case "WebDev":
                            StartingDate = "2024-06-14";
                            break;
                        default:
                            break;
                    }
                    String sql = "INSERT INTO coursesql.course_info (STUDENTNAME, COURSE_NAME, TYPEOFCLASS, CLASSSECTION, INSTRUCTOR, STARTINGDATE) VALUES (?, ?, ?, ?, ?, ?)";

                        try (Connection connection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
                             PreparedStatement statement = connection.prepareStatement(sql)) 
                        {
                            statement.setString(1, name);
                            statement.setString(2, course);
                            statement.setString(3, typeOfClass);
                            statement.setString(4, section);
                            statement.setString(5, instructor);
                            statement.setDate(6, java.sql.Date.valueOf(StartingDate));

                            int rowsInserted = statement.executeUpdate();
                            if (rowsInserted > 0) {
                                System.out.println("A new row has been inserted.");
                                // Successful insertion logic here
                            }


                            request.setAttribute("name", name);
                            request.setAttribute("course", course);
                            request.setAttribute("typeOfClass", typeOfClass);
                            request.setAttribute("StartingDate", StartingDate);
                            request.setAttribute("section", section);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("successStudentDB.jsp");
                            dispatcher.forward(request, response);


                        }
                        catch (SQLException e) {
                            e.printStackTrace();
                            String errorMessage = "SQL Error: " + e.getMessage();
                            request.getSession().setAttribute("errorMessage", errorMessage);
                            request.getRequestDispatcher("addCourse.jsp").forward(request, response);
                        }
    
                        request.setAttribute("name", name);
                        request.setAttribute("course", course);
                        request.setAttribute("typeOfClass", typeOfClass);
                        request.setAttribute("StartingDate", StartingDate);
                        request.setAttribute("section", section);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("successStudentDB.jsp");
                        dispatcher.forward(request, response);
                
                }
                
                else
                {
                    String errorMessage = "Must Select a Type of Class. Please try again.";
                    request.getSession().setAttribute("errorMessage", errorMessage);
                    request.getRequestDispatcher("addCourse.jsp").forward(request, response);
                }
            }
            else
            {
                String errorMessage = "Must Select a Course. Please try again.";
                request.getSession().setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("addCourse.jsp").forward(request, response);
            }  
        }
    }
    
    
    public String createSection(String course, String typeofclass)
    {
        String query = "SELECT COUNT(*) AS count FROM coursesql.course_info WHERE COURSE_NAME = ? AND TYPEOFCLASS = ?";
        try (Connection connection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
                             PreparedStatement statement = connection.prepareStatement(query)) 
        {
            statement.setString(1, course);
            statement.setString(2, typeofclass);

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();

            // Check the count
            if (resultSet.next()) 
            {
                int count = resultSet.getInt("count");
                System.out.println(count);
                String sectionnum = Double.toString(Math.ceil(count/20)+1);
                // Creating of Section
                char firstLetterCourse = Character.toUpperCase(course.charAt(0));
                char firstLetterType = Character.toUpperCase(typeofclass.charAt(0));
                String section = String.valueOf(firstLetterCourse) + String.valueOf(firstLetterType) + sectionnum;
                return section.substring(0, Math.min(section.length(), 3));
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            String errorMessage = "SQL Error: " + e.getMessage();
        }
         return"Hatdog";
    }
    
    public boolean sectionHasInstructor(String section)
    {
        String query = "SELECT COUNT(*) FROM coursesql.course_info WHERE CLASSSECTION = ? AND INSTRUCTOR = 'to be assigned'";
        try (Connection connection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
                             PreparedStatement statement = connection.prepareStatement(query)) 
        {
            statement.setString(1, section);

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();

            // Check the count
            int count=0;
            if (resultSet.next()) 
            {
                count = resultSet.getInt(1);
                
            }
            if (count > 0) {
                    System.out.println("There is no instructor yet");
                    return false;
                } 
                else if(count == 0)
                {
                    System.out.println("They have an instructor");
                    return true;
                }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            String errorMessage = "SQL Error: " + e.getMessage();
        }
        return false;
    }
    
    public String setInstructor(String section)
    {
        String query = "SELECT INSTRUCTOR FROM coursesql.course_info WHERE CLASSSECTION= ?";
        String instructor="";
        try (Connection connection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
                             PreparedStatement statement = connection.prepareStatement(query)) 
        {
            statement.setString(1, section);

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) 
            {
                
                instructor = resultSet.getString("INSTRUCTOR");
            }
            
            
           
            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
            return instructor;

        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            String errorMessage = "SQL Error: " + e.getMessage();
        }
        return "";
    }
    
   public boolean studentHasSameCourse(String name, String course) 
   {
        String query = "SELECT COUNT(*) FROM coursesql.course_info WHERE STUDENTNAME = ? AND COURSE_NAME = ?";
        try (Connection connection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, course);

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();

            // Check the count
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    System.out.println("Student is already enrolled in the course");
                    return true;
                } else {
                    System.out.println("Student is not enrolled in the course");
                    return false;
                }
            }

            // Close the resources
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            String errorMessage = "SQL Error: " + e.getMessage();
        }
        return false;
    }

    
    public boolean studentMaxLoad(String name)
    {
        String query = "SELECT COUNT(STUDENTNAME) FROM coursesql.course_info WHERE STUDENTNAME = ?";
//        String sql = "INSERT INTO coursesql.course_info (STUDENTNAME, COURSE_NAME, TYPEOFCLASS, CLASSSECTION, INSTRUCTOR, STARTINGDATE) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
                             PreparedStatement statement = connection.prepareStatement(query)) 
        {
            statement.setString(1, name);

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();

            // Check the count
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 1) {
                    System.out.println("Student at max load");
                    return true;
                } 
                else 
                {
                    System.out.println("Student is not at max load");
                    return false;
                }
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            String errorMessage = "SQL Error: " + e.getMessage();
        }
        return false;
    }
    
    
    
    
    public static boolean isPastDate(String year, String month, String day) 
    {
        try {
            int yearInt = Integer.parseInt(year);
            int monthInt = Integer.parseInt(month);
            int dayInt = Integer.parseInt(day);
            
            // Attempt to create a LocalDate object
            LocalDate date = LocalDate.of(yearInt, monthInt, dayInt);
            
            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Compare the input date with the current date
            return date.isBefore(currentDate);
        } catch (NumberFormatException | java.time.DateTimeException e) {
            // If parsing fails or the date is invalid, catch the exception and return false
            return false;
        }
    }
    
    public static boolean isValidDate(String year, String month, String day) 
    {
        try {
            int yearInt = Integer.parseInt(year);
            int monthInt = Integer.parseInt(month);
            int dayInt = Integer.parseInt(day);
            
            // Attempt to create a LocalDate object
            LocalDate date = LocalDate.of(yearInt, monthInt, dayInt);
            
            // If parsing is successful and no exception is thrown, return true
            return true;
        } catch (NumberFormatException | java.time.DateTimeException e) {
            // If parsing fails or the date is invalid, catch the exception and return false
            return false;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    

}
