/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package activelearning;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
/**
 *
 * @author ASUS
 */
public class ApplyJobServlet extends HttpServlet {

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
            out.println("<title>Servlet ApplyJobServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ApplyJobServlet at " + request.getContextPath() + "</h1>");
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

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    String name = (String) request.getSession().getAttribute("uname");
    String course = request.getParameter("course");
    String typeOfClass = request.getParameter("typeofclass");

    if (!"not_selected".equals(course) && typeOfClass != null) {
        try (Connection connection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword)) {
            // Find available sections for the given course and type of class
            String findAvailableSectionsQuery = "SELECT CLASSSECTION FROM coursesql.course_info WHERE COURSE_NAME = ? AND TYPEOFCLASS = ? AND INSTRUCTOR = 'to be assigned' GROUP BY CLASSSECTION HAVING COUNT(*) <= 20 LIMIT 2";

            PreparedStatement findAvailableSectionsStmt = connection.prepareStatement(findAvailableSectionsQuery);
            findAvailableSectionsStmt.setString(1, course);
            findAvailableSectionsStmt.setString(2, typeOfClass);
            ResultSet availableSectionsResult = findAvailableSectionsStmt.executeQuery();

            List<String> sections = new ArrayList<>();
            while (availableSectionsResult.next()) {
                sections.add(availableSectionsResult.getString("CLASSSECTION"));
            }

            // Check if the instructor has already occupied two sections
            int instructorOccupancy = 0;
            String instructorOccupancyQuery = "SELECT COUNT(*) AS occupancy FROM coursesql.course_info WHERE INSTRUCTOR = ?";
            PreparedStatement instructorOccupancyStmt = connection.prepareStatement(instructorOccupancyQuery);
            instructorOccupancyStmt.setString(1, name);
            ResultSet instructorOccupancyResult = instructorOccupancyStmt.executeQuery();
            if (instructorOccupancyResult.next()) {
                instructorOccupancy = instructorOccupancyResult.getInt("occupancy");
            }

            if (instructorOccupancy >= 2) {
                System.out.println("Instructor has already occupied two sections.");
                // Handle response for instructor already occupying two sections
                return;
            }

            // Assign instructor to available sections
            for (String section : sections) {
                String assignInstructorQuery = "UPDATE coursesql.course_info SET INSTRUCTOR = ? WHERE COURSE_NAME = ? AND TYPEOFCLASS = ? AND CLASSSECTION = ? AND INSTRUCTOR = 'to be assigned'";
                PreparedStatement assignInstructorStmt = connection.prepareStatement(assignInstructorQuery);
                assignInstructorStmt.setString(1, name);
                assignInstructorStmt.setString(2, course);
                assignInstructorStmt.setString(3, typeOfClass);
                assignInstructorStmt.setString(4, section);

                int rowsUpdated = assignInstructorStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Instructor " + name + " assigned successfully to section " + section + ".");
                    instructorOccupancy++;
                }
            }

            if (instructorOccupancy > 0) {
                System.out.println("Instructor " + name + " has been assigned to " + instructorOccupancy + " sections.");
                // Forward request to success page
                String sectionInstructor="";
                for (String section : sections) {
                    sectionInstructor+=" ";
                }
                request.setAttribute("name", name);
                request.setAttribute("course", course);
                request.setAttribute("typeOfClass", typeOfClass);
                request.getRequestDispatcher("successApplyJob.jsp").forward(request, response);
                
            } else {
                System.out.println("No updates made, sections may no longer be available for assignment.");
                // Handle response for no available sections
            }

        } catch (SQLException e) {
            e.printStackTrace();
            String errorMessage = "SQL Error: " + e.getMessage();
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("applyJob.jsp").forward(request, response);
            return;
        }
    } else {
        String errorMessage = "Must Select a Course and Type of Class. Please try again.";
        request.getSession().setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("applyJob.jsp").forward(request, response);
        return;
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

//          String name = (String) request.getSession().getAttribute("uname");
//        String course = request.getParameter("course");
//        String typeOfClass = request.getParameter("typeofclass");
//        if(!course.equals("not_selected"))
//            {
//                if(typeOfClass != null)
//                {
////                        String sql = "UPDATE coursesql.course_info SET INSTRUCTOR = ? WHERE CLASSSECTION='JS1' INSTRUCTOR = 'to be assigned' ";
//                        String sql = "UPDATE coursesql.course_info SET INSTRUCTOR = ? WHERE CLASSSECTION = 'JS1' AND INSTRUCTOR = 'to be assigned'";
//                        try (Connection connection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);
//                             PreparedStatement statement = connection.prepareStatement(sql)) 
//                        {
//                            statement.setString(1, name);
//
//                            int rowsInserted = statement.executeUpdate();
//                            if (rowsInserted > 0) {
//                                System.out.println("A new row has been inserted.");
//                                // Successful insertion logic here
//                            }
//                            request.setAttribute("name", name);
//                            RequestDispatcher dispatcher = request.getRequestDispatcher("successApplyJob.jsp");
//                            dispatcher.forward(request, response);
//
//
//                        }
//                        catch (SQLException e) {
//                            e.printStackTrace();
//                            String errorMessage = "SQL Error: " + e.getMessage();
//                            request.getSession().setAttribute("errorMessage", errorMessage);
//                            request.getRequestDispatcher("applyJob.jsp").forward(request, response);
//                        }
//                    
//                    
//                    
//                    request.setAttribute("name", name);
//                    request.setAttribute("course", course);
//                    request.setAttribute("typeOfClass", typeOfClass);
//                    RequestDispatcher dispatcher = request.getRequestDispatcher("successApplyJob.jsp");
//                    dispatcher.forward(request, response);
//                }
//                else
//                {
//                    String errorMessage = "Must Select a Type of Class. Please try again.";
//                    request.getSession().setAttribute("errorMessage", errorMessage);
//                    request.getRequestDispatcher("applyJob.jsp").forward(request, response);
//                }
//
//
//            }
//        else
//        {
//            String errorMessage = "Must Select a Course. Please try again.";
//            request.getSession().setAttribute("errorMessage", errorMessage);
//            request.getRequestDispatcher("applyJob.jsp").forward(request, response);
//        }
