package router;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequest {
    private InputStream inputStream = null;

    public HttpRequest(InputStream inputStream) throws IOException {
        System.out.println("Hello I am");
        while (inputStream.available() != 0) {
            System.out.print((char) inputStream.read());
        }

        // System.out.println("HttpRequest");
        this.inputStream = inputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}

// GET / HTTP/1.1
// HOST:localhost:8999
// Connection: close
// User-agent: Mozzilla/4.0
// Accept Language: fr

// public static String parse(String responseBody) {
// JSONArray albums = new JSONArray(responseBody);
// for (int i = 0 ; i < albums.length(); i++) {
// JSONObject requestJSON = albums.getJSONObject(i);

// // int userId = album.getInt("userId");
// // int id = album.getInt("id");
// // String title = album.getString("title");
// System.out.println(id+" "+title+" "+userId);
// }
// return null;
// }