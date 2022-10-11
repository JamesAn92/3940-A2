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
            // System.out.println("Upload doGET");
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
        writeImage(request);

        // Upload image to DB

        // if error send back API error

        // if Browser send back 200 with empty body
        System.out.println(request.getValue("User-Agent"));

        try {
            ByteArrayOutputStream writer = response.getOutputStream();
            response.setStatus("200");
            writer.write("status 200!".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // if client send back 200 with list of images in db for user
    }

    private void writeImage(HttpRequest request) {
        // Get image from request
        try {
            String dir = ".\\images\\";
            // String dir = "./";
            String fileName = request.getFileName("fileName");

            System.out.println("Reading image");
            OutputStream output = new FileOutputStream(new File(dir + fileName));

            String file = request.getFile(fileName);
            InputStream input = new ByteArrayInputStream(file.getBytes());
            System.out.println("Going to read now...");

            while (input.available() != 0) {
                output.write(input.read());
            }

            System.out.println("read and wrote image");

            input.close();
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}