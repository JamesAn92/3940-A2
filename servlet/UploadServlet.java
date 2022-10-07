package servlet;

import router.HttpRequest;
import router.HttpResponse;
import java.io.*;
import java.time.Clock;

public class UploadServlet implements HttpServlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            // TODO Auto-generated method stub
            System.out.println("Upload doGET");
            ByteArrayOutputStream writer = response.getOutputStream();
            writer.write("<!DOCTYPE html>\r\n".getBytes());
            writer.write("<html>\r\n".getBytes());
            writer.write("    <head>\r\n".getBytes());
            writer.write("        <title>File Upload Form</title>\r\n".getBytes());
            writer.write("    </head>\r\n".getBytes());
            writer.write("    <body>\r\n".getBytes());
            writer.write("<h1>Upload file</h1>\r\n".getBytes());
            writer.write("<form method=\"POST\" action=\"upload\" ".getBytes());
            writer.write("enctype=\"multipart/form-data\">\r\n".getBytes());
            writer.write("<input type=\"file\" name=\"fileName\"/><br/><br/>\r\n".getBytes());
            writer.write("Caption: <input type=\"text\" name=\"caption\"<br/><br/>\r\n".getBytes());
            writer.write("<br />\n".getBytes());
            writer.write("Date: <input type=\"date\" name=\"date\"<br/><br/>\r\n".getBytes());
            writer.write("<br />\n".getBytes());
            writer.write("<input type=\"submit\" value=\"Submit\"/>\r\n".getBytes());
            writer.write("</form>\r\n".getBytes());
            writer.write("</body>\r\n".getBytes());
            writer.write("</html>\r\n".getBytes());
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

        // Get image from request

        // Upload image to DB

        // if error send back API error

        // if Browser send back 200 with empty body

        // if client send back 200 with list of images in db for user
    }
}