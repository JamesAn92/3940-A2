package router;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

public class HttpRequest {

    private String HEAD_BODY_DELIM = "\r\n\r\n";
    private String method;
    private String URL;
    private String version;
    private InputStream inputStream = null;
    private HashMap<String, String> keyValues = new HashMap<>();
    private HashMap<String, String> image = new HashMap<>();
    private String boundary = null;

    public HttpRequest(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        String wholeStream = "";
        String[] head;
        String body;
        String[] seperatedRequest;
        while (inputStream.available() != 0) {
            wholeStream += (char) inputStream.read();
        }

        seperatedRequest = serperateRequest(wholeStream);
        head = seperatedRequest[0].split("\r\n");

        body = seperatedRequest[1];

        // System.out.println(wholeStream + "\r\n\r\n");

        // Splits the stream into lines and stores into array
        String[] arrayStream = wholeStream.split("\n");
        parseMethodAndProtocol(head[0]);
        parseHeaders(head);

        if (keyValues.containsKey("Content-Type") &&
                keyValues.get("Content-Type").equals("multipart/form-data")) {
            // Parse body as form data
            parseFormData(body);
            return;
        }

        // If not form data
        // parseBody();

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

            // if we reach content type obtain the boundary
            if (tempValue[0].equals("Content-Type") && tempValue[1].contains("multipart/form-data")) {
                boundary = tempValue[1].split(";")[1].split("=")[1];
                tempValue[1] = tempValue[1].split(";")[0];
            }

            // store headers in a map
            keyValues.put(tempValue[0].trim(), tempValue[1].trim());
        }
    }

    /**
     * Seperates request head and body
     * 
     * @param stream
     */
    private String[] serperateRequest(String stream) {
        String[] result = new String[2];
        for (int i = 0; i < stream.length(); i++) {
            if (stream.substring(i, i + HEAD_BODY_DELIM.length()).equals(HEAD_BODY_DELIM)) {
                result[0] = stream.substring(0, i + 2);
                result[1] = stream.substring(i + HEAD_BODY_DELIM.length());
                return result;
            }
        }
        return result;
    }

    /**
     * This will parse all form data and store it as a key value pair.
     * in the case the type is image, we will save it as a map and accesible
     * using a getImage().
     * 
     * @param stream
     * @param startingIndex
     */
    private void parseFormData(String stream) {
        System.out.println(stream);

        String[] seperatedFormData = stream.split(boundary);

        String[] temp = seperatedFormData[1].split("\r\n");
        System.out.println("Holy shit im here");

        // parse the content disposition
        String contentDisposition = temp[1];
        parseContentDisposition(contentDisposition);

        // parse th content type
        String[] contentType = temp[2].split(":");
        // image.put(contentType[0], contentType[1]);

        // we have to parse an image
        if (contentType[0] != null && contentType[1].contains("image")) {

        } else {
            // we have to parse a value
        }

        // parse the image

        // String testing = temp[5];
        // System.out.println(values[0]);
        // System.out.println(values[1]);
        // System.out.println(values[2]);
        // Arrays.toString(temp);
        // System.out.println("1");
        // System.out.println(temp[1]);

        // System.out.println("2");
        // System.out.println(temp[2]);

        // System.out.println("3");
        // System.out.println(temp[3]);

        // System.out.println("4");
        // System.out.println(temp[4]);

        // System.out.println("5");
        // System.out.println(temp[5]);

        // System.out.println("6");
        // System.out.println(temp[6]);
    }

    private void parseContentDisposition(String contentDisposition) {
        String[] contentDispositionVals = contentDisposition.split(";");
        for (int i = 1; i < contentDispositionVals.length; i++) {
            String[] keyVals = contentDispositionVals[i].split("=");
            System.out.println(keyVals[0]);
            System.out.println(keyVals[1]);
        }
    }

    private void parseFormDataElement(String currentFormData) {

    }

    private void parseBody(String[] stream, int startingIndex) {
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
