/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package activelearning;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;


/*
MGA KULANG NA JSP FOR ERROR
error_1.jsp
error_2.jsp
error_3.jsp
error_4.jsp
error_5.jsp
error_6.jsp
error_path.jsp
error_session.jsp
noLoginCredentials.jsp
*/


public class LoginServlet extends HttpServlet{
    private String jdbcUrl;
    private String dbUsername;
    private String dbPassword;
    
    private String key;
    private int increment = 0;
    
    private static byte[] encryptionKey = {
        0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 
        0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79
    };
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        jdbcUrl = config.getInitParameter("jdbcUrl");
        dbUsername = config.getInitParameter("dbUsername");
        dbPassword = config.getInitParameter("dbPassword");
        key = config.getInitParameter("cipherTransformation");
        
        getServletContext().setAttribute("jdbcUrl", jdbcUrl);
        getServletContext().setAttribute("dbUsername", dbUsername);
        getServletContext().setAttribute("dbPassword", dbPassword);
        getServletContext().setAttribute("cipherTransformation", key);
        encryptPasswords(jdbcUrl, dbUsername, dbPassword, key);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //Prevent Double encryption
        if(increment == 1)
        {
            encryptPasswords(jdbcUrl, dbUsername, dbPassword, key);
            increment--;
        }
        increment++;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        request.getSession().setAttribute("usernamePrev", username);
        request.getSession().setAttribute("passwordPrev", password);
        
        HttpSession session = request.getSession(false);
        
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) 
        {
            if (username.isEmpty() && password.isEmpty()) 
            {
                System.out.println("I AM HERE 1");
                decryptPasswords(jdbcUrl, dbUsername, dbPassword, key);
                throw new NullPointerException("No login credentials provided");
                
                
                
            }
            else if (username.isEmpty() && !password.isEmpty())
            {
                request.getSession().setAttribute("password", password);
                System.out.println("I AM HERE 2");
                decryptPasswords(jdbcUrl, dbUsername, dbPassword, key);
                String errorMessage = "Username is required.";
                request.getSession().setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("loginPage.jsp").forward(request, response);
            }
            else if (!username.isEmpty() && password.isEmpty()) 
            {
                String sql = "SELECT * FROM USER_INFO WHERE username = ?";

                try (PreparedStatement statement = conn.prepareStatement(sql)) 
                {
                    statement.setString(1, username);

                    try (ResultSet resultSet = statement.executeQuery()) 
                    {
                        if (resultSet.next()) 
                        {
                            // Username exists, but password is blank
                            request.getSession().setAttribute("username", username);
                            System.out.println("I AM HERE 3");
                            decryptPasswords(jdbcUrl, dbUsername, dbPassword, key);
                            String errorMessage = "Password is required.";
                            request.getSession().setAttribute("errorMessage", errorMessage);
                            request.getRequestDispatcher("loginPage.jsp").forward(request, response);
                        } 
                        else 
                        {
                            // Username does not exist
                            request.getSession().setAttribute("username", username);
                            System.out.println("I AM HERE 4");
                            decryptPasswords(jdbcUrl, dbUsername, dbPassword, key);
                            String errorMessage = "Username does not exist and the Password is blank.";
                            request.getSession().setAttribute("errorMessage", errorMessage);
                            request.getRequestDispatcher("loginPage.jsp").forward(request, response);
                        }
                    }
                }
            } 
            else if (!username.isEmpty() && !password.isEmpty()) 
            {                
                String sql = "SELECT * FROM USER_INFO WHERE username = ?";

                String userNameDB, userPasswordDB;
                try (PreparedStatement statement = conn.prepareStatement(sql)) 
                {
                    statement.setString(1, username);

                    try (ResultSet resultSet = statement.executeQuery()) 
                    {
                        // Username provided
                        if (resultSet.next()) 
                        {
                            userNameDB = resultSet.getString("username");
                            userPasswordDB = decrypt(resultSet.getString("password"),key); //Decrypt specific password and login
                            if (userPasswordDB.equals(password)) 
                            {
                                    request.getSession().setAttribute("username", username);
                                    request.getSession().setAttribute("role", resultSet.getString("role"));
                                    decryptPasswords(jdbcUrl, dbUsername, dbPassword, key);
                                    
                                    String userRole = (String) request.getSession().getAttribute("role");
                                    System.out.println("Current User: " + userRole);
                                    String errorMessage = "null";
                                    request.getSession().setAttribute("errorMessage", errorMessage);
                                   
                                    if(userRole.equals("instructor"))
                                    {
//                                        request.getSession().setAttribute("currentUser", username); 
                                        request.getSession().setAttribute("jdbcUrl", jdbcUrl);
                                        request.getSession().setAttribute("dbUsername", dbUsername);
                                        request.getSession().setAttribute("dbPassword", dbPassword);
                                        session.setAttribute("uname", username);
                                        session.setAttribute("role", userRole);
                                        session.setMaxInactiveInterval(5 * 60); // 5 minutes
                                        response.sendRedirect("CaptchaServlet"); 
                                    }
                                    else if(userRole.equals("student"))
                                    {
//                                        request.getSession().setAttribute("currentUser", username"); //For display ng currentUsername
                                        request.getSession().setAttribute("jdbcUrl", jdbcUrl);
                                        request.getSession().setAttribute("dbUsername", dbUsername);
                                        request.getSession().setAttribute("dbPassword", dbPassword);
                                        session.setAttribute("uname", username);
                                        session.setAttribute("role", userRole);
                                        session.setMaxInactiveInterval(5 * 60); // 5 minutes
                                        response.sendRedirect("CaptchaServlet"); 
                                    } 
                                    else if(userRole.equals("admin"))
                                    {
                                        request.getSession().setAttribute("jdbcUrl", jdbcUrl);
                                        request.getSession().setAttribute("dbUsername", dbUsername);
                                        request.getSession().setAttribute("dbPassword", dbPassword);
                                        session.setAttribute("uname", username);
                                        session.setAttribute("role", userRole);
                                        session.setMaxInactiveInterval(5 * 60); // 5 minutes
                                        response.sendRedirect("CaptchaServlet"); 
                                    }
                            } 
                            else 
                            {
                                // Incorrect password
                                request.getSession().setAttribute("username", username);
                                System.out.println("I AM HERE 5");
                                decryptPasswords(jdbcUrl, dbUsername, dbPassword, key);
                                String errorMessage = "Incorrect Password. Please login again.";
                                request.getSession().setAttribute("errorMessage", errorMessage);
                                request.getRequestDispatcher("loginPage.jsp").forward(request, response);
                            }
                        } 
                        else 
                        {
                            // Username does not exist
                            request.getSession().setAttribute("username", username);
                            request.getSession().setAttribute("password", password);
                            System.out.println("I AM HERE 6");
                            decryptPasswords(jdbcUrl, dbUsername, dbPassword, key);
                            String errorMessage = "Username does not exist.";
                            request.getSession().setAttribute("errorMessage", errorMessage);
                            request.getRequestDispatcher("loginPage.jsp").forward(request, response);
                        }
                    }
                }
            }
        } 
        catch (SQLException e) 
        {    
            System.out.println("I AM HERE 7");
            response.sendRedirect("error_4.jsp");
        } 
        catch (NullPointerException ex) {
            // No login credentials provided
            request.getSession().setAttribute("username","hi");
            request.getSession().setAttribute("password", "and no"); 
            System.out.println("I AM HERE 8");
//            response.sendRedirect("noLoginCredentials.jsp");
            String errorMessage = "Please put your login credentials below.";
            request.getSession().setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("loginPage.jsp").forward(request, response);
        } 
    }
    
    //TO BE CHECKED
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Incorrect context path
            String requestURI = request.getRequestURI();
            if (requestURI.equals(request.getContextPath() + "/")) {
                // Request is for the root URL
                response.sendRedirect("error_4.jsp");
            } 
            else 
            {
                // Incorrect context path
                response.sendRedirect("error_4.jsp");
            }
    }
    
    public void encryptPasswords(String jdbcUrl, String dbUsername, String dbPassword,String cipherTransformation) throws NullPointerException{
            try {
            Connection con = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String query = "SELECT password FROM USER_INFO";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            

            while (rs.next()) 
            {
                String password = rs.getString("password");
                String setPassword = encrypt(password,cipherTransformation); //Culprit
                
                String updateQuery = "UPDATE USER_INFO SET password = ? WHERE password = ?";
                PreparedStatement updatePs = con.prepareStatement(updateQuery);
                updatePs.setString(1, setPassword);
                updatePs.setString(2, password);
                updatePs.executeUpdate();
                updatePs.close();
                
            }
            rs.close();
            ps.close();
            con.close();
            System.out.println("Encryption Successful");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
        public void decryptPasswords(String jdbcUrl, String dbUsername, String dbPassword, String cipherTransformation) throws NullPointerException {
        try {
            Connection con = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String query = "SELECT password FROM USER_INFO";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String encryptedPassword = rs.getString("password");
                String decryptedPassword = decrypt(encryptedPassword, cipherTransformation);

                // Update the password with decrypted value
                String updateQuery = "UPDATE USER_INFO SET password = ? WHERE password = ?";
                PreparedStatement updatePs = con.prepareStatement(updateQuery);
                updatePs.setString(1, decryptedPassword);
                updatePs.setString(2, encryptedPassword);
                updatePs.executeUpdate();
                updatePs.close();
                System.out.println("Encrypted Password: " + encryptedPassword + " Decrypted Version: " + decryptedPassword); //For Checking
            }
            rs.close();
            ps.close();
            con.close();
            System.out.println("Decryption Successful");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }    
        
            public String encrypt(String strToEncrypt, String cipherTransformation) {
        String encryptedString = null;
        try {
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            final SecretKeySpec secretKey = new SecretKeySpec(encryptionKey, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return encryptedString;
    }
    
    public String decrypt(String codeDecrypt, String cipherTransformation) {
        String decryptedString = null;
        try {
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            final SecretKeySpec secretKey = new SecretKeySpec(encryptionKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedString = new String(cipher.doFinal(Base64.decodeBase64(codeDecrypt)));
        } catch (Exception e) 
        {
            System.err.println(e.getMessage());
        }
        return decryptedString;
    }
    
    
}