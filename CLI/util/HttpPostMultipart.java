package CLI.util;

import java.io.*;
import java.net.Socket;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class HttpPostMultipart {
    private final String HTTP_VERSION = "HTTP/1.1";
    private final String METHOD = "POST";
    private final String URL = "localhost:8999";
    private ByteArrayOutputStream privateStream;
    private ByteArrayOutputStream publicStream = new ByteArrayOutputStream();
    private Map<String, String> headers;

    private final String boundary;
    private static final String LINE = "\r\n";
    private Socket socket;
    private String charset;
    public OutputStream outputStream;
    // private PrintWriter writer;

    /**
     * This constructor initializes a new HTTP POST request with content type
     * is set to multipart/form-data
     *
     * @param requestURL
     * @param charset
     * @param headers
     * @throws IOException
     */
    public HttpPostMultipart(String requestURL, String charset, Map<String, String> headers) throws IOException {
        this.headers = headers;
        System.out.println("Httpmultip shit");
        this.charset = charset;
        boundary = UUID.randomUUID().toString();
        //URL url = new URL(requestURL);
        socket = new Socket("localhost", 8999);
        // httpConn.setUseCaches(false);
        // httpConn.setDoOutput(true);
        // httpConn.setRequestMethod("POST"); // indicates POST method
        // httpConn.setDoInput(true);
        // httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        
        outputStream = socket.getOutputStream();

        // writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
        buildResponseHead();
        buildResponseHeaders();

        // if (headers != null && headers.size() > 0) {
        //     Iterator<String> it = headers.keySet().iterator();
        //     while (it.hasNext()) {
        //         String key = it.next();
        //         String value = headers.get(key);
        //         // outputStream.write(key, value);
        //     }
        // }
    }

    /**
     * Adds a form field to the request
     *
     * @param name  field name
     * @param value field value
     */
    public void addFormField(String name, String value) {
        try{
        outputStream.write(("--" + boundary+ LINE).getBytes());
        outputStream.write(("Content-Disposition: form-data; name=\"" + name + "\"" + LINE).getBytes());
        outputStream.write(("Content-Type: text/plain; charset=" + charset + LINE).getBytes());
        outputStream.write(LINE.getBytes());
        outputStream.write((value+LINE).getBytes());
        outputStream.flush();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a upload file section to the request
     *
     * @param fieldName
     * @param uploadFile
     * @throws IOException
     */
    public void addFilePart(String fieldName, File uploadFile) throws IOException {
        try {
            System.out.println(uploadFile.toString());
            String fileName = uploadFile.getName();
            outputStream.write(("--" + boundary +LINE).getBytes());
            outputStream.write(("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\""+LINE).getBytes());
            outputStream.write(("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)+LINE).getBytes());
            outputStream.write(("Content-Transfer-Encoding: binary"+LINE).getBytes());
            outputStream.write(LINE.getBytes());
            outputStream.flush();

            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                System.out.println(buffer);
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            outputStream.write(LINE.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Completes the request and receives response from the server.
     *
     * @return String as response in case the server returned
     *         status OK, otherwise an exception is thrown.
     * @throws IOException
     */
    public String finish() throws IOException {
        System.out.println("finish");
        String response = "";
        outputStream.flush();
        System.out.println("flush");
        try{
            System.out.println("inside try of Finish");
        outputStream.write(("--" + boundary + "--"+LINE).getBytes());
        System.out.println("Close");
        outputStream.close();
        System.out.println("Response Code");
        // checks server's status code first
        // int status = httpConn.getResponseCode();
        System.out.println("After response code");
        // if (status == HttpURLConnection.HTTP_OK) {
            System.out.println("HttpOk");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            System.out.println(" Before loop");
            InputStream ips = socket.getInputStream();
            BufferedInputStream inputS = new BufferedInputStream( ips );
            // baos.write(inputS);
            // while ((length = socket.getInputStream().read(buffer)) != -1) {
            //     System.out.println("loop");
            //     baos.write(buffer, 0, length);
            // }
            response = baos.toString(this.charset);
            System.out.println("Before disconnect");
            socket.close();
        // } else {
            // throw new IOException("Server returned non-OK status: " + status);
        // }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("Return");
        return response;
    }

    private void buildResponseHeaders() throws IOException {
        for (Entry<String, String> entry : headers.entrySet()) {
            String header = entry.getKey() + " " + entry.getValue().toString() + "\r\n";
            this.outputStream.write(header.getBytes());
        }
        this.outputStream.write(("Content-Type: multipart/form-data; boundary=\""+ boundary +"\"\r\n").getBytes());
        this.outputStream.write("\r\n".getBytes());
    }

    private void buildResponseHead() throws IOException {
        outputStream.write((METHOD + " " + URL + " " + HTTP_VERSION + "\r\n").getBytes());
    }
}