package router;

import java.io.OutputStream;

public class HttpResponse {
    private OutputStream outputStream = null;

    public HttpResponse(OutputStream outputStream) {
        // System.out.println(outputStream);
        // System.out.println("HttpResponse");
        this.outputStream = outputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }
}
