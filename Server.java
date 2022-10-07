import java.net.*;
import java.io.*;

//import router.Router;

public class Server {
<<<<<<< HEAD
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8999);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 8999.");
            System.exit(-1);
        }
        while (true) {
            System.out.println("Waiting for connection.....");
            new Router(serverSocket.accept()).start();
        }

        Exception e = new IOException();
        throw e;
=======
    public static void main(String[] args) throws Exception {

        Exception e = new IOException();
        throw e;

        // ServerSocket serverSocket = null;
        // try {
        // serverSocket = new ServerSocket(8999);
        // } catch (IOException e) {
        // System.err.println("Could not listen on port: 8999.");
        // System.exit(-1);
        // }
        // while (true) {
        // System.out.println("Hello");
        // new Router(serverSocket.accept()).start();
        // }
>>>>>>> 60e9e950eeade0a3f3cbfcc4765dd8ca65dab44b
    }

}