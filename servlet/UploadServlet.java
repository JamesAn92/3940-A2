package servlet;

import router.HttpRequest;
import router.HttpResponse;
import java.io.*;
import java.time.Clock;
import exceptions.APIError;

public class UploadServlet implements HttpServlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws Exception {

        if (isCLI(request)) {
            // TODO CLI GET

        } else {
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
        }
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws Exception {
        InputStream in = request.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] content = new byte[1];
        int bytesRead = -1;
        while ((bytesRead = in.read(content)) != -1) {
            baos.write(content, 0, bytesRead);
        }
        Clock clock = Clock.systemDefaultZone();
        long milliSeconds = clock.millis();
        OutputStream outputStream = new FileOutputStream(new File(String.valueOf(milliSeconds) + ".png"));
        baos.writeTo(outputStream);
        outputStream.close();
        PrintWriter out = new PrintWriter(response.getOutputStream(), true);
        File dir = new File(".");
        String[] chld = dir.list();
        for (int i = 0; i < chld.length; i++) {
            String fileName = chld[i];
            out.println(fileName + "\n");
            System.out.println(fileName);
        }
    }

    private boolean isCLI(HttpRequest request) {
        return Boolean.parseBoolean((String) request.getAttribute("isCLI"));
    }
}