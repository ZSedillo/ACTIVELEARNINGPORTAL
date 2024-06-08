/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package activelearning;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.management.openmbean.InvalidKeyException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
public class PrintOwnRecordServlet extends HttpServlet {
 private String jdbcUrl;
    private String mysqlUrl;
    private String dbUsername;
    private String dbPassword;
    private String key;
    
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
        String studentName="";
        String instructor="" ;
        String courseName="" ;
        String typeOfClass="";
        String classSection="";
        String startingDate=""; 
    try {
        writer = PdfWriter.getInstance(document, baos);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent(loggedInUser, loggedInUser+ " Record");
        writer.setPageEvent(event);
        document.open();
        System.out.println("THIS IS THE URL SQL"+mysqlUrl);
    
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CourseSQL?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false", "root", "1234");
        if(role.equals("student"))
        {
            String q = "SELECT STUDENTNAME,INSTRUCTOR,COURSE_NAME,TYPEOFCLASS,CLASSSECTION,STARTINGDATE FROM course_info WHERE STUDENTNAME ='"+loggedInUser + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(q); 


            System.out.println("THIS IS THE URL SQL"+mysqlUrl);



            while (resultSet.next()) 
            {
                studentName = resultSet.getString("STUDENTNAME");
                instructor = resultSet.getString("INSTRUCTOR");
                courseName = resultSet.getString("COURSE_NAME");
                typeOfClass = resultSet.getString("TYPEOFCLASS");
                classSection = resultSet.getString("CLASSSECTION");
                startingDate = resultSet.getString("STARTINGDATE");
            }


                document.add(new Paragraph("Your Username: " + studentName));
                document.add(new Paragraph("Your Role: " + role));
                document.add(new Paragraph("Instructor: " + instructor));
                document.add(new Paragraph("Course Name: " + courseName));
                document.add(new Paragraph("Type of Class: " + typeOfClass));
                document.add(new Paragraph("Class Section: " + classSection));
                document.add(new Paragraph("Starting Date: " + startingDate));
        }
        else if(role.equals("instructor"))
        {
            String q = "SELECT STUDENTNAME,INSTRUCTOR,COURSE_NAME,TYPEOFCLASS,CLASSSECTION,STARTINGDATE FROM course_info WHERE INSTRUCTOR ='"+loggedInUser + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(q); 


            System.out.println("THIS IS THE URL SQL"+mysqlUrl);



            while (resultSet.next()) 
            {
                studentName = resultSet.getString("STUDENTNAME");
                instructor = resultSet.getString("INSTRUCTOR");
                courseName = resultSet.getString("COURSE_NAME");
                typeOfClass = resultSet.getString("TYPEOFCLASS");
                classSection = resultSet.getString("CLASSSECTION");
                startingDate = resultSet.getString("STARTINGDATE");
            }
            document.add(new Paragraph("Your Username: " + instructor));
            document.add(new Paragraph("Your Role: " + role));
            document.add(new Paragraph("Course Name: " + courseName));
            document.add(new Paragraph("Type of Class: " + typeOfClass));
            document.add(new Paragraph("Class Section: " + classSection));
            document.add(new Paragraph("Starting Date: " + startingDate));
        }
        else if(role.equals("admin"))
        {
            document.add(new Paragraph("Your Username: " + loggedInUser));
            document.add(new Paragraph("Your Role: " + role));
        }
        
        
        document.close();
        writer.close();
        
        File tempFile = File.createTempFile("tempPDF", ".pdf");
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(baos.toByteArray());
        fos.close();

        updateFooters(tempFile.getAbsolutePath(),loggedInUser, response);
        
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        OutputStream outputStream = response.getOutputStream();
        baos.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();


    } catch (DocumentException e) {
        Logger.getLogger(PrintCourseListDBServlet.class.getName()).log(Level.SEVERE, "Document generation failed", e);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error in generating document");
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(PrintCourseListDBServlet.class.getName()).log(Level.SEVERE, "Database access failed", ex);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database access issue");
    } catch (InvalidKeyException ex) {
            Logger.getLogger(PrintCourseListDBServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PrintCourseListDBServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        if (document.isOpen()) {
            document.close();
        }
    }
}
public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
    }

private PdfPTable createInstructorTable(String mysqlUrl) throws ClassNotFoundException, SQLException, DocumentException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, Exception {
    LoginServlet security = new LoginServlet();
           
    
    PdfPTable table = new PdfPTable(6);
    table.setWidthPercentage(100);
    float[] columnWidths = new float[]{3, 2,2,2,2,2};
    table.setWidths(columnWidths);
    

    PdfPCell studName = new PdfPCell(new Phrase("Student Name", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC)));
    PdfPCell cellinstructor = new PdfPCell(new Phrase("Instructor", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC)));
    PdfPCell cellcoursename = new PdfPCell(new Phrase("Course Name", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC)));
    PdfPCell typeOfClass = new PdfPCell(new Phrase("Type of Class", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC)));
    PdfPCell classSection = new PdfPCell(new Phrase("Section", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC)));
    PdfPCell srtDate = new PdfPCell(new Phrase("Start Date", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC)));
    
    studName.setHorizontalAlignment(Element.ALIGN_CENTER);
    cellinstructor.setHorizontalAlignment(Element.ALIGN_CENTER);
    cellcoursename.setHorizontalAlignment(Element.ALIGN_CENTER);
    typeOfClass.setHorizontalAlignment(Element.ALIGN_CENTER);
    classSection.setHorizontalAlignment(Element.ALIGN_CENTER);
    srtDate.setHorizontalAlignment(Element.ALIGN_CENTER);

    studName.setBorder(Rectangle.BOX);
    cellinstructor.setBorder(Rectangle.BOX);
    cellcoursename.setBorder(Rectangle.BOX);
    typeOfClass.setBorder(Rectangle.BOX);
    classSection.setBorder(Rectangle.BOX);
    srtDate.setBorder(Rectangle.BOX);
    
    table.addCell(studName);
    table.addCell(cellinstructor);
    table.addCell(cellcoursename);
    table.addCell(typeOfClass);  
    table.addCell(classSection); 
    table.addCell(srtDate);  
    
    System.out.println("THIS IS THE URL SQL"+mysqlUrl);
    
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CourseSQL?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false", "root", "1234");
    
    String q = "SELECT STUDENTNAME,INSTRUCTOR,COURSE_NAME,TYPEOFCLASS,CLASSSECTION,STARTINGDATE FROM course_info";
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(q); 
  
    
    System.out.println("THIS IS THE URL SQL"+mysqlUrl);
    
    while (resultSet.next()) {
            String studNameDB = resultSet.getString("STUDENTNAME");
            PdfPCell studNameCell = new PdfPCell(new Phrase( studNameDB));
            studNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            studNameCell.setBorder(Rectangle.BOX);
            table.addCell(studNameDB);
            
            String instructorDB = resultSet.getString("INSTRUCTOR");
            PdfPCell usernameCell = new PdfPCell(new Phrase( instructorDB));
            usernameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            usernameCell.setBorder(Rectangle.BOX);
            table.addCell(instructorDB);

            String course_nameDB = resultSet.getString("COURSE_NAME");
            PdfPCell roleCell = new PdfPCell(new Phrase(course_nameDB));
            roleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            usernameCell.setBorder(Rectangle.BOX);
            table.addCell(course_nameDB);
            
            String typeOfClassDB = resultSet.getString("TYPEOFCLASS");
            PdfPCell typeOfClassCell = new PdfPCell(new Phrase( typeOfClassDB));
            typeOfClassCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            typeOfClassCell.setBorder(Rectangle.BOX);
            table.addCell(studNameDB);
            
            String classSectionDB = resultSet.getString("CLASSSECTION");
            PdfPCell classSectionCell = new PdfPCell(new Phrase( classSectionDB));
            classSectionCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            classSectionCell.setBorder(Rectangle.BOX);
            table.addCell(classSectionDB);
            
            String srtDateDB = resultSet.getString("STARTINGDATE");
            PdfPCell srtDateCell = new PdfPCell(new Phrase( srtDateDB));
            srtDateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            srtDateCell.setBorder(Rectangle.BOX);
            table.addCell(srtDateDB);
            
       
   
    }
    return table;
}
/*private PdfPTable createAdminTable(String jdbcUrl) throws ClassNotFoundException, SQLException, DocumentException {
        
       
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
        Connection connection = DriverManager.getConnection(jdbcUrl, "app", "app");
        //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CourseSQL?zeroDateTimeBehavior=CONVERT_TO_NULL", "root", "12345678");
        Statement statement = connection.createStatement();
   
        ResultSet resultSet = statement.executeQuery("SELECT USERNAME, ROLE FROM USER_INFO"); 
    
        
        while (resultSet.next()) {
            
            
            String userNameDB = resultSet.getString("username");
            PdfPCell usernameCell = new PdfPCell(new Phrase( userNameDB));
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
    }*/

    private void updateFooters(String originalPdf,String loggedInUser, HttpServletResponse response) throws IOException, DocumentException {
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
    String filename = loggedInUser+"_" + getCurrentTime() + ".pdf";
    response.setHeader("Content-Disposition", "attachment; filename=" + filename);
    response.setContentLength(baos.size());
    baos.writeTo(response.getOutputStream());
    response.getOutputStream().flush();
}
}
