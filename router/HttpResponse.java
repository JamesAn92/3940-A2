package router;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

public class HttpResponse {
    private final String HTTP_VERSION = "HTTP/1.1";
    private String status = "200";
    private ByteArrayOutputStream privateStream;
    private ByteArrayOutputStream publicStream = new ByteArrayOutputStream();
    private HashMap<String, Object> headers = new HashMap<>();

    public HttpResponse(ByteArrayOutputStream outputStream) {
        System.out.println(outputStream);
        System.out.println("HttpResponse");
        this.privateStream = outputStream;
    }

    public ByteArrayOutputStream getOutputStream() {
        return publicStream;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getHeader(String headerKey) {
        return headers.get(headerKey);
    }

    public void putHeader(String key, Object value) {
        headers.put(key, value);
    }

    public ByteArrayOutputStream newBuilder() {
        // We need a new outpust stream to append attach the body to
        // Then we use a non accesbile stream to build the head, headers
        // then append the output stream to the non accesbile stream.

        try {
            buildResponseHead();
            buildResponseHeaders();
            appendBodyToStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return privateStream;
    }

    private void appendBodyToStream() throws IOException {
        this.privateStream.write(publicStream.toByteArray());
    }

    private void buildResponseHeaders() throws IOException {
        for (Entry<String, Object> entry : headers.entrySet()) {
            String header = entry.getKey() + " " + entry.getValue().toString() + "\r\n";
            this.privateStream.write(header.getBytes());
        }
        this.privateStream.write("\r\n".getBytes());
    }

    private void buildResponseHead() throws IOException {
        privateStream.write((HTTP_VERSION + " " + status + " " + "bitch" + "\r\n").getBytes());
    }
}
