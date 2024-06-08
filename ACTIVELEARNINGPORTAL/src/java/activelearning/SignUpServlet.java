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
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
public class SignUpServlet extends HttpServlet {

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
            out.println("<title>Servlet SignUpServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SignUpServlet at " + request.getContextPath() + "</h1>");
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
    
    private String jdbcUrl;
    private String dbUsername;
    private String dbPassword;
    
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        jdbcUrl = config.getInitParameter("jdbcUrl");
        dbUsername = config.getInitParameter("dbUsername");
        dbPassword = config.getInitParameter("dbPassword");
        
        getServletContext().setAttribute("jdbcUrl", jdbcUrl);
        getServletContext().setAttribute("dbUsername", dbUsername);
        getServletContext().setAttribute("dbPassword", dbPassword);
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
            throws ServletException, IOException 
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("conpassword");
        String role = request.getParameter("role");
        
        System.out.println("username "+username);
        System.out.println("password "+password);
        System.out.println("confrim password"+confirmPassword);
        System.out.println(role); 
        

            if(username.equals("") && password.equals("") && confirmPassword.equals(""))
            {
                String errorMessage = "Fill up the form provided";
                request.getSession().setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("signUp.jsp").forward(request, response);
            }
            else if (username.equals("") && !password.equals("") && !confirmPassword.equals(""))
            {
                String errorMessage = "Username is required.";
                request.getSession().setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("signUp.jsp").forward(request, response);
            }
            else if (!username.equals("") && password.equals("") && confirmPassword.equals(""))
            {
                String errorMessage = "Password is required.";
                request.getSession().setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("signUp.jsp").forward(request, response);
            }
            else if(!username.equals("") && !password.equals("") && !confirmPassword.equals(""))
            {
                if(matchPassword( password,confirmPassword)  == true )
                {
               
                    try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) 
                    {
                        Statement stmt = conn.createStatement();
                        String query1 = "SELECT USERNAME FROM USER_INFO ORDER BY USERNAME";
                        ResultSet rs1 = stmt.executeQuery(query1);
                        while (rs1.next()) {
                            if (rs1.getString("USERNAME").trim().equals(username.trim())) {    
                                String errorMessage = "Username already exist. Please try again..";
                                request.getSession().setAttribute("errorMessage", errorMessage);
                                request.getRequestDispatcher("signUp.jsp").forward(request, response);
                            }
                        }

                        String insertString = "INSERT INTO USER_INFO (USERNAME, PASSWORD, ROLE) VALUES (?, ?, ?)";
                        try (PreparedStatement ps = conn.prepareStatement(insertString)) {
                            ps.setString(1, username);
                            ps.setString(2, password);
                            ps.setString(3, role);
                            ps.executeUpdate();
                        }

                        // Close the connection
                        rs1.close();
                        stmt.close();
                        conn.close();
                       response.sendRedirect("successSignUp.jsp");
                    } 
                    catch (SQLException e) {    
                        String errorMessage = "Sql did not connect." ;
                        request.getSession().setAttribute("errorMessage", errorMessage);
                        request.getRequestDispatcher("signUp.jsp").forward(request, response);
                    } 
                    
                }
                else
                {
                    String errorMessage = "Password does not match.";
                    request.getSession().setAttribute("errorMessage", errorMessage);
                    request.getRequestDispatcher("signUp.jsp").forward(request, response);
                }
            } 
    }
    
    

    
    private boolean matchPassword(String password, String confirmPassword)
    {
        if(password.trim().equals(confirmPassword) && !password.equals(""))
        {
            return true;
        }
        else
        {
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
