/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
//package activelearning;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// *
// * @author ASUS
// */
//public class PrintUsersDBServlet extends HttpServlet {
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try ( PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet PrintUsersDBServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet PrintUsersDBServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package activelearning;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.management.openmbean.InvalidKeyException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author ivanmendoza
 */
@WebServlet("/UserReport")
public class PrintUsersDBServlet extends HttpServlet {
   
    private String jdbcUrl;
    private String mysqlUrl;
    private String dbUsername;
    private String dbPassword;
    private String key;
    private String currentLoggedInUser;
    
   @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        jdbcUrl = config.getInitParameter("jdbcUrl");
        dbUsername = config.getInitParameter("dbUsername");
        dbPassword = config.getInitParameter("dbPassword");
        key = config.getInitParameter("cipherTransformation");
        mysqlUrl = config.getInitParameter("mysqlUrl");
        
        getServletContext().setAttribute("jdbcUrl", jdbcUrl);
        getServletContext().setAttribute("dbUsername", dbUsername);
        getServletContext().setAttribute("dbPassword", dbPassword);
        getServletContext().setAttribute("cipherTransformation", key);
        getServletContext().setAttribute("mysqlUrl", mysqlUrl);
        
       
         System.out.println("PRINT INIT");
         
          System.out.println("PRINT INIT"+jdbcUrl);
    }
     

    class HeaderFooterPageEvent extends PdfPageEventHelper {
    private final Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.ITALIC);
    private final String loggedInUser;
    private final Font headerFont = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLDITALIC);
    private final String reportTitle;

    HeaderFooterPageEvent(String loggedInUser, String reportTitle) {
        this.loggedInUser = loggedInUser;
        this.reportTitle = reportTitle;
        currentLoggedInUser = loggedInUser;
        System.out.println("current loggin in user is"+loggedInUser);
    }
     
    
   
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContent(),
            Element.ALIGN_CENTER, new Phrase(reportTitle, headerFont),
            document.getPageSize().getWidth() / 2, document.getPageSize().getHeight() - 60, 0);
        try {
            document.add(new Paragraph(" "));
            Paragraph spacer = new Paragraph(new Chunk(new VerticalPositionMark()));
            spacer.setSpacingAfter(10);
            document.add(spacer);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        int currentPage = writer.getPageNumber();
        PdfContentByte cb = writer.getDirectContent();
        Phrase footerLeft = new Phrase(loggedInUser, footerFont);

        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footerLeft,
            document.leftMargin(), document.bottom() - 15, 0);
    }
}
 
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
   
    String loggedInUser = (session != null && session.getAttribute("uname") != null) ? (String) session.getAttribute("uname") : "Unknown User";
    String role = (session != null && session.getAttribute("role") != null) ? (String) session.getAttribute("role") : "none";

    boolean isAdmin = "admin".equalsIgnoreCase(role);
    boolean isIns = "instructor".equalsIgnoreCase(role);

    
    Document document;
    if (isAdmin) {
        document = new Document(PageSize.A4.rotate());
    } else if (isIns) {
        document = new Document(PageSize.A4.rotate());
        System.out.println("Its the instructor");
    }else {
        document = new Document(PageSize.A4.rotate());
    }

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = null;

    try {
        writer = PdfWriter.getInstance(document, baos);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent(loggedInUser, "Active Learning User List");
        writer.setPageEvent(event);
        document.open();

       // PdfPTable table = isAdmin ? createAdminTable(jdbcUrl) : isIns ? createInstructorTable(mysqlUrl) : new PdfPTable(1);
         PdfPTable table = createAdminTable(loggedInUser);
         
        document.add(table);
        document.close();
        writer.close();
        
        File tempFile = File.createTempFile("tempPDF", ".pdf");
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(baos.toByteArray());
        fos.close();

        updateFooters(tempFile.getAbsolutePath(), response);
        
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        OutputStream outputStream = response.getOutputStream();
        baos.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();

    } catch (DocumentException e) {
        Logger.getLogger(PrintUsersDBServlet.class.getName()).log(Level.SEVERE, "Document generation failed", e);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error in generating document");
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(PrintUsersDBServlet.class.getName()).log(Level.SEVERE, "Database access failed", ex);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database access issue");
    }  
    
}
public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
    }

private PdfPTable createAdminTable( String currentUser) throws ClassNotFoundException, SQLException, DocumentException {
        
       
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        float[] columnWidths = new float[]{3, 1};
        table.setWidths(columnWidths);
        PdfPCell cellUsername = new PdfPCell(new Phrase("Username", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLDITALIC)));
        PdfPCell cellRole = new PdfPCell(new Phrase("Role", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLDITALIC)));

        cellUsername.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellRole.setHorizontalAlignment(Element.ALIGN_CENTER);

        cellUsername.setBorder(Rectangle.BOX);
        cellRole.setBorder(Rectangle.BOX);

        table.addCell(cellUsername);
        table.addCell(cellRole);
        
        System.out.println("THIS IS THE URL"+jdbcUrl);

        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
        Statement statement = connection.createStatement();
   
        ResultSet resultSet = statement.executeQuery("SELECT USERNAME, ROLE FROM USER_INFO"); 
    
        while (resultSet.next()) {
            //String userNameDB = resultSet.getString("username");
            String userNameDB = resultSet.getString("username");
            
            
            if(currentUser.equals(userNameDB))
            {
                userNameDB = userNameDB + "*";
            }
            
            
            PdfPCell usernameCell = new PdfPCell(new Phrase(userNameDB));
            usernameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            usernameCell.setBorder(Rectangle.BOX);
            table.addCell(usernameCell);

            String roleDB = resultSet.getString("role");
            PdfPCell roleCell = new PdfPCell(new Phrase(roleDB));
            roleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            usernameCell.setBorder(Rectangle.BOX);
            table.addCell(roleCell);
       
        }


        return table;
    }

    private void updateFooters(String originalPdf, HttpServletResponse response) throws IOException, DocumentException {
    PdfReader reader = new PdfReader(originalPdf);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfStamper stamper = new PdfStamper(reader, baos);
    int total = reader.getNumberOfPages();
    Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.ITALIC);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm a");
    String currentTime = dtf.format(LocalDateTime.now());

    for (int i = 1; i <= total; i++) {
        PdfContentByte content = stamper.getOverContent(i);
        Phrase updatedFooter = new Phrase(String.format("%s, Page %d of %d", currentTime, i, total), footerFont);
        Rectangle pageSize = reader.getPageSizeWithRotation(i);
        ColumnText.showTextAligned(content, Element.ALIGN_RIGHT, updatedFooter,
                pageSize.getRight() - 36, pageSize.getBottom() + 21, 0);
    }

    stamper.close();
    reader.close();


    response.setContentType("application/pdf");
    String filename = "ActiveLearningUsersList" + getCurrentTime() + ".pdf";
    response.setHeader("Content-Disposition", "attachment; filename=" + filename);
    response.setContentLength(baos.size());
    baos.writeTo(response.getOutputStream());
    response.getOutputStream().flush();
}
}
