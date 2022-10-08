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
    private String boundary = null;

    public HttpRequest(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        String wholeStream = "";
        while (inputStream.available() != 0) {
            wholeStream += (char) inputStream.read();
        }
        System.out.println(wholeStream);
        // Splits the stream into lines and stores into array
        String[] arrayStream = wholeStream.split("\n");
        parseMethodAndProtocol(arrayStream[0]);
        int currentParseIndex = parseHeaders(arrayStream);

        if (keyValues.containsKey("Content-Type") && keyValues.get("Content-Type").equals("multipart/form-data")) {
            // Parse body as form data 
            parseFormData(arrayStream, currentParseIndex);
            return; 
        }

        // If not form data

    }

    // Sets method, url, version from first line of inputstream
    private void parseMethodAndProtocol(String stream) {
        String[] arrayStream = stream.split(" ");
        setMethod(arrayStream[0]);
        setURL(arrayStream[1]);
        setVersion(arrayStream[2]);
    }

    // Parse header and store into Hashmap
    private int parseHeaders(String[] stream) {
        for (int i = 1; i < stream.length - 1; i++) {
            // check if we reach the boudary between body and headers (/r/n)
            if (stream[i].equals("\r")) {
                System.out.println("fuck yes we have reached the end of the header");
                return i;
            }

            //System.out.println("this is a line: " + Arrays.toString(stream[i].getBytes()));

            String[] tempValue = stream[i].split(": ");

            // if we reach content type obtain the boundary
            if (tempValue[0].equals("Content-Type") && tempValue[1].contains("multipart/form-data")) {
                boundary = tempValue[1].split(";")[1].split("=")[1];
                System.out.println("boundary: " + boundary);
            }

            // store headers in a map
            keyValues.put(tempValue[0].trim(), tempValue[1].trim());
        }
        return -1;
    }

    private void parseFormData(String[] stream, int startingIndex) {
        
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
