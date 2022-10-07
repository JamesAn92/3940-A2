package router;

import servlet.*;
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
        HttpRequest request;
        HttpResponse response;
        try {
            request = new HttpRequest(socket.getInputStream());
            response = new HttpResponse(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        // Reflection
        // Because we only have one URL we will only use UploadServlet
        HttpServlet thread;
        try {
            myClass = Class.forName("Servlets.UploadServlet");
            thread = (HttpServlet) myClass.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        // call appropriate method
        switch (request.getMethod()) {
            case GET:
                thread.doGet(request, response);
            case POST:
                thread.doPost(request, response);
        }
    }

}