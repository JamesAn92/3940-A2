package router;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

public class HttpRequest {

    private String method;
    private String URL;
    private String version;
    private InputStream inputStream = null;
    private HashMap<String, String> keyValues = new HashMap<String, String>();

    public HttpRequest(InputStream inputStream) throws IOException {
        System.out.println("Hello I am");
        this.inputStream = inputStream;
        String wholeStream = "";
        while (inputStream.available() != 0) {
            wholeStream += (char) inputStream.read();
        }
        // Splits the stream into lines and stores into array
        String[] arrayStream = wholeStream.split("\n");
        parseMethodAndProtocol(arrayStream[0]);
        parseHeaders(arrayStream);
    }

    // Sets method, url, version from first line of inputstream
    private void parseMethodAndProtocol(String stream) {
        String[] arrayStream = stream.split(" ");
        setMethod(arrayStream[0]);
        setURL(arrayStream[1]);
        setVersion(arrayStream[2]);
    }

    // Parse header and store into Hashmap
    private void parseHeaders(String[] stream) {
        for (int i = 1; i < stream.length - 1; i++) {
            String[] tempValue = stream[i].split(": ");
            keyValues.put(tempValue[0].trim(), tempValue[1].trim());
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAttribute(String headerKey) {
        return keyValues.get(headerKey);
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
