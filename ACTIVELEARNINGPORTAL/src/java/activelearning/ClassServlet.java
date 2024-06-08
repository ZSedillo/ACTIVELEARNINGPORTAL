/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package activelearning;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ClassServlet extends HttpServlet {
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
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String courseName = request.getParameter("courseName");
         String typeOfClass = request.getParameter("typeOfClass");
        if (courseName != null) {
            switch (courseName) {
                case "Python":
                    response.sendRedirect("PythonClass.jsp?typeOfClass=" + typeOfClass);
                    break;
                case "Java":
                    response.sendRedirect("JavaClass.jsp?typeOfClass=" + typeOfClass);
                    break;
                case "WebDev":
                    response.sendRedirect("WebdevClass.jsp?typeOfClass=" + typeOfClass);
                    break;
                default:
                    response.getWriter().println("Course not found");
            }
        } else {
            response.getWriter().println("Please provide a course name");
        }
    }
}
