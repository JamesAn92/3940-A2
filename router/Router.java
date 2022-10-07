package router;

import servlet.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Router extends Thread {

    final String POST = "POST";
    final String GET = "GET";

    private Class<?> myClass;
    private Socket socket;

    public Router(Socket socket) {
        System.out.println("Router");
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            OutputStream baos = new ByteArrayOutputStream();
            HttpRequest request = new HttpRequest(socket.getInputStream());
            HttpResponse response = new HttpResponse(baos);

            // Reflection
            // Because we only have one URL we will only use UploadServlet
            myClass = Class.forName("servlet.UploadServlet");
            HttpServlet thread = (HttpServlet) myClass.newInstance();

            // call appropriate method
            switch (request.getMethod()) {
                case GET:
                    thread.doGet(request, response);
                    break;
                case POST:
                    thread.doPost(request, response);
                    break;
            }
            OutputStream out = socket.getOutputStream();
            out.write(((ByteArrayOutputStream) baos).toByteArray());
            socket.close();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        /*
         * Multi trycatch
         * HttpRequest request;
         * HttpResponse response;
         * OutputStream baos = new ByteArrayOutputStream();
         * try {
         * request = new HttpRequest(socket.getInputStream());
         * response = new HttpResponse(baos);
         * } catch (Exception e) {
         * System.out.println(e.getMessage());
         * return;
         * }
         * 
         * // Reflection
         * // Because we only have one URL we will only use UploadServlet
         * HttpServlet thread;
         * try {
         * myClass = Class.forName("servlet.UploadServlet");
         * thread = (HttpServlet) myClass.newInstance();
         * // call appropriate method
         * switch (request.getMethod()) {
         * case GET:
         * thread.doGet(request, response);
         * break;
         * case POST:
         * thread.doPost(request, response);
         * break;
         * }
         * OutputStream out = socket.getOutputStream();
         * out.write(((ByteArrayOutputStream) baos).toByteArray());
         * // socket.close();
         * } catch (ClassNotFoundException | InstantiationException |
         * IllegalAccessException | IOException e) {
         * // TODO Auto-generated catch block
         * e.printStackTrace();
         * return;
         * }
         */

    }

}